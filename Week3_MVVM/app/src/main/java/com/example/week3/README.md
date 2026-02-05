# *Viikkotehtävä #3 - MVVM*



#### *Demovideo:* https://youtu.be/8hJl0AqQ6Ww

#### *Idea lyhyesti:*
Tehtävässä toteutetaan tehtävienhallintasovellus MVVM-arkkitehtuurilla. 
Sovelluksen Viewmodel vastaa sovelluslogiikasta sekä tilan hallinnasta, kun UI keskittyy näkymien piirtämiseen.


MVVM helpottaa koodin ylläpitoa, koska logiikka sekä käyttöliittymä on erotettu toisistaan.
Tämä helpottaa sovelluksen laajentamista myöhemmissä vaiheissa.
 

###  *Stateflow*
StateFlow’ta käytetään sovelluksen tilan välittämiseen ViewModelista käyttöliittymälle.
ViewModel hallitsee tilaa MutableStateFlow-olion avulla, ja Compose seuraa tilaa reaktiivisesti, jolloin käyttöliittymä päivittyy automaattisesti tilan muuttuessa ilman erillisiä päivityskutsuja.

Automaattinen tilan päivittyminen parantaa sovelluksen toimintaa, koska turhia komentoja käyttöliittymän päivittämiseen ei tarvita.