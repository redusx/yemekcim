package com.example.yemekcim.uix.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yemekcim.uix.viewModel.StartDestination
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun OrderConfirmedPage(
    navController: NavController,
) {
    BackHandler {
        navController.navigate(StartDestination.MAIN.name)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.DeliveryDining,
            contentDescription = "DeliveryDining",
            modifier = Modifier.size(250.dp)
        )
        Text(text = "Siparişiniz Onaylandı", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(Modifier.height(5.dp))
        Text(
            text = "Tahmini Gönderim Süresi: 35-40 dk",
            fontSize = 20.sp,
            modifier = Modifier.graphicsLayer(alpha = 0.5f )
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "Afiyet Olsun..",
            fontSize = 20.sp,
            modifier = Modifier.graphicsLayer(alpha = 0.5f )
        )
    }
}

