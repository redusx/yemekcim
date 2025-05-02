package com.example.yemekcim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yemekcim.Dao.UserDao
import com.example.yemekcim.data.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}