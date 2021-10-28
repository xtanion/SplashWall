package com.xtanion.splashwalls.downloads

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun applyWallpaper(context: Context,bmp:Bitmap?,view: View){
    withContext(Dispatchers.IO){
        val wallManager = WallpaperManager.getInstance(context)
        if (bmp!=null){
            try {
                wallManager.setBitmap(bmp)
                Snackbar.make(view, "Wallpaper Applied",Snackbar.LENGTH_SHORT).show()
            }catch (e:Exception){
                Log.d("Wallpaper",e.toString())
                Snackbar.make(view, "Failed to set Wallpaper",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}