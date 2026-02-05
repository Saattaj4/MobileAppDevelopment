package com.example.viikko1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val allTasks = remember { mockTasks }
    var tasks by remember { mutableStateOf(allTasks) }

    Column(modifier = modifier.padding(16.dp)) {

        Text("My Tasks")

        Button(onClick = {
            tasks = allTasks
        }) {
            Text("All")
        }

        Row {
            Button(onClick = {
                val newTask = Task(
                    id = tasks.size + 1,
                    title = "New Task",
                    description = "Description",
                    priority = 1,
                    dueDate = "2025-01-01",
                    done = false
                )
                tasks = addTask(tasks, newTask)
            }) {
                Text("New task")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { tasks = filterByDone(tasks, true) }) {
                Text("Completed")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { tasks = sortByDueDate(tasks) }) {
                Text("Sort by Date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        tasks.forEach { task ->
            Row(modifier = Modifier.padding(vertical = 4.dp)) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(task.title)
                    Text(task.description)
                    Text(text = "Priority: ${task.priority}")
                    Text("Due: ${task.dueDate}")
                }

                Button(onClick = { tasks = toggleDone(tasks, task.id) }) {
                    Text(if (task.done) "Undo" else "Done")
                }
            }
        }
    }
}
