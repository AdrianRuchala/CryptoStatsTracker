package com.droidcode.apps.cryptostatstracker.domain.repository

import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails

interface CryptoRepository {
    suspend fun get10Coins(): List<Coin>
    suspend fun getCoinData(coinId: String): CoinDetails
    suspend fun addCoinToFavourites(userId: String, coinId: String)
    suspend fun checkIfCoinIsInFavourites(
        userId: String,
        coinId: String,
        isInFavourites: (Boolean) -> Unit
    )

    suspend fun removeCoinFromFavourites(
        userId: String,
        coinId: String,
        onSuccess: (Boolean) -> Unit
    )
}
