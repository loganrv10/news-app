package com.masai.newsapi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masai.newsapi.R
import com.masai.newsapi.data.local.NewsEntity
import com.masai.newsapi.ui.clicklistener.ClickListener
import com.masai.newsapi.ui.viewholder.NewsViewHolder

class NewsAdapter(
    val context: Context,
    var newsResultList: List<NewsEntity>,
    val clickListener: ClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsResultList = newsResultList[position]
        holder.setData(newsResultList)

        holder.crdNews.setOnClickListener {
            clickListener.onArticleClick(newsResultList)
        }
    }

    override fun getItemCount(): Int {
        return newsResultList.size
    }

    fun updateList(newsList: List<NewsEntity>) {
        newsResultList = newsList
        notifyDataSetChanged()
    }
}