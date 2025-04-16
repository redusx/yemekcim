package com.example.yemekcim.uix.viewModel


import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val yemeklerRepository: YemeklerRepository,
    private val connectivityManager: ConnectivityManager ) :ViewModel()
{
    // Yemekler listesi için StateFlow (Compose ile kullanım için)
    private val _yemeklerStateFlow = MutableStateFlow<List<Yemekler>>(emptyList())
    val yemeklerStateFlow: StateFlow<List<Yemekler>> = _yemeklerStateFlow.asStateFlow()

    // Yemekler listesi için LiveData (XML UI ile kullanım için)
    private val _yemeklerLiveData = MutableLiveData<List<Yemekler>>(emptyList())
    val yemeklerLiveData: LiveData<List<Yemekler>> = _yemeklerLiveData

    // Yükleme durumu
    private val _yukleniyor = MutableStateFlow(false)
    val yukleniyor: StateFlow<Boolean> = _yukleniyor.asStateFlow()

    // Hata durumu
    private val _hata = MutableStateFlow<String?>(null)
    val hata: StateFlow<String?> = _hata.asStateFlow()
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    fun tumYemekleriGetir() {
        viewModelScope.launch {
            _yukleniyor.value = true
            _hata.value = null

            try {
                val yemekler = yemeklerRepository.tumYemekleriGetir()
                _yemeklerStateFlow.value = yemekler
                _yemeklerLiveData.value = yemekler
            } catch (e: Exception) {
                _hata.value = "Yemekler yüklenirken hata oluştu: ${e.message}"
            } finally {
                _yukleniyor.value = false
            }
        }
    }

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    init {
        tumYemekleriGetir()
        try {
            _isConnected.value = isCurrentlyConnected()
            println("Network Check: Initial connection state set to: ${_isConnected.value}")
        } catch (e: Exception) {
            println("Network Check: Error setting initial connection state: ${e.message}")
        }

        setupNetworkCallback()
        startMonitoring()
    }


    val kategoriyeGoreYemekler = mapOf(
        "0" to listOf("kofte.png", "ayran.png", "baklava.png", "fanta.png",
            "izgarasomon.png", "izgaratavuk.png", "kadayif.png", "kahve.png",
            "lazanya.png", "makarna.png", "pizza.png", "su.png",
            "sutlac.png", "tiramisu.png"),
        "1" to listOf("kofte.png", "izgarasomon.png", "izgaratavuk.png", "lazanya.png", "makarna.png", "pizza.png"),
        "2" to listOf("ayran.png", "fanta.png", "kahve.png", "su.png"),
        "3" to listOf("baklava.png", "kadayif.png", "sutlac.png", "tiramisu.png"),
        "4" to listOf() // Dondurma kategorisi boş
    )

    private fun setupNetworkCallback() {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _isConnected.value = true
                println("Network Check: Internet Available")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _isConnected.value = isCurrentlyConnected()
                println("Network Check: Internet Lost, rechecking: ${_isConnected.value}")
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                _isConnected.value = hasInternet
                println("Network Check: Capabilities Changed, hasInternet: $hasInternet")
            }
        }
    }

    private fun isCurrentlyConnected(): Boolean {
        println("Network Check: isCurrentlyConnected called")
        try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            val connected = (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            println("Network Check: isCurrentlyConnected check result: $connected")
            return connected
        } catch (e: Exception) {
            println("Network Check: Error checking current connection: ${e.message}")
            return false // Hata durumunda bağlı değil varsay
        }
    }

    private fun startMonitoring() {
        println("Network Check: startMonitoring called")
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        try {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            println("Network Check: Monitoring Started, initial state: ${_isConnected.value}")
        } catch (e: SecurityException) {
            println("Network Check: SecurityException registering callback. Check ACCESS_NETWORK_STATE permission. ${e.message}")
        } catch (e: Exception) {
            println("Network Check: Exception registering callback: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            println("Network Check: Monitoring Stopped")
        } catch (e: IllegalArgumentException) {
            println("Network Check: Callback already unregistered or never registered: ${e.message}")
        } catch (e: Exception) {
            println("Network Check: Error unregistering callback: ${e.message}")
        }
    }
}