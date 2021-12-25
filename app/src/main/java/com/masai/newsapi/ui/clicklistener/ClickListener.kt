package com.masai.newsapi.ui.clicklistener

import com.masai.newsapi.data.db.NewsEntity
import com.masai.newsapi.data.model.Articles

interface ClickListener {

    fun onArticleClick(newsEntity: NewsEntity)
}