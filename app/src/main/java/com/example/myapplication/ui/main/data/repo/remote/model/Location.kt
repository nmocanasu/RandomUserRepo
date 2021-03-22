package com.example.myapplication.ui.main.data.repo.remote.model

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
)