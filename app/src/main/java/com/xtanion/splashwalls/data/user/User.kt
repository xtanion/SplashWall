package com.xtanion.splashwalls.data.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksX,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val social: Social,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
)

data class LinksX(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)

data class Social(
    val instagram_username: String,
    val paypal_email: Any,
    val portfolio_url: String,
    val twitter_username: String
)