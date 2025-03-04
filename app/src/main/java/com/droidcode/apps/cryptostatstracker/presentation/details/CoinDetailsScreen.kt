package com.droidcode.apps.cryptostatstracker.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.R
import com.droidcode.apps.cryptostatstracker.domain.models.CoinDetails
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoIntent
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoState
import com.droidcode.apps.cryptostatstracker.presentation.viewmodels.CryptoViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CoinDetailsScreen(
    modifier: Modifier,
    coinId: String,
    viewModel: CryptoViewModel,
    onNavigateUp: () -> Unit
) {
    val userId = Firebase.auth.currentUser?.uid.toString()
    val isInFavourite = remember { mutableStateOf(false) }

    LaunchedEffect(coinId) {
        viewModel.onAction(CryptoIntent.LoadCoinData(coinId))
        viewModel.onAction(CryptoIntent.CheckIfCoinIsInFavourites(userId, coinId) { isFavourite ->
            isInFavourite.value = isFavourite
        } )
    }
    val cryptoState by viewModel.coins
    val coinData = (cryptoState as? CryptoState.SingleCoinSuccess)?.coin


    var coinName by remember { mutableStateOf("") }
    var coinImage by remember { mutableStateOf("") }

    coinName = coinData?.name.toString()
    coinImage = coinData?.image?.large.toString()


    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        item {
            TopBar(modifier, coinName, coinId, userId, isInFavourite, viewModel) { onNavigateUp() }
        }

        item {
            CoinData(modifier, coinData)
        }

    }
}

@Composable
private fun TopBar(
    modifier: Modifier,
    coinName: String,
    coinId: String,
    userId: String,
    isInFavourite: MutableState<Boolean>,
    viewModel: CryptoViewModel,
    onNavigateUp: () -> Unit
) {
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

        if(isInFavourite.value){
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.onAction(CryptoIntent.RemoveCoinFromFavourites(userId,coinId)
                        {
                                isFavourite -> isInFavourite.value = isFavourite
                        })
                    }
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.onAction(CryptoIntent.AddCoinToFavourites(userId, coinId))
                        viewModel.onAction(CryptoIntent.CheckIfCoinIsInFavourites(userId, coinId,)
                        {
                            isFavourite -> isInFavourite.value = isFavourite
                        })
                    }
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}

@Composable
private fun CoinData(modifier: Modifier, coinData: CoinDetails?) {
    var coinImage by remember { mutableStateOf("") }
    var coinSymbol by remember { mutableStateOf("") }
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

    coinImage = coinData?.image?.large.toString()
    coinSymbol = coinData?.name.toString()
    coinCurrentPrice = coinData?.market_data?.current_price?.usd.toString()
    coinMarketCap = coinData?.market_data?.market_cap?.usd.toString()
    coinMarketCapRank = coinData?.market_cap_rank.toString()
    coinFullyDilutedValuation = coinData?.market_data?.fully_diluted_valuation?.usd.toString()
    coinTotalVolume = coinData?.market_data?.total_volume?.usd.toString()
    coinHigh24h = coinData?.market_data?.high_24h?.usd.toString()
    coinLow24h = coinData?.market_data?.low_24h?.usd.toString()
    coinPriceChange24h = coinData?.market_data?.price_change_24h.toString()
    coinPriceChangePercentage24h = coinData?.market_data?.price_change_percentage_24h.toString()
    coinMarketCapChange24h = coinData?.market_data?.market_cap_change_24h.toString()
    coinMarketCapChangePercentage24h =
        coinData?.market_data?.market_cap_change_percentage_24h.toString()
    coinCirculatingSupply = coinData?.market_data?.circulating_supply.toString()
    coinTotalSupply = coinData?.market_data?.total_supply.toString()
    coinMaxSupply = coinData?.market_data?.max_supply.toString()
    coinAth = coinData?.market_data?.ath?.usd.toString()
    coinAthChangePercentage = coinData?.market_data?.ath_change_percentage?.usd.toString()
    coinAthDate = coinData?.market_data?.ath_date?.usd.toString()
    coinAtl = coinData?.market_data?.atl?.usd.toString()
    coinAtlChangePercentage = coinData?.market_data?.atl_change_percentage?.usd.toString()
    coinAtlDate = coinData?.market_data?.atl_date?.usd.toString()
    coinRoi = coinData?.market_data?.roi.toString()
    coinLastUpdated = coinData?.last_updated.toString()

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { coinImage },
            modifier.size(120.dp)
        )

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_symbol),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinSymbol, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_current_price),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(text = "$${coinCurrentPrice}", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_market_cap),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(text = "$${coinMarketCap}", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_market_cap_rank),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinMarketCapRank, style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_market_cap_change_in_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinMarketCapChange24h, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_market_cap_change_percentage_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${coinMarketCapChangePercentage24h}%",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_fully_dilutedValuation),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "$${coinFullyDilutedValuation}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_total_volume),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(text = "$${coinTotalVolume}", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_high_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(text = "$${coinHigh24h}", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_low_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(text = "$${coinLow24h}", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_price_change_in_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinPriceChange24h, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_price_change_percentage_24h),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${coinPriceChangePercentage24h}%",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_circulating_supply),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinCirculatingSupply, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_total_supply),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinTotalSupply, style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_max_supply),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinMaxSupply, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.coin_ath), style = MaterialTheme.typography.labelLarge)

                Text(text = "$${coinAth}", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_ath_change_percentage),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${coinAthChangePercentage}%",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_ath_date),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinAthDate, style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.coin_atl), style = MaterialTheme.typography.labelLarge)

                Text(text = "$${coinAtl}", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_atl_change_percentage),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "${coinAtlChangePercentage}%",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.coin_atl_date),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(coinAtlDate, style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier
                    .padding(8.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(180.dp, 100.dp)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.coin_roi), style = MaterialTheme.typography.labelLarge)

                Text(coinRoi, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
    Text(stringResource(R.string.coin_last_updated) + " $coinLastUpdated", modifier.padding(8.dp))
}
