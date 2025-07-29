package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val latitude: String,
    val longitude: String
)