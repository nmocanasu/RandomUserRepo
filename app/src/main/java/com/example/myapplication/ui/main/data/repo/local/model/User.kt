package com.example.myapplication.ui.main.data.repo.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "remoteUUID") val remoteUUID: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "dob") val dob: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "cell") val cell: String,
    @ColumnInfo(name = "thumb") val thumb: String,
    @ColumnInfo(name = "picture") val picture: String,
    @ColumnInfo(name = "page") var page: Int
) {
    constructor(
        remoteUUID: String,
        name: String,
        gender: String,
        address: String,
        email: String,
        userName: String,
        dob: String,
        age: Int,
        phone: String,
        cell: String,
        thumb: String,
        picture: String
    ) : this(0, remoteUUID, name, gender, address, email, userName, dob, age, phone, cell, thumb, picture, 0)
}
