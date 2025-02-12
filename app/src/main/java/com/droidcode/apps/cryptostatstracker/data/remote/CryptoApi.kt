package com.droidcode.apps.cryptostatstracker.data.remote

import com.droidcode.apps.cryptostatstracker.data.Coin
import retrofit2.http.GET

interface CryptoApi {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1")
    suspend fun get10Coins(): List<Coin>
}
