package com.accurate.peoplesync.di

import com.accurate.peoplesync.data.remote.UserRemoteDataSource
import com.accurate.peoplesync.data.remote.api.UserApi
import com.accurate.peoplesync.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi
    ): UserRepository = UserRemoteDataSource(userApi)
}