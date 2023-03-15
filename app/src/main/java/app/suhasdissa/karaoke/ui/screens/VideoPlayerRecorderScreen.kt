package app.suhasdissa.karaoke.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.ui.components.AudioRecordController
import app.suhasdissa.karaoke.ui.components.AudioVisualizer
import app.suhasdissa.karaoke.ui.components.VideoPlayer
import app.suhasdissa.karaoke.ui.models.VideoPlayerModel

@Composable
fun VideoPlayerRecorderScreen(
    videoPlayerModel: VideoPlayerModel = viewModel()
) {

    val pickVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { videoUri ->
        if (videoUri != null) {
            videoPlayerModel.vidUri = videoUri
        }
    }
    LaunchedEffect(Unit) {
        if (videoPlayerModel.vidUri == Uri.EMPTY) {
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
            if (videoPlayerModel.vidUri != Uri.EMPTY) {
                Box(Modifier.heightIn(0.dp,250.dp)) {
                    VideoPlayer(videoUri = videoPlayerModel.vidUri)
                }
            }
            AudioVisualizer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            AudioRecordController()
        }
    }
}
