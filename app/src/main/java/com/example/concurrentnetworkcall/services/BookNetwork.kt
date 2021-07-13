package com.example.concurrentnetworkcall.services

import com.example.concurrentnetworkcall.models.book.Book
import com.example.concurrentnetworkcall.models.token.Token
import retrofit2.http.*

interface BookNetwork {
    @GET("book/error")
    suspend fun error(): Any

    @GET("book/authentication")
    suspend fun authentication(): Token

    @GET("book/authentication/error")
    suspend fun authenticationFailed(): Token

    @GET("book/free")
    suspend fun free(): Book

    @GET("book/free/error")
    suspend fun freeError(): Book

    @GET("book/history")
    suspend fun history(
        @Header("Authorization") bearer: String
    ): Book
}