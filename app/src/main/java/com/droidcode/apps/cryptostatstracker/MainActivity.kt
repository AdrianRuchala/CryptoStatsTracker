package com.droidcode.apps.cryptostatstracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.droidcode.apps.cryptostatstracker.auth.AuthActivity
import com.droidcode.apps.cryptostatstracker.ui.theme.CryptoStatsTrackerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Firebase.auth.currentUser == null) {
            Intent(this, AuthActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            CryptoStatsTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(Modifier, navController)
                }
            }
        }
    }
}
