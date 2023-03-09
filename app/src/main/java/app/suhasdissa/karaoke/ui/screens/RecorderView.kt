package app.suhasdissa.karaoke.ui.screens

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
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.enums.RecorderState
import app.suhasdissa.karaoke.ui.common.ClickableIcon
import app.suhasdissa.karaoke.ui.components.SettingsBottomSheet
import app.suhasdissa.karaoke.ui.models.RecorderModel
import app.suhasdissa.karaoke.ui.views.VideoView

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
                Box {
                    VideoView(videoUri = currentVideo.value)
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = DateUtils.formatElapsedTime(recorderModel.recordedTime),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
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
