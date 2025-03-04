package com.droidcode.apps.cryptostatstracker.presentation.crypto

import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails

sealed class CryptoState {
    object Loading : CryptoState()
    data class Success(val coins: List<Coin>) : CryptoState()
    data class SingleCoinSuccess(val coin: CoinDetails) : CryptoState()
    data class FavouriteCoinsSuccess(val coins: List<CoinDetails>) : CryptoState()
    data class Error(val message: String) : CryptoState()
    object Empty : CryptoState()
}
