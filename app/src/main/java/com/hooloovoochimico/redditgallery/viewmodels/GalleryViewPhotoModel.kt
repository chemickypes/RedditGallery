package com.hooloovoochimico.redditgallery.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem
import com.hooloovoochimico.redditgallery.provider.ImageProvider
import com.hooloovoochimico.redditgallery.storage.ImageSaver

class GalleryViewPhotoModel  : ViewModel() {
    
    private val imagesRepo = ImageProvider
    val images = MutableLiveData<List<UnsplashImageBeanItem>>()

    val imageSaved = MutableLiveData<String>()

    fun getImages() {
        images.value = imagesRepo.getCachedImages()?.results ?: emptyList()
    }

    fun savePic(context: Context, bitmap: Bitmap?, name: String?) {
        
        bitmap?.let {
            ImageSaver(context).saveImage(bitmap,name?:"image_")
                .subscribe { t1, t2 ->
                    if(t1 != null){
                        imageSaved.value = "saved: $t1"
                    }else {
                        t2.printStackTrace()
                        imageSaved.value = "error"
                    }
                }
        }?: kotlin.run {
            imageSaved.value = "error"
        }
        
    }
}