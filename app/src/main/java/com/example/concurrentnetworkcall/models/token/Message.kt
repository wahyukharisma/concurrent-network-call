package com.example.concurrentnetworkcall.models.token

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val success: String
)