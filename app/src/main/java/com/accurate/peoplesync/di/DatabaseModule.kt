package com.accurate.peoplesync.di

import android.content.Context
import androidx.room.Room
import com.accurate.peoplesync.data.repository.DbRepository
import com.accurate.peoplesync.data.room.CityDao
import com.accurate.peoplesync.data.room.PeopleSyncDatabase
import com.accurate.peoplesync.data.room.RepositoryDb
import com.accurate.peoplesync.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun providesUserDao(db: PeopleSyncDatabase): UserDao = db.userDao()

    @Provides
    fun providesCityDao(db: PeopleSyncDatabase): CityDao = db.cityDao()

    @Provides
    @Singleton
    fun provideDbRepository(
        userDao: UserDao,
        cityDao: CityDao
    ): DbRepository = RepositoryDb(userDao, cityDao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PeopleSyncDatabase =
        Room.databaseBuilder(context, PeopleSyncDatabase::class.java, "people_sync_database")
            .fallbackToDestructiveMigration(false)
            .build()
}