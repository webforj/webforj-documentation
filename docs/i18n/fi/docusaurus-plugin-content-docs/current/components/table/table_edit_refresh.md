---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 1a8c3b1ab877d759906737431d8601e1
---
Tietojen muokkaaminen `Table`-komponentissa tapahtuu vuorovaikutuksessa `Repository`-komponentin kanssa, joka sisältää `Table`:n tiedot. `Repository` toimii sillan tavoin `Table`:n ja taustalla olevan tietojoukon välillä, tarjoten menetelmiä tietojen hakemiseen, muokkaamiseen ja päivittämiseen. Alla on esimerkki, joka toteuttaa käyttäytymisen muokata halutun rivin "Otsikkoa" perustuen.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Yllä olevassa esimerkissä `TitleEditorComponent`-luokka helpottaa "Otsikko" kentän muokkaamista valitulle `MusicRecord`:ille. Komponentti sisältää syöttökentän uudelle otsikolle sekä "Tallenna" ja "Peruuta" -painikkeet.

Liittääksesi muokkauskomponentin `Table`:hun, "Muokkaa" -painike lisätään `Table`:hen `VoidElementRenderer`in kautta. Tämän painikkeen klikkaaminen laukaisee `TitleEditorComponent`:n `edit()`-menetelmän, joka mahdollistaa käyttäjien muokata "Otsikko" arvoa.

## Commit-methodi {#commit-method}

Kun käyttäjä muokkaa otsikkoa ja napsauttaa "Tallenna" -painiketta, `TitleEditorComponent` laukaisee `save()`-menetelmän. Tämä menetelmä päivittää vastaavan `MusicRecord`:n otsikon ja lähettää mukautetun `SaveEvent`:in.

Tietojen reaaliaikainen päivitys varastossa saavutetaan `commit()`-menetelmällä. Tämä menetelmä käytetään `onSave`-tapahtuman kuuntelijassa, varmistaen, että muokkauskomponentin kautta tehdyt muutokset heijastuvat taustalla olevaan tietojoukkoon.

`commit()`-menetelmää kutsutaan ilmoittamaan kaikille kiinnostuneille komponenteille, että tiedot on muutettu. `Table` vastaanottaa `RepositoryCommitEvent`:in ja päivittää uusien tietojen perusteella. 

:::tip Päivitys ja uusien tietojen luominen
`commit()`-menetelmän kutsuminen päivittää olemassa olevia merkintöjä ja **lisää kaikki uudet merkinnät, jotka on luotu**.
:::
