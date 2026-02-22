package com.example.week5_weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5_weatherapp.data.local.AppDatabase
import com.example.week5_weatherapp.data.remote.WeatherApi
import com.example.week5_weatherapp.data.repository.WeatherRepository
import com.example.week5_weatherapp.ui.WeatherScreen
import com.example.week5_weatherapp.viewmodel.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(WeatherApi::class.java)

        val repo = WeatherRepository(db.weatherDao(), api)

        setContent {
            val factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WeatherViewModel(repo) as T
                }
            }

            val vm: WeatherViewModel = viewModel(factory = factory)

            MaterialTheme {
                WeatherScreen(viewModel = vm)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Text(text = "WeatherScreen preview")
    }
}