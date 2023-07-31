package app.suhasdissa.karaoke.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.backend.viewmodels.VideoPlayerViewModel
import app.suhasdissa.karaoke.ui.components.AudioRecordController
import app.suhasdissa.karaoke.ui.components.AudioVisualizer
import app.suhasdissa.karaoke.ui.components.VideoPlayer

@Composable
fun VideoPlayerRecorderScreen(
    videoPlayerViewModel: VideoPlayerViewModel = viewModel()
) {
    val pickVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { videoUri ->
        if (videoUri != null) {
            videoPlayerViewModel.vidUri = videoUri
        }
    }
    LaunchedEffect(Unit) {
        if (videoPlayerViewModel.vidUri == Uri.EMPTY) {
            pickVideo.launch("video/*")
        }
    }
    Scaffold { pV ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pV),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (videoPlayerViewModel.vidUri != Uri.EMPTY) {
                Box(Modifier.heightIn(0.dp, 250.dp)) {
                    VideoPlayer(videoUri = videoPlayerViewModel.vidUri)
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
