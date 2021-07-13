package com.example.concurrentnetworkcall.repositories

import com.example.concurrentnetworkcall.models.book.Book
import com.example.concurrentnetworkcall.models.token.Token
import com.example.concurrentnetworkcall.services.RetrofitClient
import com.example.concurrentnetworkcall.util.ResultOfNetwork

class BookRepository {
    suspend fun getFreeBook(): ResultOfNetwork<Book> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.free()
        )
    }

    suspend fun getHistoryBook(token: String): ResultOfNetwork<Book> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.history(token)
        )
    }

    suspend fun getError500Book(): ResultOfNetwork<Any> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.error()
        )
    }

    suspend fun getError402(): ResultOfNetwork<Book> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.freeError()
        )
    }

    suspend fun getAuthenticationFailed(): ResultOfNetwork<Token> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.authenticationFailed()
        )
    }

    suspend fun getAuthenticationBook(): ResultOfNetwork<Token> {
        return ResultOfNetwork.Success(
            RetrofitClient.bookServices.authentication()
        )
    }
}