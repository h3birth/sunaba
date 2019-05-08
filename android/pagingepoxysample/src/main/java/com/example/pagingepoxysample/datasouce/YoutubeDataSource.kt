package com.example.pagingepoxysample.datasouce

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pagingepoxysample.model.Item
import com.example.pagingepoxysample.repository.YoutubeApiRepository
import com.example.pagingepoxysample.repository.params.YoutubeQueryParams
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class YoutubeDataSource(
    private val query: YoutubeQueryParams,
    private val repository: YoutubeApiRepository,
    private val dispose: CompositeDisposable
): PageKeyedDataSource<Int, Item>() {
    val isLoding: MutableLiveData<Boolean> = MutableLiveData()
    val totalCount: MutableLiveData<Int> = MutableLiveData()
    val nextPageToken: MutableLiveData<String> = MutableLiveData()

    // 始めのデータを取得
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {
        isLoding.postValue(true)
        repository.getYoutubeData(query.key, query.q, query.pageToken).subscribeOn(Schedulers.io()).subscribe({
            totalCount.postValue(it.pageInfo.totalResults)
            nextPageToken.postValue(it.nextPageToken)
            isLoding.postValue(false)
            callback.onResult(it.items, null, 2)
        },{
            Log.e("DataSource", it.stackTrace.toString())
        }).addTo(dispose)
    }

    // 次のページのデータを取得
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        isLoding.postValue(true)
        repository.getYoutubeData(query.key, query.q, query.pageToken).subscribeOn(Schedulers.io()).subscribe({
            nextPageToken.postValue(it.nextPageToken)
            isLoding.postValue(false)
            callback.onResult(it.items, params.key + 1)
        },{
            Log.e("DataSource", it.stackTrace.toString())
        }).addTo(dispose)
    }

    // 前のページのデータを取得（今回は使用しない）
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {}

}
