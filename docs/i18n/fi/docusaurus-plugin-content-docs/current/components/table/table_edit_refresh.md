---
sidebar_position: 30
title: Muokkaaminen ja Päivittäminen
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
Tietojen muokkaaminen `Table`-komponentissa tapahtuu vuorovaikutuksessa `Repository`-komponentin kanssa, joka sisältää tiedot `Table`:lle. `Repository` toimii sillan tavoin `Table`-komponentin ja taustalla olevan datan välillä, tarjoten menetelmiä datan hakemiseen, muokkaamiseen ja päivittämiseen. Alla on esimerkki, joka toteuttaa käyttäytymisen muokata halutun rivin "Otsikko".

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Yllä olevassa esimerkissä `TitleEditorComponent`-luokka mahdollistaa "Otsikko"-kentän muokkaamisen valitulle `MusicRecord`:lle. Komponentti sisältää syöttökentän uudelle otsikolle sekä "Tallenna" ja "Peruuta" -painikkeet.

Yhdistääksesi muokkauskomponentin `Table`-komponenttiin, "Muokkaa" -painike lisätään `Table`-komponenttiin `VoidElementRenderer`-komponentin kautta. Tämä painike laukaisee `TitleEditorComponent`:in `edit()`-menetelmän, joka mahdollistaa käyttäjien muokata "Otsikkoa".

## Commit-menetelmä {#commit-method}

Kun käyttäjä muokkaa otsikkoa ja napsauttaa "Tallenna" -painiketta, `TitleEditorComponent` laukaisee `save()`-menetelmän. Tämä menetelmä päivittää vastaavan `MusicRecord`:in otsikon ja lähettää mukautetun `SaveEvent`:in.

Reaaliaikainen tietojen päivitys repositoriossa saavutetaan `commit()`-menetelmällä. Tämä menetelmä käytetään `onSave`-tapahtuman kuuntelijassa varmistaen, että muokkauskomponentin kautta tehdyt muutokset heijastuvat taustadatassa.

`commit()`-menetelmää kutsutaan ilmoittamaan kaikille kiinnostuneille komponenteille, että dataa on muutettu. `Table` vastaanottaa `RepositoryCommitEvent`:in ja päivittää sen mukaan uusista tiedoista.

:::tip Kirjausten päivittäminen ja luominen
`commit()`-menetelmän kutsuminen päivittää olemassa olevia kirjauksia ja **lisää kaikki uudet kirjaukset, jotka on luotu**.
:::#
