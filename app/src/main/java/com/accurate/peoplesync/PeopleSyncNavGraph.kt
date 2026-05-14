package com.accurate.peoplesync

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.accurate.peoplesync.ui.module.form.FormScreen
import com.accurate.peoplesync.ui.module.home.HomeScreen
import com.accurate.peoplesync.viewmodel.home.HomeViewModel

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
            val viewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                viewModel = viewModel,
                navigateForm = navigateForm
            )
        }

        composable(route = PeopleSyncRoutes.Form.route) {
            FormScreen(navigateBack = { navController.popBackStack() })
        }
    }
}