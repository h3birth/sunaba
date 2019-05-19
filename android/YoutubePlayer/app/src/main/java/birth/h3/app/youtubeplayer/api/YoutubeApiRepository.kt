package birth.h3.app.youtubeplayer.api

class YoutubeApiRepository {
    val apiService = YoutubeApiService.create()

    fun getYoutubeData(key: String, q: String) = apiService.getYoutubeData(key, "video", "snippet", q, 50)
}
