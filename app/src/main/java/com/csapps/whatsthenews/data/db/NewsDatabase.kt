package com.csapps.whatsthenews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.csapps.whatsthenews.AppConstants
import com.csapps.whatsthenews.data.db.dao.NewsArticlesDao
import com.csapps.whatsthenews.data.entities.NewsArticle

@Database(entities = [NewsArticle::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsArticlesDao(): NewsArticlesDao


    companion object{

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase{
            return INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(context, NewsDatabase::class.java,
                    AppConstants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}