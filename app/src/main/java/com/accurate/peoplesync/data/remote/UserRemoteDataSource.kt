package com.accurate.peoplesync.data.remote

import com.accurate.peoplesync.data.remote.api.UserApi
import com.accurate.peoplesync.data.repository.UserRepository
import com.accurate.peoplesync.data.repository.model.UserRequest
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class UserRemoteDataSource(
    private val userApi: UserApi
): UserRepository {
    override suspend fun getUser(): Flow<UserResponse> =
        flow {
            val response = userApi.getUser()

            emit(response)
        }

    override suspend fun addUser(userData: UserRequest): Flow<Response<UserResponse>> =
        flow {
            val response = userApi.addUser(userData)

            emit(response)
        }
}