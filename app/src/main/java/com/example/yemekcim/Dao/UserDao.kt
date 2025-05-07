package com.example.yemekcim.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yemekcim.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_profile WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun observeUserProfile(): Flow<UserEntity>

    @Query("UPDATE user_profile SET last_latitude = :latitude, last_longitude = :longitude WHERE username = :username")
    suspend fun updateUserLocation(username: String, latitude: Double?, longitude: Double?)

    @Query("DELETE FROM user_profile WHERE username = :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getFirstUser(): UserEntity?

    @Query("SELECT * FROM user_profile WHERE username = :targetUsername LIMIT 1")
    fun observeUserProfileByUsername(targetUsername: String): Flow<UserEntity?>

}