package birth.h3.app.youtubeplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import birth.h3.app.youtubeplayer.api.YoutubeApiRepository
import birth.h3.app.youtubeplayer.model.YoutubeDataResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class MainViewModel : ViewModel(), KoinComponent {
    val repository: YoutubeApiRepository by inject()
    val disposable: CompositeDisposable = CompositeDisposable()
    val jsonKeys: String = BuildConfig.API_KEY
    val youtubeDataResponse: MutableLiveData<YoutubeDataResponse> = MutableLiveData()

    fun getYoutubeData(q: String) {
        disposable.addAll(
            repository.getYoutubeData(jsonKeys, q).subscribeOn(Schedulers.io()).subscribe({
                youtubeDataResponse.postValue(it)
            }, {
                Timber.e("error $it")
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
