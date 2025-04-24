package com.example.yemekcim.uix.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.yemekcim.uix.viewModel.CartPageViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(cartViewModel: CartPageViewModel = hiltViewModel(), navController: NavController) {
    val sepet = cartViewModel.sepetListesi.value

    LaunchedEffect(Unit) {
        cartViewModel.sepettekiYemekleriGetir()
    }

    Scaffold(
        topBar = {TopAppBar(title = { Text(text = "Sepetim") })},
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars))
    { innerPadding ->
        if (sepet.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sepetiniz şu anda boş görünüyor!.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(sepet) { yemek ->
                    var displayedAdet by remember {
                        mutableStateOf(yemek.yemekSiparisAdet.toIntOrNull() ?: 0)
                    }
                    val basePrice = yemek.yemekFiyat.toIntOrNull() ?: 0
                    val totalPrice = basePrice * displayedAdet
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1.5f)
                                    .fillMaxSize()
                            ) {
                                AsyncImage(
                                    model = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemekResimAdi}",
                                    contentDescription = yemek.yemekAdi,
                                    modifier = Modifier.size(80.dp),
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxSize()
                            ) {
                                Text(
                                    text = yemek.yemekAdi,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "${totalPrice}₺",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(3f)
                                    .fillMaxSize()
                                    .padding(start = 25.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxSize()) {
                                    Button(
                                        onClick = {
                                            if (displayedAdet > 1) {
                                                displayedAdet--
                                            }
                                        }, shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (displayedAdet == 1) {
                                                Color.Red
                                            } else {
                                                Color(0xFF4CAF50)
                                            },
                                            contentColor = Color.White
                                        ),
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(44.dp, 44.dp)
                                    )
                                    {
                                        if (displayedAdet == 1) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteForever,
                                                contentDescription = "Delete",
                                                modifier = Modifier.size(30.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "-",
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 30.sp
                                            )
                                        }
                                    }
                                    Text(
                                        text = "${displayedAdet}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 27.sp,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(start = 8.dp, end = 8.dp)
                                    )
                                    Button(
                                        onClick = { displayedAdet++ },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF4CAF50),
                                            contentColor = Color.White
                                        ),
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.size(44.dp, 44.dp)
                                    )
                                    {
                                        Text(
                                            text = "+",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 30.sp
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}