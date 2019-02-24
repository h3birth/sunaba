package birth.h3.app.youtubeplayer

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



class MainActivity : AppCompatActivity() {

    val youtubeLink: String = "http://youtube.com/watch?v="
    val youTubeVideoID: String = "ogfYd705cRs"

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
                val downloadUrl = ytFiles.get(itag).url
                Timber.d("youtube url = "+downloadUrl)
            }
        }
    }.extract(youtubeLink+youTubeVideoID, true, true)


}
