package com.masai.newsapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDAO {

    @Insert
    fun insertNewsData(newsEntity: NewsEntity)

    @Query("select * from news_table")
    fun getData(): LiveData<List<NewsEntity>>

    @Query("delete from news_table")
    fun deleteData()

}