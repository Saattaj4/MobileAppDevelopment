package com.example.week4.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.week4.navigation.BottomNavBar
import com.example.week4.navigation.NavGraph
import com.example.week4.viewmodel.TaskViewModel
import com.example.week4.viewmodel.ThemeViewModel

@Composable
fun MainScreen(
    viewModel: TaskViewModel,
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            viewModel = viewModel,
            themeViewModel = themeViewModel,  // Pass it here
            modifier = Modifier.padding(paddingValues)
        )
    }
}