package com.csapps.whatsthenews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*database table to hold news feed data.

* this class is also used as one of the response model class, hence the
    annotation @SerializedName is used in addition to @ColumnInfo

   column values are specified as nullable because the api returns null values
   for some news
    */

@Entity(tableName = "news_article")
data class NewsArticle(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id") val newsId: Int?=0,

    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String?,

    @ColumnInfo(name = "description")
    @SerializedName("description") var description: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage") var urlToImage: String?,

    @ColumnInfo(name = "author")
    @SerializedName("author") var author: String?
)
