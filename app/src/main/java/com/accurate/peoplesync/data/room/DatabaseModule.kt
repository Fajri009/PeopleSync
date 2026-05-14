package com.accurate.peoplesync.data.room

import android.content.Context
import androidx.room.Room
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
    fun provideDatabase(@ApplicationContext context: Context): PeopleSyncDatabase =
        Room.databaseBuilder(context, PeopleSyncDatabase::class.java, "people_sync_database")
            .fallbackToDestructiveMigration(false)
            .build()
}