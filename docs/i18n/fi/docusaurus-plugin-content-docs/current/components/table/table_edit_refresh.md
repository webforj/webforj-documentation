---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 6ecf362668be7d0633c3c13e7da068ec
---
Tietojen muokkaaminen `Table`-komponentissa tapahtuu vuorovaikutuksessa `Repositoryn` kanssa, joka sisältää `Table`:n tiedot. `Repository` toimii silta `Table`:n ja taustalla olevan tietojoukon välillä, tarjoten menetelmiä tietojen hakemiseen, muokkaamiseen ja päivittämiseen. Alla on esimerkki, joka toteuttaa käyttäytymisen muokatakseen halutun rivin "Otsikon".

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä `TitleEditorComponent`-luokka helpottaa "Otsikko" kentän muokkaamista valitulle `MusicRecord`:lle. Komponentissa on syöttökenttä uudelle otsikolle sekä "Tallenna" ja "Peruuta" painikkeet.

Yhdistääksemme muokkauskomponentin `Table`:en, "Muokkaa" painike lisätään `Table`:n kautta `VoidElementRenderer`in avulla. Tämän painikkeen klikkaaminen aktivoi `TitleEditorComponent`:n `edit()`-metodin, jolloin käyttäjät voivat muokata "Otsikko" arvoa.

## Commit-menetelmä {#commit-method}

Kun käyttäjä muokkaa otsikkoa ja napsauttaa "Tallenna" painiketta, `TitleEditorComponent` aktivoi `save()`-metodin. Tämä metodi päivittää vastaavan `MusicRecord`:n otsikon ja lähettää mukautetun `SaveEvent`:in.

Reaaliaikainen tietojen päivitys repositoriossa saavutetaan `commit()`-menetelmällä. Tätä menetelmää käytetään `onSave`-tapahtuman kuuntelijassa, varmistaen että muokkauskomponentin kautta tehdyt muutokset heijastuvat taustalla olevaan tietojoukkoon.

`commit()`-metodia kutsutaan ilmoittamaan kaikille kiinnostuneille komponenteille, että tiedot ovat muuttuneet. `Table` nappaa `RepositoryCommitEvent`:in ja päivittää tietojen perusteella uuden tiedon.

:::tip Muokkaaminen ja entryjen luominen
`commit()`-metodin kutsuminen sekä päivittää olemassa olevia entryjä että **lisää kaikki uudet entryt, jotka on luotu**.
:::
