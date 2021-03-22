package com.example.myapplication.ui.main.data.repo

import com.example.myapplication.ui.main.view.model.DetailedUser
import com.example.myapplication.ui.main.view.model.ListUser

interface Repository {
    suspend fun fetchItems(page: Int, items: Int = 25): List<ListUser>
    suspend fun fetchDetails(uuid: String): DetailedUser
}

