package app.suhasdissa.karaoke.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import java.text.SimpleDateFormat
import java.util.*

object StorageHelper {
    @SuppressLint("SimpleDateFormat")
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")

    fun getOutputFile(context: Context, extension: String): String {
        val currentTime = dateTimeFormat.format(Calendar.getInstance().time)
        val dir = context.getExternalFilesDir(null) ?: context.filesDir
        return dir.absolutePath + "/$currentTime.$extension"
    }

    fun getOutputDir(context: Context): DocumentFile {
        val prefDir = Preferences.prefs.getString(Preferences.targetFolderKey, "")
        val audioDir = when {
            prefDir.isNullOrBlank() -> {
                val dir = context.getExternalFilesDir(null) ?: context.filesDir
                DocumentFile.fromFile(dir)
            }

            else -> DocumentFile.fromTreeUri(context, Uri.parse(prefDir))
        }
        return audioDir!!
    }
}
