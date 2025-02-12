package com.droidcode.apps.cryptostatstracker.domain.repository

import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails

interface CryptoRepository {
    suspend fun get10Coins(): List<Coin>
    suspend fun getCoinData(coinId: String): CoinDetails
}
