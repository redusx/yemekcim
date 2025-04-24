package com.example.yemekcim.di

import android.content.Context
import android.net.ConnectivityManager
//import com.example.yemekcim.data.datasource.YemeklerDataSource
import com.example.yemekcim.data.repo.YemeklerRepository
import com.example.yemekcim.retrofit.YemeklerDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "http://kasimadalan.pe.hu/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideYemeklerApiService(retrofit: Retrofit): YemeklerDao {
        return retrofit.create(YemeklerDao::class.java)
    }
}