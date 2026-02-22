# *Week 6 - Room*

#### *Demovideo*: https://youtu.be/R5EgNQJXttw


## Mitä Room tekee?
- Entity = tietokantataulukon rivi (WeatherEntity)
- DAO = SQL‑kyselyt/CRUD (WeatherDao)
- Database = Room‑instanssi (AppDatabase)
- Repository = yhdistää Roomin ja verkon (WeatherRepository), vastaa välimuistista ja datan synkronoinnista
- ViewModel = kutsuu Repositorya ja pitää UI:n tilan
- UI = Compose‑näyttö joka kuuntelee ViewModelin StateFlow/Flow:ta

## Projektin rakenne (pääkansiot)
- data.model — DTO:t ja entityt (WeatherResponse, WeatherEntity)
- data.local — Room: WeatherDao, AppDatabase
- data.remote — Retrofit API (WeatherApi)
- data.repository — WeatherRepository (cache + verkko)
- viewmodel — WeatherViewModel
- ui — Compose‑komponentit (WeatherScreen, CityInputDialog)
- MainActivity — riippuvuuksien luonti (Retrofit, DB, Repo, ViewModel)

## Datavirta
UI → ViewModel.fetchWeatherForCity(city) → Repository.getOrFetchLatestForCity(city) →
- Repository tarkistaa Roomista (getLatestByCity)
    - Jos cache on tuore (≤ 30 min) → palauttaa cache
    - Muuten hakee OpenWeather API:sta, tallentaa Roomiin ja palauttaa tallennetun rivin
      Room Flow lähettää päivityksen → ViewModelin StateFlow päivittyy → UI päivittyy

## Välimuistilogikka
- Oletusarvoinen välimuistin kesto: 30 minuuttia (CACHE_DURATION_MS)
- Verkkovirheissä Repository palauttaa olemassa olevan cache‑arvon, jos sellainen on; muuten null


