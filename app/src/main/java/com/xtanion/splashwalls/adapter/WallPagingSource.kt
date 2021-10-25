package com.xtanion.splashwalls.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xtanion.splashwalls.api.WallpaperApi
import com.xtanion.splashwalls.data.photo.Photo

class WallPagingSource(private val wallApi:WallpaperApi): PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
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