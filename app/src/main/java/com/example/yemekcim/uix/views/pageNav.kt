package com.example.yemekcim.uix.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import com.example.yemekcim.uix.viewModel.ProfilePageViewModel

@Composable
fun pageNav(mainViewModel: MainPageViewModel){
    val navController= rememberNavController()
    NavHost(navController =navController, startDestination = "mainpage" ){
        composable("mainpage"){
            mainPage(navController = navController,mainViewModel)
        }
        composable("profilePage"){
            profilePage(navController=navController)
        }
        composable("cartPage"){
            CartPage(navController = navController)
        }

    }
}