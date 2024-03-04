package com.mildgrind.androidmvvm

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class NewsViewModel : ViewModel() {
    private val _textLiveData = MutableLiveData<String>()
    private val _apiKey = "pub_39332ba43168174b2635dc66f444711a6c1f3"
    private val _url = "https://newsdata.io/api/1/news?apikey=$_apiKey"
    private val _tag = "MVVM"
    val textLiveData: LiveData<String>
        get() = _textLiveData

    fun setText(value: String) {
        _textLiveData.value = value
    }
    fun jsonStringToMapWithGson(json: String): Map<String, Any> {
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
                    Log.i(_tag,"[response bytes] ${String(bytes)}")
                    val responseData = String(bytes)
                    Log.i(_tag,"[response bytes] $responseData")
                    var newsMap = jsonStringToMapWithGson(responseData)
                    val resultList = (newsMap["results"] as? ArrayList<Map<String, Any>>) ?: ArrayList()
                    val newsList = resultList.map { item ->
                        NewsArticle(
                            articleId = item["article_id"].toString(),
                            title = item["title"].toString(),
                            sourceUrl = item["source_url"].toString(),
                            creator = item["creator"].toString(),
                            description = item["description"].toString(),
                            pubDate = item["pubDate"].toString(),
                            link = item["link"].toString()
                        )
                    }
                    Log.i(_tag, newsList.toString())
                    completionCallback(newsList)
                }
            }
    }
    override fun onCleared() {
        super.onCleared()
        //Log.i("Cleared view-model")
        Log.i("mvvm", "clear view-model")
    }
}