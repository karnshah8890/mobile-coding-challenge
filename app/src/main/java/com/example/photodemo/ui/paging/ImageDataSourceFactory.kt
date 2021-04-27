package com.example.photodemo.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.photodemo.data.model.UnsplashImage
import com.example.photodemo.data.repository.MainRepository
import com.example.photodemo.util.NetworkHelper
import kotlinx.coroutines.CoroutineScope

class ImageDataSourceFactory(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, UnsplashImage>() {
    val photoDataSourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, UnsplashImage> {
        val photoDataSource = ImageDataSource(mainRepository, networkHelper, scope)
        photoDataSourceLiveData.postValue(photoDataSource)
        return photoDataSource
    }
}