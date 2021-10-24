package com.xtanion.animewalls.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xtanion.animewalls.api.ApiRepository
import com.xtanion.animewalls.api.RetrofitInstance
import com.xtanion.animewalls.data.WallData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WallpaperViewModel(application: Application):AndroidViewModel(application) {
    val wallpaperList = MutableLiveData<MutableList<WallData>>()
    private val oldWallpaperList = mutableListOf<WallData>()
    private val repository :ApiRepository
    private var currentPage:Int = 1

    init {
        repository = ApiRepository()
        getData()

    }

    fun addNewPage(){
        currentPage+=1
        //oldWallpaperList.addAll( wallpaperList.value!!)
        getData()
        Log.d("DataAdded","NewSize = ${oldWallpaperList.size} and page = $currentPage")
    }

    fun refreshData(){
        getData()
    }

    fun loadNewPage(page:Int){
        currentPage = page
        getData()
        Log.d("DataAdded","From Pager: NewSize = ${oldWallpaperList.size} and page = $currentPage")
    }

    private fun getData(){
        viewModelScope.launch {
            val response = try{
                repository.getWall(currentPage)
            }catch (e: IOException){
                Log.d("Response",e.toString())
                return@launch
            }catch (e: HttpException){
                Log.d("Response",e.toString())
                return@launch
            }

            if (response.isSuccessful && response.body()!=null){
//                wallpaperList.value = response.body()
                oldWallpaperList.addAll(response.body()!!)
                wallpaperList.value = oldWallpaperList
                Log.d("Response Successful","From page $currentPage")
            }
        }
    }

    val wallPagerData = repository.getResult()
}