package com.example.yemekcim.uix.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.yemekcim.R
import com.example.yemekcim.data.datasource.YemeklerDataSource
import com.example.yemekcim.data.entity.NavigationItem
import com.example.yemekcim.data.entity.Yemekler
import com.example.yemekcim.data.repo.YemeklerRepository
import com.example.yemekcim.retrofit.YemeklerDao
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import javax.annotation.meta.When


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController,mainViewModel: MainPageViewModel) {
    val yemekListesi = mainViewModel.yemeklerListesi.observeAsState(listOf())
    var user_name="Rıza"
    var query = remember { mutableStateOf("") }
    val buttonLabels = listOf("Popüler", "Yemekler", "İçecekler", "Tatlılar", "Dondurmalar")
    var filterButton= remember { mutableStateOf("0") }
    val seciliKategoriYemekler = mainViewModel.kategoriyeGoreYemekler[filterButton.value] ?: listOf()
    var contextName=buttonLabels[filterButton.value.toInt()]


    LaunchedEffect(key1 = true) {
        mainViewModel.getYemekler()

    }

    Column (
           modifier = Modifier
               .windowInsetsPadding(WindowInsets.statusBars).windowInsetsPadding(WindowInsets.navigationBars)
               .fillMaxSize(),
           verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.CenterHorizontally
           ){

          Row (modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              ){
              Text(
                  text = "Merhaba, $user_name!",
                  modifier = Modifier
                      .padding(start = 10.dp)
                      .graphicsLayer(alpha = 0.5f)
              )
          }
         Row (modifier = Modifier.fillMaxWidth().padding(top =2.dp),
             verticalAlignment = Alignment.CenterVertically,
         ){
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
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFe6e7e8),
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.White,
                unfocusedLabelColor = Color.Black,
                unfocusedIndicatorColor = Color.White
            )
        )
        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp)) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp).padding(start = 10.dp, bottom = 0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                items(buttonLabels.size) { index ->
                    Column(
                        modifier = Modifier.padding(end = 15.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                filterButton.value="$index"

                            },
                            modifier = Modifier.size(70.dp,100.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonColors(
                                containerColor = Color(0xFFe6e7e8),
                                contentColor = Color.Black,
                                disabledContentColor = Color.LightGray,
                                disabledContainerColor = Color.LightGray),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = when(index){
                                        0 -> R.drawable.popular
                                        1 -> R.drawable.food
                                        2 -> R.drawable.drink
                                        3 -> R.drawable.dessert
                                        4 -> R.drawable.ice_cream
                                        else -> R.drawable.popular
                                    }),
                                contentDescription = "",
                                modifier = Modifier.size(60.dp,40.dp))
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
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)){
            Text(
                text = "$contextName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,)
        }


        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, bottom = 56.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(
                    when (filterButton.value)
                    {
                        "0" -> yemekListesi.value.size
                        "1" -> 6
                        "2" -> 4
                        "3" -> 4
                        "4" -> 0
                        else -> 0
                    }
                )
                {index ->

                    Box(
                        modifier = Modifier
                            .size(100.dp, 250.dp)
                            .padding(end = 10.dp, bottom = 8.dp, top = 10.dp)
                            .background(Color(0x80F1F1F1), RoundedCornerShape(10.dp))
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.fillMaxSize()
                        ) {
                            mainViewModel.YemekItem(yemek_resim_adi = seciliKategoriYemekler[index])
                        }
                        Box(
                            modifier = Modifier
                                .size(100.dp, 50.dp)
                                .padding(top = 200.dp)
                                .background(Color(0xFFF1F1F1), RoundedCornerShape(10.dp))
                        ){

                        }
                    }
                }
            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun mainPagePreview() {
//     val navController= rememberNavController()
//     val mainViewModel=MainPageViewModel(yrepo = YemeklerRepository(YemeklerDataSource(YemeklerDao)))
//    mainPage(navController,mainViewModel)
//}