---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 203177b6049bc42493e3d0dbc0bf5233
---
`Table`-komponentti tarjoaa erilaisia valintakykyjä. On olemassa menetelmiä yksittäisten kohteiden, useiden kohteiden tai ohjelmallisen valinnan hallintaan.

:::tip Tietojen hallinta ja kysely
Lisätietoja siitä, kuinka käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

## Valintatila {#selection-mode}

Taulukon valintatila määrää, miten käyttäjä voi valita kohteita. Se tarjoaa vaihtoehtoja kohteiden valinnan käyttäytymisen konfiguroimiseksi. Table-luokka tarjoaa menetelmän valintatilan asettamiseen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäinen valinta) 
>- `MULTI` - (monivalinta)
>- `NONE` - (ei valintaa).

## Valintatapahtuma {#selection-event}

`Table`-komponenttipaketti julkaisee useita tapahtumia, jotka liittyvät rivin valintaan. Nämä tapahtumat tallentavat muutoksia `Table`-rivit valintatilassa. Alla on keskeiset valintatapahtumat niiden kuvauksineen:

>- `TableItemSelectEvent` - Julkaistaan, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Julkaistaan, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Julkaistaan, kun koko taulukon valinta muuttuu, tai kun lisävalinta tehdään.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät käynnisty, kun monivalintatila on aktiivinen ja valinta tehdään otsikkotarkistuksen kautta. Tässä tapauksessa tulee käyttää `TableItemSelectionChange`-tapahtumaa sen sijaan.
:::

Esimerkissä alla `TableItemSelectEvent`-tapahtuma laukaistaan aina, kun käyttäjä valitsee rivin. Tapahtumaa voidaan käsitellä lisäämällä kuuntelija taulukkoon käyttäen `onItemSelect()`-menetelmää.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Tarkistusvalinta {#checkbox-selection}

Tarkistusvalinta on käytössä, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien kätevän valita yksi tai useampi kohde jokaiselle riville liittyviin tarkistusruutuihin. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjät tarvitsevat suorittaa joukkotoimintoja valituille kohteille. Table-luokka tarjoaa menetelmiä tarkistusvalinnan sallimiseksi ja mukauttamiseksi.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)`-menetelmää voidaan määrittää, että tarkistusruudut näytetään kunkin rivin vieressä, mikä mahdollistaa käyttäjien kohteiden valitsemisen. Ohjelma alla näyttää monivalinnan ja tarkistusvalinnan käytössä:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Ohjelmallinen valinta {#programatic-selection}

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, jotka mahdollistavat valittujen kohteiden muokkaamisen joko niiden avaimilla tai koko kohteilla. 

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)`-menetelmä mahdollistaa kohteiden ohjelmallisen valinnan käyttäen niiden avaimia. Voit välittää yhdelle tai useammalle avaimelle tämän menetelmän, ja se päivittää valinnan vastaavasti.

### Valitse indeksin mukaan {#select-by-index}

Käyttämällä `selectIndex(int... indices)`-menetelmää voit välittää yhdelle tai useammalle indeksille tähän menetelmään, ja se päivittää valitut kohteet vastaavasti.

### Koko kohteiden valinta {#selecting-entire-items}

Lopuksi `select(T... items)`-menetelmä mahdollistaa kohteiden ohjelmallisen valinnan välittämällä yhdelle tai useammalle kohteelle itselleen tähän menetelmään valinnan päivittämiseksi vastaavasti.
