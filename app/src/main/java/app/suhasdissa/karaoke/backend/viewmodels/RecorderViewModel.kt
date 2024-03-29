package app.suhasdissa.karaoke.backend.viewmodels

import android.Manifest
import android.content.*
import android.media.MediaRecorder
import android.os.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.suhasdissa.karaoke.backend.enums.RecorderState
import app.suhasdissa.karaoke.util.PermissionHelper
import app.suhasdissa.karaoke.util.PlayerHelper
import app.suhasdissa.karaoke.util.StorageHelper

class RecorderViewModel : ViewModel() {
    private val audioPermission = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var recorder: MediaRecorder? = null

    var recorderState by mutableStateOf(RecorderState.IDLE)
    var recordedTime by mutableStateOf(0L)
    val recordedAmplitudes = mutableStateListOf<Int>()

    var onStateChange: (RecorderState) -> Unit = {}

    private val handler = Handler(Looper.getMainLooper())

    fun startAudioRecorder(context: Context) {
        if (!PermissionHelper.checkPermissions(context, audioPermission)) return

        recorder = PlayerHelper.newRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(StorageHelper.getOutputFile(context, "m4a"))

            prepare()
            start()
        }
        recorderState = RecorderState.ACTIVE
        onStateChange(recorderState)

        recordedTime = 0L
        handler.postDelayed(this::updateTime, 1000)
        handler.postDelayed(this::updateAmplitude, 100)
    }

    fun stopRecording(context: Context? = null) {
        recorderState = RecorderState.IDLE
        onStateChange(recorderState)

        recorder?.stop()
        recorder?.reset()
        recorder?.release()
        recorder = null
        recordedTime = 0L
        if (context != null) {
            Toast.makeText(context, "Recording Stopped", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun pauseRecording() {
        recorder?.pause()
        recorderState = RecorderState.PAUSED
        onStateChange(recorderState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun resumeRecording() {
        recorder?.resume()
        recorderState = RecorderState.ACTIVE
        handler.postDelayed(this::updateTime, 1000)
        handler.postDelayed(this::updateAmplitude, 100)
        onStateChange(recorderState)
    }

    private fun updateAmplitude() {
        if (recorderState != RecorderState.ACTIVE) return

        recorder?.maxAmplitude?.let {
            if (recordedAmplitudes.size >= 90) recordedAmplitudes.removeAt(0)
            recordedAmplitudes.add(it)
        }

        handler.postDelayed(this::updateAmplitude, 100)
    }

    private fun updateTime() {
        if (recorderState != RecorderState.ACTIVE) return
        recordedTime++
        handler.postDelayed(this::updateTime, 1000)
    }
}
