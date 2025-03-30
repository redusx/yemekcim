package com.example.yemekcim.uix.views

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddShoppingCart
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
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.data.entity.NavigationItem
import com.example.yemekcim.uix.viewModel.MainPageViewModel

@Composable
fun BottomBar(modifier: Modifier=Modifier,mainPageViewModel: MainPageViewModel){
    var selectedIndex by remember { mutableIntStateOf(0) }


    val item:List<NavigationItem> = listOf(
        NavigationItem(
            selectedİcon =Icons.Filled.Home,
            unselectedİcon = Icons.Outlined.Home
        ),
        NavigationItem(
            selectedİcon = Icons.Filled.AddShoppingCart,
            unselectedİcon = Icons.Outlined.AddShoppingCart
        ),
        NavigationItem(
            selectedİcon = Icons.Filled.Person,
            unselectedİcon = Icons.Outlined.Person
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
                                Icon(imageVector = navigationItem.selectedİcon, contentDescription = "selectedIcon")
                            }else{
                                Icon(imageVector = navigationItem.unselectedİcon, contentDescription = "unselectedIcon")
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
        0 -> mainPage(mainViewModel = mainPageViewModel,navController = navController)
        1 -> CartPage(navController = navController)
        2 -> profilePage(navController = navController)

    }
}