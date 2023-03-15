package app.suhasdissa.karaoke.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import app.suhasdissa.karaoke.enums.ThemeMode
import app.suhasdissa.karaoke.ui.models.ThemeModel
import app.suhasdissa.karaoke.ui.theme.RecordYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeModel: ThemeModel = ViewModelProvider(this).get()
        setContent {
            RecordYouTheme(
                when (val mode = themeModel.themeMode) {
                    ThemeMode.SYSTEM -> isSystemInDarkTheme()
                    else -> mode == ThemeMode.DARK
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    KaraokeApp()
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }
}
