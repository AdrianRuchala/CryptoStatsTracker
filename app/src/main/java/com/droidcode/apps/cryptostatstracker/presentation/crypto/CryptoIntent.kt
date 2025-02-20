package com.droidcode.apps.cryptostatstracker.presentation.crypto

sealed class CryptoIntent {
    object LoadTop10Crypto : CryptoIntent()
    data class LoadCoinData(val coinId: String) : CryptoIntent()
    data class IsCoinExisting(
        val coinId: String,
        val onSuccess: (String) -> Unit,
        val onError: (Boolean) -> Unit
    ) : CryptoIntent()

    data class AddCoinToFavourites(val userId: String, val coinId: String) : CryptoIntent()
    data class CheckIfCoinIsInFavourites(
        val userId: String,
        val coinId: String,
        val isFavourite: (Boolean) -> Unit
    ) : CryptoIntent()

    data class RemoveCoinFromFavourites(
        val userId: String,
        val coinId: String,
        val onSuccess: (Boolean) -> Unit
    ) : CryptoIntent()
}
