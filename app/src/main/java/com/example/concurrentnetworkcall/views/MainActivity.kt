package com.example.concurrentnetworkcall.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.concurrentnetworkcall.databinding.ActivityMainBinding
import com.example.concurrentnetworkcall.models.book.Book
import com.example.concurrentnetworkcall.util.ResultOfNetwork
import com.example.concurrentnetworkcall.viewmodels.MainActivityViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.btnTryAgain.setOnClickListener {
            viewModel.getData(Dispatchers.IO, "123")
        }

        viewModel.items.observe(this, { items ->
            items.forEach { item ->
                when (item.key) {
                    1 -> {
                        when (item.value) {
                            is ResultOfNetwork.Success -> {
                                val result =
                                    (item.value as ResultOfNetwork.Success<Book>).value.result
                                Log.d("LOG-DATA", "Your data 2 - ${result.books[0].title}")
                            }
                            is ResultOfNetwork.ApiFailed -> {
                                val result = (item.value as ResultOfNetwork.ApiFailed)
                                Log.d("LOG-DATA", "Api failed with status code ${result.code}")
                            }
                            is ResultOfNetwork.NetworkFailed -> Log.d("LOG-DATA", "Network failed")
                            else -> Log.d("LOG-DATA", "Unknown error")
                        }
                    }
                    2 -> {
                        when(item.value){
                            is ResultOfNetwork.Success -> {
                                Log.d("LOG-DATA", "Success")
                            }
                            is ResultOfNetwork.ApiFailed -> {
                                val result = (item.value as ResultOfNetwork.ApiFailed)
                                Log.d("LOG-DATA", "Api failed with status code ${result.code}")
                            }
                            is ResultOfNetwork.NetworkFailed -> Log.d("LOG-DATA", "Network failed")
                            else -> Log.d("LOG-DATA", "Unknown error")
                        }
                    }
                }
            }
        })

        viewModel.freeBook.observe(this, { item ->
            when (item) {
                is ResultOfNetwork.Success -> {
                    val book = item.value.result.books[0]
                    Log.d("LOG-DATA", "Your data 1 - ${book.title}")
                }
                is ResultOfNetwork.ApiFailed -> {
                    when (item.code) {
                        402 -> {
                            //viewModel.refreshToken(Dispatchers.IO)
                            Log.d("LOG-DATA", "Something went wrong code ${item.message}")
                        }
                        else -> {
                            Log.d("LOG-DATA", "Something went wrong code ${item.code}")
                        }
                    }
                }
                is ResultOfNetwork.NetworkFailed -> Log.d("LOG-DATA", "Network failed")
                else -> Log.d("LOG-DATA", "Unknown error")
            }
        })

        viewModel.token.observe(this, { item ->
            when (item) {
                is ResultOfNetwork.Success -> {
                    val result = item.value.result.token
                    viewModel.getData(Dispatchers.IO, result)
                }
                is ResultOfNetwork.ApiFailed -> {
                    when (item.code) {
                        402 -> {
                            Log.d("LOG-DATA", "Something went wrong code ${item.code}")
                        }
                        else -> {
                            Log.d("LOG-DATA", "Something went wrong code ${item.code}")
                        }
                    }
                }
                is ResultOfNetwork.NetworkFailed -> Log.d("LOG-DATA", "Network failed")
                else -> Log.d("LOG-DATA", "Unknown error")
            }
        })
    }
}