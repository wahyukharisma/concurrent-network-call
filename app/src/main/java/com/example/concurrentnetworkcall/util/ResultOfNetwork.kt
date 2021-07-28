package com.example.concurrentnetworkcall.util

/**
 * Sealed class used in manage result network request
 */
sealed class ResultOfNetwork<out T> {

    data class Loading(val isLoading: Boolean) : ResultOfNetwork<Nothing>()
    /**
     * Success response with body
     */
    data class Success<out R>(val value: R) : ResultOfNetwork<R>()

    /**
     * Failure response with status code
     */
    data class ApiFailed(
        val code: Int,
        val message: String?,
        val throwable: Throwable?
    ) : ResultOfNetwork<Nothing>()

    /**
     * Network error
     */
    data class NetworkFailed(
        val message: String?,
        val throwable: Throwable?
    ) : ResultOfNetwork<Nothing>()

    data class UnknownError(val throwable: Throwable?) : ResultOfNetwork<Nothing>()
}