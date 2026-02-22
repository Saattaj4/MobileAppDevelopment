package com.example.week5_weatherapp.data.local

import androidx.room.*
import com.example.week5_weatherapp.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather WHERE id = :id")
    suspend fun getById(id: Long): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherEntity): Long

    @Update
    suspend fun update(entity: WeatherEntity)

    @Delete
    suspend fun delete(entity: WeatherEntity)

    @Query("SELECT * FROM weather WHERE city = :city ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestByCity(city: String): WeatherEntity?
}