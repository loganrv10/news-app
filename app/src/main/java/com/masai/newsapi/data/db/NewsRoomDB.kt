package com.masai.newsapi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsRoomDB : RoomDatabase() {
    abstract fun getNewsDataFromDB(): NewsDAO
}