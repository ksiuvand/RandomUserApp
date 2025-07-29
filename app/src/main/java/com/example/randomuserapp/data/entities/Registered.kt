package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Registered(
    val date: String,
    val age: Int
)