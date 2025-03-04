package com.droidcode.apps.cryptostatstracker.presentation.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun FavouritesScreen(
    modifier: Modifier,
    viewModel: CryptoViewModel,
    navigateToCoinDetails: (String) -> Unit
) {
    val userId = Firebase.auth.currentUser?.uid.toString()

    LaunchedEffect(Unit) {
        viewModel.onAction(CryptoIntent.LoadFavouriteCoins(userId))
    }

    val cryptoState by viewModel.coins
    val coins = when (cryptoState) {
        is CryptoState.FavouriteCoinsSuccess -> (cryptoState as CryptoState.FavouriteCoinsSuccess).coins
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.favourites_headline),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    stringResource(R.string.press_to_see_details),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        items(coins) { coin ->
            CoinPlate(modifier, coin) { navigateToCoinDetails(coin.id.toString()) }
        }
    }
}

@Composable
fun CoinPlate(modifier: Modifier, coinState: CoinDetails, navigateToCoinDetails: (String) -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp)
            .clickable { navigateToCoinDetails(coinState.id.toString()) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            imageModel = { coinState.image?.large },
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
                "$${coinState.market_data.current_price?.usd.toString()}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "${coinState.market_data.market_cap_change_percentage_24h.toString()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = if (coinState.market_data.market_cap_change_percentage_24h.toString()
                        .startsWith("-")
                ) Color.Red else Color(0xFF00BC13)
            )
        }
    }
}
