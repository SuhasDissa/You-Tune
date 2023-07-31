package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.karaoke.AudioRecorderScreen
import app.suhasdissa.karaoke.Destination
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.VideoPlayerRecorderScreen
import app.suhasdissa.karaoke.YoutubeSearchScreen
import app.suhasdissa.karaoke.ui.components.PlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (destination: Destination) -> Unit
) {
    Scaffold(floatingActionButton = {
        Column {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(id = R.string.open_karaoke_video)) },
                icon = { Icon(Icons.Default.VideoFile, null) },
                onClick = { onNavigate(VideoPlayerRecorderScreen) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExtendedFloatingActionButton(
                text = { Text(stringResource(id = R.string.record_audio)) },
                icon = { Icon(Icons.Default.Mic, null) },
                onClick = { onNavigate(AudioRecorderScreen) }
            )
        }
    }, topBar = {
        CenterAlignedTopAppBar(title = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clickable { onNavigate(YoutubeSearchScreen) },
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                    Text(
                        stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        })
    }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = 20.dp
                )
                .fillMaxSize()
        ) {
            PlayerView()
        }
    }
}
