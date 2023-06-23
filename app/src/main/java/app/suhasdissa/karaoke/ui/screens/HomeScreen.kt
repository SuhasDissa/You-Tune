package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.ui.components.IconCardButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPlayerClick: () -> Unit,
    onRecordClick: () -> Unit,
    onVideoRecordClick: () -> Unit,
    onYoutubeSearchClick: () -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clickable { onYoutubeSearchClick() },
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
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                IconCardButton(
                    onClick = {
                        onVideoRecordClick()
                    },
                    text = stringResource(R.string.open_karaoke_video),
                    icon = Icons.Default.VideoFile
                )
            }
            item {
                IconCardButton(
                    onClick = { onRecordClick() },
                    text = stringResource(R.string.record_audio),
                    icon = Icons.Default.Mic
                )
            }
            item {
                IconCardButton(
                    onClick = { onPlayerClick() },
                    text = stringResource(R.string.show_recorded_files),
                    icon = Icons.Default.LibraryMusic
                )
            }
        }
    }
}