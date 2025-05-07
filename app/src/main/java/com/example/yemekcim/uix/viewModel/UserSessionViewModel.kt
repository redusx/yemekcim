package com.example.yemekcim.uix.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcim.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSessionViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val usernameFlow: Flow<String?> = dataStoreManager.getUsername()

    fun setUsername(username: String) {
        viewModelScope.launch {
            dataStoreManager.saveUsername(username)
        }
    }

    fun clearUsername() {
        viewModelScope.launch {
            dataStoreManager.clearUsername()
        }
    }
}