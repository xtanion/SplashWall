package com.xtanion.animewalls.api

import com.xtanion.animewalls.data.WallData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface WallpaperApi {
    @Headers(
        "Authorization: Client-ID g7pCnQVE4Y2DxlMqvwt2AAal-HzvbZdMsZRNqd8c9hU"
    )
    @GET("photos")
    suspend fun getWalls(
        @Query("page") page:Int,
        @Query("per_page")per_page:Int=20): Response<MutableList<WallData>>
    //?client_id=g7pCnQVE4Y2DxlMqvwt2AAal-HzvbZdMsZRNqd8c9hU&page=1
}