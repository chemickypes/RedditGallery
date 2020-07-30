package com.hooloovoochimico.redditgallery.storage

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File


class ImageSaver(private val context: Context) {

    fun saveImage(bitmap: Bitmap,name: String): Single<String> {
        return Single.defer {

            Single.just(writeBitmap(bitmap,name))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun writeBitmap(imageData:Bitmap, name: String): String{
        val stream = ByteArrayOutputStream()
        imageData.compress(
             Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()


        val path = "RedditGalleryExample"

        val dir =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(ContextCompat.getExternalFilesDirs(context, null)[0], path)
        } else {
            File(Environment.getExternalStorageDirectory(), path)
        }

        if (!dir.mkdirs()) {
            Log.e(javaClass.name, "Directory not created")
        }

        val resultFile = File(dir, "$name.png")

        resultFile.writeBytes(byteArray)

        return resultFile.absolutePath
    }
}