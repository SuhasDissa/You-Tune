package app.suhasdissa.karaoke

import android.app.Application
import app.suhasdissa.karaoke.util.Preferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
    }
}
