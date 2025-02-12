package com.droidcode.apps.cryptostatstracker.domain.repository

import com.droidcode.apps.cryptostatstracker.domain.models.Coin

interface CryptoRepository {
    suspend fun get10Coins(): List<Coin>
}
