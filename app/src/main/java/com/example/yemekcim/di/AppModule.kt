package com.example.yemekcim.di

import com.example.yemekcim.data.datasource.YemeklerDataSource
import com.example.yemekcim.data.repo.YemeklerRepository
import com.example.yemekcim.retrofit.ApiUtils
import com.example.yemekcim.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideYemeklerRepository(yds:YemeklerDataSource) : YemeklerRepository {
        return  YemeklerRepository(yds)
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao:YemeklerDao) : YemeklerDataSource {
        return  YemeklerDataSource(ydao)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao() : YemeklerDao {
        return ApiUtils.getYemeklerDao()
    }
}