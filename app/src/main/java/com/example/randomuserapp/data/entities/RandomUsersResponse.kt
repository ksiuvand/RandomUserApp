package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class RandomUsersResponse(
    val results: List<UserResult>,
    val info: Info
)