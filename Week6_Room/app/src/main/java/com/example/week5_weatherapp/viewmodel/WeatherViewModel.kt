package com.example.week5_weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week5_weatherapp.data.model.WeatherEntity
import com.example.week5_weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val weatherListState: StateFlow<List<WeatherEntity>> =
        repository.observeWeatherList()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addWeather(city: String, tempC: Double, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWeather(
                WeatherEntity(city = city, temperatureC = tempC, description = description)
            )
        }
    }

    fun updateWeather(entity: WeatherEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeather(entity)
        }
    }

    fun deleteWeather(entity: WeatherEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWeather(entity)
        }
    }
    fun fetchWeatherForCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOrFetchLatestForCity(city)
            // Room emits the change; UI observing the Flow updates automatically
        }
    }
}