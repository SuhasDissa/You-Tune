package app.suhasdissa.karaoke

import androidx.navigation.NavType
import androidx.navigation.navArgument


interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}

object PlayerScreen : Destination {
    override val route = "playerscreen"
}

object AudioRecorderScreen : Destination {
    override val route = "recorderscreen"
}

object VideoPlayerRecorderScreen : Destination {
    override val route = "videoplayerrecorder"
}

object YoutubeSearchScreen : Destination {
    override val route = "youtubesearch"
}

object YtPlayerRecorderScreen : Destination {
    override val route = "ytplayer"
    val routeWithArgs = "$route/{id}"
    val arguments = listOf(navArgument("id") { type = NavType.StringType })
}
/*
object FunnyVideoView : Destination {
    override val route = "funvidview"
}

object FeedView : Destination {
    override val route = "feedview"
}

object Settings : Destination {
    override val route = "settings"
}

object About : Destination {
    override val route = "about"
}

object PhotoView : Destination {
    override val route = "memescreen"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(navArgument("url") { type = NavType.StringType })
}

object VideoPlayer : Destination {
    override val route = "video"
    val routeWithArgs = "$route/{url}"
    val arguments = listOf(navArgument("url") { type = NavType.StringType })
}

object TextViewer : Destination {
    override val route = "textviewer"
    val routeWithArgs = "$route/{text}"
    val arguments = listOf(navArgument("text") { type = NavType.StringType })
}

*/