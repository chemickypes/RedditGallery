package com.hooloovoochimico.redditgallery.provider

import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.models.RedditBean
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.RuntimeException

interface RedditRepo {
    @GET("/r/{query}/top.json")
    fun getImages(@Path("query") query: String): Single<RedditBean>
}


//lo faccio object perché avrei avuto bisogno di Koin o Dagger, am mi sembrava come schiacciare la mosca con il martello
object RedditImageProvider {

    private val BASE_URL = "https://www.reddit.com"

    private var cachedList: List<ImageBean>? = null

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val redditRepo = retrofit.create(RedditRepo::class.java)

    fun getImages(query: String = "soccer"): Single<List<ImageBean>> {
        return redditRepo.getImages(query = query)
            .flatMap{

                if(it.error !=null){
                   return@flatMap Single.error<List<ImageBean>>(RuntimeException())
                }else {
                    cachedList = it.data?.children?.filter {redditPost ->
                        redditPost.data?.thumbnail != null && (
                                redditPost.data.thumbnail.isNotEmpty() ||
                                        redditPost.data.thumbnail.isNotBlank()
                                )
                    }?.map {redditPost ->
                        ImageBean(
                            url = redditPost.data?.thumbnail,
                            title = redditPost.data?.author,
                            description = redditPost.data?.title,
                            id = redditPost.data?.id
                        )
                    }

                    return@flatMap Single.just(cachedList!!)
                }

            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCachedImages(): List<ImageBean>? {
        return cachedList
    }
}

/*

 ImageBean(
                        url = item.urls?.regular,
                        title = item.user?.username,
                        description = item.description,
                        id = item.id
                    )
 */