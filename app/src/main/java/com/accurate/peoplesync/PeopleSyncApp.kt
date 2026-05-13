package com.accurate.peoplesync

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PeopleSyncApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}