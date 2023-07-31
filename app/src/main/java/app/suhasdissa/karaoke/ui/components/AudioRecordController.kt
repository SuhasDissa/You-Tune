package app.suhasdissa.karaoke.ui.components

import android.os.Build
import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        recorderViewModel.recordedTime.let {
            Text(
                text = DateUtils.formatElapsedTime(it),
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        Color(0xA8EE665B)
                    } else {
                        Color(
                            0xffdd6f62
                        )
                    },
                    contentColor = Color.White
                ),
                shape = CircleShape
            ) {
                val buttonDescription = stringResource(
                    if (recorderViewModel.recorderState != RecorderState.IDLE) {
                        R.string.stop
                    } else {
                        R.string.record
                    }
                )
                IconButton(
                    onClick = {
                        when {
                            recorderViewModel.recorderState != RecorderState.IDLE -> recorderViewModel.stopRecording()
                            else -> recorderViewModel.startAudioRecorder(context)
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .semantics { contentDescription = buttonDescription }
                ) {
                    when {
                        recorderViewModel.recorderState != RecorderState.IDLE -> {
                            Icon(
                                Icons.Default.Stop,
                                modifier = Modifier.size(36.dp),
                                contentDescription = stringResource(R.string.pause)
                            )
                        }

                        else -> {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x9FFFFFFF))
                            )
                            Box(
                                Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x9FFFFFFF))
                            )
                        }
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && recorderViewModel.recorderState != RecorderState.IDLE) {
                Spacer(modifier = Modifier.width(20.dp))
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    ClickableIcon(
                        imageVector = if (recorderViewModel.recorderState == RecorderState.PAUSED) {
                            Icons.Default.PlayArrow
                        } else {
                            Icons.Default.Pause
                        },
                        contentDescription = stringResource(
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
}
