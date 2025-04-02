package com.example.yemekcim.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.uix.viewModel.MainPageViewModel

@Composable
fun pageNav(mainViewModel: MainPageViewModel){
    val navController= rememberNavController()
    NavHost(navController =navController, startDestination = "mainpage" ){
        composable("mainpage"){
            MainPage(navController = navController,mainViewModel)
        }
        composable("profilePage"){
            ProfilePage(navController=navController)
        }
        composable("cartPage"){
            CartPage(navController = navController)
        }

    }
}