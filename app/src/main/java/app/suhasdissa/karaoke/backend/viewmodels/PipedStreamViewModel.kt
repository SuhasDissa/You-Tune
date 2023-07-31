package app.suhasdissa.karaoke.backend.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhasdissa.karaoke.backend.api.PipedApi
import app.suhasdissa.karaoke.backend.models.PipedVideoResponse
import kotlinx.coroutines.launch

class PipedStreamViewModel : ViewModel() {
    sealed interface VideoStreamState {
        data class Success(val response: PipedVideoResponse) : VideoStreamState
        data class Error(val error: String) : VideoStreamState
        object Loading : VideoStreamState
    }

    var state: VideoStreamState by mutableStateOf(VideoStreamState.Loading)

    fun getStreams(vidId: String) {
        viewModelScope.launch {
            state = VideoStreamState.Loading
            state = try {
                VideoStreamState.Success(
                    PipedApi.retrofitService.getStreams(vidId)
                )
            } catch (e: Exception) {
                VideoStreamState.Error(e.toString())
            }
        }
    }
}
