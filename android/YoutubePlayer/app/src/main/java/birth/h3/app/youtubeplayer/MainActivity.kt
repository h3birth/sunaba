package birth.h3.app.youtubeplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray
import at.huber.youtubeExtractor.YouTubeExtractor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val youtubeLink: String = "http://youtube.com/watch?v="
    val youTubeVideoID: String = "ogfYd705cRs"
    var parseUrl:String = ""
    private val appName: String = "sunaba"
    private lateinit var playerManager: PlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDashYoutube()
    }

    fun getDashYoutube() {
        GlobalScope.launch(Dispatchers.Main){
            async(){dlYoutube()}.await().let{
            }
        }
    }

    fun dlYoutube() = object : YouTubeExtractor(this) {
        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>, vMeta: VideoMeta) {
            if (ytFiles != null) {
                val itag = 17
                parseUrl = ytFiles.get(itag).url
                Timber.d("youtube url = "+parseUrl)
                setPlayer()
            }
        }
    }.extract(youtubeLink+youTubeVideoID, true, true)

    fun setPlayer() {
        playerManager = PlayerManager(this, appName)
        playerManager.playserNewInstatnce()
        playerManager.buildMediaSource(Uri.parse(parseUrl), null)
        playerManager.playerView = playerView
        playerManager.execute()
    }

}
