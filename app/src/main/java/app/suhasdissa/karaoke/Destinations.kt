package app.suhasdissa.karaoke

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
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

object Settings : Destination {
    override val route = "settings"
}