package com.accurate.peoplesync.data.repository.model.userResponse

typealias UserResponse = List<UserItem>

data class UserItem(
    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val city: String,
    val gender: Int,
    val id: String
)