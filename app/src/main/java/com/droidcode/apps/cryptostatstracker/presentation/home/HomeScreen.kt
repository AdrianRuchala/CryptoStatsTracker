package com.droidcode.apps.cryptostatstracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.R
import com.droidcode.apps.cryptostatstracker.domain.models.Coin
import com.droidcode.apps.cryptostatstracker.presentation.crypto.CryptoState
import com.droidcode.apps.cryptostatstracker.presentation.viewmodels.CryptoViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(modifier: Modifier, viewModel: CryptoViewModel) {
    val cryptoState by viewModel.coins.observeAsState()
    var searchValue by remember { mutableStateOf("") }
    val coins = when (cryptoState) {
        is CryptoState.Success -> (cryptoState as CryptoState.Success).coins
        else -> emptyList()
    }

    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                stringResource(R.string.search_for_coin),
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.enter_coin_name)) }
                )

                Button(
                    onClick = {
                        //TODO
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                        )
                        Text(stringResource(R.string.search))
                    }
                }
            }
        }

        item {
            HorizontalDivider(modifier.padding(vertical = 16.dp))
        }

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.home_headline),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    stringResource(R.string.press_to_see_details),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        items(coins) { coin ->
            CoinPlate(modifier, coin)
        }
    }
}

@Composable
fun CoinPlate(modifier: Modifier, coinState: Coin) {
    Row(
        modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            imageModel = { coinState.image },
            modifier.size(48.dp)
        )

        Column(
            modifier.padding(8.dp)
        ) {
            Text(coinState.name.toString(), style = MaterialTheme.typography.titleMedium)
            Text(coinState.symbol.toString(), style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier.padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                "$${coinState.current_price.toString()}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "${coinState.market_cap_change_percentage_24h.toString()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = if (coinState.market_cap_change_percentage_24h.toString()
                        .startsWith("-")
                ) Color.Red else Color(0xFF00BC13)
            )
        }
    }
}
