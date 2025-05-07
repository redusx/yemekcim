package com.example.yemekcim.uix.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcim.data.entity.UserEntity
import com.example.yemekcim.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val userProfile: UserEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ProfilePageViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _uiProfileState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiProfileState: StateFlow<ProfileUiState> = _uiProfileState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {


        _uiProfileState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = userRepository.getUserProfile()

                if (user != null) {
                    Log.d("ProfileVM", "User profile loaded: ${user.username}")
                    _uiProfileState.update {
                        it.copy(userProfile = user, isLoading = false)
                    }
                } else {
                    Log.w("ProfileVM", "User profile not found.")
                    _uiProfileState.update {
                        it.copy(
                            userProfile = null,
                            isLoading = false,
                            errorMessage = "Kullanıcı profili bulunamadı."
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileVM", "Error loading user profile", e)
                _uiProfileState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Profil yüklenirken bir hata oluştu: ${e.message}"
                    )
                }
            }
        }
    }

}