package com.droidcode.apps.cryptostatstracker.data.repository

import com.droidcode.apps.cryptostatstracker.data.remote.CryptoApi
import javax.inject.Inject


class CryptoRepository @Inject constructor(
    private val api: CryptoApi
) {
    suspend fun get10Coins(): List<Coin> {
        return api.get10Coins()
    }
}
