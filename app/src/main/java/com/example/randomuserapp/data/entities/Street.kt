package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Street(
    val number: Int,
    val name: String
)