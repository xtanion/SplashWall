package com.xtanion.splashwalls.data.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallpaperData(
    val color: String,
    val height: Int,
    val id: String,
    val likes: Int,
    val updated_at: String,
    val urls: Urls,
    val links: Links,
    val userName: String,
    val userId: String,
    val width: Int
):Parcelable
