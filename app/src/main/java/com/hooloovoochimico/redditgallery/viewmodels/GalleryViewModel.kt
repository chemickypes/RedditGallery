package com.hooloovoochimico.redditgallery.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {


    val images = MutableLiveData<List<String>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun search(searchString: String = "") {

        loading.value = true

        images.value = listOf<String>(
            "https://via.placeholder.com/150",
            "https://via.placeholder.com/150",
            "https://via.placeholder.com/150"
        )

        error.value = false
        loading.value = false
    }
}