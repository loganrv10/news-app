package com.masai.newsapi.ui.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.newsapi.R
import com.masai.newsapi.data.Status
import com.masai.newsapi.data.db.NewsEntity
import com.masai.newsapi.data.model.Articles
import com.masai.newsapi.ui.adapter.NewsAdapter
import com.masai.newsapi.ui.clicklistener.ClickListener
import com.masai.newsapi.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ClickListener {

    private val newsResponseList = mutableListOf<NewsEntity>()
    private lateinit var newNewsList: ArrayList<NewsEntity>
    private lateinit var tempArrayList: ArrayList<NewsEntity>
    private var newsList = emptyList<NewsEntity>()
    var checkIfThereInDatabase: String? = null

    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialization()
        setRecyclerView()
        fetchDataFromDB()

        val connectionManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkData = connectionManager.activeNetworkInfo
        if (networkData != null && networkData.isConnected) {

            newsViewModel.getDataFromDB().observe(this, Observer {

                /*
                if launching app for the first time then it[0].title will throw index out of bound exception
                so we catch it and it the api and not let our app crash
                 */

                try {
                    checkIfThereInDatabase = it[0].title
                } catch (e: Exception) {
                    if (checkIfThereInDatabase == null) {

                        /*
                      if internet access is there and also database contains previous data then delete all
                      data from database and hit the api and store the new list into database
                       */

                        CoroutineScope(Dispatchers.IO).launch {
                            newsViewModel.deleteDataFromDB()
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            newsViewModel.getDataFromApi(
                                "tesla",
//                                "2021-12-24",
                                "3f355e979ae645aaa0e437ec430d011d"
                            )
                            runOnUiThread {
                                fetchDataFromDB()
                            }
                        }
                    } else {

                        /*
                       if checkIfThereInDatabase is not null means data is already present in database,
                       so we don't need to hit api again and  fetch the data from database
                        */
                        fetchDataFromDB()
                    }
                }
            })
        }
    }

    private fun setRecyclerView() {
        newsAdapter = NewsAdapter(this, newsResponseList, this)
        recyclerview.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
    }

    private fun initialization() {
        tempArrayList = arrayListOf<NewsEntity>()
        newNewsList = arrayListOf<NewsEntity>()
    }

    private fun fetchDataFromDB() {
        newsViewModel.getDataFromDB().observe(this, Observer {
            newsList = it
            newNewsList.addAll(it)
            tempArrayList.addAll(it)
            newsAdapter.updateList(tempArrayList)
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        })
    }

    override fun onArticleClick(newsEntity: NewsEntity) {

        val intent = Intent(this, NewsDetails::class.java)
        intent.putExtra("title", newsEntity.title)
        intent.putExtra("author", newsEntity.author)
        intent.putExtra("content", newsEntity.content)
        intent.putExtra("publishedAt", newsEntity.publishedAt)
        intent.putExtra("url", newsEntity.url)
        intent.putExtra("urlToImage", newsEntity.urlToImage)
        intent.putExtra("description", newsEntity.description)
        startActivity(intent)
    }


    override fun onResume() {
        shimmer.startShimmer()
        super.onResume()
    }

    override fun onPause() {
        shimmer.stopShimmer()
        super.onPause()
    }
}