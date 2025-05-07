package com.example.yemekcim.uix.views

import android.graphics.Color
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yemekcim.R
import androidx.navigation.compose.rememberNavController
import com.example.yemekcim.data.entity.NavigationItem
import com.example.yemekcim.uix.viewModel.MainPageViewModel
import com.example.yemekcim.uix.viewModel.StartDestination
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

object BottomNavRoutes {
    const val HOME = "home_route"
    const val CART = "cart_route"
    const val PROFILE = "profile_route"
}

@Composable
fun BottomBar(navController: NavController) {
    val items: List<NavigationItem> = listOf(
        NavigationItem(
            route = StartDestination.MAIN.name,
            selectedVectorIcon = Icons.Filled.Home,
            unselectedVectorIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            route = "cartPage",
            selectedPainterIcon = painterResource(id = R.drawable.cart),
            unselectedPainterIcon = painterResource(id = R.drawable.cartunselected)
        ),
        NavigationItem(
            route = "profilePage",
            selectedVectorIcon = Icons.Filled.Person,
            unselectedVectorIcon = Icons.Outlined.Person
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.White,
        tonalElevation = 10.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(androidx.compose.ui.graphics.Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate("${screen.route}") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (selected) androidx.compose.ui.graphics.Color(0xFFF0F0F0)
                                else androidx.compose.ui.graphics.Color.Transparent
                            )
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Crossfade(targetState = selected, label = "iconCrossfade") { isSelected ->
                            val vectorIcon = if (isSelected) screen.selectedVectorIcon else screen.unselectedVectorIcon
                            val painterIcon = if (isSelected) screen.selectedPainterIcon else screen.unselectedPainterIcon

                            if (vectorIcon != null) {
                                Icon(
                                    imageVector = vectorIcon,
                                    contentDescription = screen.route,
                                    modifier = Modifier.size(26.dp),
                                    tint = if (isSelected) androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.Gray
                                )
                            } else if (painterIcon != null) {
                                Image(
                                    painter = painterIcon,
                                    contentDescription = screen.route,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer, // Seçili ikon rengi
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Seçili olmayan ikon
                    selectedTextColor = MaterialTheme.colorScheme.onSurface, // Seçili label rengi (label varsa)
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant // Seçili olmayan label rengi
                )
            )
        }
    }
}



