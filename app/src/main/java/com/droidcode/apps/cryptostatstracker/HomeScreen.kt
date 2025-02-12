package com.droidcode.apps.cryptostatstracker

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.data.Coin
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(modifier: Modifier, viewModel: CryptoViewModel) {
    viewModel.get10Coins()
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(viewModel.coins.value) { coin ->
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
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
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
    }
}
