package com.example.week2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx. compose.runtime.*
import androidx. compose.ui.Modifier
import androidx.compose.ui.unit. dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week2.domain.Task
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy. items

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: TaskViewModel = viewModel()
    var taskTitle by remember {mutableStateOf("")}

    Column(modifier = modifier.padding(16.dp)) {

        Text(
            text = "My Tasks",
        )

        Spacer(modifier = Modifier.height(16.dp))

                // Button row
        Row {
            Button(onClick = {
                viewModel.showAllTasks()
            }) {
                Text("All")
            }
            Spacer(modifier = Modifier. width(8.dp))

            Button(onClick = {
                viewModel.filterByDone(true)
            }) {
                Text("Completed tasks")
            }

            Spacer(modifier = Modifier. width(8.dp))
            Button(onClick = {
                viewModel.sortByDueDate()   // Sort by date
            }) {
                Text("Sort by Date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input field to text
        TextField(
            value = taskTitle,
            onValueChange = {taskTitle = it},
            label = { Text("Task title")},
            modifier = Modifier. fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

            // Add new task to list
        Button(
            onClick = {
                if (taskTitle.isNotBlank()) {
                    val newTask = Task(
                        id = viewModel.tasks.size +1,
                        title = taskTitle,
                        description = "",
                        priority = 5,
                        dueDate = "13.09.2029",
                        done = false
                    )
                    viewModel. addTask(newTask)
                    taskTitle = ""  // Clears the Task title row when added to list
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Add to list")
        }
        Spacer(modifier = Modifier.height(16.dp))

            // Tasks list
        LazyColumn(
            verticalArrangement = Arrangement. spacedBy(8.dp)
        ) {
            items(viewModel.tasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                            //Task  = Done - Not done
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task. id) }
                        )
                        Spacer(modifier = Modifier. width(8.dp))

                                // Task details
                        Column(modifier = Modifier. weight(1f)) {
                            Text(task.title)
                            Text(task.description)
                            Text(text = "Priority: ${task.priority}")
                            Text("Due: ${task.dueDate}")
                        }

                        Spacer(modifier = Modifier. width(8.dp))

                                // Delete task from list
                        Button(onClick = {
                            viewModel.removeTask(task.id)
                        }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}