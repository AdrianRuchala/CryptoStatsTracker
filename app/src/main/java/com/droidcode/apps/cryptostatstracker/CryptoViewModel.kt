package com.droidcode.apps.cryptostatstracker

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcode.apps.cryptostatstracker.data.repository.Coin
import com.droidcode.apps.cryptostatstracker.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {
    var coins = mutableStateOf(emptyList<Coin>())

    init {
        viewModelScope.launch {
            coins.value = repository.get10Coins()
        }
    }
}
