package com.example.randomuserapp.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomuserapp.data.entities.Name
import com.example.randomuserapp.data.entities.Location
import com.example.randomuserapp.data.entities.Login
import com.example.randomuserapp.data.entities.Dob
import com.example.randomuserapp.data.entities.Registered
import com.example.randomuserapp.data.entities.Id
import com.example.randomuserapp.data.entities.Picture
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uuid: String,
    val gender: String,
    @Embedded(prefix = "name_")
    val name: Name,
    @Embedded(prefix = "location_")
    val location: Location,
    val email: String,
    @Embedded(prefix = "login_")
    val login: Login,
    @Embedded(prefix = "dob_")
    val dob: Dob,
    @Embedded(prefix = "reg_")
    val registered: Registered,
    val phone: String,
    val cell: String,
    @Embedded(prefix = "id_")
    val id: Id,
    @Embedded(prefix = "pic_")
    val picture: Picture,
    val nat: String
)