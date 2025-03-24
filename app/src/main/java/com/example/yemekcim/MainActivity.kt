package com.example.yemekcim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.yemekcim.ui.theme.YemekcimTheme
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import com.example.yemekcim.uix.views.pageNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainPVM:MainPageViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemekcimTheme {
                pageNav(mainPVM)
            }
        }
    }
}
