package com.example.yemekcim.uix.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.uix.viewModel.AuthViewModel
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.yemekcim.uix.viewModel.StartDestination


@Composable
fun PageNav(mainViewModel: MainPageViewModel, startRoute: String, authViewModel: AuthViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = startRoute) {
        composable(StartDestination.MAIN.name) {
            MainPage(navController = navController, mainViewModel = mainViewModel)
        }
        composable("profilePage") {
            ProfilePage(navController = navController)
        }
        composable("cartPage") {
            CartPage(navController = navController)
        }
        composable(StartDestination.REGISTER.name) {
            RegisterPage(navController, authViewModel)
        }
        composable(StartDestination.LOGIN.name) {
            LoginPage(navController, authViewModel)
        }
        composable(
            "confirmScreen/{totalPrice}",
            arguments = listOf(navArgument("totalPrice") { type = NavType.IntType })
        ) { backStackEntry ->

            val totalPrice = backStackEntry.arguments?.getInt("totalPrice") ?: 0

            CartConfirmScreen(
                totalPrice = totalPrice,
                navController = navController
            )
        }
        composable("ordercpage") {
            OrderConfirmedPage(navController = navController)
        }
    }
}