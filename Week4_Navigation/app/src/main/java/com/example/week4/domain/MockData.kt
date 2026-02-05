package com.example.week4.domain

import com.example.week4.model.Task

val mockTasks = listOf(
    Task(
        id = 1,
        title = "Kauppahommia",
        description = "Maitua, Vettä, Olutta, Raksuja, Dibbs",
        priority = 2,
        dueDate = "14.3.2025",
        done = false
    ),
    Task(
        id = 2,
        title = "Pyykkitupa",
        description = "Muista varata",
        priority = 1,
        dueDate = "2.7.2026",
        done = false
    ),
    Task(
        id = 3,
        title = "Ruoki elukat",
        description = "Päivittäin (mieluusti)",
        priority = 1,
        dueDate = "19.11.2024",
        done = false
    ),
    Task(
        id = 4,
        title = "Mitäs sitä?",
        description = "Kyllä vain.",
        priority = 5,
        dueDate = "8.5.2027",
        done = false
    ),
    Task(
        id = 5,
        title = "Kävele kouluun",
        description = "Lyhyt matka se on!",
        priority = 5,
        dueDate = "25.12.2025",
        done = false
    ),
    Task(
        id = 6,
        title = "Kyllä.",
        description = "Kyllä.",
        priority = 2,
        dueDate = "3.9.2028",
        done = false
    )
)
