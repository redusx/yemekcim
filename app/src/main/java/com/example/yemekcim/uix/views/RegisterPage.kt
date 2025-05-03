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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yemekcim.R
import com.example.yemekcim.ui.theme.YemekcimTheme
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.StartDestination
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterPage(
    navController: NavController,
    authViewModel: AuthViewModel
){
    val uiState by authViewModel.registerUiState.collectAsState()

    // --- Navigation Event Listener ---
    LaunchedEffect(key1 = Unit) {
        authViewModel.navigateToLogin.collectLatest {
            navController.navigate(StartDestination.LOGIN.name) {
                popUpTo(StartDestination.REGISTER.name) { inclusive = true }
            }
        }
    }

    if (uiState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Kayıt İşlemi Başarısız") },
            text = { Text(uiState.errorMessage!!) },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { authViewModel.dismissRegisterErrorDialog() },
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
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
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
                    text = "Kayıt Ol",
                    modifier = Modifier.padding(start = 40.dp),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,


                    )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = uiState.username,
                onValueChange = { authViewModel.onRegisterUsernameChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(size = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                isError = uiState.errorMessage != null,
                label = { Text("Kullanıcı Adı") },
                singleLine = true)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = uiState.password,
                onValueChange = { authViewModel.onRegisterPasswordChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(size = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                isError = uiState.errorMessage != null,
                label = { Text("Şifre")
                },
                singleLine = true)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = uiState.email,
                onValueChange = { authViewModel.onEmailChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(size = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = uiState.errorMessage != null,
                label = { Text("E-posta Adresi") })
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {authViewModel.onRegisterClicked()},
                shape = RoundedCornerShape(size = 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                contentPadding = PaddingValues(0.dp)
            ) {
                if (uiState.isLoading) { CircularProgressIndicator() }else{
                Text(text = "Kayıt Ol", fontSize = 22.sp, fontWeight = FontWeight.Bold)}
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Hesabın var mı?",fontSize = 15.sp, color = Color.White)
                TextButton(onClick = {authViewModel.onLoginNavigationClicked()}) {
                    Text(text = "Giriş Yap", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
}

