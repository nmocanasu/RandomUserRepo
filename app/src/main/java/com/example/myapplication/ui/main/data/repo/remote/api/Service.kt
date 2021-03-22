package com.example.myapplication.ui.main.data.repo.remote.api

import com.example.myapplication.ui.main.data.repo.remote.model.ResultsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    // /api?page=2&results=10&seed=abc
    @GET("/api")
    fun getUsers(@Query("page") page: Int, @Query(value = "results") results: Int) : Call<ResultsData>
}