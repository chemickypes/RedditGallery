package com.hooloovoochimico.redditgallery.models
import com.google.gson.annotations.SerializedName


data class RedditPost(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("kind")
    val kind: String?
)

data class Data(
    @SerializedName("author")
    val author: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?
)

data class RedditBean(
    @SerializedName("data")
    val `data`: DataRedditBean?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("error")
    val error: Int?

/*
reason: "private",
message: "Forbidden",
error: 403
 */
)

data class DataRedditBean(
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: Any?,
    @SerializedName("children")
    val children: List<RedditPost>?,
    @SerializedName("dist")
    val dist: Int?,
    @SerializedName("modhash")
    val modhash: String?
)

class Gildings(
)

class MediaEmbed(
)

class Preview(
)

class SecureMediaEmbed(
)