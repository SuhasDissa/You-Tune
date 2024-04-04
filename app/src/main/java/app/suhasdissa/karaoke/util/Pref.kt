package app.suhasdissa.karaoke.util

import android.content.SharedPreferences
import androidx.core.content.edit
import app.suhasdissa.karaoke.backend.models.PipedInstance
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Pref {
    private const val pipedInstanceKey = "SelectedPipedInstanceKey"

    lateinit var sharedPreferences: SharedPreferences

    val pipedInstances = listOf(
        PipedInstance(
            "kavin.rocks",
            "https://pipedapi.kavin.rocks/"
        ),
        PipedInstance(
            "lunar.icu",
            "https://piped-api.lunar.icu/"
        ),
        PipedInstance(
            "whatever.social",
            "https://watchapi.whatever.social/"
        ),
        PipedInstance(
            "tokhmi.xyz",
            "https://pipedapi.tokhmi.xyz/"
        ),
        PipedInstance(
            "mha.fi",
            "https://api-piped.mha.fi/"
        ),
        PipedInstance(
            "garudalinux.org",
            "https://piped-api.garudalinux.org/"
        ),
        PipedInstance(
            "piped.yt",
            "https://api.piped.yt/"
        )
    )

    val currentInstance
        get() = run {
            runCatching {
                val instanceJsonStr = sharedPreferences.getString(pipedInstanceKey, "").orEmpty()
                return@run json.decodeFromString<PipedInstance>(instanceJsonStr)
            }
            pipedInstances.first()
        }

    private val json = Json { ignoreUnknownKeys = true }

    fun setInstance(instance: PipedInstance) {
        val instanceJson = json.encodeToString(instance)
        sharedPreferences.edit(commit = true) { putString(pipedInstanceKey, instanceJson) }
    }
}