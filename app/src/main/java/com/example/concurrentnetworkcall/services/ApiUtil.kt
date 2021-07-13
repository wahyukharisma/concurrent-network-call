package com.example.concurrentnetworkcall.services

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL_V1 = "https://private-1c473e-wahyukharisma.apiary-mock.com/v1/"

private val okHttpClient =
    OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()

private val retrofitMock = Retrofit.Builder()
    .baseUrl(BASE_URL_V1)
    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
    .client(okHttpClient)
    .build()

class RetrofitClient{
    companion object{
        val bookServices : BookNetwork = retrofitMock.create(BookNetwork::class.java)
    }
}