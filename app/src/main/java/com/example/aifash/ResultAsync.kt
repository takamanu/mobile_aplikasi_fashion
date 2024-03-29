package com.example.aifash

sealed class ResultAsync<out T> {
    data class Success<out T>(val data: T?) : ResultAsync<T>()
    data class Error(val exception: Exception) : ResultAsync<Nothing>()
}


