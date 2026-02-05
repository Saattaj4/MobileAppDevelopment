# Viikkotehtävä 4 - Navigointi

Demovideo: https://youtube.com/shorts/-MOsQ1tfz8A?feature=share

## Projektin kuvaus

Uusi ominaisuus- navigointi. Sovelluksessa on kolme sivua: HomeScreen (tehtävälista), CalendarScreen (kalenterinäkymä) ja SettingsScreen (asetukset).

---

## Navigointi Jetpack Composessa

Navigointi tarkoittaa käyttäjän siirtymistä eri näkymien välillä sovelluksen sisällä. Jetpack Compose käyttää **Single-Activity-arkkitehtuuria**, eli koko sovellus toimii yhden Activity:n sisällä ja näkymät vaihtuvat composable-funktioina.

---

## NavHost ja NavController

**NavController:**
- Hallitsee navigointia näkymien välillä
- Pitää kirjaa navigointihistoriasta
- Metodit `navigate()` ja `popBackStack()`

**NavHost:**
- Määrittelee mahdolliset navigointireitit
- Yhdistää reitit ("home", "calendar", "settings) composable-funktioihin
- Näyttää oikean näkymän nykyisen reitin perusteella

---

## Navigaatiorakenteen toteutus

**Routes.kt** - Määritellään reitit:
```kotlin
object Routes {
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val SETTINGS = "settings"
}
```

**NavGraph.kt** - Yhdistetään reitit näkymiin:
```kotlin
NavHost(navController, startDestination = Routes.HOME) {
    composable(Routes.HOME) { HomeScreen(...) }
    composable(Routes.CALENDAR) { CalendarScreen(...) }
    composable(Routes.SETTINGS) { SettingsScreen(...) }
}
```

**BottomNavBar.kt** - Navigointipalkki sovelluksen alalaidalla, jossa näkymien vaihto tapahtuu

**Home <--> Calendar navigointi:**
Klikataan Calendar-ikonia → `navController.navigate("calendar")` → NavHost vaihtaa näkymän kalenterinäkymään.

---

## Arkkitehtuuri: MVVM + Navigointi

### Sama ViewModel kahdelle screenille

**TaskViewModel** luodaan MainActivity:ssä ja välitetään sekä HomeScreenille että CalendarScreenille:

```kotlin
val viewModel = TaskViewModel()

NavHost(...) {
    composable("home") { HomeScreen(viewModel) }
    composable("calendar") { CalendarScreen(viewModel) }
}
```

### ViewModelin tilan jakaminen

- ViewModel luodaan **activity-scopessa**, joten se ei tuhoudu navigoinnin aikana
- **Sama instanssi** jaetaan molemmille ruuduille
- Kun HomeScreenillä lisätään tehtävä, kalenterinäkymä saa muutoksen heti
- `mutableStateOf` huolehtii automaattisesta UI-päivityksestä

```kotlin
class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf<List<Task>>(listOf())
    
    fun addTask(task: Task) {
        tasks = tasks + task  // Kaikki UI:t päivittyvät
    }
}
```

---

## CalendarScreen - Toteutus

**Tehtävien ryhmittely:**
```kotlin
val tasksByDate = tasks.groupBy { it.dueDate }.toSortedMap()
```

**Näyttäminen:**
- `groupBy` ryhmittelee tehtävät `dueDate`-kentän mukaan
- `toSortedMap()` järjestää päivämäärät aikajärjestykseen
- LazyColumn:issa näytetään ensin päivämäärän otsikko, sitten kaikki tehtävät kyseiselle päivälle
- Prioriteetti näkyy tähtinä (⭐) ja tehtäviä voi merkitä valmiiksi suoraan kalenterinäkymästä

---

## AlertDialog: addTask ja editTask



Tehtävän lisäys ja muokkaus eivät ole omia navigointiruutuja, vaan dialogeja. Näin varsinaista navigointia käytetään vain pääsivujen (Home, Calendar, Settings) välillä.

**AddTaskDialog:**
- Käyttäjä painaa (+) -nappia HomeScreenillä
- Dialog aukeaa, käyttäjä täyttää tekstikenttiin tiedot
- "Add" → `viewModel.addTask()`, "Cancel" → dialog sulkeutuu

**DetailDialog:**
- Käyttäjä klikkaa tehtävää listalla
- Dialog aukeaa esitäytetyllä datalla
- Käyttäjä voi muokata, poistaa tai peruuttaa muutokset

**Edut:**
- Nopea käyttökokemus (ei ruudunvaihtoa)
- Konteksti säilyy (lista näkyy taustalla)
- Yksinkertainen toteutus

---

## Lisäominaisuus: Teeman vaihto

SettingsScreenillä voi vaihtaa tumman ja vaalean teeman välillä. ThemeViewModel hallitsee teeman tilaa. 

---

## Projektin rakenne

```
com.example.week4
├── domain/          # Business logiikka, testidata
├── model/           # Task data class
├── navigation/      # Routes, NavGraph, BottomNavBar
├── view/            # Screens ja dialogit
├── viewmodel/       # TaskViewModel, ThemeViewModel
├── ui.theme/        # Teema, värit, typografia
└── MainActivity.kt
