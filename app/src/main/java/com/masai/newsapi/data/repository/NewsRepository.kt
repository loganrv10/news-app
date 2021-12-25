package com.masai.newsapi.data.repository

import androidx.lifecycle.LiveData
import com.masai.newsapi.data.local.NewsDAO
import com.masai.newsapi.data.local.NewsEntity
import com.masai.newsapi.data.model.Articles
import com.masai.newsapi.data.model.NewsResponse
import com.masai.newsapi.di.Module
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDAO: NewsDAO) {


    private lateinit var response: NewsResponse

    suspend fun getDataFromApi(
        query: String,
//        from: String,
        key: String
    ) {
        response = Module.getAPIService().getNewsData(query, key)
        addNewsDataToRoom(response.articles)
    }

    private fun addNewsDataToRoom(articles: List<Articles>) {
        articles.forEach {
            val newsTable = NewsEntity(
                it.author,
                it.title,
                it.description,
                it.url,
                it.urlToImage,
                it.publishedAt,
                it.content
            )
            newsDAO.insertNewsData(newsTable)
        }
    }

    fun deleteData() {
        newsDAO.deleteData()
    }

    fun getNewsData(): LiveData<List<NewsEntity>> {
        return newsDAO.getData()
    }
}