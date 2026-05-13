package com.accurate.peoplesync

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.accurate.peoplesync.ui.module.home.HomeScreen

@Composable
fun PeopleSyncNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = PeopleSyncRoutes.Home.route,
    navActions: PeopleSyncNavigationActions =
        remember(navController) { PeopleSyncNavigationActions(navController) }
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        val navigateForm = { navActions.navigateTo(PeopleSyncRoutes.Form.route) }

        composable(route = PeopleSyncRoutes.Home.route) {
            HomeScreen(navigateForm = navigateForm)
        }

        composable(route = PeopleSyncRoutes.Form.route) {

        }
    }
}