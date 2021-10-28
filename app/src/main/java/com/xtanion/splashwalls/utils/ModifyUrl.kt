package com.xtanion.splashwalls.utils

import android.util.Log

class ModifyUrl(private val rawUrl: String) {
    fun small(): String {
        Log.d("URL_MODIFIER", DISPLAY_WIDTH.toString())
        return "$rawUrl&q=80&w=480"
    }
    fun high(): String{
        val highUrl =  "$rawUrl&q=80&w=1920"
        Log.d("URL_MODIFIER",highUrl)
        return highUrl
    }
}