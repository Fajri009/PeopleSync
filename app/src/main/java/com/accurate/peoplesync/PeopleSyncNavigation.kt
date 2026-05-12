package com.accurate.peoplesync

import androidx.navigation.NavHostController

sealed class PeopleSyncRoutes(val route: String) {
    object Home : PeopleSyncRoutes("home")
}

class PeopleSyncNavigationActions(private val navController: NavHostController) {
    fun navigateTo(routes: String) {
        navController.navigate(routes)
    }
}