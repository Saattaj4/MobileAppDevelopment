package com.example.week3.domain

import com.example.week3.model.Task

fun addTask(list: List<Task>, task: Task): List<Task>{
    return list + task
}

fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map{
        if (it.id == id) it.copy(done = !it.done) else it
    }
}

fun filterByDone(list: List<Task>, done: Boolean): List<Task> {
    return list.filter {it.done == done }
}

fun sortByDueDate(list: List<Task>): List<Task> {
    return list.sortedBy {it.dueDate}
}