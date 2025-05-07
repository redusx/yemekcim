package com.example.yemekcim.data.repo

import android.location.Location
import android.util.Log
import com.example.yemekcim.Dao.UserDao
import com.example.yemekcim.data.entity.UserEntity
import com.example.yemekcim.utils.HashingUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
) {

    // Kullanıcıyı kaydet/güncelle (giriş veya ilk kayıt)
    suspend fun saveOrUpdateUser(username: String, password: String, location: Location?) {
        val hashedPassword = HashingUtils.hashPassword(password)
        val userEntity = UserEntity(
            username = username,
            hashedPassword = hashedPassword,
            latitude = location?.latitude,
            longitude = location?.longitude
        )
        userDao.insertUser(userEntity)
    }

    // Giriş Kontrolü
    suspend fun checkLoginCredentials(username: String, password: String): Boolean {
        val user = userDao.getUserByUsername(username)
        return if (user != null) {
            HashingUtils.checkPassword(password, user.hashedPassword)
        } else {
            false
        }
    }

    // Profil verisini Flow olarak sun (UI otomatik güncellensin diye)
    fun getUserProfileFlow(): Flow<UserEntity?> {
        return userDao.observeUserProfile()
    }

    // Sadece profil verisini bir kerelik almak için
    suspend fun getUserProfile(): UserEntity? {
        return userDao.getFirstUser()
    }

    // Konumu güncellemek için (eğer gerekirse)
    suspend fun updateUserLocation(username: String, location: Location?) {
        userDao.updateUserLocation(username, location?.latitude, location?.longitude)
    }

    suspend fun hasRegisteredUser(): Boolean {
        return userDao.getFirstUser() != null
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }

    fun getUserProfileFlowForUsername(username: String): Flow<UserEntity?> {
        Log.d("UserRepository_DEBUG", "getUserProfileFlowForUsername called for: $username")

        return userDao.observeUserProfileByUsername(username)
    }
}