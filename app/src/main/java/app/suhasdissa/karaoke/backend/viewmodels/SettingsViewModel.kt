package app.suhasdissa.karaoke.backend.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.suhasdissa.karaoke.backend.api.PipedApi
import app.suhasdissa.karaoke.util.Pref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel() : ViewModel() {
    var instances by mutableStateOf(Pref.pipedInstances)

    suspend fun loadInstances() = runCatching {
        instances = withContext(Dispatchers.IO) {
            PipedApi.retrofitService.getInstanceList()
        }
    }
}