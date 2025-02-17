package com.droidcode.apps.cryptostatstracker.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoIntent
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoState
import com.droidcode.apps.cryptostatstracker.presentation.viewmodels.CryptoViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CoinDetailsScreen(
    modifier: Modifier,
    coinId: String,
    viewModel: CryptoViewModel,
    onNavigateUp: () -> Unit
) {
    LaunchedEffect(coinId) {
        viewModel.onAction(CryptoIntent.LoadCoinData(coinId))
    }
    val cryptoState by viewModel.coins
    val coinData = (cryptoState as? CryptoState.SingleCoinSuccess)?.coin

    var coinName by remember { mutableStateOf("") }
    var coinSymbol by remember { mutableStateOf("") }
    var coinImage by remember { mutableStateOf("") }
    var coinCurrentPrice by remember { mutableStateOf("") }
    var coinMarketCap by remember { mutableStateOf("") }
    var coinMarketCapRank by remember { mutableStateOf("") }
    var coinFullyDilutedValuation by remember { mutableStateOf("") }
    var coinTotalVolume by remember { mutableStateOf("") }
    var coinHigh24h by remember { mutableStateOf("") }
    var coinLow24h by remember { mutableStateOf("") }
    var coinPriceChange24h by remember { mutableStateOf("") }
    var coinPriceChangePercentage24h by remember { mutableStateOf("") }
    var coinMarketCapChange24h by remember { mutableStateOf("") }
    var coinMarketCapChangePercentage24h by remember { mutableStateOf("") }
    var coinCirculatingSupply by remember { mutableStateOf("") }
    var coinTotalSupply by remember { mutableStateOf("") }
    var coinMaxSupply by remember { mutableStateOf("") }
    var coinAth by remember { mutableStateOf("") }
    var coinAthChangePercentage by remember { mutableStateOf("") }
    var coinAthDate by remember { mutableStateOf("") }
    var coinAtl by remember { mutableStateOf("") }
    var coinAtlChangePercentage by remember { mutableStateOf("") }
    var coinAtlDate by remember { mutableStateOf("") }
    var coinRoi by remember { mutableStateOf("") }
    var coinLastUpdated by remember { mutableStateOf("") }

    coinName = coinData?.name.toString()
    coinSymbol = coinData?.name.toString()
    coinImage = coinData?.image?.large.toString()
    coinCurrentPrice = coinData?.current_price.toString()
    coinMarketCap = coinData?.market_cap.toString()
    coinMarketCapRank = coinData?.market_cap_rank.toString()
    coinFullyDilutedValuation = coinData?.fully_diluted_valuation.toString()
    coinTotalVolume = coinData?.total_volume.toString()
    coinHigh24h = coinData?.high_24h.toString()
    coinLow24h = coinData?.low_24h.toString()
    coinPriceChange24h = coinData?.price_change_24h.toString()
    coinPriceChangePercentage24h = coinData?.price_change_percentage_24h.toString()
    coinMarketCapChange24h = coinData?.market_cap_change_24h.toString()
    coinMarketCapChangePercentage24h = coinData?.market_cap_change_percentage_24h.toString()
    coinCirculatingSupply = coinData?.circulating_supply.toString()
    coinTotalSupply = coinData?.total_supply.toString()
    coinMaxSupply = coinData?.max_supply.toString()
    coinAth = coinData?.ath.toString()
    coinAthChangePercentage = coinData?.ath_change_percentage.toString()
    coinAthDate = coinData?.ath_change_percentage.toString()
    coinAtl = coinData?.atl.toString()
    coinAtlChangePercentage = coinData?.atl_change_percentage.toString()
    coinAtlDate = coinData?.atl_date.toString()
    coinRoi = coinData?.roi.toString()
    coinLastUpdated = coinData?.last_updated.toString()


    LazyColumn(modifier.fillMaxSize()) {

        item {
            TopBar(modifier, coinName) { onNavigateUp() }
        }

        item {
            GlideImage(
                imageModel = { coinImage },
                modifier.size(60.dp)
            )
        }

    }
}

@Composable
private fun TopBar(modifier: Modifier, coinName: String, onNavigateUp: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clickable { onNavigateUp() }
        )

        Text(
            text = coinName,
            modifier = Modifier
                .padding(all = 8.dp),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier.size(36.dp))
    }
    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}
