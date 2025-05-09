package com.example.yemekcim.uix.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.mutableStateMapOf
import com.example.yemekcim.data.entity.SepetYemek
import com.example.yemekcim.uix.viewModel.UserSessionViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(
    cartViewModel: CartPageViewModel = hiltViewModel(),
    navController: NavController,
    userSessionViewModel: UserSessionViewModel = hiltViewModel()
) {
    val username by userSessionViewModel.usernameFlow.collectAsState(initial = "")
    val sepet: List<SepetYemek> by cartViewModel.sepetListesi
    val displayedQuantities = remember { mutableStateMapOf<String, Int>() }

    Log.d("Page_DEBUG", "Current username in Composable: $username")

    LaunchedEffect(username) {
        delay(250)
        cartViewModel.sepettekiYemekleriGetir(kullaniciAdi ="$username")
    }
    LaunchedEffect(sepet) {
        sepet.forEach { yemek ->
            val yemekId = yemek.sepetYemekId
            val apiQuantity = yemek.yemekSiparisAdet.toIntOrNull() ?: 0
            if (!displayedQuantities.containsKey(yemekId)) {
                displayedQuantities[yemekId] = apiQuantity
            }
        }
        val currentKeys = sepet.map { it.sepetYemekId }.toSet()
        if (displayedQuantities.keys.retainAll(currentKeys)) {
            println("Map cleaned. Remaining keys: ${displayedQuantities.keys}")
        }
    }

    val totalCartPrice by remember(sepet, displayedQuantities.entries) {
        derivedStateOf {
            sepet.sumOf { yemek ->
                val quantity = displayedQuantities[yemek.sepetYemekId] ?: 0
                val price = yemek.yemekFiyat.toIntOrNull() ?: 0
                quantity * price
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sepetim", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                })
        },
        bottomBar = {
            if (!sepet.isNullOrEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                        .navigationBarsPadding(),
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomEnd = 10.dp,
                        bottomStart = 10.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Toplam Tutar:",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                            Text(
                                "₺$totalCartPrice ",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp
                            )
                        }

                        Button(
                            onClick = {
                                navController.navigate("confirmScreen/${totalCartPrice}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF79a31b),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Sepeti Onayla", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (sepet.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sepetiniz şu anda boş görünüyor...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 15.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(sepet, key = { it.sepetYemekId }) { yemek ->
                        var displayedAdet by remember(yemek.sepetYemekId) {
                            mutableIntStateOf(
                                displayedQuantities[yemek.sepetYemekId]
                                    ?: yemek.yemekSiparisAdet.toIntOrNull() ?: 0
                            )
                        }

                        val basePrice = yemek.yemekFiyat.toIntOrNull() ?: 0
                        val totalPriceForEach = basePrice * displayedAdet
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFe6e7e8))
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
                                        .padding(start = 2.dp)
                                ) {
                                    Text(
                                        text = yemek.yemekAdi,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "${totalPriceForEach}₺",
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
                                                    val newQuantity = displayedAdet - 1
                                                    displayedAdet = newQuantity
                                                    displayedQuantities[yemek.sepetYemekId] =
                                                        newQuantity
                                                }else{
                                                    val yemekIdInt = yemek.sepetYemekId.toIntOrNull()
                                                    if (yemekIdInt != null) {
                                                        cartViewModel.yemekSil(yemekIdInt,"$username")
                                                    }
                                                }
                                            }, shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (displayedAdet == 1) {
                                                    Color(0xFFcf3333)
                                                } else {
                                                    Color(0xFF79a31b)
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
                                            text = "$displayedAdet",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 27.sp,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .padding(start = 8.dp, end = 8.dp)
                                        )
                                        Button(
                                            onClick = {
                                                val newQuantity = displayedAdet + 1
                                                displayedAdet = newQuantity
                                                displayedQuantities[yemek.sepetYemekId] =
                                                    newQuantity
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF79a31b),
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
}

