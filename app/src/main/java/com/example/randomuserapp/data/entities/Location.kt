package com.example.randomuserapp.data.entities

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @Embedded(prefix = "street_")
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    @Embedded(prefix = "coord_")
    val coordinates: Coordinates,
    @Embedded(prefix = "tz_")
    val timezone: Timezone
)