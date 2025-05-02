package com.example.yemekcim.uix.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.yemekcim.uix.viewModel.StartDestination


@Composable
fun pageNav(mainViewModel: MainPageViewModel,startRoute: String,authViewModel: AuthViewModel){
    val navController: NavHostController = rememberNavController()

    NavHost(navController =navController, startDestination =startRoute ){
        composable(StartDestination.MAIN.name){
            MainPage(navController = navController, mainViewModel = mainViewModel)
        }
        composable("profilePage"){
            ProfilePage(navController=navController)
        }
        composable("cartPage"){
            CartPage(
                navController = navController,
                onBack = { navController.navigate(StartDestination.MAIN.name)
                { popUpTo(StartDestination.MAIN.name){ inclusive = true } } })
        }
        composable(StartDestination.REGISTER.name){
            RegisterPage(navController, authViewModel)
        }
        composable(StartDestination.LOGIN.name){
             LoginPage(navController, authViewModel)
        }
    }
}