package com.xtanion.animewalls.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.xtanion.animewalls.adapter.WallPagingSource
import com.xtanion.animewalls.data.WallData
import retrofit2.Response

class ApiRepository {
    suspend fun getWall(page:Int):Response<MutableList<WallData>>{
        return RetrofitInstance.api.getWalls(page)
    }

    fun getResult() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {WallPagingSource(RetrofitInstance.api)}
    ).liveData
}