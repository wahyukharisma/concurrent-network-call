package com.example.concurrentnetworkcall.models.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookX(
    @Json(name = "author")
    val author: String,
    @Json(name = "date")
    val date: Int,
    @Json(name = "exist")
    val exist: Boolean,
    @Json(name = "img")
    val img: String,
    @Json(name = "isbn")
    val isbn: String,
    @Json(name = "publisher")
    val publisher: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "synopsis")
    val synopsis: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String
)