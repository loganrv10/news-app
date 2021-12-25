package com.masai.newsapi.di

import android.content.Context
import androidx.room.Room
import com.masai.newsapi.data.remote.ApiService
import com.masai.newsapi.data.local.NewsDAO
import com.masai.newsapi.data.local.NewsRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {
    private const val BASE_URL = "https://newsapi.org/"
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun getAPIService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): NewsRoomDB {
        val database = Room.databaseBuilder(
            context,
            NewsRoomDB::class.java,
            "news.db"
        )
        database.fallbackToDestructiveMigration()
        return database.build()
    }

    @Singleton
    @Provides
    fun provideDataToDAO(database: NewsRoomDB): NewsDAO {
        return database.getNewsDataFromDB()
    }
}