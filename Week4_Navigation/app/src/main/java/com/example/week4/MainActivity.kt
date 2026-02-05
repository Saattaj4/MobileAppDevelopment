package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.week4.ui.theme.Viikko1Theme
import com.example.week4.view.MainScreen
import com.example.week4.viewmodel.TaskViewModel
import com.example.week4.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = TaskViewModel()
        val themeViewModel = ThemeViewModel()

        setContent {
            Viikko1Theme(darkTheme = themeViewModel.isDarkTheme) {
                MainScreen(
                    viewModel = viewModel,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}