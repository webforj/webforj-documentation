---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 46e92f0b5b3f1dafbf040176711ae5ac
---
`Table`-komponentti tarjoaa erilaisia valintamahdollisuuksia. On olemassa menetelmiä yksittäisten kohteiden valitsemiseen, useiden kohteiden valitsemiseen tai valintojen hallitsemiseen ohjelmallisesti.

:::tip Datan hallinta ja kysely
Tietoa `Repository`-mallin käytöstä kokoelmien hallitsemiseksi ja kyselyjen tekemiseksi löytyy [Repository-artikkeleista](/docs/advanced/repository/overview).
:::

## Valintatila {#selection-mode}

Taulukon valintatila määrittää, kuinka käyttäjä voi valita kohteita. Se tarjoaa vaihtoehtoja kohteiden valinnan käyttäytymisen määrittämiseksi. Table-luokka tarjoaa menetelmän valintatilan asettamiseen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäisvalinta) 
>- `MULTI` - (monivalinta)
>- `NONE` - (ei valintaa).

## Valintatapahtuma {#selection-event}

`Table`-komponenttipaketti emittoi useita tapahtumia, jotka liittyvät rivivalintaan. Nämä tapahtumat tallentavat muutoksia `Table`-rivien valintatilassa. Alla ovat keskeiset valintatapahtumat kuvauksineen:

>- `TableItemSelectEvent` - Emittoituu, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Emittoituu, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Emittoituu, kun taulukon yleinen valinta muuttuu tai kun valitaan lisävalinta.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät laukaisemmat, kun monivalintatila on aktiivinen ja valinta tehdään yläpalkin valintaruutua käyttämällä. Tässä tapauksessa on käytettävä `TableItemSelectionChange`-tapahtumaa.
:::

Alla olevassa esimerkissä `TableItemSelectEvent`-tapahtuma laukaistaan aina, kun käyttäjä valitsee rivin. Tapahtuma voidaan käsitellä lisäämällä kuuntelija taulukkoon käyttämällä `onItemSelect()`-menetelmää.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Valintaruutuvalinta {#checkbox-selection}

Valintaruutuvalinta on käytössä, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien valita kätevästi yksi tai useampi kohde jokaisen rivin vieressä olevien valintaruutujen avulla. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjien on suoritettava massatoimia valituilla kohteilla. Table-luokka tarjoaa menetelmiä valintaruudun valinnan aktivoimiseen ja mukauttamiseen.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)`-menetelmää valintaruudut voidaan määrittää näytettäväksi jokaisen rivin viereen, jolloin käyttäjät voivat valita kohteita. Alla oleva ohjelma näyttää, kuinka monivalinta ja valintaruutuvalinta on käytössä:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Ohjelmallinen valinta {#programatic-selection}

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, joiden avulla voit manipuloida valittuja kohteita joko niiden avaimilla tai kokonaisilla kohteilla. 

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)`-menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen käyttäen niiden avaimia. Voit välittää tälle menetelmälle yhden tai useamman avaimen, ja se päivityttää valinnan vastaavasti.

### Valintakohteiden valinta {#selecting-entry-items}

Lopuksi `select(T... items)`-menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen välittämällä tälle menetelmälle yksi tai useampi itse kohteista päivityksen tekemiseksi valintaan.
