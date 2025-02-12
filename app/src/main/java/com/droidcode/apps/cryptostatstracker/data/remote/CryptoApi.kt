package com.droidcode.apps.cryptostatstracker.data.remote

import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1")
    suspend fun get10Coins(): List<Coin>

    @GET("coins/{coinId}")
    suspend fun getCoinData(@Path("coinId") coinId: String): CoinDetails
}
