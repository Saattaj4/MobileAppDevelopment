# Viikko 1 harjoitus.

Yksinkertainen Android-sovellus.

Tiedostot löytyvät:
Week1_Task_Manager/app/src/main/java/com/example/viikko1


Videolinkki: https://youtube.com/shorts/dgwWOnj4Vfs


## Datamallit

### *Task.kt*
##### - Sovelluksen perusrakenne, jossa määritellään yksinkertaiset tehtävät.

### *MockData.kt* 

#### - Sisältää valmiiksi luotuja tehtäviä, jotka näkyvät listassa.

### *TaskFunctions.kt* 
#### - addTask -> Lisää listaan uuden tehtävän. 
#### - toggleDone -> Vaihtaa tehtävän tilaa tehty / ei tehty.
#### - filterByDone -> Suodattaa tehtävät niiden prioriteetin mukaan.
#### - sortByDueDate -> Järjestää listan päivämäärän mukaan

### *HomeScreen.kt (Käyttöliittymä)*

#### Sovelluksen päänäkymä, joka sisältää kaikki toiminnallisuudet:
##### **All** - näytää kaikki tehtävät listalla
##### **New Task** - Lisää uuden tehtävän listaan
##### **Completed** - Näyttää suoritetut tehtävät
##### **Sort by Date** - Järjestää tehtävät

