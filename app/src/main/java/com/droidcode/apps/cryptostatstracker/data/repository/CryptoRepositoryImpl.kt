package com.droidcode.apps.cryptostatstracker.data.repository

import com.droidcode.apps.cryptostatstracker.data.remote.CryptoApi
import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails
import com.droidcode.apps.cryptostatstracker.domain.repository.CryptoRepository
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject


class CryptoRepositoryImpl @Inject constructor(
    private val api: CryptoApi
) : CryptoRepository {
    private val database = FirebaseDatabase.getInstance().getReference("Users")

    override suspend fun get10Coins(): List<Coin> {
        return api.get10Coins()
    }

    override suspend fun getCoinData(coinId: String): CoinDetails {
        return api.getCoinData(coinId)
    }

    override suspend fun addCoinToFavourites(userId: String, coinId: String) {
        database.child(userId).child("Favourites").push().setValue(coinId)
    }

    override suspend fun checkIfCoinIsInFavourites(
        userId: String,
        coinId: String,
        isInFavourites: (Boolean) -> Unit
    ) {
        database.child(userId).child("Favourites").get()
            .addOnSuccessListener { snapshot ->
                val exists = snapshot.children.any { it.value == coinId }
                if (exists) {
                    isInFavourites(true)
                } else {
                    isInFavourites(false)
                }
            }
            .addOnFailureListener {
                isInFavourites(false)
            }
    }

    override suspend fun removeCoinFromFavourites(
        userId: String,
        coinId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        database.child(userId).child("Favourites").get()
            .addOnSuccessListener { snapshot ->
                for (child in snapshot.children) {
                    if (child.value == coinId) {
                        child.ref.removeValue()
                        onSuccess(false)
                        break
                    }
                }
            }
    }
}
