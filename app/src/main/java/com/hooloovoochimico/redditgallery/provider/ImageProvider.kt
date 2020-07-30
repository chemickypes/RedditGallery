package com.hooloovoochimico.redditgallery.provider

import android.media.Image
import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.models.UnsplashImageBean
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashRepo {
    @GET("/search/photos")
    fun getImages(@Query("client_id") clientId: String, @Query("query") query: String, @Query("per_page") perPage: Int = 20): Single<UnsplashImageBean>
}


//lo faccio object perché avrei avuto bisogno di Koin o Dagger, am mi sembrava come schiacciare la mosca con il martello
object ImageProvider {

    private val BASE_URL = "https://api.unsplash.com"

    private val clientId = "e2658d4b6b17ae24b50a7ab36d13ca67da9761322a5e4cb0e9cc531e69cecb90"


    private var cachedList: List<ImageBean>? = null

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val unsplashRepo = retrofit.create(UnsplashRepo::class.java)

    fun getImages(query: String = "soccer"): Single<List<ImageBean>> {
        return unsplashRepo.getImages(clientId = clientId,query = query)
            .map {
                cachedList = it.results.map { item ->
                    ImageBean(
                        url = item.urls?.regular,
                        title = item.user?.username,
                        description = item.description,
                        id = item.id
                    )
                }

                cachedList!!
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCachedImages(): List<ImageBean>? {
        return cachedList
    }
}