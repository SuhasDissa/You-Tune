package app.suhasdissa.karaoke.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.suhasdissa.karaoke.ui.components.AudioRecordController
import app.suhasdissa.karaoke.ui.components.AudioVisualizer

@Composable
fun AudioRecorderScreen() {
    Scaffold { pV ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pV),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AudioVisualizer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            AudioRecordController()

        }
    }
}
