package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.ui.components.*
import app.suhasdissa.karaoke.ui.models.PipedStreamModel

@Composable
fun YtPlayerRecorderScreen(
    vidId: String,
    pipedStreamModel: PipedStreamModel = viewModel()
) {
    LaunchedEffect(Unit) {
        pipedStreamModel.getStreams(vidId)
    }
    Scaffold { pV ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pV),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when (val streamState = pipedStreamModel.state) {
                is PipedStreamModel.VideoStreamState.Error -> {
                    ErrorScreen(
                        error = streamState.error,
                        onRetry = { pipedStreamModel.getStreams(vidId) })
                }
                is PipedStreamModel.VideoStreamState.Loading -> {
                    LoadingScreen()
                }
                is PipedStreamModel.VideoStreamState.Success -> {
                    Box(Modifier.heightIn(0.dp, 250.dp)) {
                        if (streamState.response.hls != null) {
                            VideoPlayer(videoUri = streamState.response.hls!!.toUri())
                        } else {
                            ErrorScreen(error = "Video Playback Error", onRetry = {})
                        }
                    }
                    AudioVisualizer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    AudioRecordController()
                }
            }


        }
    }
}
