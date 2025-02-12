package com.droidcode.apps.cryptostatstracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droidcode.apps.cryptostatstracker.CryptoViewModel
import com.droidcode.apps.cryptostatstracker.HomeScreen
import com.droidcode.apps.cryptostatstracker.settings.Profile
import com.droidcode.apps.cryptostatstracker.settings.SettingsInfo
import com.droidcode.apps.cryptostatstracker.settings.SettingsScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun CryptoStatsTrackerNavHost(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: CryptoViewModel,
    onLogout: () -> Unit
) {

    val userData = Profile(
        name = Firebase.auth.currentUser?.displayName,
        email = Firebase.auth.currentUser?.email,
        profilePictureUrl = Firebase.auth.currentUser?.photoUrl.toString()
    )

    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier.padding()
    ) {
        composable(Home.route) {
            HomeScreen(Modifier, viewModel)
        }

        composable(Favourites.route) {
            //FavouritesScreen
        }

        composable(Settings.route) {
            SettingsScreen(
                Modifier,
                SettingsInfo(
                    Profile(userData.name, userData.email, userData.profilePictureUrl),
                    "1.0"
                )
            ) { onLogout() }
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
