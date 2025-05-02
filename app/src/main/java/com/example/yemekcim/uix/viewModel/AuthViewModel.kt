package com.example.yemekcim.uix.viewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcim.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class StartDestination {
    LOADING,
    REGISTER,
    LOGIN,
    MAIN
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        checkUserStatus()
    }

    // --- UI State ---
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    // --- Login State ---
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // --- Register State ---
    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    // --- Navigation Events ---
    private val _navigateToMain = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToMain = _navigateToMain.asSharedFlow()

    private val _navigateToRegister = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToRegister = _navigateToRegister.asSharedFlow()

    private val _navigateToLogin = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToLogin = _navigateToLogin.asSharedFlow()

    private val _startDestination = MutableStateFlow(StartDestination.LOADING)
    val startDestination: StateFlow<StartDestination> = _startDestination.asStateFlow()

    // --- Event Handlers ---

    fun onUsernameChanged(username: String) {
        _uiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onLoginClicked() {
        val currentState = _uiState.value

        if (currentState.username.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Kullanıcı adı ve şifre boş olamaz!") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginSuccess = userRepository.checkLoginCredentials(
                    username = currentState.username,
                    password = currentState.password
                )

                if (loginSuccess) {
                    Log.d("AuthViewModel", "Login successful for ${currentState.username}")
                    _uiState.update { it.copy(isLoading = false) }
                    userAuthenticated()
                    _navigateToMain.tryEmit(Unit)
                } else {
                    Log.d("AuthViewModel", "Login failed for ${currentState.username}")
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Kullanıcı adı veya şifre hatalı!") }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login error: ${e.localizedMessage}", e)
                _uiState.update { it.copy(isLoading = false, errorMessage = "Bir hata oluştu: ${e.message}") }
            }
        }
    }

    // --- Register Event Handlers ---
    fun onRegisterUsernameChanged(username: String) {
        _registerUiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun onRegisterPasswordChanged(password: String) {
        _registerUiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun onEmailChanged(email: String) {
        _registerUiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onRegisterClicked() {
        val currentState = _registerUiState.value

        if (currentState.username.isBlank() || currentState.password.isBlank() || currentState.email.isBlank()) {
            _registerUiState.update { it.copy(errorMessage = "Tüm alanlar doldurulmalıdır!") }
            return
        }

        // Şifre uzunluğu vs. gibi ek doğrulamalar eklenebilir

        _registerUiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val existingUser = userRepository.getUserByUsername(currentState.username)
                if (existingUser != null) {
                    _registerUiState.update { it.copy(isLoading = false, errorMessage = "Bu kullanıcı adı zaten alınmış!") }
                    return@launch
                }

                // TODO: Konum izni alıp gerçek konumu buraya ekle (veya sonra güncelle)
                val location: Location? = null
                userRepository.saveOrUpdateUser(
                    username = currentState.username,
                    password = currentState.password,
                    location = location
                )

                Log.d("AuthViewModel", "Registration successful for ${currentState.username}")
                _registerUiState.update { it.copy(isLoading = false) }
                _navigateToLogin.tryEmit(Unit)

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Registration error: ${e.localizedMessage}", e)
                _registerUiState.update { it.copy(isLoading = false, errorMessage = "Kayıt sırasında bir hata oluştu: ${e.message}") }
            }
        }
    }

    fun onRegisterNavigationClicked() {
        _navigateToRegister.tryEmit(Unit)
    }
    fun onLoginNavigationClicked() {
        _navigateToLogin.tryEmit(Unit)
    }

    private fun checkUserStatus() {
        viewModelScope.launch {
            // İsteğe bağlı: Çok kısa bir gecikme eklenebilir (splash screen hissi için)
            delay(500) // Örnek: Yarım saniye

            try {
                val hasUser = userRepository.hasRegisteredUser()
                if (hasUser) {
                    // Kayıtlı kullanıcı VARSA: Ana Ekrana (BottomBar'ın olduğu yer)
                    // TODO: Daha sonra buraya "giriş yapılmış mı" kontrolü eklenmeli.
                    _startDestination.value = StartDestination.MAIN
                    Log.d("AuthViewModel", "User found, navigating to MAIN")
                } else {
                    // Kayıtlı kullanıcı YOKSA: Kayıt Ekranına
                    _startDestination.value = StartDestination.REGISTER
                    Log.d("AuthViewModel", "No user found, navigating to REGISTER")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error checking user status: ${e.message}", e)
                // Hata durumunda varsayılan olarak Kayıt veya Giriş ekranına yönlendirilebilir
                _startDestination.value = StartDestination.REGISTER // Veya LOGIN
            }
        }
    }

    fun userAuthenticated() {
        _startDestination.value = StartDestination.MAIN
    }
}