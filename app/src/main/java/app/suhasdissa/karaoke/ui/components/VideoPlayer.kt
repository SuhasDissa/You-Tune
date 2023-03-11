package app.suhasdissa.karaoke.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.enums.RecorderState
import app.suhasdissa.karaoke.ui.models.RecorderModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayer(videoUri: Uri, recorderModel: RecorderModel = viewModel()) {
    val context = LocalContext.current
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .setUsePlatformDiagnostics(false)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
    }

    recorderModel.onStateChange = {
        when (it) {
            RecorderState.ACTIVE -> exoPlayer.play()
            RecorderState.IDLE -> {
                exoPlayer.pause()
                exoPlayer.seekTo(0)
            }
            RecorderState.PAUSED -> exoPlayer.pause()
        }
    }
    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setShowFastForwardButton(false)
                    setShowRewindButton(false)
                    controllerAutoShow = false
                }
            }
        )

    ) {
        onDispose { exoPlayer.release() }
    }
}
