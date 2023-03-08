package com.bnyro.recorder.ui.views

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bnyro.recorder.enums.RecorderState
import com.bnyro.recorder.ui.models.RecorderModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoView(videoUri: Uri) {
    val context = LocalContext.current
    val recorderModel: RecorderModel = viewModel()
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
            RecorderState.IDLE -> exoPlayer.pause()
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
                }
            }
        )
    ) {
        onDispose { exoPlayer.release() }
    }
}
