package com.droidcode.apps.cryptostatstracker.data.repository

import com.droidcode.apps.cryptostatstracker.data.remote.CryptoApi
import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.repository.CryptoRepository
import javax.inject.Inject


class CryptoRepositoryImpl @Inject constructor(
    private val api: CryptoApi
): CryptoRepository {
    override suspend fun get10Coins(): List<Coin> {
        return api.get10Coins()
    }
}
