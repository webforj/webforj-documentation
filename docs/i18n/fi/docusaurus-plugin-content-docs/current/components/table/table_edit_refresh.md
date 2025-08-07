---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
Tietojen muokkaaminen `Table`-komponentissa tapahtuu vuorovaikutuksessa `Repository`:n kanssa, joka sisältää `Table`:n tietoja. `Repository` toimii silta `Table`:n ja taustalla olevan tietojoukon välillä, tarjoten menetelmiä tietojen hakemiseen, muokkaamiseen ja päivittämiseen. Alla on esimerkki, joka toteuttaa käyttäytymisen muokata halutun rivin "Otsikkoa".

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Edellä olevassa esimerkissä `TitleEditorComponent`-luokka mahdollistaa "Otsikko"-kentän muokkaamisen valitulle `MusicRecord`:ille. Komponentti sisältää syöttökentän uudelle otsikolle sekä "Tallenna" ja "Peruuta" -painikkeet.

Yhdistääksemme muokkauskomponentin `Table`:n kanssa, "Muokkaa"-painike lisätään `Table`:hen `VoidElementRenderer`:in kautta. Tämän painikkeen napsauttaminen laukaisee `TitleEditorComponent`:n `edit()`-menetelmän, mikä antaa käyttäjille mahdollisuuden muokata "Otsikkoa".

## Commit-menetelmä {#commit-method}

Kun käyttäjä muokkaa otsikkoa ja napsauttaa "Tallenna"-painiketta, `TitleEditorComponent` laukaisee `save()`-menetelmän. Tämä menetelmä päivittää vastaavan `MusicRecord`:in otsikon ja käynnistää mukautetun `SaveEvent`:in.

Reaaliaikainen tietojen päivitys repositoriossa saavutetaan `commit()`-menetelmän avulla. Tätä menetelmää käytetään `onSave`-tapahtumakuuntelijassa, varmistaen, että muokkauskomponentin kautta tehdyt muutokset heijastuvat taustalla olevaan tietojoukkoon.

`commit()`-menetelmää kutsutaan ilmoittamaan kaikille kiinnostuneille komponenteille, että tiedot ovat muuttuneet. `Table` vastaanottaa `RepositoryCommitEvent`:in ja päivittää uuden datan perusteella.

:::tip Tietojen päivittäminen ja luominen
`commit()`-menetelmän kutsuminen päivittää olemassa olevia merkintöjä ja **lisää kaikki uudet merkinnät, jotka on luotu**.
:::
