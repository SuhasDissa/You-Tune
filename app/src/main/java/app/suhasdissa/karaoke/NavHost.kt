package app.suhasdissa.karaoke

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.karaoke.ui.screens.*


@Composable
fun AppNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = Home.route, modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(onPlayerClick = {
                navController.navigateTo(PlayerScreen.route)
            },
                onRecordClick = { navController.navigateTo(AudioRecorderScreen.route) },
                onVideoRecordClick = {
                    navController.navigateTo(VideoPlayerRecorderScreen.route)
                },
                onYoutubeSearchClick = {
                    navController.navigateTo(YoutubeSearchScreen.route)
                })
        }
        composable(route = PlayerScreen.route) {
            PlayerScreen()
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
            YoutubeSearchScreen(onClickVideoCard = { navController.navigateTo("${YtPlayerRecorderScreen.route}/$it") })
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
        /*
        composable(route = FunnyVideoView.route) {
            FunnyVideoScreen(onClickTextCard = { url ->
                navController.navigateTo("${VideoPlayer.route}/$url")
            })
        }
        composable(route = FeedView.route) {
            FeedScreen(onClickTextCard = { text ->
                navController.navigateTo("${TextViewer.route}/$text")
            })
        }
        composable(
            route = PhotoView.routeWithArgs, arguments = PhotoView.arguments
        ) {
            val imgurl = it.arguments?.getString("url")
            if (imgurl != null) {
                PhotoView(imgurl)
            }

        }

        composable(
            route = VideoPlayer.routeWithArgs, arguments = VideoPlayer.arguments
        ) {
            val url = it.arguments?.getString("url")
            if (url != null) {
                VideoPlayer(url)
            }
        }
        composable(
            route = TextViewer.routeWithArgs, arguments = TextViewer.arguments
        ) {
            val text = it.arguments?.getString("text")
            if (text != null) {
                TextView(text)
            }
        }
        */
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}