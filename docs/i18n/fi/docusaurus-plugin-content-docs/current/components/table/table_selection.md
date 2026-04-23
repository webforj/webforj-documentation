---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 35cb02c29edafbe9a0715b4630be56c1
---
`Table`-komponentti tarjoaa erilaisia valintakykyjä. On olemassa menetelmiä yhden kohteen, useiden kohteiden valitsemiseksi tai valintojen hallitsemiseksi ohjelmallisesti.

:::tip Tietojen hallinta ja kysely
Jos haluat tietää, miten `Repository`-kaaviota käytetään kokoelmien hallitsemiseen ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

## Valintatila {#selection-mode}

Valintatila taulukossa määrittää, miten käyttäjä voi valita kohteita. Se tarjoaa vaihtoehtoja kohdevalinnan käyttäytymisen määrittämiseksi. Table-luokka tarjoaa menetelmän valintatilan asettamiseen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäisvalinta) 
>- `MULTI` - (monivalinta)
>- `NONE` - (ei valintaa).

## Valinta-tapahtuma {#selection-event}

`Table`-komponenttipaketti lähettää useita tapahtumia, jotka liittyvät rivin valintaan. Nämä tapahtumat tallentavat muutoksia `Table`-rivien valintatilassa. Alla ovat keskeiset valintatapahtumat ja niiden kuvaukset:

>- `TableItemSelectEvent` - Lähetetään, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Lähetetään, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Lähetetään, kun koko taulukon valinta muuttuu tai kun lisävalinta tehdään.

:::info
`TableItemSelectEvent`- ja `TableItemDeselectEvent`-tapahtumia ei laukaista, kun monivalintatila on aktiivinen ja valinta tehdään otsikkorivin valintaruudun kautta. Tässä tapauksessa tulisi käyttää `TableItemSelectionChange`-tapahtumaa.
:::

Esimerkissä alla `TableItemSelectEvent`-tapahtuma laukaistaan aina, kun käyttäjä valitsee rivin. Tapahtumaa voidaan käsitellä lisäämällä kuuntelija taulukkoon käyttämällä `onItemSelect()`-menetelmää.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Valintaruutuvalinta {#checkbox-selection}

Valintaruutuvalinta on käytettävissä, kun valintatila on `MULTI`, ja antaa käyttäjille mahdollisuuden valita kätevästi yksi tai useampi kohde kullekin riville liitettyjen valintaruutujen avulla. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjien on suoritettava joukkoja toimintoja valituista kohteista. Table-luokka tarjoaa menetelmiä valintaruutuvalinnan mahdollistamiseksi ja mukauttamiseksi.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)` -menetelmää voit määrittää, että valintaruudut näytetään kunkin rivin vieressä, jolloin käyttäjät voivat valita kohteita. Ohjelma alla näyttää usean valinnan ja valintaruutuvalinnan käytön:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Ohjelmallinen valinta {#programatic-selection}

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, joiden avulla voit manipuloida valittuja kohteita joko niiden avaimien avulla tai kokonaisia kohteita käyttäen.

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)` -menetelmä mahdollistaa kohteiden ohjelmallisen valinnan niiden avaimien avulla. Voit siirtää tähän menetelmään yhden tai useamman avaimen, ja se päivittää valinnan sen mukaisesti.

### Kohteiden valinta {#selecting-entry-items}

Lopuksi `select(T... items)` -menetelmä mahdollistaa kohteiden ohjelmallisen valinnan, kun siirrät yksi tai useampi kohde tähän menetelmään, jolloin valinta päivitetään vastaavasti.
