package com.example.myapplication.ui.main.data.repo.remote.api

import retrofit2.Call
import retrofit2.Response

sealed class RequestResult<T> {
    data class Success<T>(val call: Call<T>, val response: Response<T>): RequestResult<T>()
    data class Failure<T>(val call: Call<T>, val error: Throwable): RequestResult<T>()
}