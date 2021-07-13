package com.example.concurrentnetworkcall.models.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    @Json(name = "code")
    val code: Int,
    @Json(name = "message")
    val message: Message,
    @Json(name = "result")
    val result: Result,
    @Json(name = "status")
    val status: String
)