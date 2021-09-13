package com.csapps.whatsthenews.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/*specify api endpoints here*/
interface ApiInterFace {

    /*api call to fetch the top headlines for that day*/
    @GET("top-headlines")
    suspend fun fetchTopHeadLines(
        @QueryMap requestMap: Map<String, String>): Response<NewsResponse>
}