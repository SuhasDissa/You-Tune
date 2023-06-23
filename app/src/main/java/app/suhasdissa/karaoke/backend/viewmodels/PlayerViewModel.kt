package app.suhasdissa.karaoke.backend.viewmodels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhasdissa.karaoke.util.PlayerHelper
import app.suhasdissa.karaoke.util.StorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PlayerViewModel : ViewModel() {
    private var player: MediaPlayer? = null
    val files = mutableStateListOf<DocumentFile>()
    private var onFinish: () -> Unit = {}

    fun loadFiles(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val f = getAvailableFiles(context)
                files.clear()
                files.addAll(f)
            }
        }
    }

    fun startPlaying(context: Context, file: DocumentFile, onEnded: () -> Unit) {
        onFinish.invoke()
        onFinish = onEnded

        stopPlaying()

        player = getMediaPlayer().apply {
            try {
                context.contentResolver.openFileDescriptor(file.uri, "r")?.use {
                    setDataSource(it.fileDescriptor)
                }
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("reading file", e.toString())
            }
        }
        player?.setOnCompletionListener {
            onFinish.invoke()
        }
    }

    fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun getAvailableFiles(context: Context): List<DocumentFile> {
        return StorageHelper.getOutputDir(context).listFiles().filter { it.isFile }.toList()
    }

    private fun getMediaPlayer(): MediaPlayer {
        return MediaPlayer().apply {
            setAudioAttributes(PlayerHelper.getAudioAttributes())
        }
    }
}
