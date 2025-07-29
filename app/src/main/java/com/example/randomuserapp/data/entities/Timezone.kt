package com.example.randomuserapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Timezone(
    val offset: String,
    val description: String
)