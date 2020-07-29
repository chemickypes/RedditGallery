package com.hooloovoochimico.redditgallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.provider.ImageProvider

class GalleryViewModel : ViewModel() {

    val imagesRepo = ImageProvider()


    val images = MutableLiveData<List<String>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun search(searchString: String = "soccer") {

        loading.value = true

        imagesRepo.getImages(searchString).subscribe { imagesList, exception ->

            loading.value = false

            if (imagesList != null) {
                error.value = false
                images.value = imagesList.results.map { it.urls?.regular ?: "" }
            } else {
                exception.printStackTrace()
                error.value = true
            }
        }

    }
}