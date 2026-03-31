---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox`-komponentti näyttää vieritettävän luettelon kohteista, joka pysyy näkyvissä ilman, että avattavaa valikkoa tarvitsee avata. Se tukee sekä yksittäistä että useita valintoja, ja se toimii hyvin, kun käyttäjät tarvitsevat kaikki saatavilla olevat vaihtoehdot näkyville kerralla.

<!-- INTRO_END -->

## Käytöt {#usages}

<ParentLink parent="List" />

1. **Käyttäjäroolien määrittäminen**: Sovelluksissa, joissa on käyttäjäoikeuden hallinta, järjestelmänvalvojat voivat käyttää `ListBox`-komponenttia roolien ja oikeuksien määrittämiseen käyttäjille. Käyttäjät valitaan luettelosta, ja roolit tai oikeudet määritetään heidän valintansa perusteella. Tämä varmistaa tarkan ja hallitun pääsyn eri ominaisuuksiin ja tietoihin sovelluksessa.

2. **Projektitehtävien määrittäminen**: Projektinhallintaohjelmistoissa `ListBox`-komponentit ovat hyödyllisiä tehtävien määrittämisessä tiimin jäsenille. Käyttäjät voivat valita tehtäviä luettelosta ja määrittää ne eri tiimin jäsenille. Tämä yksinkertaistaa tehtävien jakamista ja varmistaa, että vastuut on määritelty selkeästi tiimissä.

3. **Monikategorisen suodattamisen käyttö**: Hakusovelluksessa käyttäjät tarvitsevat usein suodattaa hakutuloksia useiden kriteerien perusteella. `ListBox` voi näyttää erilaisia suodatinvaihtoehtoja, kuten 
>- Tuotteen ominaisuudet
>- Hintaluokat
>- Merkit. 

  Käyttäjät voivat valita kohteita kustakin suodatinkategoriasta, mikä mahdollistaa hakutulosten tarkentamisen ja löytää juuri etsimänsä.

4. **Sisällön luokittelu**: Sisällönhallintajärjestelmissä `ListBox`-komponentit auttavat artikkeleiden, kuvien tai tiedostojen luokittelussa. Käyttäjät voivat valita yhden tai useamman kategorian yhdistettäviksi sisältönsä kanssa, mikä helpottaa sisällön järjestämistä ja etsimistä järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Oletusarvoisesti listalaatikko on konfiguroitu sallimaan yhden kohteen valinta kerrallaan. `ListBox` toteuttaa <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> -rajapinnan, joka voidaan konfiguroida sisäänrakennetulla menetelmällä, jonka avulla käyttäjät voivat valita useita kohteita ***käyttämällä `Shift`-näppäintä*** vierekkäisten valintojen tekemiseksi ja ***`Control` (Windows) tai `Command` (Mac) näppäintä*** erillisten, useiden kohteiden valitsemiseksi. 

Käytä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> -toimintoa tämän ominaisuuden muuttamiseksi. Tämä menetelmä hyväksyy joko `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteiden käyttäytyminen
Kosketuslaitteilla, kun monivalinta on käytössä, käyttäjät voivat valita useita kohteita ilman, että shift-näppäintä tarvitsee painaa.
:::

Lisäksi nuolinäppäimiä voidaan käyttää `ListBox`:n navigointiin, ja kirjaimen näppäimen kirjoittaminen `ListBox`:n ollessa aktiivisena valitsee vaihtoehdon, joka alkaa sillä kirjaimella, tai kiertää vaihtoehtojen joukossa, jotka alkavat sillä kirjaimella, jos useita vaihtoehtoja on olemassa.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Tyylittely {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Jotta `ChoiceBox`-komponentin käyttöön liittyisi optimaalinen käyttäjäkokemus, harkitse seuraavia parhaita käytäntöjä:

1. **Tietohierarkian priorisointi**: Käyttäessäsi `ListBox`-komponenttia varmista, että kohteet on järjestetty loogiseen ja hierarkkiseen järjestykseen. Aseta tärkeimmät ja yleisimmin käytetyt vaihtoehdot luettelon alkuun. Tämä helpottaa käyttäjien löytämään tarvitsemansa ilman liiallista vierittämistä.

2. **Luettelon pituuden rajoittaminen**: Vältä käyttäjien kuormittamista liian pitkällä `ListBox`:lla. Jos näytettävien kohteiden määrä on suuri, harkitse sivutusta, hakua tai suodatusvaihtoehtoja käyttäjien auttamiseksi kohteiden быстрее löytämisessä. Vaihtoehtoisesti voit jakaa kohteet kategorioihin luettelon pituuden vähentämiseksi.

3. **Selkeät ja kuvailevat tarrat**: Anna selkeitä ja kuvailevia tarroja jokaiselle kohteelle `ListBox`:ssa. Käyttäjien tulisi pystyä ymmärtämään kunkin vaihtoehdon tarkoitus ilman epäselvyyksiä. Käytä tiiviitä ja merkityksellisiä kohdetarroja.

4. **Monivalinnan palaute**: Jos `ListBox`-komponenttisi sallii useita valintoja, anna visuaalista tai tekstipohjaista palautetta, joka osoittaa, että luettelosta voidaan valita useita kohteita.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`:lle, erityisesti jos jokin vaihtoehto on käyttökelpoisempi kuin muut. Tämä voi virtaviivaistaa käyttäjäkokemusta esivalitsemalla todennäköisimmin valitun vaihtoehdon.
