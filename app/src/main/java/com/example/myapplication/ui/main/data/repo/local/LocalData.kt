package com.example.myapplication.ui.main.data.repo.local

interface LocalData<T> {
    fun getUsers(page: Int) : List<T>
    fun save(list: List<T>, page: Int)
    fun details(uuid: String): T
}