package com.accurate.peoplesync.data.repository

import com.accurate.peoplesync.data.repository.model.UserRequest
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import kotlinx.coroutines.flow.Flow

interface PeopleSyncRepository {
    suspend fun getUser(): Flow<UserResponse>
    suspend fun addUser(userData: UserRequest): Flow<UserResponse>
}