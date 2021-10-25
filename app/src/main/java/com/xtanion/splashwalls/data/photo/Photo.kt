package com.xtanion.splashwalls.data.photo

import android.os.Parcelable
import com.xtanion.splashwalls.data.user.User
import kotlinx.android.parcel.Parcelize

data class Photo(
    val alt_description: Any,
    val blur_hash: String,
    val categories: List<Any>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: Any,
    val sponsorship: Any,
    val topic_submissions: TopicSubmissions,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

@Parcelize
data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
): Parcelable

@Parcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable

data class TopicSubmissions(
    val nature: Nature,
    val wallpapers: Wallpapers
)
data class Nature(
    val approved_on: String,
    val status: String
)
data class Wallpapers(
    val approved_on: String,
    val status: String
)

