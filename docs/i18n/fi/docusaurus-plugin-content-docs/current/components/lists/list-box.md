---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: ea83ed0b82b2f6f91d7fbe9dedebeef2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox`-komponentti näyttää vieritettävän luettelon kohteista, joka pysyy näkyvissä ilman, että avattavaa valikkoa tarvitsee käyttää. Se tukee sekä yksittäistä että monivalintaa ja toimii hyvin, kun käyttäjien tarvitsee nähdä kaikki saatavilla olevat vaihtoehdot kerralla.

<!-- INTRO_END -->

## Käyttötavat {#usages}

<ParentLink parent="List" />

1. **Käyttäjäroolien määrittäminen**: Sovelluksissa, joissa on käyttäjän pääsynhallinta, järjestelmänvalvojat voivat käyttää `ListBox`-komponenttia määrittääkseen rooleja ja oikeuksia käyttäjille. Käyttäjät valitaan luettelosta, ja roolit tai oikeudet määritetään niiden valinnan mukaan. Tämä varmistaa tarkan ja hallitun pääsyn eri ominaisuuksiin ja tietoihin sovelluksessa.

2. **Projektitehtävien määrittäminen**: Projektinhallintasoftassa `ListBox`-komponentit ovat hyödyllisiä tehtävien määrittämisessä tiimin jäsenille. Käyttäjät voivat valita tehtäviä luettelosta ja määrittää ne eri tiimin jäsenille. Tämä yksinkertaistaa tehtävien delegointia ja varmistaa, että vastuut ovat selvästi määriteltyjä tiimissä.

3. **Monikategorinen suodatus**: Hakusovelluksessa käyttäjien on usein tarpeen suodattaa hakutuloksia useiden kriteerien perusteella. `ListBox` voi näyttää erilaisia suodatusvaihtoehtoja, kuten
>- Tuotteen ominaisuudet
>- Hintaluokat
>- Brändit.

  Käyttäjät voivat valita kohteita jokaisesta suodatuskategoriasta, mikä mahdollistaa hakutulosten tarkentamisen ja löytää tarkalleen sen, mitä he etsivät.

4. **Sisällön kategorisointi**: Sisällönhallintajärjestelmissä `ListBox`-komponentit auttavat artikkeleiden, kuvien tai tiedostojen kategorisoinnissa. Käyttäjät voivat valita yhden tai useamman kategorian liitettäväksi sisältöönsä, mikä helpottaa sisällön järjestämistä ja hakemista järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Oletusarvoisesti luettelo on konfiguroitu sallimaan vain yhden kohteen valinta kerrallaan. `ListBox`-komponentti toteuttaa <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> -rajapinnan, joka voidaan konfiguroida sisäänrakennetulla menetelmällä, joka sallii käyttäjien valita useita kohteita ***käyttämällä `Shift`-näppäintä*** peräkkäisten valintojen tekemiseen ja ***`Control` (Windows) tai `Command` (Mac) näppäintä*** erillisten, useiden kohteiden valintaan.

Käytä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> -funktiota tämän ominaisuuden muuttamiseksi. Tämä menetelmä hyväksyy joko `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteen käyttäytyminen
Kosketuslaitteilla, kun usean valinnan mahdollisuus on käytössä, käyttäjät voivat valita useita kohteita ilman, että shift-näppäintä tarvitsee pitää painettuna.
:::

Lisäksi nuolinäppäimiä voidaan käyttää `ListBox`-komponentin navigoimiseen, ja kirjaimen kirjoittaminen, kun `ListBox` on valittuna, valitsee vaihtoehdon, joka alkaa kyseisellä kirjaimella, tai kiertää vaihtoehtojen läpi, jotka alkavat kyseisellä kirjaimella, jos useita vaihtoehtoja on olemassa.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Tyylitys {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ChoiceBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Priorisoi tietohierarkia**: Kun käytät `ListBox`-komponenttia, varmista, että kohteet on järjestetty loogiseen ja hierarkkiseen järjestykseen. Aseta tärkeimmät ja yleisimmin käytetyt vaihtoehdot luettelon alkuun. Tämä helpottaa käyttäjien löytämistä ilman liiallista vieritystä.

2. **Rajoita luettelon pituus**: Vältä käyttäjien yliwälittämistä äärimmäisen pitkällä `ListBox`-komponentilla. Jos näytettäviä kohteita on paljon, harkitse sivutuksen, haun tai suodatusvaihtoehtojen toteuttamista, jotta käyttäjät voivat löytää kohteita nopeasti. Vaihtoehtoisesti voit ryhmitellä kohteita kategorioihin luettelon pituuden vähentämiseksi.

3. **Selkeät ja kuvaavat nimilaput**: Tarjoa selkeät ja kuvaavat nimilaput jokaiselle `ListBox`-komponentin kohteelle. Käyttäjien pitäisi pystyä ymmärtämään joka vaihtoehdon tarkoitus ilman epäselvyyksiä. Käytä ytimekkäitä ja merkityksellisiä nimilappuja.

4. **Monivalintapalautteet**: Jos `ListBox`-komponentti sallii useita valintoja, tarjoa visuaalista tai tekstipohjaista palautetta, joka osoittaa, että luettelosta voi valita useita kohteita.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`-komponentille, erityisesti jos yksi vaihtoehto on yleisemmin käytetty kuin muut. Tämä voi yksinkertaistaa käyttäjäkokemusta ennakkoon valitsemalla todennäköisimmin valitun vaihtoehdon.
