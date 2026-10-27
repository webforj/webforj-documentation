---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 47f6b24c0a0621d810e5e2975a0b4749
---
`Table`-komponentti tarjoaa erilaisia valintamahdollisuuksia. On olemassa menetelmiä yksittäisen kohteen valitsemiseen, useiden kohteiden valitsemiseen tai valintojen hallintaan ohjelmallisesti.

:::tip Datan hallinta ja kysely
Lisätietoja siitä, kuinka käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

## Valintatila {#selection-mode}

Taulukon valintatila määrittää, kuinka käyttäjät voivat valita kohteita. Se tarjoaa vaihtoehtoja kohteen valinnan käytöksen määrittämiseksi. Taulukko-luokka tarjoaa menetelmän valintatilan asettamiseksi:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäinen valinta)
>- `MULTI` - (monivalinta)
>- `NONE` - (ei valintaa).

## Valintaeventti {#selection-event}

`Table`-komponenttipaketti julkaisee useita tapahtumia, jotka liittyvät rivivalintaan. Nämä tapahtumat tallentavat muutoksia `Table`-rivien valintatilassa. Alla ovat tärkeimmät valinta-eventit sekä niiden kuvaukset:

>- `TableItemSelectEvent` - Julkaistaan, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Julkaistaan, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Julkaistaan, kun taulukon kokonaisvalinta muuttuu tai kun lisävalinta tehdään.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät aktivoidu, kun monivalintatila on käytössä ja valinta tehdään otsikkoruudun valintaruudun kautta. Tässä tapauksessa tulee käyttää `TableItemSelectionChange`-tapahtumaa.
:::

Esimerkissä alla `TableItemSelectEvent`-tapahtuma laukaisee aina, kun käyttäjä valitsee rivin. Tapahtumalle voidaan lisätä käsittelijä taulukkoon käyttämällä `onItemSelect()`-metodia.

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

Valintaruutuvalinta on käytössä, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien valita yhden tai useamman kohteen helposti valintaruudujen avulla, jotka on liitetty jokaiseen riviin. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjät tarvitsevat suorittaa massatoimia valituilla kohteilla. Taulukko-luokka tarjoaa menetelmiä valintaruudun valinnan mahdollistamiseen ja mukauttamiseen.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)`-metodia voidaan konfiguroida valintaruudut näytettäväksi jokaisen rivin vieressä, jolloin käyttäjät voivat valita kohteita. Alla oleva ohjelma näyttää monivalinnan ja valintaruutuvalinnan olevan käytössä:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Ohjelmallinen valinta {#programmatic-selection}

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, jotka mahdollistavat valittujen kohteiden muokkaamisen joko niiden avaimilla tai koko kohteilla.

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)`-metodi mahdollistaa kohteiden ohjelmallisen valitsemisen niiden avaimien avulla. Voit välittää tälle metodille yhden tai useamman avaimen, ja se päivittää valinnan vastaavasti.

### Valitsemalla entiteettikohteet {#selecting-entry-items}

Lopuksi `select(T... items)`-metodi mahdollistaa kohteiden ohjelmallisen valitsemisen välittämällä yksi tai useampi kohde tälle metodille, jolloin valinta päivitetään vastaavasti.
