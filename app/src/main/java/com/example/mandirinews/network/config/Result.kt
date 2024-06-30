package com.example.mandirinews.network.config

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    data object Empty : Result<Nothing>()
}