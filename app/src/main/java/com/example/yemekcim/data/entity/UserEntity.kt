package com.example.yemekcim.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "hashed_password") val hashedPassword:String,
    @ColumnInfo(name = "last_latitude") val latitude:Double?,
    @ColumnInfo(name = "last_longitude") val longitude:Double?
)
