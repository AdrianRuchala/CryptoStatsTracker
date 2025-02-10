package com.droidcode.apps.cryptostatstracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.droidcode.apps.cryptostatstracker.navigation.CryptoStatsTrackerNavHost
import com.droidcode.apps.cryptostatstracker.navigation.CryptoStatsTrackerNavItems
import com.droidcode.apps.cryptostatstracker.navigation.Login
import com.droidcode.apps.cryptostatstracker.navigation.navigateSingleTopTo

@Composable
fun MainScreen(modifier: Modifier, navController: NavHostController) {
    Scaffold(
        bottomBar = { CryptoStatsTrackerNavigationBar(navController) }
    ) { padding ->
        CryptoStatsTrackerNavHost(modifier.padding(padding), navController)
    }
}

@Composable
fun CryptoStatsTrackerNavigationBar(navController: NavHostController) {
    val items: List<CryptoStatsTrackerNavItems>

    items = listOf(
        CryptoStatsTrackerNavItems.Home,
        CryptoStatsTrackerNavItems.Favourites,
        CryptoStatsTrackerNavItems.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != Login.route) {
        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigateSingleTopTo(item.route)
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                    },
                    label = { Text(item.title) }
                )
            }
        }
    }
}
