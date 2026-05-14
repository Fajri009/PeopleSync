package com.accurate.peoplesync.data.repository

import com.accurate.peoplesync.data.repository.model.UserRequest
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(): Flow<UserResponse>
    suspend fun addUser(userData: UserRequest): Flow<Response<UserResponse>>
}