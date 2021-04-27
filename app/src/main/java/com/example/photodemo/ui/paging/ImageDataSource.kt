package com.example.photodemo.ui.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.photodemo.R
import com.example.photodemo.data.model.UnsplashImage
import com.example.photodemo.data.repository.MainRepository
import com.example.photodemo.util.Constants
import com.example.photodemo.util.NetworkHelper
import com.example.photodemo.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException

class ImageDataSource(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, UnsplashImage>() {

    var responsePhotos = MutableLiveData<Resource<List<UnsplashImage>>>()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UnsplashImage>
    ) {
        scope.launch {
            responsePhotos.postValue(Resource.loading(null))
            try {
                mainRepository.getPhotos(
                    Constants.INITIAL_PAGE,
                    Constants.IMAGE_PER_PAGE,
                    Constants.ORDER_BY
                ).let {
                    if (it.isSuccessful) {
                        responsePhotos.postValue(Resource.success(null))
                        callback.onResult(
                            it.body()!!, null, Constants.INITIAL_PAGE + 1
                        )
                    } else {
                        responsePhotos.postValue(
                            Resource.error(
                                it.errorBody().toString(),
                                null
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is UnknownHostException) {
                    responsePhotos.postValue(
                        Resource.error(Constants.NO_INTERNET_MSG, null)
                    )
                }
            }

        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UnsplashImage>
    ) {
        scope.launch {
            responsePhotos.postValue(Resource.loading(null))
            try {
                mainRepository.getPhotos(
                    params.key,
                    params.requestedLoadSize,
                    Constants.ORDER_BY
                ).let {
                    if (it.isSuccessful) {
                        responsePhotos.postValue(Resource.success(it.body()))
                        callback.onResult(it.body()!!, params.key + 1)
                    } else {
                        responsePhotos.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is UnknownHostException) {
                    responsePhotos.postValue(
                        Resource.error(Constants.NO_INTERNET_MSG, null)
                    )
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UnsplashImage>
    ) {

    }
}