package com.xtanion.splashwalls.api

import com.xtanion.splashwalls.BuildConfig
import com.xtanion.splashwalls.data.photo.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WallpaperApi {
    @Headers(
        "Authorization: Client-ID ${BuildConfig.UNSPLASH_KEY}"
    )
    @GET("topics/wallpapers/photos")
    suspend fun getWalls(
        @Query("page") page:Int,
        @Query("per_page")per_page:Int=20): Response<MutableList<Photo>>
    //?client_id=g7pCnQVE4Y2DxlMqvwt2AAal-HzvbZdMsZRNqd8c9hU&page=1
}