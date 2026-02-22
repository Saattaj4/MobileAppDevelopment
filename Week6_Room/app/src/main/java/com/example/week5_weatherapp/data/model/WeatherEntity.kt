package com.example.week5_weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val city: String,
    val temperatureC: Double,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)