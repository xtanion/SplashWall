package com.xtanion.animewalls.api

import com.xtanion.animewalls.data.WallData
import retrofit2.Response

class ApiRepository {
    suspend fun getWall(page:Int):Response<MutableList<WallData>>{
        return RetrofitInstance.api.getWalls(page)
    }
}