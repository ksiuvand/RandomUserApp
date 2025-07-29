package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val title: String,
    val first: String,
    val last: String
)