package com.csapps.whatsthenews.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.csapps.whatsthenews.data.db.dao.NewsArticlesDao
import com.csapps.whatsthenews.data.entities.NewsArticle
import com.csapps.whatsthenews.data.remote.ApiInterFace
import com.csapps.whatsthenews.data.remote.NewsResponse
import retrofit2.Response
import javax.inject.Inject

/*repository to handle database calls and network calls*/
class NewsArticleRepository @Inject constructor(
    private val newsArticlesDao: NewsArticlesDao,
    private val apiService: ApiInterFace){

    /*live data to observe the changes in the database*/
    val newsArticleList: LiveData<List<NewsArticle>> =
        newsArticlesDao.getAllNewsArticles()

    @WorkerThread
    suspend fun insertNewsArticles(newsList: List<NewsArticle>){
        newsArticlesDao.insertAllNews(newsList)
    }

    @WorkerThread
    suspend fun deleteNewsArticles(){
        newsArticlesDao.deleteAllNews()
    }

    @WorkerThread
    suspend fun fetchNewFeed(mapRequest: Map<String, String>): Response<NewsResponse> {
        return apiService.fetchTopHeadLines(mapRequest)
    }
}