package birth.h3.app.youtubeplayer.model

data class YoutubeDataResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)
