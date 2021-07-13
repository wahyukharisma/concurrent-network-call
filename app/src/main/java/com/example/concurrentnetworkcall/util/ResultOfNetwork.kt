package com.example.concurrentnetworkcall.util

/**
 * Sealed class used in manage result network request
 */
sealed class ResultOfNetwork<out T> {
    data class Success<out R>(val value: R) : ResultOfNetwork<R>()

    data class ApiFailed(
        val code: Int,
        val message: String?,
        val throwable: Throwable?
    ) : ResultOfNetwork<Nothing>()

    data class NetworkFailed(
        val message: String?,
        val throwable: Throwable?
    ) : ResultOfNetwork<Nothing>()

    data class UnknownError(val throwable: Throwable?) : ResultOfNetwork<Nothing>()
}