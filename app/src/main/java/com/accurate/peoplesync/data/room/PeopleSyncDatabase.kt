package com.accurate.peoplesync.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        CityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PeopleSyncDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao
}