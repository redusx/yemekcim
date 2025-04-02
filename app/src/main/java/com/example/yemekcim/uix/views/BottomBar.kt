package com.example.yemekcim.uix.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yemekcim.R
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.data.entity.NavigationItem
import com.example.yemekcim.uix.viewModel.MainPageViewModel

@Composable
fun BottomBar(modifier: Modifier=Modifier,mainPageViewModel: MainPageViewModel){
    var selectedIndex by remember { mutableIntStateOf(0) }


    val item:List<NavigationItem> = listOf(
        NavigationItem(
            selectedVectorIcon =Icons.Filled.Home,
            unselectedVectorIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            selectedPainterIcon = painterResource(id = R.drawable.cart),
            unselectedPainterIcon = painterResource(id = R.drawable.cartunselected)
        ),
        NavigationItem(
            selectedVectorIcon = Icons.Filled.Person,
            unselectedVectorIcon = Icons.Outlined.Person
        )

    )

    Scaffold (
        bottomBar = {
            NavigationBar {
                item.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = selectedIndex==index,
                        onClick = {selectedIndex=index},
                        icon ={
                            if (selectedIndex==index){
                                navigationItem.selectedVectorIcon?.let {
                                    Icon(imageVector = it, contentDescription = "Selected Icon", modifier = Modifier.size(36.dp))
                                } ?: navigationItem.selectedPainterIcon?.let {
                                    Image(painter = it, contentDescription = "Selected Icon", modifier = Modifier.size(36.dp))
                                }
                            }else{
                                navigationItem.unselectedVectorIcon?.let {
                                    Icon(imageVector = it, contentDescription = "Unselected Icon", modifier = Modifier.size(36.dp))
                                } ?: navigationItem.unselectedPainterIcon?.let {
                                    Image(painter = it, contentDescription = "Unselected Icon", modifier = Modifier.size(36.dp))
                                }
                            }
                        })
                }
            }
        },
        ){innerPadding->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            mainPageViewModel = mainPageViewModel)
    }
}

@Composable
fun ContentScreen(modifier: Modifier=Modifier,
                  selectedIndex:Int,
                  mainPageViewModel: MainPageViewModel){
    val navController= rememberNavController()

    when(selectedIndex){
        0 -> MainPage(mainViewModel = mainPageViewModel,navController = navController)
        1 -> CartPage(navController = navController)
        2 -> ProfilePage(navController = navController)

    }
}