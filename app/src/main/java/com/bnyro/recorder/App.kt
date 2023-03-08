package com.bnyro.recorder

import android.app.Application
import com.bnyro.recorder.util.Preferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
    }
}
