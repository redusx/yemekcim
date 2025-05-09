package com.example.yemekcim.uix.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.yemekcim.uix.viewModel.StartDestination
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yemekcim.uix.viewModel.ProfilePageViewModel
import com.example.yemekcim.uix.viewModel.ProfileUiState
import androidx.compose.runtime.getValue
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.UserSessionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    navController: NavController,
    profilePageViewModel: ProfilePageViewModel = hiltViewModel(),
    authViewModel: AuthViewModel= hiltViewModel(),
    userSessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val username by userSessionViewModel.usernameFlow.collectAsState(initial = "")
    val uiState: ProfileUiState by profilePageViewModel.uiProfileState.collectAsState()

    val listItems = listOf(
        Triple(Icons.Default.Person, "Hesap Bilgileri", "Kullanıcı Adı: ${username}\nE-Posta: donmezrizaefe@gmail.com"),
        Triple(Icons.Default.AccountBalanceWallet, "Kayıtlı Kartlar", "**** 1234"),
        Triple(Icons.Default.LocationOn, "Konum", "İstanbul / Maltepe"),
        Triple(Icons.Default.Receipt, "Siparişlerim", ""),
        Triple(Icons.Default.SupportAgent, "Yardım Merkezi", ""),
        Triple(Icons.AutoMirrored.Filled.MenuBook, "Kullanım Koşulları Ve Veri Politikası", ""),

        )

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = { BottomBar(navController = navController) },
        topBar = {TopAppBar(title = { Text("Hesabım")})}
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Hata: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
                    Button(onClick = { profilePageViewModel.loadUserProfile() }) { Text("Tekrar Dene") }
                }
            }

            uiState.userProfile != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // Bilgi Listesi
                        listItems.forEachIndexed { index, (icon, title, detail) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = if (index == 0) 0.dp else 16.dp,
                                        bottom = 16.dp
                                    )
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    if (title=="Siparişlerim"||title=="Yardım Merkezi"||title=="Kullanım Koşulları Ve Veri Politikası"){
                                        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

                                    }
                                    else{
                                    Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                                    Text(text = detail, color = Color.Gray, fontSize = 16.sp)}
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                    Button(
                        onClick = {
                            authViewModel.logout {
                                navController.navigate(StartDestination.LOGIN.name) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,

                            ),
                        contentPadding = PaddingValues(0.dp)
                    )
                    {
                        Text(text = "Çıkış Yap", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }

                }
            }
        }
    }
}