package com.droidcode.apps.cryptostatstracker.presentation.crypto

sealed class CryptoIntent {
    data class LoadTop10Crypto(val coins: CryptoState) : CryptoIntent()
}
