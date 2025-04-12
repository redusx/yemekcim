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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.yemekcim.data.utils.isInternetAvailable
import com.example.yemekcim.ui.theme.YemekcimTheme
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import com.example.yemekcim.uix.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainPWM: MainPageViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemekcimTheme {
                val isConnected by mainPWM.isConnected.collectAsState()

                MainScreen(isConnected = isConnected, mainPageViewModel = mainPWM)
            }
        }
    }
}

@Composable
fun MainScreen(isConnected: Boolean, mainPageViewModel: MainPageViewModel){
    val context = LocalContext.current
    var showInternetDialog by remember { mutableStateOf(false) }
    var canProceed by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // İnternet varsa ana içeriği (BottomBar) göster
            if (isConnected) {
                BottomBar(mainPageViewModel = mainPageViewModel, modifier = Modifier)
            } else {
                // İnternet yoksa bir mesaj gösterebiliriz (opsiyonel)
                Text("İnternet bağlantısı bekleniyor...")
                // Dialog zaten gösterilecek
            }
        }
    }

    // İnternet yoksa AlertDialog'u göster
    if (!isConnected) {
        InternetRequiredDialog(
            onWifiSettingsClick = {
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    Log.e("SettingsIntent", "Wi-Fi ayarları açılamadı.")
                }
            },
            onCloseAppClick = {
                // Activity'yi kapatmak için context üzerinden erişim
                (context as? ComponentActivity)?.finish()
            }
        )
    }
}

@Composable
fun InternetRequiredDialog(
    onWifiSettingsClick: () -> Unit,
    onCloseAppClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Dialog dışına tıklayınca kapanmasın */ },
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



