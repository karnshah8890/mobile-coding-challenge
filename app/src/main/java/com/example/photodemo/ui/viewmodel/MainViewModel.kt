package com.example.photodemo.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photodemo.data.model.UnsplashImage
import com.example.photodemo.data.repository.MainRepository
import com.example.photodemo.ui.paging.ImageDataSource
import com.example.photodemo.ui.paging.ImageDataSourceFactory
import com.example.photodemo.util.Constants
import com.example.photodemo.util.NetworkHelper
import com.example.photodemo.util.Resource

class MainViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val imagesDataSourceFactory: ImageDataSourceFactory =
        ImageDataSourceFactory(mainRepository, networkHelper, viewModelScope)
    val photos: LiveData<PagedList<UnsplashImage>>
    private val progressLoadStatus: LiveData<Resource<List<UnsplashImage>>>
    var selectedPos = 0

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(Constants.IMAGE_PER_PAGE)
            .setInitialLoadSizeHint(Constants.IMAGE_PER_PAGE * 2)
            .setEnablePlaceholders(false)
            .build()
        photos = LivePagedListBuilder(imagesDataSourceFactory, config).build()

        progressLoadStatus = Transformations.switchMap(
            imagesDataSourceFactory.photoDataSourceLiveData,
            ImageDataSource::responsePhotos
        )
    }

    fun getProgressStatus() = progressLoadStatus

    fun listIsEmpty(): Boolean {
        return photos.value?.isEmpty() ?: true
    }

}
