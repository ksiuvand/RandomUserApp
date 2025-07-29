package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Dob(
    val date: String,
    val age: Int
)