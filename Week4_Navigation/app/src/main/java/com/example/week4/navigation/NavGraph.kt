package com.example.week4.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.week4.view.HomeScreen
import com.example.week4.view.CalendarScreen
import com.example.week4.view.SettingsScreen
import com.example.week4.viewmodel.TaskViewModel
import com.example.week4.viewmodel.ThemeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: TaskViewModel,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(route = Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Routes.CALENDAR) {
            CalendarScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Routes.SETTINGS) {
            SettingsScreen(
                navController = navController,
                themeViewModel = themeViewModel
            )
        }
    }
}