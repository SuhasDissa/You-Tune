package app.suhasdissa.karaoke.ui.components

import android.os.Build
import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.R
import app.suhasdissa.karaoke.backend.enums.RecorderState
import app.suhasdissa.karaoke.backend.viewmodels.RecorderViewModel

@Composable
fun AudioRecordController(recorderViewModel: RecorderViewModel = viewModel()) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = DateUtils.formatElapsedTime(recorderViewModel.recordedTime),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(onClick = {
                when {
                    recorderViewModel.recorderState != RecorderState.IDLE -> recorderViewModel.stopRecording()
                    else -> recorderViewModel.startAudioRecorder(context)
                }
            }) {
                Icon(
                    imageVector = when {
                        recorderViewModel.recorderState != RecorderState.IDLE -> Icons.Default.Stop
                        else -> Icons.Default.Mic
                    }, contentDescription = stringResource(
                        if (recorderViewModel.recorderState != RecorderState.IDLE) {
                            R.string.stop
                        } else {
                            R.string.record
                        }
                    )
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && recorderViewModel.recorderState != RecorderState.IDLE) {
                Spacer(modifier = Modifier.width(20.dp))
                ClickableIcon(
                    imageVector = if (recorderViewModel.recorderState == RecorderState.PAUSED) {
                        Icons.Default.PlayArrow
                    } else {
                        Icons.Default.Pause
                    }, contentDescription = stringResource(
                        if (recorderViewModel.recorderState == RecorderState.PAUSED) {
                            R.string.resume
                        } else {
                            R.string.pause
                        }
                    )
                ) {
                    if (recorderViewModel.recorderState == RecorderState.PAUSED) {
                        recorderViewModel.resumeRecording()
                    } else {
                        recorderViewModel.pauseRecording()
                    }
                }
            }
        }
    }
}