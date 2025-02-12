package com.droidcode.apps.cryptostatstracker.data.repository

data class Coin (
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String?,
    val current_price: Double?,
    val market_cap_change_percentage_24h: Double?
)
