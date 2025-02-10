package com.droidcode.apps.cryptostatstracker.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.R

@Composable
fun LoginScreen(modifier: Modifier, onLogin: () -> Unit, showError: Boolean) {

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hello),
            modifier.padding(40.dp),
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = stringResource(R.string.nice_to_see_you),
            modifier.padding(horizontal = 20.dp),
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier
                .padding(20.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
                .padding(all = 16.dp)
                .clickable { onLogin() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = null,
                modifier.size(32.dp)
            )

            Text(
                text = stringResource(R.string.login_with_google),
                modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if(showError){
            LoginError(modifier)
        }
    }
}

@Composable
private fun LoginError(modifier: Modifier){
    Row(
        modifier
            .padding(20.dp)
            .border(2.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.error),
            modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
