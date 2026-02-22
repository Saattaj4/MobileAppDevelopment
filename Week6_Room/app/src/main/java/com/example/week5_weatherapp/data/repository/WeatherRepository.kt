package com.example.week5_weatherapp.data.repository

import android.util.Log
import com.example.week5_weatherapp.BuildConfig
import com.example.week5_weatherapp.data.local.WeatherDao
import com.example.week5_weatherapp.data.model.WeatherEntity
import com.example.week5_weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val dao: WeatherDao,
    private val api: WeatherApi? = null
) {

    companion object {
        private const val CACHE_DURATION_MS = 30 * 60 * 1000L
    }

    fun observeWeatherList(): Flow<List<WeatherEntity>> = dao.observeAll()

    suspend fun getWeather(id: Long): WeatherEntity? = dao.getById(id)

    suspend fun addWeather(entity: WeatherEntity): Long = dao.insert(entity)

    suspend fun updateWeather(entity: WeatherEntity) = dao.update(entity)

    suspend fun deleteWeather(entity: WeatherEntity) = dao.delete(entity)

    suspend fun getOrFetchLatestForCity(city: String): WeatherEntity? {
        val normalizedCity = city.trim()
        val cached = dao.getLatestByCity(normalizedCity)
        val now = System.currentTimeMillis()

        if (cached != null && now - cached.timestamp <= CACHE_DURATION_MS) {
            Log.d("WeatherRepository", "Returning cached weather for $normalizedCity")
            return cached
        }

        val localApi = api ?: return cached

        return try {
            val resp = localApi.getWeather(normalizedCity, BuildConfig.OPENWEATHER_API_KEY)

            val cityName = resp.name ?: normalizedCity
            val temp = resp.main?.temp ?: 0.0
            val desc = resp.weather?.firstOrNull()?.description ?: ""

            val entity = WeatherEntity(
                city = cityName,
                temperatureC = temp,
                description = desc,
                timestamp = now
            )

            dao.insert(entity)
            Log.d("WeatherRepository", "Fetched from API and saved for $cityName")
            dao.getLatestByCity(cityName)
        } catch (e: Exception) {
            Log.w("WeatherRepository", "Failed to fetch weather for $city: ${e.message}")
            cached
        }
    }
}