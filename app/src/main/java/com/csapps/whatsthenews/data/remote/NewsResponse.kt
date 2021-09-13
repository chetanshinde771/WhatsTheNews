package com.csapps.whatsthenews.data.remote

import com.csapps.whatsthenews.data.entities.NewsArticle
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status : String,
    @SerializedName("totalResults") val totalResults : Int,
    @SerializedName("articles") val articles : List<NewsArticle>
)
