package com.example.week2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.week2.domain.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf<List<Task>>(listOf())
        private set

    private var allTasks: List<Task> = listOf()

    init {
        allTasks = mockTasks
        tasks = mockTasks
    }

    fun addTask(task: Task) {
        tasks = addTask(tasks, task)
        allTasks = tasks
    }
    fun toggleDone(id: Int) {
        tasks = toggleDone(tasks, id)
        allTasks = toggleDone (allTasks, id)
    }
    fun filterByDone(done: Boolean) {
        tasks = filterByDone(allTasks, done)
    }
    fun sortByDueDate() {
        tasks = sortByDueDate(tasks)
    }
    fun showAllTasks() {
        tasks = allTasks
    }

    fun removeTask(id: Int) {
        tasks = tasks.filter { it.id != id}
        allTasks = allTasks.filter { it.id != id }
    }
}