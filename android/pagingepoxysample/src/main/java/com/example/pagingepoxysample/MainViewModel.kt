package com.example.pagingepoxysample

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingepoxysample.datasouce.YoutubeDataSource
import com.example.pagingepoxysample.datasouce.YoutubeDataSourceFactory
import com.example.pagingepoxysample.model.Item
import com.example.pagingepoxysample.repository.YoutubeApiRepository
import com.example.pagingepoxysample.repository.params.YoutubeQueryParams
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(): ViewModel() {
    private val repository: YoutubeApiRepository = YoutubeApiRepository()
    private val disposable: CompositeDisposable = CompositeDisposable()

    // query
    val key: String = BuildConfig.API_KEY
    val q: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }

    // paging
    val youtubeDataSourceFactory by lazy { YoutubeDataSourceFactory(YoutubeQueryParams(key, q.value!!, nextPageToken?.value!!),repository, disposable) }
    val itemsBuilder by lazy { LivePagedListBuilder(youtubeDataSourceFactory, 5) }
    val items: LiveData<PagedList<Item>> by lazy { itemsBuilder.build() }
    val totalCount: LiveData<Int>
        get() = Transformations.switchMap(youtubeDataSourceFactory.dataSource, YoutubeDataSource::totalCount)
    val isLoading: LiveData<Boolean>
        get() = Transformations.switchMap(youtubeDataSourceFactory.dataSource, YoutubeDataSource::isLoding)
    val nextPageToken: LiveData<String>?
        get() = Transformations.switchMap(youtubeDataSourceFactory.dataSource, YoutubeDataSource::nextPageToken)

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
