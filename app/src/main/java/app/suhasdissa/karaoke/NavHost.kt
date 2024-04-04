package app.suhasdissa.karaoke

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.karaoke.ui.screens.AudioRecorderScreen
import app.suhasdissa.karaoke.ui.screens.HomeScreen
import app.suhasdissa.karaoke.ui.screens.SettingsScreen
import app.suhasdissa.karaoke.ui.screens.VideoPlayerRecorderScreen
import app.suhasdissa.karaoke.ui.screens.YoutubeSearchScreen
import app.suhasdissa.karaoke.ui.screens.YtPlayerRecorderScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(onNavigate = { navController.navigateTo(it.route) })
        }
        composable(route = AudioRecorderScreen.route) {
            AudioRecorderScreen()
        }
        composable(
            route = VideoPlayerRecorderScreen.route
        ) {
            VideoPlayerRecorderScreen()
        }
        composable(
            route = YoutubeSearchScreen.route
        ) {
            YoutubeSearchScreen(onClickVideoCard = {
                navController.navigateTo("${YtPlayerRecorderScreen.route}/$it")
            })
        }

        composable(
            route = YtPlayerRecorderScreen.routeWithArgs,
            arguments = YtPlayerRecorderScreen.arguments
        ) {
            val vidId = it.arguments?.getString("id")
            if (vidId != null) {
                YtPlayerRecorderScreen(
                    vidId = vidId
                )
            }
        }

        composable(
            route = Settings.route
        ) {
            SettingsScreen()
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}
