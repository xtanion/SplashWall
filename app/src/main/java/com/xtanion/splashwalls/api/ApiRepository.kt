package com.xtanion.splashwalls.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.xtanion.splashwalls.adapter.WallPagingSource
import com.xtanion.splashwalls.data.photo.Photo
import retrofit2.Response

class ApiRepository {
    suspend fun getWall(page:Int):Response<MutableList<Photo>>{
        return RetrofitInstance.api.getWalls(page)
    }

    fun getResult() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 80,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {WallPagingSource(RetrofitInstance.api)}
    ).liveData
}