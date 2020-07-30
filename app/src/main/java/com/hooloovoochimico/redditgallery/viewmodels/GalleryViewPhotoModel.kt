package com.hooloovoochimico.redditgallery.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem
import com.hooloovoochimico.redditgallery.provider.ImageProvider

class GalleryViewPhotoModel  : ViewModel() {
    private val imagesRepo = ImageProvider
    val images = MutableLiveData<List<UnsplashImageBeanItem>>()

    fun getImages() {
        images.value = imagesRepo.getCachedImages()?.results ?: emptyList()
    }

    fun savePic(bitmap: Bitmap?, name: String?) {
        TODO("Not yet implemented")
    }
}