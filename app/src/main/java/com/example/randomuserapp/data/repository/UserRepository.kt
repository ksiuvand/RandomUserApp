package com.example.randomuserapp.data.repository

import com.example.randomuserapp.data.api.RandomUserApi
import com.example.randomuserapp.data.local.UserDao
import com.example.randomuserapp.data.local.UserEntity
import com.example.randomuserapp.data.local.toEntity

class UserRepository(
    private val api: RandomUserApi,
    private val userDao: UserDao
) {
    private var cachedUsers: List<UserEntity> = emptyList()

    suspend fun getUsers(count: Int = 20, forceRefresh: Boolean = false): List<UserEntity> {
        val cached = if (!forceRefresh) userDao.getAllUsers() else emptyList()
        if (cached.isNotEmpty()) {
            cachedUsers = cached
            return cached
        }

        val response = api.getUsers(count)
        val entities = response.results.map { it.toEntity() }
        userDao.clearAll()
        userDao.insertAll(entities)
        cachedUsers = entities
        return entities
    }
}