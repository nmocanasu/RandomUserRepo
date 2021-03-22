package com.example.myapplication.ui.main.data.repo.remote.model

data class RemoteUser(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)