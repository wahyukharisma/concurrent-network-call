package com.example.concurrentnetworkcall.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.concurrentnetworkcall.models.book.Book
import com.example.concurrentnetworkcall.models.token.Token
import com.example.concurrentnetworkcall.repositories.BookRepository
import com.example.concurrentnetworkcall.util.ResultOfNetwork
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi




class MainActivityViewModel : ViewModel() {

    val items: LiveData<Map<Int, ResultOfNetwork<Any>>> get() = _items
    private val _items = MutableLiveData<Map<Int, ResultOfNetwork<Any>>>()

    val token: LiveData<ResultOfNetwork<Token>> get() = _token
    private val _token = MutableLiveData<ResultOfNetwork<Token>>()

    val freeBook: LiveData<ResultOfNetwork<Book>> get() = _freeBook
    private val _freeBook = MutableLiveData<ResultOfNetwork<Book>>()

    private val repository = BookRepository()

    fun refreshToken(dispatcher: CoroutineDispatcher) {
        viewModelScope.launch(dispatcher) {
            try {
                _token.postValue(repository.getAuthenticationBook())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> _token.postValue(
                        ResultOfNetwork.NetworkFailed(
                            "[IO] error ${throwable.message} please retry",
                            throwable
                        )
                    )
                    is HttpException -> {
                        _token.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                    }
                    else -> _token.postValue(
                        ResultOfNetwork.UnknownError(
                            throwable
                        )
                    )
                }
            }
        }
    }

    fun getData(dispatcher: CoroutineDispatcher, token: String) {
        viewModelScope.launch(dispatcher) {
            try {
                supervisorScope {

                    /*
                       Main content must load first
                    */
                    try {
                        _freeBook.postValue(ResultOfNetwork.Loading(true))
                        _freeBook.postValue(repository.getFreeBook())
                    } catch (throwable: Throwable) {
                        _freeBook.postValue(ResultOfNetwork.Loading(false))
                        when (throwable) {
                            is IOException -> _freeBook.postValue(
                                ResultOfNetwork.NetworkFailed(
                                    "[IO] error ${throwable.message} please retry",
                                    throwable
                                )
                            )
                            is HttpException -> {

                                val book = withContext(Dispatchers.IO){
                                    val moshi = Moshi.Builder().build()
                                    val jsonAdapter: JsonAdapter<Book> =
                                        moshi.adapter(Book::class.java)
                                    jsonAdapter.fromJson(throwable.response()?.errorBody()?.string())
                                }

                                _freeBook.postValue(
                                    ResultOfNetwork.ApiFailed(
                                        throwable.code(),
                                        "[HTTP] error ${book?.result?.books?.get(0)?.title} please retry",
                                        throwable
                                    )
                                )
                                throw CancellationException()
                            }
                            else -> _freeBook.postValue(
                                ResultOfNetwork.UnknownError(
                                    throwable
                                )
                            )
                        }
                    }




                    /*
                       Second liner content
                    */
                    val job1 = async { repository.getError500Book() }
                    val job2 = async { repository.getHistoryBook(token) }


                    val historyBooks = try {
                        job2.await()
                    } catch (throwable: Throwable) {
                        when (throwable) {
                            is IOException -> ResultOfNetwork.NetworkFailed(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                            is HttpException -> ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                            else -> ResultOfNetwork.UnknownError(throwable)
                        }
                    }

                    val errorBook = try {
                        job1.await()
                    } catch (throwable: Throwable) {
                        when (throwable) {
                            is IOException -> ResultOfNetwork.NetworkFailed(
                                "[IO] error ${throwable.message} please retry",
                                throwable
                            )
                            is HttpException -> ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                            else -> ResultOfNetwork.UnknownError(throwable)
                        }
                    }

                    _items.postValue(mapOf(1 to historyBooks, 2 to errorBook))
                }
            } catch (ex: Exception) {
                Log.d("LOG-DATA", "Something went wrong")
            }
        }
    }

    private suspend fun getErrorBook500(repository: BookRepository){
        try{
            repository.getError500Book()
        }catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultOfNetwork.NetworkFailed(
                    "[IO] error ${throwable.message} please retry",
                    throwable
                )
                is HttpException -> ResultOfNetwork.ApiFailed(
                    throwable.code(),
                    "[HTTP] error ${throwable.message} please retry",
                    throwable
                )
                else -> ResultOfNetwork.UnknownError(throwable)
            }
        }
    }
}