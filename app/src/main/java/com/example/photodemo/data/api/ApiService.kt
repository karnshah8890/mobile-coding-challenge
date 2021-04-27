package com.example.photodemo.data.api

import com.example.photodemo.data.model.UnsplashImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/photos/")
    suspend fun getPhotos(
        @Query("client_id") clientId: String, @Query("page") page: Int,
        @Query("per_page") perPage: Int, @Query("order_by") orderBy: String
    ): Response<List<UnsplashImage>?>

}