package app.suhasdissa.karaoke.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.suhasdissa.karaoke.AppNavHost

@Composable
fun KaraokeApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavHost(navController = navController, modifier = Modifier)
    }
}
