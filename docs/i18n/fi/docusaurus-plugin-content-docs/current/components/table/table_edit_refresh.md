---
sidebar_position: 30
title: Muokkaaminen ja päivittäminen
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
`Table`-taulukon tietojen muokkaaminen tapahtuu käsittelemällä `Repository`-säilöä, joka sisältää taulukon tiedot. `Repository` toimii siltana `Table`-taulukon ja taustalla olevan tietojoukon välillä ja tarjoaa menetelmiä tietojen hakemiseen, muokkaamiseen ja päivittämiseen. Alla olevassa esimerkissä toteutetaan toiminta, jolla halutun rivin ”Otsikko”-kenttää voidaan muokata.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä `TitleEditorComponent`-luokka mahdollistaa valitun `MusicRecord`-tietueen ”Otsikko”-kentän muokkaamisen. Komponentissa on uuden otsikon syöttökenttä sekä ”Tallenna”- ja ”Peruuta”-painikkeet.

Muokkauskomponentti yhdistetään `Table`-taulukkoon lisäämällä siihen ”Muokkaa”-painike `VoidElementRenderer`-komponentin avulla. Painikkeen napsauttaminen käynnistää `TitleEditorComponent`-komponentin `edit()`-menetelmän, jonka avulla käyttäjä voi muuttaa ”Otsikko”-arvoa.

## Commit-menetelmä {#commit-method}

Kun käyttäjä on muokannut otsikkoa ja napsauttanut ”Tallenna”-painiketta, `TitleEditorComponent` käynnistää `save()`-menetelmän. Tämä menetelmä päivittää vastaavan `MusicRecord`-tietueen otsikon ja lähettää mukautetun `SaveEvent`-tapahtuman.

Tietojen reaaliaikainen päivittäminen säilössä tapahtuu `commit()`-menetelmän avulla. Menetelmää käytetään `onSave`-tapahtumankuuntelijassa, jotta muokkauskomponentilla tehdyt muutokset päivittyvät taustalla olevaan tietojoukkoon.

`commit()`-menetelmää kutsutaan ilmoittamaan kaikille asiasta kiinnostuneille komponenteille, että tietoja on muutettu. `Table` vastaanottaa `RepositoryCommitEvent`-tapahtuman ja päivittää tietonsa uusien tietojen perusteella.

:::tip Tietueiden päivittäminen ja luominen
`commit()`-menetelmän kutsuminen sekä päivittää olemassa olevia tietueita että **lisää kaikki luodut uudet tietueet**.
:::
