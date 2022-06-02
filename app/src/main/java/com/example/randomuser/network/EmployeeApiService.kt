package com.example.randomuser.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://gorest.co.in/"
//private const val BASE_URL = "https://api2.binance.com/"
//private const val BASE_URL = "https://mars.udacity.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface EmployeeApiService {
    @GET("public/v2/users")
//    @GET("api/v3/ticker/24hr")
//    @GET("realestate")
    fun getValues():
            Deferred<List<EmployeeDetails>>
}

object EmployeeApi {
    val RETROFIT_SERVICE : EmployeeApiService by lazy {
        retrofit.create(EmployeeApiService::class.java)
    }
}