package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Id(
    val name: String,
    val value: String?
)