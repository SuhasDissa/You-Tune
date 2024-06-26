package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import app.suhasdissa.karaoke.Settings
import app.suhasdissa.karaoke.VideoPlayerRecorderScreen
import app.suhasdissa.karaoke.YoutubeSearchScreen
import app.suhasdissa.karaoke.ui.components.IconCardButton
import app.suhasdissa.karaoke.ui.components.PlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (destination: Destination) -> Unit
) {
    Scaffold(topBar = {
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
        }, actions = {
            IconButton(onClick = { onNavigate(Settings) }) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.settings)
                )
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
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(300.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    IconCardButton(
                        onClick = { onNavigate(VideoPlayerRecorderScreen) },
                        text = stringResource(id = R.string.open_karaoke_video),
                        icon = Icons.Default.VideoFile
                    )
                }
                item {
                    IconCardButton(
                        onClick = { onNavigate(AudioRecorderScreen) },
                        text = stringResource(id = R.string.record_audio),
                        icon = Icons.Default.Mic
                    )
                }
            }
            PlayerView()
        }
    }
}
