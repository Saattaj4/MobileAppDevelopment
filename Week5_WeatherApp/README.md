# Week 5 - Weather App

Demovideo: https://youtu.be/y7LU_DJixaQ


## Retrofit
- Tekee HTTP‑pyynnöt OpenWeatherMapille.
- `WeatherApi` määrittelee reitit (esim. `GET weather`).
- `RetrofitInstance` hoitaa `baseUrl`in ja Gson‑muuntimen.

## HTTP‑pyynnöt
- Kaupunki: `getWeather(q, appid, units=metric, lang=fi)`
- Koordinaatit: `getWeatherByCoordinates(lat, lon, appid, units=metric, lang=fi)`

## JSON → dataluokat (Gson)
- `converter-gson` muuttaa JSONin suoraan Kotlin‑dataluokiksi.
- Käytössä `WeatherResponse` (+ sisäiset luokat `Main`, `Weather`, `Wind`, `Sys`).

## Coroutines
- `viewModelScope.launch { ... }` tekee API‑kutsun taustalla.
- Kun data tulee, UI päivittyy automaattisesti.

## UI‑tila
- `Result`: `Loading` → `Success(data)` → `Error(e)`.
- `StateFlow` ViewModelissa, Compose kerää `collectAsState()`.

## API‑avain
- `local.properties` → `BuildConfig.OPENWEATHER_API_KEY` → välitetään Retrofit‑pyyntöön.
