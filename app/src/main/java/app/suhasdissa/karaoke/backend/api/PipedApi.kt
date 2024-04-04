package app.suhasdissa.karaoke.backend.api

import app.suhasdissa.karaoke.backend.models.PipedInstance
import app.suhasdissa.karaoke.backend.models.PipedSearchResult
import app.suhasdissa.karaoke.backend.models.PipedVideoResponse
import app.suhasdissa.karaoke.util.Pref
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .baseUrl("https://pipedapi.kavin.rocks/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface ApiService {
    @GET("https://{instance}/search")
    suspend fun searchPiped(
        @Path("instance") instance: String = Pref.currentInstance.netLoc,
        @Query("filter") filter: String = "videos",
        @Query("q") query: String
    ): PipedSearchResult

    @GET("https://{instance}/streams/{videoid}")
    suspend fun getStreams(
        @Path("instance") instance: String = Pref.currentInstance.netLoc,
        @Path("videoid") vidId: String
    ): PipedVideoResponse

    @GET("https://{instance}/suggestions")
    suspend fun getSuggestions(
        @Path("instance") instance: String = Pref.currentInstance.netLoc,
        @Query("query") query: String
    ): List<String>

    @GET("https://piped-instances.kavin.rocks")
    suspend fun getInstanceList(): List<PipedInstance>
}

object PipedApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
