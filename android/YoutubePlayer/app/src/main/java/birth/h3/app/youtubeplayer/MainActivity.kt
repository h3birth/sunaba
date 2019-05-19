package birth.h3.app.youtubeplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.huber.youtubeExtractor.YouTubeExtractor
import birth.h3.app.youtubeplayer.databinding.ActivityMainBinding
import birth.h3.app.youtubeplayer.model.Item
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.get

class MainActivity : AppCompatActivity(), MainController.ClickLister {
    val youtubeLink: String = "http://youtube.com/watch?v="
    var youTubeVideoID: String = ""
    var parseUrl: String = ""
    private val appName: String = "youtubeplayer"
    private var playerManager: PlayerManager? = null

    val viewModel: MainViewModel by viewModel()
    lateinit var controller: MainController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        observeItem()

        controller = MainController(this)
        recycle_view.adapter = controller.adapter
        recycle_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getYoutubeData(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeItem() {
        viewModel.youtubeDataResponse.observeForever {
            controller.setData(it.items)
        }
    }

    fun dlYoutube() = object : YouTubeExtractor(this) {
        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>, vMeta: VideoMeta) {
            if (ytFiles != null) {
                parseUrl = ytFiles.get(ytFiles.keyAt(0)).url
                setPlayer()
            }
        }
    }.extract(youtubeLink + youTubeVideoID, true, true)

    fun setPlayer() {
        if (playerManager != null) playerManager?.release()
        playerManager = PlayerManager(this, appName)
        playerManager?.playserNewInstatnce()
        playerManager?.buildMediaSource(Uri.parse(parseUrl), null)
        playerManager?.playerView = playerView
        playerManager?.execute()
    }

    override fun onItemClickListener(item: Item) {
        if (youTubeVideoID == item.id.videoId) return

        youTubeVideoID = item.id.videoId
        dlYoutube()
    }

    override fun onStop() {
        super.onStop()
        playerManager?.stop()
    }

    override fun onResume() {
        super.onResume()
        playerManager?.replay()
    }
}
