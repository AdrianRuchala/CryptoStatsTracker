package com.droidcode.apps.cryptostatstracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

interface CryptoStatsTrackerDestinations {
    val route: String
}

object Home : CryptoStatsTrackerDestinations {
    override val route = "home"
}

object Favourites : CryptoStatsTrackerDestinations {
    override val route = "favourites"
}

object Settings : CryptoStatsTrackerDestinations {
    override val route = "settings"
}

object Login : CryptoStatsTrackerDestinations {
    override val route = "login"
}

sealed class CryptoStatsTrackerNavItems(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : CryptoStatsTrackerNavItems("home", Icons.Filled.Home, "Home")
    object Favourites :
        CryptoStatsTrackerNavItems("favourites", Icons.Filled.Favorite, "Favourites")

    object Settings : CryptoStatsTrackerNavItems("settings", Icons.Filled.Settings, "Settings")
}
