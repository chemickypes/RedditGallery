package com.hooloovoochimico.redditgallery.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.provider.RedditImageProvider
import com.hooloovoochimico.redditgallery.provider.UnsplashImageProvider
import com.hooloovoochimico.redditgallery.storage.ImageSaver

class GalleryViewPhotoModel  : ViewModel() {
    
    private val imagesRepo = RedditImageProvider
    val images = MutableLiveData<List<ImageBean>>()

    val imageSaved = MutableLiveData<String>()

    fun getImages() {
        images.value = imagesRepo.getCachedImages() ?: emptyList()
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