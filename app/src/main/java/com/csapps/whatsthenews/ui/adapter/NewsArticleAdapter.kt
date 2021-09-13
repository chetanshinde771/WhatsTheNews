package com.csapps.whatsthenews.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.csapps.whatsthenews.data.entities.NewsArticle
import com.csapps.whatsthenews.databinding.NewsItemBinding
import org.jetbrains.annotations.NotNull

class NewsArticleAdapter(var newsList: List<NewsArticle> ):
            RecyclerView.Adapter<NewsArticleAdapter.NewsViewHolder>() {


    class NewsViewHolder(private val context: Context, val itemBinding: NewsItemBinding):
                RecyclerView.ViewHolder(itemBinding.root){
        fun bind(
            newsArticle: NewsArticle){

            itemBinding.newsArticle = newsArticle
            Glide.with(context).load(newsArticle.urlToImage).into(itemBinding.articleImage)
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = NewsItemBinding.inflate(inflater)

        return NewsViewHolder(parent.context, itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size


}