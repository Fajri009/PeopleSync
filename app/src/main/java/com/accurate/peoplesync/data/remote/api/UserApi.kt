package com.accurate.peoplesync.data.remote.api

import com.accurate.peoplesync.data.repository.model.UserRequest
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("user")
    suspend fun getUser(
        @Query("name") name: String = "",
        @Query("city") city: String = ""
    ): UserResponse

    @POST("user")
    suspend fun addUser(
        @Body request: UserRequest
    ): Response<UserResponse>
}