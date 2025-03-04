package com.droidcode.apps.cryptostatstracker.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails
import com.droidcode.apps.cryptostatstracker.domain.models.FavouriteCoin
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

            is CryptoIntent.AddCoinToFavourites -> addCoinToFavourites(action.userId, action.coinId)
            is CryptoIntent.CheckIfCoinIsInFavourites -> checkIfCoinIsInFavourites(
                action.userId,
                action.coinId,
                action.isFavourite
            )

            is CryptoIntent.RemoveCoinFromFavourites -> removeCoinFromFavourites(
                action.userId,
                action.coinId,
                action.onSuccess
            )

            is CryptoIntent.LoadFavouriteCoins -> loadFavouriteCoinsIds(action.userId)
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

    private fun addCoinToFavourites(userId: String, coinId: String) {
        viewModelScope.launch {
            try {
                repository.addCoinToFavourites(userId, coinId)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun checkIfCoinIsInFavourites(
        userId: String,
        coinId: String,
        isInFavourites: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.checkIfCoinIsInFavourites(userId, coinId, isInFavourites)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun removeCoinFromFavourites(
        userId: String,
        coinId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.removeCoinFromFavourites(userId, coinId, onSuccess)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun loadFavouriteCoinsIds(userId: String) {
        viewModelScope.launch {
            val favouriteCoinsList = mutableListOf<FavouriteCoin>()
            repository.getFavouriteCoinsIds(userId, favouriteCoinsList)
            loadFavouritesCrypto(favouriteCoinsList)
        }
    }

    private fun loadFavouritesCrypto(favouriteCoinsIds: MutableList<FavouriteCoin>) {
        viewModelScope.launch {
            _coinState.value = CryptoState.Loading
            try {
                if (favouriteCoinsIds.isNotEmpty()) {
                    val coinList = mutableListOf<CoinDetails>()

                    favouriteCoinsIds.forEach {
                        coinList.add(repository.getCoinData(it.coinId))
                    }

                    _coinState.value =
                        CryptoState.FavouriteCoinsSuccess(coinList.sortedBy { it.market_cap_rank })
                } else {
                    _coinState.value = CryptoState.Empty
                }
            } catch (e: Exception) {
                _coinState.value = CryptoState.Error("Error: ${e.message}")
            }
        }
    }
}
