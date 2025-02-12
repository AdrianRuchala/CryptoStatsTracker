package com.droidcode.apps.cryptostatstracker.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droidcode.apps.cryptostatstracker.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SettingsScreen(modifier: Modifier, settingsInfo: SettingsInfo, onLogout: () -> Unit) {
    Column(
        modifier.fillMaxSize()
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!settingsInfo.profile.name.isNullOrEmpty() && !settingsInfo.profile.email.isNullOrEmpty()) {
                GlideImage(
                    imageModel = { settingsInfo.profile.profilePictureUrl },
                    modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(settingsInfo.profile.name, style = MaterialTheme.typography.titleMedium)
                    Text(settingsInfo.profile.email, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        HorizontalDivider(modifier.padding(8.dp))

        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.app_version))

            Text(settingsInfo.appVersion, style = MaterialTheme.typography.titleMedium)
        }

        HorizontalDivider(modifier.padding(8.dp))

        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onLogout() },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}
