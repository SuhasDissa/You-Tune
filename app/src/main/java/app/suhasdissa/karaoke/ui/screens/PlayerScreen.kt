package app.suhasdissa.karaoke.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.ui.components.PlayerView

@Composable
fun PlayerScreen() {
    val orientation = LocalConfiguration.current.orientation

    Column(
        modifier = Modifier.padding(
            horizontal = 20.dp
        )
    ) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = stringResource(R.string.recordings),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
        PlayerView()
    }

}
