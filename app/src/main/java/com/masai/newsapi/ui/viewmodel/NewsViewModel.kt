package com.masai.newsapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.masai.newsapi.data.Resource
import com.masai.newsapi.data.db.NewsEntity
import com.masai.newsapi.data.model.NewsResponse
import com.masai.newsapi.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    suspend fun getDataFromApi(
        query: String,
//        from: String,
        key: String
    ) {
        newsRepository.getDataFromApi(query, key)
    }

    fun getDataFromDB(): LiveData<List<NewsEntity>> {
        return newsRepository.getNewsData()
    }

    fun deleteDataFromDB() {
        newsRepository.deleteData()
    }
}