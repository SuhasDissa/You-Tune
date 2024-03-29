package app.suhasdissa.karaoke.backend.viewmodels

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

    fun getSuggestions(query: String) {
        viewModelScope.launch {
            runCatching {
                suggestions = PipedApi.retrofitService.getSuggestions(query)
            }
        }
    }

    fun searchPiped(query: String) {
        viewModelScope.launch {
            state = PipedSearchState.Loading
            state = try {
                PipedSearchState.Success(
                    PipedApi.retrofitService.searchPiped("$query karaoke").items
                )
            } catch (e: Exception) {
                PipedSearchState.Error(e.toString())
            }
        }
    }
}
