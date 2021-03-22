package com.example.myapplication.ui.main.util

import com.example.myapplication.ui.main.converter.DataToEntityConverter
import com.example.myapplication.ui.main.converter.EntityToDetailedUserConverter
import com.example.myapplication.ui.main.converter.EntityToUserListConverter
import com.example.myapplication.ui.main.data.repo.local.model.User
import com.example.myapplication.ui.main.data.repo.remote.model.RemoteUser
import com.example.myapplication.ui.main.view.model.DetailedUser
import com.example.myapplication.ui.main.view.model.ListUser


private interface Converter<From, To> {
    fun convert(input: From): To
    fun convert(input: List<From>): List<To>

    companion object{
        val dataToEntity = DataToEntityConverter()
        val entityToUser = EntityToDetailedUserConverter()
        val entityToUserList = EntityToUserListConverter()
    }
}

internal abstract class AbstractConverter<From, To>: Converter<From, To> {
    abstract override fun convert(input: From): To

    override fun convert(input: List<From>): List<To> {
        return input.map { convert(it) }
    }
}

fun List<User>.toUserModel(): List<ListUser> {
    return Converter.entityToUserList.convert(this)
}

fun User.toUserDetailsModel(): DetailedUser {
    return Converter.entityToUser.convert(this)
}

fun List<RemoteUser>.toDaoModel(): List<User> {
    return Converter.dataToEntity.convert(this)
}

