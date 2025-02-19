package com.droidcode.apps.cryptostatstracker.domain.models

data class CoinDetails (
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: CoinImages?,
    val market_data: MarketData,
    val market_cap_rank: Double?,
    val last_updated: String?
)
