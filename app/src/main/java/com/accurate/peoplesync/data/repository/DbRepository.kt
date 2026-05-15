package com.accurate.peoplesync.data.repository

import com.accurate.peoplesync.data.repository.model.cityResponse.CityResponse
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    fun getUsers(): Flow<UserResponse>
    suspend fun insertUsers(users: UserResponse)

    fun getCity(): Flow<CityResponse>
    suspend fun insertCity(city: CityResponse)
}