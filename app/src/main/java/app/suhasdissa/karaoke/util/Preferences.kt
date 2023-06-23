package app.suhasdissa.karaoke.util

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private const val PREF_FILE_NAME = "RecordYou"
    lateinit var prefs: SharedPreferences

    const val targetFolderKey = "targetFolder"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

}
