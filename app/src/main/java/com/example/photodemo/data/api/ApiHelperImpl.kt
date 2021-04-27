package com.example.photodemo.data.api

import com.example.photodemo.data.model.UnsplashImage
import retrofit2.Response
import retrofit2.http.Query

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPhotos(
        clientId: String,
        pageNo: Int,
        pageSize: Int,
        orderBy: String
    ): Response<List<UnsplashImage>?> = apiService.getPhotos(clientId, pageNo, pageSize, orderBy)

}