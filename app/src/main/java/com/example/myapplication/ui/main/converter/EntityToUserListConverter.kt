package com.example.myapplication.ui.main.converter

import com.example.myapplication.ui.main.data.repo.local.model.User
import com.example.myapplication.ui.main.util.AbstractConverter
import com.example.myapplication.ui.main.view.model.ListUser

internal class EntityToUserListConverter: AbstractConverter<User, ListUser>() {
    override fun convert(input: User): ListUser {
        return ListUser(
            name = input.name,
            gender = input.gender,
            thumb = input.thumb,
            uuid = input.remoteUUID
        )
    }
}