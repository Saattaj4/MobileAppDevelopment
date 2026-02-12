package com.example.week5_weatherapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5_weatherapp.data.model.WeatherResponse
import com.example.week5_weatherapp.viewmodel.Result
import com.example.week5_weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherState by viewModel.weatherState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { viewModel.searchWeather() }
        )

        when (val state = weatherState) {
            is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Result.Success -> {
                WeatherContent(weather = state.data)
            }
            is Result.Error -> {
                ErrorScreen(
                    message = state.exception.message ?: "Virhe",
                    onRetry = { viewModel.searchWeather() }
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Kaupunki") },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = onSearch) {
            Text("Hae")
        }
    }
}

@Composable
fun WeatherContent(weather: WeatherResponse) {
    val desc = weather.weather.firstOrNull()?.description ?: "n/a"
    Column {
        Text("Paikka: ${weather.name}", style = MaterialTheme.typography.titleMedium)
        Text("Lämpötila: ${weather.main.temp} °C")
        Text("Tuntuu: ${weather.main.feels_like} °C")
        Text("Kuvaus: $desc")
        Text("Tuuli: ${weather.wind.speed} m/s, suunta ${weather.wind.deg}°")
        Text("Maa: ${weather.sys.country}")
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Virhe: $message", color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.width(8.dp))
        Button(onClick = onRetry) { Text("Yritä uudelleen") }
    }
}