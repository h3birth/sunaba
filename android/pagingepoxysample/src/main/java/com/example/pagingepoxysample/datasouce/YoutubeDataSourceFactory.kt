package com.example.pagingepoxysample.datasouce

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pagingepoxysample.model.Item
import com.example.pagingepoxysample.repository.YoutubeApiRepository
import com.example.pagingepoxysample.repository.params.YoutubeQueryParams
import io.reactivex.disposables.CompositeDisposable

class YoutubeDataSourceFactory(
    private val query: YoutubeQueryParams,
    private val repository: YoutubeApiRepository,
    private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Item>() {

    val dataSource = MutableLiveData<YoutubeDataSource>()

    override fun create(): DataSource<Int, Item> {
        val dataSource = YoutubeDataSource(query, repository, compositeDisposable)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}
