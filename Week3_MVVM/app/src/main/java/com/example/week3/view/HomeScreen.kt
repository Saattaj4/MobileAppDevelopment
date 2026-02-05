package com.example.week3.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week3.model.Task
import com.example.week3.viewmodel.TaskViewModel

@Composable
fun HomeScreen(viewModel: TaskViewModel) {

    val tasks = viewModel.tasks  // <- just read directly, no collectAsState
    var selectedTask by remember { mutableStateOf<Task?>(null) }


    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "My important tasks \uD83C\uDF54",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
                // New task
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New Task Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newDescription,
                onValueChange = { newDescription = it },
                label = { Text("New Task Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (newTitle.isNotBlank()) {
                        viewModel.addTask(
                            Task(
                                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                                title = newTitle,
                                description = newDescription,
                                priority = 1,
                                dueDate = "2026-01-26",
                                done = false
                            )
                        )
                        newTitle = ""
                        newDescription = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

                       // Button row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.filterByDone(true) },
                modifier = Modifier.weight(1f)
            ) { Text("Show Done") }

            Button(
                onClick = { viewModel.sortByDueDate() },
                modifier = Modifier.weight(1f)
            ) { Text("Sort by Date") }

            Button(
                onClick = { viewModel.showAllTasks() },
                modifier = Modifier.weight(1f)
            ) { Text("Show All Tasks") }
        }

        Spacer(modifier = Modifier.height(16.dp))

                       // Task list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedTask = task },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { viewModel.toggleDone(task.id) }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(task.title)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(task.description, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Due: ${task.dueDate}", style = MaterialTheme.typography.bodySmall)
                    }

                }
            }
        }
    }

             // Popup screen when task is clicked
    selectedTask?.let { task ->
        DetailDialog(
            task = task,
            onUpdate = { updatedTask ->
                viewModel.updateTask(updatedTask)
                selectedTask = null
            },
            onDelete = {
                viewModel.removeTask(task.id)
                selectedTask = null
            },
            onDismiss = { selectedTask = null }
        )
    }
}
