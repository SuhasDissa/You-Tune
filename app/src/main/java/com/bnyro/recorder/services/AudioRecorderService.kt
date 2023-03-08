package com.bnyro.recorder.services

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Binder
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresApi
import androidx.core.app.ServiceCompat
import com.bnyro.recorder.enums.RecorderState
import com.bnyro.recorder.util.PlayerHelper
import com.bnyro.recorder.util.StorageHelper

class AudioRecorderService : Service() {

    private val binder = LocalBinder()
    var recorder: MediaRecorder? = null
    var fileDescriptor: ParcelFileDescriptor? = null
    private var recorderState: RecorderState = RecorderState.IDLE
    var onRecorderStateChanged: (RecorderState) -> Unit = {}
    inner class LocalBinder : Binder() {
        // Return this instance of [BackgroundMode] so clients can call public methods
        fun getService(): AudioRecorderService = this@AudioRecorderService
    }

    override fun onBind(intent: Intent?) = binder

    fun start() {
        recorder = PlayerHelper.newRecorder(this).apply {
            setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)

           val outputFile = StorageHelper.getOutputFile(
                this@AudioRecorderService, "m4a"
            )
            fileDescriptor = contentResolver.openFileDescriptor(outputFile.uri, "w")
            setOutputFile(fileDescriptor?.fileDescriptor)

            prepare()
            start()
        }
        recorderState = RecorderState.ACTIVE
        onRecorderStateChanged(recorderState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun pause() {
        recorder?.pause()
        recorderState = RecorderState.PAUSED
        onRecorderStateChanged(recorderState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun resume() {
        recorder?.resume()
        recorderState = RecorderState.ACTIVE
        onRecorderStateChanged(recorderState)

    }

    override fun onDestroy() {

        recorderState = RecorderState.IDLE
        onRecorderStateChanged(recorderState)

        recorder?.stop()
        recorder?.release()

        recorder = null
        fileDescriptor?.close()

        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }
}
