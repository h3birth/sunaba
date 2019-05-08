package com.example.pagingepoxysample.repository

class YoutubeApiRepository{
    val apiService = YoutubeApiService.create()

    fun getYoutubeData(key: String, q: String, pageToken: String) = apiService.getYoutubeData(key,  q, pageToken,"video", "snippet", 5)
}
