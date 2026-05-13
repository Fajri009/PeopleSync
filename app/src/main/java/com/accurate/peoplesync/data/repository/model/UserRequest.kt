package com.accurate.peoplesync.data.repository.model

data class UserRequest(
    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val city: String,
    val gender: Int
)
