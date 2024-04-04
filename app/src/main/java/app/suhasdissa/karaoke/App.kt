package app.suhasdissa.karaoke

import android.app.Application
import app.suhasdissa.karaoke.util.Pref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Pref.sharedPreferences = this.getSharedPreferences("karaoke", MODE_PRIVATE)
    }
}
