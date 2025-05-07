package com.example.yemekcim.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    fun getUsername(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USERNAME_KEY]
        }
    }

    suspend fun clearUsername() {
        context.dataStore.edit { prefs ->
            prefs.remove(USERNAME_KEY)
        }
    }
}