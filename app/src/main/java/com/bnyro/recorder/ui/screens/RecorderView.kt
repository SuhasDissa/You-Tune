package com.bnyro.recorder.ui.screens

import android.net.Uri
import android.os.Build
import android.text.format.DateUtils
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bnyro.recorder.R
import com.bnyro.recorder.enums.RecorderState
import com.bnyro.recorder.ui.common.ClickableIcon
import com.bnyro.recorder.ui.components.AudioVisualizer
import com.bnyro.recorder.ui.components.SettingsBottomSheet
import com.bnyro.recorder.ui.models.RecorderModel
import com.bnyro.recorder.ui.views.VideoView

@Composable
fun RecorderView() {
    val currentVideo = remember {
        mutableStateOf(Uri.EMPTY)
    }
    val recorderModel: RecorderModel = viewModel()
    val context = LocalContext.current

    val pickVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { videoUri ->
        if (videoUri != null) {
            currentVideo.value = videoUri
        }
    }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var showPlayerScreen by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(recorderModel.recorderState) {
        // update the UI when the recorder gets destroyed by the notification
        if (recorderModel.recorderState == RecorderState.IDLE) {
            recorderModel.stopRecording()
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
            if (currentVideo.value != Uri.EMPTY) {
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    VideoView(videoUri = currentVideo.value)
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (recorderModel.recordedAmplitudes.isNotEmpty()) {
                    AudioVisualizer(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    Text(text = "Open A Video Or tap Record")
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                recorderModel.recordedTime?.let {
                    Text(
                        text = DateUtils.formatElapsedTime(it),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ClickableIcon(
                        imageVector = Icons.Default.FileOpen,
                        contentDescription = stringResource(R.string.settings)
                    ) {
                        //showBottomSheet = true
                        pickVideo.launch("video/*")
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    FloatingActionButton(onClick = {
                        when {
                            recorderModel.recorderState != RecorderState.IDLE -> recorderModel.stopRecording()
                            else -> recorderModel.startAudioRecorder(context)
                        }
                    }) {
                        Icon(
                            imageVector = when {
                                recorderModel.recorderState != RecorderState.IDLE -> Icons.Default.Stop
                                else -> Icons.Default.Mic
                            }, contentDescription = stringResource(
                                if (recorderModel.recorderState != RecorderState.IDLE) {
                                    R.string.stop
                                } else {
                                    R.string.record
                                }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && recorderModel.recorderState != RecorderState.IDLE) {
                        ClickableIcon(
                            imageVector = if (recorderModel.recorderState == RecorderState.PAUSED) {
                                Icons.Default.PlayArrow
                            } else {
                                Icons.Default.Pause
                            }, contentDescription = stringResource(
                                if (recorderModel.recorderState == RecorderState.PAUSED) {
                                    R.string.resume
                                } else {
                                    R.string.pause
                                }
                            )
                        ) {
                            if (recorderModel.recorderState == RecorderState.PAUSED) {
                                recorderModel.resumeRecording()
                            } else {
                                recorderModel.pauseRecording()
                            }
                        }
                    } else {
                        ClickableIcon(
                            imageVector = Icons.Default.LibraryMusic,
                            contentDescription = stringResource(R.string.recordings)
                        ) {
                            showPlayerScreen = true
                        }
                    }
                }
            }

        }
    }

    if (showBottomSheet) {
        SettingsBottomSheet {
            showBottomSheet = false
        }
    }
    if (showPlayerScreen) {
        PlayerScreen {
            showPlayerScreen = false
        }
    }
}
