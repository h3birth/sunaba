package com.example.pagingepoxysample.repository

import com.example.pagingepoxysample.model.YoutubeDataResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService{

    @GET("youtube/v3/search")
    fun getYoutubeData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("pageToken") pageToken: String,
        @Query("type") type: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int
    ): Single<YoutubeDataResponse>

    companion object {

        fun create(): YoutubeApiService {
            val baseUrl = "https://www.googleapis.com/"

            val okhttpClient = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okhttpClient)
                .build()

            return retrofit.create(YoutubeApiService::class.java)
        }
    }
}
