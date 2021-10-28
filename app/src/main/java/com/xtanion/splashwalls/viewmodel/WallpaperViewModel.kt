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
    private val repository :ApiRepository

    init {
        repository = ApiRepository()

    }

    val wallPagerData = repository.getResult().cachedIn(viewModelScope)


}