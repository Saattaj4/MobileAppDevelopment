package com.example.week4.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week4.domain.mockTasks
import com.example.week4.model.Task


class TaskViewModel : ViewModel() {

    var tasks by mutableStateOf<List<Task>>(listOf())
        private set

    private var allTasks: List<Task> = listOf()

    init {
        allTasks = mockTasks
        tasks = mockTasks
    }

    fun addTask(task: Task) {
        tasks = tasks + task
        allTasks = tasks
    }

    fun toggleDone(id: Int) {
        tasks = tasks.map { if (it.id == id) it.copy(done = !it.done) else it }
        allTasks = allTasks.map { if (it.id == id) it.copy(done = !it.done) else it }
    }

    fun updateTask(updatedTask: Task) {
        tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it }
        allTasks = allTasks.map { if (it.id == updatedTask.id) updatedTask else it }
    }

    fun removeTask(id: Int) {
        tasks = tasks.filter { it.id != id }
        allTasks = allTasks.filter { it.id != id }
    }

    fun filterByDone(done: Boolean) {
        tasks = allTasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        tasks = tasks.sortedBy { it.dueDate } // assumes dueDate is "YYYY-MM-DD"
    }

    fun showAllTasks() {
        tasks = allTasks
    }
}
