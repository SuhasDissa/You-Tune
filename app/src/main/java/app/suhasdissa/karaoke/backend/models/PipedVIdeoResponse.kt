package app.suhasdissa.karaoke.backend.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PipedVideoResponse(
    @SerialName("title") var title: String = "",
    @SerialName("hls") var hls: String? = null,
    @SerialName("dash") var dash: String? = null,
    @SerialName("audioStreams") var audioStreams: ArrayList<AudioStreams> = arrayListOf(),
    @SerialName("videoStreams") var videoStreams: ArrayList<VideoStreams> = arrayListOf()

)

@Serializable
data class AudioStreams(

    @SerialName("url") var url: String? = null,
    @SerialName("format") var format: String? = null,
    @SerialName("quality") var quality: String? = null,
    @SerialName("mimeType") var mimeType: String? = null,
    @SerialName("codec") var codec: String? = null,
    @SerialName("audioTrackId") var audioTrackId: String? = null,
    @SerialName("audioTrackName") var audioTrackName: String? = null,
    @SerialName("videoOnly") var videoOnly: Boolean? = null,
    @SerialName("bitrate") var bitrate: Int? = null,
    @SerialName("initStart") var initStart: Int? = null,
    @SerialName("initEnd") var initEnd: Int? = null,
    @SerialName("indexStart") var indexStart: Int? = null,
    @SerialName("indexEnd") var indexEnd: Int? = null,
    @SerialName("width") var width: Int? = null,
    @SerialName("height") var height: Int? = null,
    @SerialName("fps") var fps: Int? = null

)

@Serializable
data class VideoStreams(

    @SerialName("url") var url: String? = null,
    @SerialName("format") var format: String? = null,
    @SerialName("quality") var quality: String? = null,
    @SerialName("mimeType") var mimeType: String? = null,
    @SerialName("codec") var codec: String? = null,
    @SerialName("audioTrackId") var audioTrackId: String? = null,
    @SerialName("audioTrackName") var audioTrackName: String? = null,
    @SerialName("videoOnly") var videoOnly: Boolean? = null,
    @SerialName("bitrate") var bitrate: Int? = null,
    @SerialName("initStart") var initStart: Int? = null,
    @SerialName("initEnd") var initEnd: Int? = null,
    @SerialName("indexStart") var indexStart: Int? = null,
    @SerialName("indexEnd") var indexEnd: Int? = null,
    @SerialName("width") var width: Int? = null,
    @SerialName("height") var height: Int? = null,
    @SerialName("fps") var fps: Int? = null

)
