package com.example.photodemo.data.repository

import com.example.photodemo.data.api.ApiHelper

class MainRepository(private val clientId: String,private val apiHelper: ApiHelper) {

    suspend fun getPhotos( pageNo: Int, pageSize: Int, orderBy: String
    ) = apiHelper.getPhotos(clientId, pageNo, pageSize, orderBy)

}