package com.xtanion.animewalls.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomWallData(
    val color: String,
    val height: Int,
    val id: String,
    val likes: Int,
    val updated_at: String,
    val urls: Urls,
    val userName: String,
    val userId: String,
    val width: Int
):Parcelable
