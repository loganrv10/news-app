package com.masai.newsapi.ui.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.masai.newsapi.R
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.news_item_layout.*

class NewsDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        var intent = Intent()
        intent = getIntent()
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val content = intent.getStringExtra("content")
        val publishedAt = intent.getStringExtra("publishedAt")
        val url = intent.getStringExtra("url")
        val urlToImage = intent.getStringExtra("urlToImage")
        val description = intent.getStringExtra("description")

        Glide.with(ivPosterInfo).load(urlToImage).into(ivPosterInfo)
        tvDateInfo.text = publishedAt
        tvDomainInfo.text = author
        tvNewsHeadlineInfo.text = title

        layoutNews.setOnClickListener {
            val uri = Uri.parse(url)
            val intent1 = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent1)
        }
    }
}