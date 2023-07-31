package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.backend.viewmodels.PipedStreamViewModel
import app.suhasdissa.karaoke.ui.components.AudioRecordController
import app.suhasdissa.karaoke.ui.components.AudioVisualizer
import app.suhasdissa.karaoke.ui.components.ErrorScreen
import app.suhasdissa.karaoke.ui.components.LoadingScreen
import app.suhasdissa.karaoke.ui.components.VideoPlayer

@Composable
fun YtPlayerRecorderScreen(
    vidId: String,
    pipedStreamViewModel: PipedStreamViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        pipedStreamViewModel.getStreams(vidId)
    }
    Scaffold { pV ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pV),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when (val streamState = pipedStreamViewModel.state) {
                is PipedStreamViewModel.VideoStreamState.Error -> {
                    ErrorScreen(
                        error = streamState.error,
                        onRetry = { pipedStreamViewModel.getStreams(vidId) }
                    )
                }

                is PipedStreamViewModel.VideoStreamState.Loading -> {
                    LoadingScreen()
                }

                is PipedStreamViewModel.VideoStreamState.Success -> {
                    Box(Modifier.heightIn(0.dp, 250.dp)) {
                        if (streamState.response.hls != null) {
                            VideoPlayer(videoUri = streamState.response.hls!!.toUri())
                        } else {
                            ErrorScreen(error = "Video Playback Error", onRetry = {})
                        }
                    }
                    AudioVisualizer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    AudioRecordController()
                }
            }
        }
    }
}
