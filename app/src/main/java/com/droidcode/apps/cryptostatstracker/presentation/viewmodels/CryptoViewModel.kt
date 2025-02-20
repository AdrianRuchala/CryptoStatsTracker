package com.droidcode.apps.cryptostatstracker.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcode.apps.cryptostatstracker.domain.repository.CryptoRepository
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoIntent
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {
    private val _coinState = mutableStateOf<CryptoState>(CryptoState.Loading)
    val coins = (_coinState)

    fun onAction(action: CryptoIntent) {
        when (action) {
            is CryptoIntent.LoadTop10Crypto -> loadTop10Crypto()
            is CryptoIntent.LoadCoinData -> loadCoinData(action.coinId)
            is CryptoIntent.IsCoinExisting -> isCoinExisting(
                action.coinId,
                action.onSuccess,
                action.onError
            )
        }
    }

    private fun loadTop10Crypto() {
        viewModelScope.launch {
            _coinState.value = CryptoState.Loading
            try {
                val coinList = repository.get10Coins()
                if (coinList.isNotEmpty()) {
                    _coinState.value = CryptoState.Success(coinList)
                } else {
                    _coinState.value = CryptoState.Empty
                }
            } catch (e: Exception) {
                _coinState.value = CryptoState.Error("Error: ${e.message}")
            }
        }
    }

    private fun loadCoinData(coinId: String) {
        viewModelScope.launch {
            _coinState.value = CryptoState.Loading
            try {
                val coin = repository.getCoinData(coinId)
                if (coin != null) {
                    _coinState.value = CryptoState.SingleCoinSuccess(coin)
                } else {
                    _coinState.value = CryptoState.Error("No data found for $coinId")
                }
            } catch (e: Exception) {
                _coinState.value = CryptoState.Error("Error: ${e.message}")
            }
        }
    }

    private fun isCoinExisting(
        coinId: String,
        onSuccess: (String) -> Unit,
        onError: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val coin = repository.getCoinData(coinId)
                if (coin != null) {
                    onError(false)
                    onSuccess(coinId)
                } else {
                    onError(true)
                }
            } catch (e: Exception) {
                onError(true)
            }
        }
    }
}
