---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
`Table`-komponentti tarjoaa useita valintamahdollisuuksia. On olemassa metodeja yksittäisen kohteen, useiden kohteiden valitsemiseksi tai valintojen hallitsemiseksi ohjelmallisesti.

## Valintatila {#selection-mode}

Taulukon valintatila määrää, kuinka käyttäjä voi valita kohteita. Se tarjoaa vaihtoehtoja kohteiden valinnan käyttäytymisen määrittämiseksi. Table-luokka tarjoaa menetelmän valintatilan asettamiseksi:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäinen valinta) 
>- `MULTI` - (usean valinta)
>- `NONE` - (ei valintaa).

## Valintatapahtuma {#selection-event}

`Table`-komponenttipaketti lähettää useita tapahtumia, jotka liittyvät rivivalintaan. Nämä tapahtumat tallentavat muutokset `Table`-rivien valintatilassa. Alla on keskeiset valintatapahtumat kuvauksineen:

>- `TableItemSelectEvent` - Lähetetään, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Lähetetään, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Lähetetään, kun koko taulukon valinta muuttuu tai kun valitaan lisävalinta.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät laukaise, kun usean valinnan tila on aktiivinen ja valinta tehdään otsikkorivin valintaruudun kautta. Tässä tapauksessa pitäisi käyttää `TableItemSelectionChange` -tapahtumaa.
:::

Alla olevassa esimerkissä `TableItemSelectEvent` -tapahtuma laukaistaan aina, kun käyttäjä valitsee rivin. Tapahtuman voi käsitellä lisäämällä kuuntelijan taulukkoon käyttämällä `onItemSelect()`-metodia.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Valintaruutu {#checkbox-selection}

Valintaruutuvalinta on aktivoitu, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien valita vaivattomasti yhden tai useamman kohteen jokaisen rivin viereen liitettyjen valintaruutujen avulla. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjät tarvitsevat suorittaa ryhmätoimia valituilla kohteilla. Table-luokka tarjoaa menetelmiä valintaruutuvalinnan mahdollistamiseksi ja mukauttamiseksi.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)` -metodia valintaruudut voidaan määrittää näytettäväksi kunkin rivin viereen, jolloin käyttäjät voivat valita kohteita. Alla oleva ohjelma näyttää usean valinnan ja valintaruutuvalinnan aktivoituna:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Ohjelmallinen valinta {#programatic-selection}

`Table`-komponentti tarjoaa ohjelmallisen valinnan menetelmiä, mikä mahdollistaa valittujen kohteiden manipuloinnin joko niiden avaimien tai kokonaisuuden kautta. 

### Valitse avaimella {#select-by-key}

`selectKey(Object... keys)` -metodi mahdollistaa kohteiden ohjelmallisen valitsemisen niiden avain-arvojen avulla. Voit välittää tälle metodille yhden tai useamman avaimen, ja se päivittää valinnan sen mukaisesti.

### Valitse indeksin mukaan {#select-by-index}

Käyttämällä `selectIndex(int... indices)` -metodia voit välittää metodille yhden tai useamman indeksin, ja se päivittää valittuja kohteita vastaavasti.

### Kokonaisten kohteiden valitseminen {#selecting-entire-items}

Lopuksi `select(T... items)` -metodi mahdollistaa kokonaisten kohteiden ohjelmallisen valitsemisen välittämällä tälle metodille yksi tai useampi kohde itsessään, jotta valinta voidaan päivittää vastaavasti.
