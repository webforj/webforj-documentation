---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
Tietojen muokkaaminen `Table`-komponentissa tapahtuu vuorovaikutuksessa `Repository`:n kanssa, joka sisältää taulukon tiedot. `Repository` toimii sillan tavoin `Table`-komponentin ja taustatietojoukon välillä, tarjoten menetelmiä tietojen hakemiseen, muokkaamiseen ja päivittämiseen. Alla on esimerkki, joka toteuttaa käyttäytymisen muokataksesi halutun rivin "Otsikkoa".

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä `TitleEditorComponent`-luokka helpottaa "Otsikko"-kentän muokkaamista valitulle `MusicRecord`:ille. Komponentti sisältää syötekentän uudelle otsikolle sekä "Tallenna" ja "Peruuta" -painikkeet.

Yhdistääksemme muokkauskomponentin `Table`:n kanssa, "Muokkaa" -painike lisätään `Table`:hin käyttämällä `VoidElementRenderer`:ia. Tämän painikkeen napsauttaminen laukaisee `edit()`-metodin `TitleEditorComponent`:issa, mikä mahdollistaa käyttäjien muokata "Otsikko" -arvoa.

## Commit method {#commit-method}

Kun käyttäjä muokkaa otsikkoa ja napsauttaa "Tallenna"-painiketta, `TitleEditorComponent` laukaisee `save()`-metodin. Tämä metodi päivittää vastaavan `MusicRecord`:in otsikon ja lähettää mukautetun `SaveEvent`:in.

Reaaliaikainen päivitys tietovarastossa saavutetaan `commit()`-metodin kautta. Tätä metodia käytetään `onSave`-tapahtuman kuuntelijassa, varmistaen, että muokkauskomponentissa tehdyt muutokset heijastuvat taustatietojoukkoon.

`commit()`-metodia kutsutaan ilmoittamaan kaikille kiinnostuneille komponenteille, että tiedot on muutettu. `Table`-komponentti vastaanottaa `RepositoryCommitEvent`:in ja päivittää perustuen uusiin tietoihin.

:::tip Tietojen päivittäminen ja luominen
`commit()`-metodin kutsuminen päivittää olemassa olevia tietueita ja **lisää kaikki uudet tietueet, jotka on luotu**.
:::
