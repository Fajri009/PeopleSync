package com.accurate.peoplesync.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val city: String,
    val gender: Int
)

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey val id: String,
    val name: String
)