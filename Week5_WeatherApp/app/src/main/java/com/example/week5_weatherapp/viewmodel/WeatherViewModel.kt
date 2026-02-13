package com.example.week5_weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week5_weatherapp.BuildConfig
import com.example.week5_weatherapp.data.model.WeatherResponse
import com.example.week5_weatherapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

class WeatherViewModel : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherResponse>> = _weatherState.asStateFlow()

    private val _searchQuery = MutableStateFlow("Rovaniemi")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        val city = _searchQuery.value.trim()
        if (city.isBlank()) return

        val apiKey = BuildConfig.OPENWEATHER_API_KEY
        if (apiKey.isEmpty()) {
            _weatherState.value = Result.Error(IllegalStateException("API key puuttuu"))
            return
        }

        viewModelScope.launch {
            _weatherState.value = Result.Loading
            try {
                val resp = RetrofitInstance.api.getWeather(
                    city = city,
                    apiKey = apiKey,
                    units = "metric",
                    lang = "fi"
                )
                _weatherState.value = Result.Success(resp)
            } catch (e: Exception) {
                _weatherState.value = Result.Error(e)
            }
        }
    }
}