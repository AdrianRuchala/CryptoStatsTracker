package com.droidcode.apps.cryptostatstracker.presentation.crypto

import com.droidcode.apps.cryptostatstracker.domain.models.Coin

sealed class CryptoState {
    object Loading : CryptoState()
    data class Success(val coins: List<Coin>) : CryptoState()
    data class Error(val message: String) : CryptoState()
    object Empty : CryptoState()
}
