package com.example.week5_weatherapp.data.remote

import com.example.week5_weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    // GET https://api.openweathermap.org/data/2.5/weather?q=Helsinki&appid=YOUR_KEY&units=metric&lang=fi
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fi"
    ): WeatherResponse

    // GET https://api.openweathermap.org/data/2.5/weather?lat=60.17&lon=24.94&appid=YOUR_KEY&units=metric&lang=fi
    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fi"
    ): WeatherResponse
}