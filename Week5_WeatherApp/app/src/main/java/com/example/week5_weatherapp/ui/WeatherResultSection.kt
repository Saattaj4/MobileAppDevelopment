package com.example.week5_weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import com.example.week5_weatherapp.data.model.WeatherResponse

@Composable
fun WeatherResultSection(weather: WeatherResponse) {
    val w = weather.weather.firstOrNull()
    val icon = weatherIcon(main = w?.main, description = w?.description)

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = w?.description ?: "Sää",
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(12.dp))

            val temp = String.format("%.1f °C", weather.main.temp)
            val feels = String.format("%.1f °C", weather.main.feels_like)
            val wind = String.format("%.0f m/s", weather.wind.speed) // whole m/s

            Text("Paikka: ${weather.name}", style = MaterialTheme.typography.titleMedium)
            Text("Lämpötila: $temp")
            Text("Tuntuu: $feels")
            Text("Kuvaus: ${w?.description ?: "n/a"}")
            Text("Tuuli: $wind")
            Text("Maa: ${weather.sys.country}")
        }
    }
}

/** Map OpenWeather "main"/description to a Material icon. */
private fun weatherIcon(main: String?, description: String?): ImageVector {
    val m = main?.lowercase() ?: ""
    val d = description?.lowercase() ?: ""
    return when {
        m == "clear" -> Icons.Filled.WbSunny
        m == "clouds" -> Icons.Filled.Cloud
        m == "rain" || m == "drizzle" -> Icons.Filled.WaterDrop
        m == "snow" -> Icons.Filled.AcUnit
        m == "thunderstorm" -> Icons.Filled.FlashOn
        m in listOf("mist", "fog", "haze", "smoke", "dust", "sand") || d.contains("sumu") || d.contains("pilvinen") -> Icons.Filled.BlurOn
        else -> Icons.Filled.Cloud
    }
}