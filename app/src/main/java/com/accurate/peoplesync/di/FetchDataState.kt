package com.accurate.peoplesync.di

sealed class FetchDataState<out T> {
    data object Loading : FetchDataState<Nothing>()

    data class Success<out T>(val data: T) : FetchDataState<T>()

    data class Error(val message: String) : FetchDataState<Nothing>()
}