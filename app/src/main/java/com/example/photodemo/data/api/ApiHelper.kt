package com.example.photodemo.data.api

import com.example.photodemo.data.model.UnsplashImage
import retrofit2.Response

interface ApiHelper {

    suspend fun getPhotos(
        clientId: String, pageNo: Int, pageSize: Int, orderBy: String
    ): Response<List<UnsplashImage>?>
}