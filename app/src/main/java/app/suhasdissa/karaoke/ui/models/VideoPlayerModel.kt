package app.suhasdissa.karaoke.ui.models

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VideoPlayerModel : ViewModel() {
    var vidUri: Uri by mutableStateOf(Uri.EMPTY)
}