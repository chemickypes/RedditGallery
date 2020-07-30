package com.hooloovoochimico.redditgallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem
import com.hooloovoochimico.redditgallery.provider.ImageProvider
import io.reactivex.disposables.Disposable

class GalleryViewModel : ViewModel() {

    private var disposable: Disposable? = null
    private val imagesRepo = ImageProvider

    val images = MutableLiveData<List<UnsplashImageBeanItem>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun search(searchString: String = "soccer") {

        loading.value = true

        error.value = false

        disposable = imagesRepo.getImages(searchString).subscribe { imagesList, exception ->

            loading.value = false

            if (imagesList != null) {
                error.value = false
                images.value = imagesList.results
            } else {
                exception.printStackTrace()
                error.value = true
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        try {
            disposable?.dispose()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}