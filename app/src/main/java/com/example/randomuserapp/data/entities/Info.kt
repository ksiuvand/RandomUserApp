package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)