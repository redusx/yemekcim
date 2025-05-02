package com.example.yemekcim.di

import android.content.Context
import androidx.room.Room
import com.example.yemekcim.Dao.UserDao
import com.example.yemekcim.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "yemekcim_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase):UserDao{
        return database.userDao()
    }
}




