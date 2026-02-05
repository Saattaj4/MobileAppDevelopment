package com.example.week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.week3.view.HomeScreen
import com.example.week3.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = TaskViewModel()

        setContent {
            HomeScreen(viewModel)
        }
    }
}
