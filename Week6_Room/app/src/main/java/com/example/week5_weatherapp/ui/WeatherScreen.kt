package com.example.week5_weatherapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week5_weatherapp.data.model.WeatherEntity
import com.example.week5_weatherapp.viewmodel.WeatherViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val list by viewModel.weatherListState.collectAsState()
    var showCityDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCityDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Fetch")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(list) { item ->
                    WeatherRow(
                        item,
                        onDelete = { viewModel.deleteWeather(it) },
                        onEdit = {
                        }
                    )
                }
            }
        }

        if (showCityDialog) {
            CityInputDialog(
                onFetch = { city ->
                    viewModel.fetchWeatherForCity(city)
                    showCityDialog = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Requested weather for $city")
                    }
                },
                onDismiss = { showCityDialog = false }
            )
        }
    }
}

@Composable
fun WeatherRow(
    item: WeatherEntity,
    onDelete: (WeatherEntity) -> Unit,
    onEdit: (WeatherEntity) -> Unit
) {
    val sdf = remember { SimpleDateFormat("dd.MM HH:mm", Locale.getDefault()) }
    val lastUpdated = remember(item.timestamp) {
        if (item.timestamp > 0L) "Päivitetty: ${sdf.format(Date(item.timestamp))}" else ""
    }

    ListItem(
        headlineContent = { Text("${item.city}: ${"%.2f".format(item.temperatureC)}°C") },
        supportingContent = {
            Column {
                Text(item.description)
                if (lastUpdated.isNotEmpty()) {
                    Text(lastUpdated, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit(item) },
        trailingContent = {
            IconButton(onClick = { onDelete(item) }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    )
}

@Composable
fun CityInputDialog(onFetch: (String) -> Unit, onDismiss: () -> Unit) {
    var city by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Fetch weather") },
        text = {
            Column {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val input = city.trim()
                if (input.isNotEmpty()) onFetch(input)
            }) {
                Text("Fetch")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}