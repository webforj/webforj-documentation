---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
`Table`-komponentti sallii suodatustoiminnallisuuden toteuttamisen, jonka avulla voit rajata näytettäviä tietoja tietyille kielille. Suodatus voidaan toteuttaa määrittämällä suodatuskriteerit käyttäen `setFilter(Predicate<T> filter)` -menetelmää, joka on saatavilla taulukkoon liittyvältä `Repository`-luokalta.

<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Yllä olevassa esimerkissä `setFilter()`-menetelmää käytetään määrittämään suodatuskriteerit `MusicRecord`-nimikkeen mukaan.

:::tip
`setFilter()`-menetelmä kuuluu `Repository`-luokkaan, eikä se ole `Table`-komponentin sisäänrakennettu toiminnallisuus.
:::

Suodatus sovelletaan, kun käyttäjä muuttaa hakukentän sisältöä, päivittäen searchTerm-arvon ja laukaisee `commit()`-menetelmän, joka päivittää näytettävät tiedot.
