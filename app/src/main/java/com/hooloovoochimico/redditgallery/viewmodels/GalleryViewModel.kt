package com.hooloovoochimico.redditgallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.provider.RedditImageProvider
import com.hooloovoochimico.redditgallery.provider.UnsplashImageProvider
import io.reactivex.disposables.Disposable

class GalleryViewModel : ViewModel() {

    private var disposable: Disposable? = null
    private val imagesRepo = RedditImageProvider

    val images = MutableLiveData<List<ImageBean>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun search(searchString: String = "soccer") {

        loading.value = true

        error.value = false

        disposable = imagesRepo.getImages(searchString).subscribe { imagesList, exception ->

            loading.value = false

            if (imagesList != null) {
                error.value = false
                images.value = imagesList

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