package com.bnyro.recorder.ui.models

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.bnyro.recorder.enums.RecorderState
import com.bnyro.recorder.services.AudioRecorderService
import com.bnyro.recorder.util.PermissionHelper

class RecorderModel : ViewModel() {
    private val audioPermission = arrayOf(Manifest.permission.RECORD_AUDIO)

    var recorderState by mutableStateOf(RecorderState.IDLE)
    var recordedTime by mutableStateOf(0L)

    private val handler = Handler(Looper.getMainLooper())

    private var recorderService: AudioRecorderService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            recorderService = (service as AudioRecorderService.LocalBinder).getService()
            recorderService?.onRecorderStateChanged = {
                recorderState = it
            }
            recorderService?.start()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            recorderService = null
        }
    }

    fun startAudioRecorder(context: Context) {
        if (!PermissionHelper.checkPermissions(context, audioPermission)) return

        val serviceIntent = Intent(context, AudioRecorderService::class.java)
        startRecorderService(context, serviceIntent)

        recordedTime = 0L
        handler.postDelayed(this::updateTime, 1000)
    }

    private fun startRecorderService(context: Context, intent: Intent) {
        runCatching {
            context.unbindService(connection)
        }
        listOf(AudioRecorderService::class.java).forEach {
            runCatching {
                context.stopService(Intent(context, it))
            }
        }
        ContextCompat.startForegroundService(context, intent)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun stopRecording() {
        recorderService?.onDestroy()
        recordedTime = 0L
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun pauseRecording() {
        recorderService?.pause()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun resumeRecording() {
        recorderService?.resume()
        handler.postDelayed(this::updateTime, 1000)
    }

    private fun updateTime() {
        if (recorderState != RecorderState.ACTIVE) return
        recordedTime++
        handler.postDelayed(this::updateTime, 1000)
    }
}
