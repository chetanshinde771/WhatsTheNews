package com.csapps.whatsthenews.viewmodel

import androidx.lifecycle.ViewModel
import com.csapps.whatsthenews.data.repository.NewsArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsArticlesViewModel @Inject constructor(
    private var newsArticleRepository: NewsArticleRepository): ViewModel() {

    val newsList = newsArticleRepository.newsArticleList

    fun deleteAllNews() {

        val job = CoroutineScope(Dispatchers.IO).launch {
            newsArticleRepository.deleteNewsArticles()
        }
    }

    fun fetchNewsArticles(map: Map<String, String>) {

        CoroutineScope(Dispatchers.IO).launch {
            var response = newsArticleRepository.fetchNewFeed(map)
            if (response.isSuccessful) {
                var body = response.body()
                body?.let {
                    if (body.articles != null && body.articles.isNotEmpty()) {

                        newsArticleRepository.insertNewsArticles(body.articles)
                    }
                }
            }

        }

    }
}