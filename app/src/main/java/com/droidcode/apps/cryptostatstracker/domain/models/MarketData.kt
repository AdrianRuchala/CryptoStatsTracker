package com.droidcode.apps.cryptostatstracker.domain.models

data class MarketData(
    val current_price: Currency?,
    val market_cap: Currency?,
    val market_cap_change_24h: Double?,
    val market_cap_change_percentage_24h: Double?,
    val high_24h: Currency?,
    val low_24h: Currency?,
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?,
    val fully_diluted_valuation: Currency?,
    val total_volume: Currency?,
    val circulating_supply: Double?,
    val total_supply: Double?,
    val max_supply: Double?,
    val ath: Currency?,
    val ath_change_percentage: Currency?,
    val ath_date: DateArea?,
    val atl: Currency?,
    val atl_change_percentage: Currency?,
    val atl_date: DateArea?,
    val roi: Double?,
)
