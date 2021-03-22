package com.example.myapplication.ui.main.data.repo.remote.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "https://randomuser.me"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: Service = getRetrofit().create(Service::class.java)
}

fun <T> Call<T>.enqueue( result: (RequestResult<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, error: Throwable) {
            result(RequestResult.Failure(call, error))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            result(RequestResult.Success(call, response))
        }
    })
}

fun <T> Call<T>.executeForResult(): RequestResult<T> {
    return try {
        val response = execute()
        if (response.isSuccessful) {
            RequestResult.Success(this, response)
        }
        else {
            RequestResult.Failure(this, Exception(response.errorBody()?.toString()))
        }
    } catch (e: Exception) {
        Log.e("HttpClient", Log.getStackTraceString(e))
        RequestResult.Failure(this, e)
    }
}