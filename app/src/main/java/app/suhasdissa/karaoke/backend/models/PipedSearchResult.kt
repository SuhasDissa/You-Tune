package app.suhasdissa.karaoke.backend.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PipedSearchResult(
    @SerialName("items") var items: ArrayList<Items> = arrayListOf()
)

@Serializable
data class Items(
    @SerialName("url") var url: String = "",
    @SerialName("title") var title: String = "",
    @SerialName("thumbnail") var thumbnail: String = "",
    @SerialName("duration") var duration: Int = 0
){
    val id
        get() = url.replace("/watch?v=", "")
}