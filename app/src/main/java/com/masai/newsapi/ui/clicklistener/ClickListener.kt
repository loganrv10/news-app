package com.masai.newsapi.ui.clicklistener

import com.masai.newsapi.data.local.NewsEntity

interface ClickListener {

    fun onArticleClick(newsEntity: NewsEntity)
}