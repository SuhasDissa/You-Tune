package app.suhasdissa.karaoke.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.backend.viewmodels.PlayerViewModel
import app.suhasdissa.mellowmusic.ui.components.IllustratedMessageScreen

@Composable
fun PlayerView() {
    val playerViewModel: PlayerViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        playerViewModel.loadFiles(context)
    }

    val files = playerViewModel.files

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (files.isNotEmpty()) {
            Text(
                text = stringResource(R.string.recordings),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(files) {
                    RecordingItem(recordingFile = it)
                }
            }
        } else {
            IllustratedMessageScreen(
                image = R.drawable.ic_launcher_monochrome,
                message = R.string.get_started,
                messageColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
