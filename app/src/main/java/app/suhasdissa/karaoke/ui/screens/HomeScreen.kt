package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.suhasdissa.karaoke.ui.components.IconCardButton

@Composable
fun HomeScreen(
    onPlayerClick: () -> Unit,
    onRecordClick: () -> Unit,
    onVideoRecordClick: () -> Unit,
    onYoutubeSearchClick: () -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            IconCardButton(onClick = {
                onYoutubeSearchClick()
            }, text = "Search Youtube Video", icon = Icons.Default.Search)
        }
        item {
            IconCardButton(onClick = {
                onVideoRecordClick()
            }, text = "Open Karaoke Video", icon = Icons.Default.VideoFile)
        }
        item {
            IconCardButton(
                onClick = { onRecordClick() }, text = "Record Audio", icon = Icons.Default.Mic
            )
        }
        item {
            IconCardButton(
                onClick = { onPlayerClick() },
                text = "Show Recorded Files",
                icon = Icons.Default.LibraryMusic
            )
        }
    }
}