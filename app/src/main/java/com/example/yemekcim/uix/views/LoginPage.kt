package com.example.yemekcim.uix.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yemekcim.R
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.StartDestination
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginPage(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
){
    val uiState by authViewModel.loginUiState.collectAsState()

    LaunchedEffect(key1 = Unit){
        authViewModel.navigateToMain.collectLatest{
            navController.navigate(StartDestination.MAIN.name){
                popUpTo(StartDestination.LOGIN.name){inclusive=true}
            }
        }
    }

    LaunchedEffect(key1 = Unit){
        authViewModel.navigateToRegister.collectLatest {
            navController.navigate(StartDestination.REGISTER.name)
        }
    }

    if (uiState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Giriş Başarısız") },
            text = { Text(uiState.errorMessage!!) },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { authViewModel.dismissLoginErrorDialog() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Tamam")
                    }
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_page_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Tekrar Hoşgeldin!",
                    modifier = Modifier.padding(start = 40.dp),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,


                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = uiState.username,
                onValueChange = { authViewModel.onUsernameChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(size = 10.dp),
                isError = uiState.errorMessage != null,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                label = { Text("Kullanıcı Adı")})
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = uiState.password,
                onValueChange = { authViewModel.onPasswordChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(size = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                isError = uiState.errorMessage != null,
                label = { Text("Şifre") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick ={authViewModel.onLoginClicked()},
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(size = 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                contentPadding = PaddingValues(0.dp)
            ) {
                if (uiState.isLoading) { CircularProgressIndicator() }
                else { Text(text = "Giriş Yap", fontSize = 22.sp, fontWeight = FontWeight.Bold) }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Hesabın yok mu?",fontSize = 15.sp, color = Color.White)
                TextButton(onClick = {authViewModel.onRegisterNavigationClicked()}) {
                    Text(text = "Kayıt Ol", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
}
