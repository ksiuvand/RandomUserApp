package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.data.repository.UserRepository
import com.example.randomuserapp.data.local.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUsers(count: Int = 20, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = repository.getUsers(count, forceRefresh)
                _users.value = list
            } catch (e: Exception) {
                _error.value = when (e) {
                    is java.net.UnknownHostException -> "Нет подключения к интернету"
                    is java.net.SocketTimeoutException -> "Сервер не отвечает"
                    is retrofit2.HttpException -> {
                        when (e.code()) {
                            404 -> "Сервер не найден (404)"
                            500 -> "Ошибка на сервере (500)"
                            else -> "Сетевая ошибка: ${e.code()}"
                        }
                    }
                    else -> "Что-то пошло не так: ${e.localizedMessage ?: "Неизвестная ошибка"}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}