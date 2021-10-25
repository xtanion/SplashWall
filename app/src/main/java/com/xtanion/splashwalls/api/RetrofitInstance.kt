package com.xtanion.splashwalls.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: WallpaperApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WallpaperApi::class.java)
        //https://api.unsplash.com/topics/wallpapers/
    }
}