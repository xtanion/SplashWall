package com.xtanion.animewalls.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xtanion.animewalls.api.ApiRepository
import com.xtanion.animewalls.api.WallpaperApi
import com.xtanion.animewalls.data.WallData
import com.xtanion.animewalls.viewmodel.WallpaperViewModel

class WallPagingSource(private val wallApi:WallpaperApi): PagingSource<Int,WallData>() {

    override fun getRefreshKey(state: PagingState<Int, WallData>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallData> {
        val page = params.key ?: 1

        return try {
            val data = wallApi.getWalls(page)
            val wallList = data.body()?.toList()

            LoadResult.Page(
                data = wallList!!,
                prevKey = if (page==1)null else page-1,
                nextKey = if (data.body().isNullOrEmpty())null else page+1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}