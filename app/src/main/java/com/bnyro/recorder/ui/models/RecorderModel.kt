package com.bnyro.recorder.ui.models

import android.Manifest
import android.content.*
import android.media.MediaRecorder
import android.os.*
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bnyro.recorder.enums.RecorderState
import com.bnyro.recorder.util.PermissionHelper
import com.bnyro.recorder.util.PlayerHelper
import com.bnyro.recorder.util.StorageHelper

class RecorderModel : ViewModel() {
    private val audioPermission = arrayOf(Manifest.permission.RECORD_AUDIO)
    var recorder: MediaRecorder? = null

    var recorderState by mutableStateOf(RecorderState.IDLE)
    var recordedTime by mutableStateOf(0L)

    var onStateChange: (RecorderState) -> Unit = {}

    private val handler = Handler(Looper.getMainLooper())

    fun startAudioRecorder(context: Context) {
        if (!PermissionHelper.checkPermissions(context, audioPermission)) return

        recorder = PlayerHelper.newRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
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
    }

    fun stopRecording() {
        recorderState = RecorderState.IDLE
        onStateChange(recorderState)

        recorder?.stop()
        recorder?.reset()
        recorder?.release()
        recorder = null
        recordedTime = 0L
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
        onStateChange(recorderState)
    }

    private fun updateTime() {
        if (recorderState != RecorderState.ACTIVE) return
        recordedTime++
        handler.postDelayed(this::updateTime, 1000)
    }
}
