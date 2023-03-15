package app.suhasdissa.karaoke.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.karaoke.ui.models.RecorderModel

@Composable
fun AudioVisualizer(
    modifier: Modifier = Modifier
) {
    val viewModel: RecorderModel = viewModel()
    val maxAmplitude = 3000
    val primary = MaterialTheme.colorScheme.primary
    Canvas(modifier = modifier) {
        val height = this.size.height / 2
        val width = this.size.width * 0.75f
        viewModel.recordedAmplitudes.forEachIndexed { index, amplitude ->
            translate(width, height) {
                val boxHeight = (height * (amplitude.toFloat() / maxAmplitude))
                drawRoundRect(
                    color = primary,
                    topLeft = Offset(
                        15f * (index - viewModel.recordedAmplitudes.size),
                        -boxHeight / 2
                    ),
                    size = Size(10f, boxHeight)
                )
            }
        }
    }
}