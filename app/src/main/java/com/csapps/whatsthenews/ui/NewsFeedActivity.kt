package com.csapps.whatsthenews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.csapps.whatsthenews.R
import com.csapps.whatsthenews.data.entities.NewsArticle
import com.csapps.whatsthenews.databinding.ActivityNewsFeedBinding
import com.csapps.whatsthenews.ui.adapter.NewsArticleAdapter
import com.csapps.whatsthenews.util.ConnectionType
import com.csapps.whatsthenews.util.NetworkMonitorUtil
import com.csapps.whatsthenews.util.UtilFunctions.isNetworkAvailable
import com.csapps.whatsthenews.util.UtilFunctions.showLog
import com.csapps.whatsthenews.util.UtilFunctions.showToast
import com.csapps.whatsthenews.viewmodel.NewsArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_feed.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class NewsFeedActivity : AppCompatActivity() {

    /*injecting divider dependency*/
    @Inject
    lateinit var decoration: DividerItemDecoration

    @Inject
    @Named("newsRequestMap")
    lateinit var newsRequest: Map<String, String>

    private val newsArticlesViewModel by viewModels<NewsArticlesViewModel>()

    @Inject
    lateinit var networkMonitor: NetworkMonitorUtil

    private var adapter: NewsArticleAdapter? = null
    private var newsList: List<NewsArticle>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNewsFeedBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news_feed)

        /*adding recyclerview item decoration, so that its not added everytime data refreshes*/
        newsFeedRecyclerView.addItemDecoration(decoration)

        /*listening to data changes in database*/
        newsArticlesViewModel.newsList.observe(this, Observer {
            if (it.isNotEmpty()) {
                newsList = it
                binding.isDataAvailable = true
                setAdapter()
            } else {
                newsList = it
                binding.isDataAvailable = false
            }
        })


        /*listen to network connection changes*/
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                showLog("n/w type", "wifi")
                                newsArticlesViewModel.fetchNewsArticles(newsRequest)
                            }
                            ConnectionType.Cellular -> {
                                showLog("n/w type", "mobile data")
                                newsArticlesViewModel.fetchNewsArticles(newsRequest)
                            }
                        }
                    }
                    false -> {
                        showLog("is connected", "no")
                        showToast(this, getString(R.string.no_internet))
                    }
                }
            }
        }

    }

    private fun setAdapter() {
        adapter = NewsArticleAdapter(newsList!!)
        newsFeedRecyclerView.adapter = adapter
        newsFeedRecyclerView.layoutManager =
            GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}