package com.example.photodemo.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier

data class UnsplashImage(
    val id: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val likes: Int,
    val description: String?,
    @Json(name = "alt_description")
    val altDescription: String?,
    val urls: Urls,
    val color:String?
)
