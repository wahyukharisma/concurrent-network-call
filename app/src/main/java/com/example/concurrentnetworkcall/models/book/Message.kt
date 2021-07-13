package com.example.concurrentnetworkcall.models.book

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "success")
    val success: String
)