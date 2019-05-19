package birth.h3.app.youtubeplayer

import android.content.Context
import android.util.JsonReader
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import birth.h3.app.youtubeplayer.api.YoutubeApiRepository
import birth.h3.app.youtubeplayer.model.JsonKeys
import birth.h3.app.youtubeplayer.model.YoutubeDataResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber
import java.io.InputStream
import java.io.InputStreamReader

class MainViewModel : ViewModel(), KoinComponent {
    val context: Context by inject()
    val repository: YoutubeApiRepository by inject()
    val jsonKeys: String = BuildConfig.API_KEY
    val youtubeDataResponse: MutableLiveData<YoutubeDataResponse> = MutableLiveData()

    fun getYoutubeData(q: String) {
        repository.getYoutubeData(jsonKeys, q).subscribeOn(Schedulers.io()).subscribe({
            youtubeDataResponse.postValue(it)
        }, {
            Timber.e("error $it")
        })
    }

    fun jsonParse() {
        val inputStream: InputStream = context.resources.assets.open("keys.json")
        val jsonReader: JsonReader = JsonReader(InputStreamReader(inputStream))!!
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<JsonKeys> = moshi.adapter(JsonKeys::class.java)
    }
}
