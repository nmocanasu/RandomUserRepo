package com.example.myapplication.ui.main.data.repo.remote

interface RemoteData<T> {
    @Throws(Exception::class)
    fun pullUserData(page: Int, items: Int): T
}