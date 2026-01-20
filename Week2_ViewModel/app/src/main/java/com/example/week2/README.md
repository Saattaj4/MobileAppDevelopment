# *Viikkotehtävä #2 - Viewmodel*

### Tehtävienhallintasovellus ViewModelin avulla.

#### *Demovideo:* https://youtu.be/saX0ZzeIGcc

#### *Idea lyhyesti:*

- Tilaa säilytetään mutableStateOf:ssa
- Tilan muuttuessa, @Compose päivittää UI:n
- Tilan (state) muuttumisesta ei tällöin tarvitse huolehtia

## Esimerkiksi:


 **Tila:    var tasks by mutableStateOf<List<Task>>(listOf())** 

**tasks = newList**      Muutos -> Päivitys UI:n tilaan heti.

### Viikko 1 koodi:
**var tasks by remember { mutableStateOf(mockTasks) }**

- Mikäli ruutua / laitetta käännettään, tieto katoaa näytöstä.
- Logiikka on kiinni UI:ssa

### Viikko 2 (ViewModel):
**class TaskViewModel : ViewModel() {**

**var tasks by mutableStateOf<List<Task>>(listOf())
}**

- Logiikkaa kutsutaan ViewModelin funktioiden kautta. 
- Tieto säilyy, vaikka näyttöä käänetään.
- Koodin luettavuus helpottuu,
- Koodia ei tarvitse toistaa useaan kertaan (DRY - sääntö).
- ViewModel sisältää funktiot tilan muuttamiseen

    --> Esimerkiksi tehtävän lisääminen tai poistaminen, joita UI kutsuu.