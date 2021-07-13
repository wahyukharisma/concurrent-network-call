package com.example.concurrentnetworkcall.models.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "books")
    val books: List<BookX>
)