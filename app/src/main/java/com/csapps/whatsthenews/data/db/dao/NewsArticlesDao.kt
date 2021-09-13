package com.csapps.whatsthenews.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csapps.whatsthenews.data.entities.NewsArticle

@Dao
interface NewsArticlesDao {

    @Query("SELECT * FROM news_article")
    fun getAllNewsArticles(): LiveData<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(newsList: List<NewsArticle>)

    @Query("DELETE FROM news_article")
    fun deleteAllNews()
}