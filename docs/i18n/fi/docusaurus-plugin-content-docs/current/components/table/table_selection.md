---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: c0e15da31dab7ea7701ea65b47ce67f8
---
`Table`-komponentti tarjoaa erilaisia valintamahdollisuuksia. Se sisältää menetelmiä yksittäisten kohteiden, useiden kohteiden tai ohjelmallisen valinnan hallintaan.

## Valintatila {#selection-mode}

Taulukon valintatila määrittää, miten käyttäjä voi valita kohteita. Se tarjoaa vaihtoehtoja kohteiden valinnan käyttäytymisen määrittelyyn. Table-luokka tarjoaa menetelmän valintatilan asettamiseen:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäisvalinta) 
>- `MULTI` - (useita valintoja)
>- `NONE` - (ei valintaa).

## Valintatapahtuma {#selection-event}

`Table`-komponenttipaketti lähettää useita tapahtumia, jotka liittyvät rivivalintaan. Nämä tapahtumat tallentavat muutoksia `Table`-rivien valintatilassa. Alla on keskeiset valintatapahtumat kuvauksineen:

>- `TableItemSelectEvent` - Lähetetään, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Lähetetään, kun taulukon kohteesta luovutaan.
>- `TableItemSelectionChange` - Lähetetään, kun taulukon yleinen valinta muuttuu tai kun valitaan lisävalinta.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät käynnisty, kun usean valinnan tila on aktiivinen ja valinta tehdään pääotsikon valintaruudun kautta. Tällöin `TableItemSelectionChange`-tapahtumaa tulisi käyttää sen sijaan.
:::

Alla olevassa esimerkissä laukaisemme `TableItemSelectEvent`-tapahtuman aina, kun käyttäjä valitsee rivin. Tapahtuma voidaan käsitellä lisäämällä kuuntelija taulukkoon käyttämällä `onItemSelect()`-menetelmää.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Valintaruutuvalinta {#checkbox-selection}

Valintaruutuvalinta on käytössä, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien mukavasti valita yksi tai useampi kohde kunkin rivin yhteydessä olevilla valintaruutuilla. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjät tarvitsevat tehdä massatoimia valituilla kohteilla. Table-luokka tarjoaa menetelmiä valintaruutuvalinnan mahdollistamiseksi ja mukauttamiseksi.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)`-menetelmää valintaruutuja voidaan konfiguroida näkyviin joka rivin viereen, jolloin käyttäjät voivat valita kohteita. Alla oleva ohjelma näyttää usean valinnan ja valintaruudun valinnan olevan käytössä:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Ohjelmallinen valinta {#programatic-selection}

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, jotta voit manipuloida valittuja kohteita niiden avaimien tai koko kohteen perusteella.

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)`-menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen niiden avaimia käyttäen. Voit antaa tälle menetelmälle yhden tai useamman avaimen, ja se päivittää valinnan vastaavasti.

### Valitse indeksin mukaan {#select-by-index}

`selectIndex(int... indices)`-menetelmän käyttäminen mahdollistaa yhden tai useamman indeksin lähettämisen menetelmälle, ja se päivittää valitut kohteet vastaavasti.

### Koko kohteiden valitseminen {#selecting-entire-items}

Lopuksi `select(T... items)`-menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen, kunhan annat tämän menetelmän kautta yhden tai useamman kohteen, ja se päivittää valinnan vastaavasti.
