package com.mildgrind.androidmvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsViewModel : ViewModel() {
    private val _apiKey = "pub_39332ba43168174b2635dc66f444711a6c1f3"
    private val _url = "https://newsdata.io/api/1/news?apikey=$_apiKey&q=Swift"
    private val _tag = "MVVM"

    private fun jsonStringToMapWithGson(json: String): Map<String, Any> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }

    fun fetchNews(completionCallback: (List<NewsArticle>) -> Unit) {
        Fuel.get(_url)
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    val responseData = String(bytes)
                    var newsMap = jsonStringToMapWithGson(responseData)
                    val resultList =
                        (newsMap["results"] as? ArrayList<Map<String, Any>>) ?: ArrayList()
                    val newsList =
                        resultList.map { item ->
                            NewsArticle(
                                articleId = item.getOrElse("article_id") { "" }.toString(),
                                title = item.getOrElse("title") { "" }.toString(),
                                sourceUrl = item.getOrElse("source_url") { "" }.toString(),
                                creator = item.getOrElse("creator") { "" }.toString(),
                                description = item.getOrElse("description") { "" }.toString(),
                                pubDate = item.getOrElse("pubDate") { "" }.toString(),
                                link = item.getOrElse("link") { "" }.toString(),
                            )
                        }
                    completionCallback(newsList)
                }
            }
    }
}
