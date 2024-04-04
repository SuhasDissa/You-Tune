package app.suhasdissa.karaoke.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.suhasdissa.karaoke.R

@Composable
fun ErrorScreen(error: String, onRetry: () -> Unit, onSettings: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(error)
        Button(onClick = { onRetry() }) {
            Text(stringResource(R.string.retry))
        }
        Button(onClick = { onSettings() }) {
            Text(stringResource(id = R.string.network_settings))
        }
    }
}
