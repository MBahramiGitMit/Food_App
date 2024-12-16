package com.mbahrami.foodapp.util

sealed class RequestState<out T> {
    data object Empty : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val message: String?) : RequestState<Nothing>()
}