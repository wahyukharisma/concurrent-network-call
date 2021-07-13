package com.example.concurrentnetworkcall.models.token

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    val code: Int,
    val message: Message,
    val result: Result,
    val status: String
)