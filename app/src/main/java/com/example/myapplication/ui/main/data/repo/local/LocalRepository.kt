package com.example.myapplication.ui.main.data.repo.local

import com.example.myapplication.ui.main.data.repo.local.model.User

class LocalRepository(private val room: UserDao): LocalData<User> {

    override fun save(list: List<User>, page: Int) {
        list.forEach {
            it.page = page
            room.insert(it)
        }
    }

    override fun details(uuid: String): User {
        return room.findUser(uuid)
    }

    override fun getUsers(page: Int): List<User> {
        return room.loadAllByPage(page)
    }
}