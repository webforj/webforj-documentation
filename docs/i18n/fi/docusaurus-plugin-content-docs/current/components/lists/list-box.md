---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 48b12429da76fbbe3a7961a0eac4efa9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox`-komponentti näyttää vieritettävän luettelon kohteista, joka pysyy näkyvissä ilman, että dropdownia tarvitsee avata. Se tukee sekä yksittäistä että useaa valintaa, ja se toimii hyvin, kun käyttäjät tarvitsevat nähdä kaikki käytettävissä olevat vaihtoehdot kerralla.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="List" />

1. **Käyttäjäroolien määrittäminen**: Sovelluksissa, joissa on käyttäjäoikeuksien hallinta, ylläpitäjät voivat käyttää `ListBox`-komponenttia määrittääkseen rooleja ja oikeuksia käyttäjille. Käyttäjät valitaan luettelosta, ja roolit tai oikeudet määritetään valinnan perusteella. Tämä varmistaa tarkkuuden ja hallitun pääsyn erilaisiin ominaisuuksiin ja tietoihin sovelluksessa.

2. **Projektitehtävien määrittäminen**: Projektinhallintaohjelmistoissa `ListBox`-komponentit ovat hyödyllisiä tehtävien jakamisessa tiimin jäsenille. Käyttäjät voivat valita tehtäviä luettelosta ja määrittää ne eri tiimin jäsenille. Tämä yksinkertaistaa tehtävien jakamista ja varmistaa, että vastuut on selkeästi määritelty tiimissä.

3. **Monikategorinen suodatus**: Hakusovelluksessa käyttäjät tarvitsevat usein suodattaa hakutuloksia useiden kriteerien perusteella. `ListBox` voi näyttää erilaisia suodatusvaihtoehtoja, kuten 
>- Tuoteominaisuudet
>- Hintaluokat
>- Brändit. 

  Käyttäjät voivat valita kohteita jokaisesta suodatuskategoriasta, mikä auttaa heitä tarkentamaan hakutuloksia ja löytämään tarkalleen mitä he etsivät.

4. **Sisällön luokittelu**: Sisällönhallintajärjestelmissä `ListBox`-komponentit auttavat artikkeleiden, kuvien tai tiedostojen luokittelussa. Käyttäjät voivat valita yhden tai useamman kategorian yhdistettäväksi sisältöönsä, mikä helpottaa sisällön järjestämistä ja etsimistä järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Oletusarvoisesti valintaruutu on konfiguroitu sallimaan vain yhden kohteen valinta kerrallaan. Kuitenkin `ListBox` toteuttaa <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> -rajapinnan, joka voidaan määrittää sisäänrakennetun menetelmän avulla, joka sallii käyttäjien valita useita kohteita ***käyttäen `Shift`-näppäintä*** vierekkäisten valintojen valitsemiseksi ja ***`Control` (Windows) tai `Command` (Mac) näppäintä*** erillisten, moninkertaisten kohteiden valitsemiseksi.

Käytä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> -toimintoa tämän ominaisuuden muuttamiseksi. Tämä menetelmä hyväksyy joko `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteen käyttäytyminen
Kosketuslaitteilla, kun monivalinta on käytössä, käyttäjät voivat valita useita kohteita ilman, että shift-näppäintä tarvitaan.
:::

Lisäksi nuolinäppäimiä voidaan käyttää `ListBox`-ruudussa navigoimiseen, ja kirjaimen kirjoittaminen, kun `ListBox` on aktiivinen, valitsee vaihtoehdon, joka alkaa sillä kirjaimella, tai kiertää vaihtoehtoja, jotka alkavat sillä kirjaimella, jos useita vaihtoehtoja on saatavilla.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Tyylit {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ChoiceBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Tietohierarkian priorisoiminen**: Kun käytät `ListBox`-komponenttia, varmista, että kohteet on järjestetty loogiseen ja hierarkkiseen järjestykseen. Aseta tärkeimmät ja eniten käytetyt vaihtoehdot luettelon yläosaan. Tämä helpottaa käyttäjien löytämistä tarvitsemansa ilman liiallista vierittämistä.

2. **Luettelon pituuden rajoittaminen**: Vältä käyttäjien ylivoimaamista liian pitkällä `ListBox`:lla. Jos näytettävänä on suuri määrä kohteita, harkitse sivutusta, hakua tai suodatusvaihtoehtoja auttaaksesi käyttäjiä löytämään kohteita nopeasti. Voit myös ryhmitellä kohteita kategorioihin vähentääksesi luettelon pituutta.

3. **Selkeät ja kuvailevat tunnisteet**: Tarjoa selkeitä ja kuvailevia tunnisteita jokaiselle `ListBox`-komponentin kohteelle. Käyttäjien tulisi ymmärtää kunkin vaihtoehdon tarkoitus ilman epäselvyyksiä. Käytä tiiviitä ja merkityksellisiä kohteen tunnisteita.

4. **Monivalintapalautteen tarjoaminen**: Jos `ListBox`-komponentti sallii useita valintoja, tarjoa visuaalista tai tekstillistä palautetta, joka osoittaa, että luettelosta voidaan valita useita kohteita.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`:lle, erityisesti jos yksi vaihtoehto on muita yleisemmin käytetty. Tämä voi sujuvoittaa käyttäjäkokemusta valitsemalla ennakkoon todennäköisimmän vaihtoehdon.
