package com.example.myapplication.ui.main.converter

import com.example.myapplication.ui.main.data.repo.local.model.User
import com.example.myapplication.ui.main.util.AbstractConverter
import com.example.myapplication.ui.main.data.repo.remote.model.RemoteUser

internal class DataToEntityConverter: AbstractConverter<RemoteUser, User>() {
    override fun convert(input: RemoteUser): User {
        return User(
            remoteUUID = input.login.uuid,
            name = input.name.title + " " + input.name.first + " " + input.name.last,
            gender = input.gender,
            address = input.location.street.name +  " " +
                    input.location.street.number + " " +
                    input.location.city + " " +
                    input.location.state + " " +
                    input.location.country,
            email = input.email,
            userName = input.login.username,
            dob = input.dob.date,
            age = input.dob.age,
            phone = input.phone,
            cell = input.cell,
            thumb = input.picture.thumbnail,
            picture = input.picture.medium
        )
    }
}