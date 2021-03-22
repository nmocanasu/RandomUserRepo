package com.example.myapplication.ui.main.converter

import com.example.myapplication.ui.main.data.repo.local.model.User
import com.example.myapplication.ui.main.util.AbstractConverter
import com.example.myapplication.ui.main.view.model.DetailedUser

internal class EntityToDetailedUserConverter: AbstractConverter<User, DetailedUser>() {
    override fun convert(input: User): DetailedUser {
        return DetailedUser(
            remoteUUID = input.remoteUUID,
            name = input.name,
            picture = input.picture,
            cell = input.cell,
            phone = input.phone,
            age = input.age,
            dob = input.dob,
            userName = input.userName,
            email = input.email,
            address = input.address
        )
    }
}