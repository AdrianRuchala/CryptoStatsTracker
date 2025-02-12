package com.droidcode.apps.cryptostatstracker.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _coinState = MutableLiveData<CryptoState>()
    val coins: LiveData<CryptoState> get() = _coinState

    fun onAction(action: CryptoIntent) {
        when(action){
            is CryptoIntent.LoadTop10Crypto -> loadTop10Crypto()
            is CryptoIntent.LoadCoinData -> loadCoinData(action.coinId)
        }
    }

    init {
        loadTop10Crypto()
    }

    private fun loadTop10Crypto(){
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

    private fun loadCoinData(coinId: String){
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
}
