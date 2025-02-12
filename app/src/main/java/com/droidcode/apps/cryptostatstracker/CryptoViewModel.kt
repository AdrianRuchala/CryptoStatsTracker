package com.droidcode.apps.cryptostatstracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcode.apps.cryptostatstracker.data.Coin
import com.droidcode.apps.cryptostatstracker.di.CryptoStatsTrackerModule
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {
    var coins = mutableStateOf(emptyList<Coin>())

    fun get10Coins() {
        viewModelScope.launch {
            coins.value = CryptoStatsTrackerModule.cryptoStatsTrackerApiService.get10Coins()
        }
    }
}
