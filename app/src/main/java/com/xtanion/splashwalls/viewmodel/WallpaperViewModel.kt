package com.xtanion.splashwalls.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.xtanion.splashwalls.api.ApiRepository
import com.xtanion.splashwalls.data.photo.Photo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WallpaperViewModel(application: Application):AndroidViewModel(application) {
    val wallpaperList = MutableLiveData<MutableList<Photo>>()
    private val oldWallpaperList = mutableListOf<Photo>()
    private val repository :ApiRepository
    private var currentPage:Int = 1

    init {
        repository = ApiRepository()

    }

    val wallPagerData = repository.getResult().cachedIn(viewModelScope)

//    private fun getData(){
//        viewModelScope.launch {
//            val response = try{
//                repository.getWall(currentPage)
//            }catch (e: IOException){
//                Log.d("Response",e.toString())
//                return@launch
//            }catch (e: HttpException){
//                Log.d("Response",e.toString())
//                return@launch
//            }
//
//            if (response.isSuccessful && response.body()!=null){
////                wallpaperList.value = response.body()
//                oldWallpaperList.addAll(response.body()!!)
//                wallpaperList.value = oldWallpaperList
//                Log.d("Response Successful","From page $currentPage")
//            }
//        }
//    }

}