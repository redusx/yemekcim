package com.example.yemekcim.uix.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yemekcim.R
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.uix.viewModel.UserSessionViewModel


@Composable
fun MainPage(
    navController: NavController,
    mainViewModel: MainPageViewModel,
    userSessionViewModel: UserSessionViewModel = hiltViewModel()

) {
    val username by userSessionViewModel.usernameFlow.collectAsState(initial = "")
    val yemekler by mainViewModel.yemeklerStateFlow.collectAsState()
    Log.d("Page_DEBUG", "Current username in Composable: $username")
    val query = remember { mutableStateOf("") }
    val buttonLabels = listOf("Popüler", "Yemekler", "İçecekler", "Tatlılar", "Dondurmalar")
    val filterButton = remember { mutableStateOf("0") }
    val seciliKategoriYemekler =
        mainViewModel.kategoriyeGoreYemekler[filterButton.value] ?: listOf()
    val contextName = buttonLabels[filterButton.value.toInt()]
    val seciliYemek by mainViewModel.dialogState

    LaunchedEffect(key1 = true) {
        mainViewModel.tumYemekleriGetir()
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Merhaba, ${username ?: "Misafir"}!",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .graphicsLayer(alpha = 0.5f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Ben YEMEKÇİM\nBugün ne yemek istersin?",
                    modifier = Modifier
                        .padding(start = 10.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                    fontFamily = FontFamily(Font(R.font.datang_story))
                )
            }
            TextField(
                value = query.value,
                onValueChange = { query.value = it },
                label = { Text("Arama") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFe6e7e8),
                    unfocusedContainerColor = Color(0xFFe6e7e8),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp)
            ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 10.dp, bottom = 0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    items(buttonLabels.size) { index ->
                        Column(
                            modifier = Modifier.padding(end = 15.dp, bottom = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    filterButton.value = "$index"

                                },
                                modifier = Modifier.size(70.dp, 100.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonColors(
                                    containerColor = Color(0xFFe6e7e8),
                                    contentColor = Color.Black,
                                    disabledContentColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = when (index) {
                                            0 -> R.drawable.popular
                                            1 -> R.drawable.food
                                            2 -> R.drawable.drink
                                            3 -> R.drawable.dessert
                                            4 -> R.drawable.ice_cream
                                            else -> R.drawable.popular
                                        }
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.size(60.dp, 40.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = buttonLabels[index],
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = contextName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp, start = 10.dp, end = 10.dp)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(seciliKategoriYemekler) { id ->
                        val yemek = yemekler.find { it.yemek_id == id }

                        yemek?.let {
                            YemekKarti(yemek = it, mainViewModel = mainViewModel)
                        }
                    }
                }
            }
        }

        //--------
        //sepete ekle dialog
        seciliYemek?.let { yemek ->
            SepeteEkleDialog(
                yemek = yemek,
                onDismiss = { mainViewModel.closeDialog() },
                onEkle = { adet ->
                    mainViewModel.sepeteEkle(yemek, adet,"$username")
                }
            )
        }
    }
}


@Composable
fun YemekKarti(yemek: Yemekler, mainViewModel: MainPageViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFe6e7e8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"

            AsyncImage(
                model = imageUrl,
                contentDescription = yemek.yemek_adi,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = yemek.yemek_adi,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${yemek.yemek_fiyat}₺",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                Button(
                    onClick = { mainViewModel.showDialog(yemek) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF79a31b),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Sepete Ekle",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
