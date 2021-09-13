package com.csapps.whatsthenews.di

import android.content.Context
import com.csapps.whatsthenews.Config
import com.csapps.whatsthenews.data.db.NewsDatabase
import com.csapps.whatsthenews.data.db.dao.NewsArticlesDao
import com.csapps.whatsthenews.data.remote.ApiInterFace
import com.csapps.whatsthenews.util.NetworkMonitorUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*app level module for common dependencies needed
* throughout the app*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*provides single database instance which will be used throughout app*/
    @Singleton
    @Provides
    fun provideNewsDatabase(
        @ApplicationContext context: Context): NewsDatabase{
        return NewsDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNewsArticlesDao(newsDatabase: NewsDatabase): NewsArticlesDao{
        return newsDatabase.newsArticlesDao()
    }

    @Singleton
    @Provides
    fun provideOkHTTPClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit): ApiInterFace{
        return retrofit.create(ApiInterFace::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context): NetworkMonitorUtil {
        return NetworkMonitorUtil(context)
    }
}