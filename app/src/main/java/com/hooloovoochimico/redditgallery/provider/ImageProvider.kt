package com.hooloovoochimico.redditgallery.provider

import com.hooloovoochimico.redditgallery.models.UnsplashImageBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashRepo {
    @GET("/search/photos")
    fun getImages(@Query("client_id") clientId: String, @Query("query") query: String, @Query("per_page") perPage: Int = 20): Single<UnsplashImageBean>
}

class ImageProvider {

    private val BASE_URL = "https://api.unsplash.com"

    private val clientId = "e2658d4b6b17ae24b50a7ab36d13ca67da9761322a5e4cb0e9cc531e69cecb90"


    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val unsplashRepo = retrofit.create(UnsplashRepo::class.java)

    fun getImages(query: String = "soccer"): Single<UnsplashImageBean> {
        return unsplashRepo.getImages(clientId = clientId,query = query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}