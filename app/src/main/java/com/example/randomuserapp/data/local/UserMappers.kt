package com.example.randomuserapp.data.local

import com.example.randomuserapp.data.entities.UserResult

fun UserResult.toEntity(): UserEntity = UserEntity(
    uuid = login.uuid,
    gender = gender,
    name = name,
    location = location,
    email = email,
    login = login,
    dob = dob,
    registered = registered,
    phone = phone,
    cell = cell,
    id = id,
    picture = picture,
    nat = nat
)

fun UserEntity.toUserResult(): UserResult = UserResult(
    gender = gender,
    name = name,
    location = location,
    email = email,
    login = login,
    dob = dob,
    registered = registered,
    phone = phone,
    cell = cell,
    id = id,
    picture = picture,
    nat = nat
)