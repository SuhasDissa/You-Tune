package app.suhasdissa.karaoke.backend.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel : ViewModel() {
    var vidUri: Uri by mutableStateOf(Uri.EMPTY)
}