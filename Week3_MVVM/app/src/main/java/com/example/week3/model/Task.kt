package com.example.week3.model

data class Task(
val id: Int,
val title: String,
val description: String,
val priority: Int,
val dueDate: String,
    val done: Boolean
)