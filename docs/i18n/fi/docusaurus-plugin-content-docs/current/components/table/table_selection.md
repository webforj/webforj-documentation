---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
`Table`-komponentti tarjoaa erilaisia valintamahdollisuuksia. On olemassa metodeja yksittäisten kohteiden, useiden kohteiden tai ohjelmallisten valintojen hallintaan.

:::tip Tietojen hallinta ja kysely
Tietoa siitä, miten käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

## Valintatila {#selection-mode}

Taulukon valintatila määrittää, miten käyttäjät voivat valita kohteita. Se tarjoaa vaihtoehtoja kohteiden valintakäyttäytymisen määrittämiseksi. Table-luokka tarjoaa menetelmän valintatilan asettamiseksi:

```java
setSelectionMode(SelectionMode selectionMode)
```

Saatavilla olevat SelectionMode-vaihtoehdot ovat:

>- `SINGLE` - (yksittäisvalinta)
>- `MULTI` - (usean valinnan)
>- `NONE` - (ei valintaa).

## Valintatapahtuma {#selection-event}

`Table`-komponenttipaketti lähettää useita tapahtumia, jotka liittyvät rivivalintoihin. Nämä tapahtumat tallentavat muutokset `Table`-rivien valintatilassa. Alla ovat keskeiset valintatapahtumat ja niiden kuvaukset:

>- `TableItemSelectEvent` - Lähetetään, kun taulukon kohde valitaan.
>- `TableItemDeselectEvent` - Lähetetään, kun taulukon kohde poistetaan valinnasta.
>- `TableItemSelectionChange` - Lähetetään, kun koko taulukkovalinta muuttuu tai kun valitaan lisävalinta.

:::info
`TableItemSelectEvent` ja `TableItemDeselectEvent` eivät laukaise, kun usean valinnan tila on aktiivinen ja valinta tehdään otsikkoruutu-checkboxin kautta. Tässä tapauksessa tulee käyttää `TableItemSelectionChange` -tapahtumaa sen sijaan.
:::

Esimerkissä alla `TableItemSelectEvent`-tapahtuma laukaistaan aina, kun käyttäjä valitsee rivin. Tapahtuman voi käsitellä lisäämällä kuuntelijan taulukolle `onItemSelect()`-menetelmän avulla.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Checkbox-valinta {#checkbox-selection}

Checkbox-valinta on käytössä, kun valintatila on `MULTI`, ja se mahdollistaa käyttäjien valita kätevästi yhden tai useamman kohteen jokaisen rivin yhteydessä olevien valintaruutujen avulla. Tämä ominaisuus on erityisen hyödyllinen tilanteissa, joissa käyttäjät tarvitsevat ryhmätoimintoja valituille kohteille. Table-luokka tarjoaa menetelmiä checkbox-valinnan mahdollistamiseksi ja mukauttamiseksi.

Käyttämällä `setCheckboxSelection(boolean checkboxSelection)` -menetelmää, voidaan määrittää, että valintaruudut näytetään jokaisen rivin vieressä, jolloin käyttäjät voivat valita kohteita. Alla oleva ohjelma näyttää usean valinnan ja checkbox-valinnan olevan käytössä:

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

`Table`-komponentti tarjoaa ohjelmallisia valintamenetelmiä, jotka mahdollistavat valittujen kohteiden käsittelyn joko avainargumenttien tai kokonaiskohteiden perusteella.

### Valitse avaimen mukaan {#select-by-key}

`selectKey(Object... keys)` -menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen niiden avaimien avulla. Tälle metodille voidaan antaa yksi tai useampi avain, ja se päivittää valinnan vastaavasti.

### Valitse kohteet {#selecting-entry-items}

Lopuksi, `select(T... items)` -menetelmä mahdollistaa kohteiden ohjelmallisen valitsemisen antamalla yksittäisiä kohteita tälle metodille, jotta valinta voidaan päivittää vastaavasti.
