package com.example.yemekcim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yemekcim.ui.theme.YemekcimTheme
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.provider.Settings
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.StartDestination
import com.example.yemekcim.uix.views.PageNav

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val mainPWM: MainPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemekcimTheme {
                val startDestination by authViewModel.startDestination.collectAsState()
                val isConnected by mainPWM.isConnected.collectAsState()

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (startDestination) {
                        StartDestination.LOADING -> {
                            CircularProgressIndicator()
                        }
                        else -> {
                            if (isConnected) {
                                PageNav(
                                    startRoute = startDestination.name,
                                    authViewModel = authViewModel,
                                    mainViewModel = mainPWM
                                )
                            }
                            else {
                                InternetRequiredDialog(
                                    onWifiSettingsClick = {
                                        val context = this@MainActivity // Context'i Activity'den al
                                        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                                        if (intent.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(intent)
                                        } else {
                                            Log.e("SettingsIntent", "Wi-Fi ayarları açılamadı.")
                                        }
                                    },
                                    onCloseAppClick = {
                                        finish()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun InternetRequiredDialog(
    onWifiSettingsClick: () -> Unit,
    onCloseAppClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* */ },
        title = { Text("İnternet Bağlantısı Yok") },
        text = { Text("Uygulamayı kullanabilmek için lütfen internet bağlantınızı kontrol edin.") },
        confirmButton = {
            Button(onClick = onWifiSettingsClick) {
                Text("Wi-Fi Ayarları")
            }
        },
        dismissButton = {
            Button(onClick = onCloseAppClick) {
                Text("Uygulamayı Kapat")
            }
        }
    )
}

