package com.droidcode.apps.cryptostatstracker.presentation.crypto

sealed class CryptoIntent {
    object LoadTop10Crypto : CryptoIntent()
    data class LoadCoinData(val coinId: String) : CryptoIntent()
}
