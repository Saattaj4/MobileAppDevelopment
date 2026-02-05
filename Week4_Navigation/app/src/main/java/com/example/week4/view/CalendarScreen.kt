package com.example.week4.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.week4.model.Task
import com.example.week4.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    navController: NavHostController
) {
    val tasks = viewModel.tasks

    val tasksByDate = tasks.groupBy { it.dueDate }.toSortedMap()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📅 Calendar View") }
            )
        }
    ) { paddingValues ->
        if (tasksByDate.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "No tasks scheduled",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                tasksByDate.forEach { (date, tasksForDate) ->
                    item {
                        Text(
                            text = formatDate(date),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

                    items(tasksForDate) { task ->
                        TaskCalendarCard(
                            task = task,
                            onToggleDone = { viewModel.toggleDone(task.id) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCalendarCard(
    task: Task,
    onToggleDone: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.done)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                if (task.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // Priority indicator
            if (task.priority <= 2) {
                Text(
                    text = "⭐",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


fun formatDate(dateString: String): String {
    return try {
        if (dateString.contains(".")) {
            val parts = dateString.split(".")
            if (parts.size == 3) {
                val day = parts[0]
                val month = parts[1]
                val year = parts[2]
                return "$day.$month.$year"
            }
        }
        if (dateString.contains("-")) {
            val parts = dateString.split("-")
            if (parts.size == 3) {
                val year = parts[0]
                val month = parts[1]
                val day = parts[2]

                val monthName = when (month.toIntOrNull()) {
                    1 -> "January"
                    2 -> "February"
                    3 -> "March"
                    4 -> "April"
                    5 -> "May"
                    6 -> "June"
                    7 -> "July"
                    8 -> "August"
                    9 -> "September"
                    10 -> "October"
                    11 -> "November"
                    12 -> "December"
                    else -> month
                }
                return "$day $monthName $year"
            }
        }
        dateString
    } catch (e: Exception) {
        dateString
    }
}