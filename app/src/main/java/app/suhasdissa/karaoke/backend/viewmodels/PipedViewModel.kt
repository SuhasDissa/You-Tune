package app.suhasdissa.karaoke.backend.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhasdissa.karaoke.backend.api.PipedApi
import app.suhasdissa.karaoke.backend.models.Items
import kotlinx.coroutines.launch

class PipedViewModel : ViewModel() {
    sealed interface PipedSearchState {
        data class Success(val items: ArrayList<Items>) : PipedSearchState
        data class Error(val error: String) : PipedSearchState
        object Loading : PipedSearchState
        object Empty : PipedSearchState
    }

    var state: PipedSearchState by mutableStateOf(PipedSearchState.Empty)
    var suggestions: List<String> by mutableStateOf(arrayListOf())

    var search by mutableStateOf("")

    fun getSuggestions() {
        if (search.length < 3) return
        val insertedTextTemp = search
        Handler(
            Looper.getMainLooper()
        ).postDelayed(
            {
                if (insertedTextTemp == search) {
                    viewModelScope.launch {
                        runCatching {
                            suggestions =
                                PipedApi.retrofitService.getSuggestions(query = search).take(6)
                        }
                    }
                }
            },
            500L
        )
    }

    fun searchPiped() {
        if (search.isEmpty()) return;
        viewModelScope.launch {
            state = PipedSearchState.Loading
            state = try {
                val result = PipedApi.retrofitService.searchPiped(query = "$search karaoke")
                Log.d("Result", result.toString())
                PipedSearchState.Success(
                    result.items
                )
            } catch (e: Exception) {
                PipedSearchState.Error(e.toString())
            }
        }
    }
}
