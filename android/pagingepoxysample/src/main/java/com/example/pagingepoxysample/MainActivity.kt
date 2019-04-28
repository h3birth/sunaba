package com.example.pagingepoxysample

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        Log.d("key", info.metaData.getString("youtube_apikey"))
    }
}
