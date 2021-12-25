package com.masai.newsapi.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masai.newsapi.R
import com.masai.newsapi.data.db.NewsEntity

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvNewsHeadline: TextView = view.findViewById(R.id.tvNewsHeadline)
    val tvDomain: TextView = view.findViewById(R.id.tvDomain)
    val tvDate: TextView = view.findViewById(R.id.tvDate)
    val ivPoster: ImageView = view.findViewById(R.id.ivPoster)
    val crdNews: CardView = view.findViewById(R.id.crdNews)


    fun setData(newsEntity: NewsEntity) {
        newsEntity.apply {
            tvNewsHeadline.text = title
            tvDomain.text = author
            tvDate.text = publishedAt
            Glide.with(ivPoster).load(urlToImage).into(ivPoster)
        }
    }
}