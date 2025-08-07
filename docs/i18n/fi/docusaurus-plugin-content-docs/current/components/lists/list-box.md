---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 33476ec9bd7a601aaec3f1e37e7c099f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

`ListBox`-komponentti on käyttöliittymäelementti, joka on suunniteltu näyttämään vieritettävä lista objekteista ja sallii käyttäjien valita listalta yhden tai useamman kohteen. Käyttäjät voivat myös käyttää nuolinäppäimiä vuorovaikutuksessa `ListBox`:n kanssa.

## Käyttötarkoitukset {#usages}

1. **Käyttäjäroolien määrittäminen**: Sovelluksissa, joissa on käyttäjäoikeuksien hallinta, pääkäyttäjät voivat käyttää `ListBox`:ia roolien ja oikeuksien määrittämiseen käyttäjille. Käyttäjät valitaan listalta, ja roolit tai oikeudet määritellään heidän valintansa perusteella. Tämä varmistaa tarkan ja hallitun pääsyn eri ominaisuuksiin ja tietoihin sovelluksessa.

2. **Projektitehtävien määrittäminen**: Projektinhallintaohjelmistoissa `ListBox`-komponentit ovat hyödyllisiä tehtävien määrittämisessä tiimin jäsenille. Käyttäjät voivat valita tehtäviä listalta ja määrittää ne eri tiimin jäsenille. Tämä yksinkertaistaa tehtävien jakamista ja varmistaa, että vastuudet on selkeästi määritelty tiimin sisällä.

3. **Monikategorinen suodatus**: Hakusovelluksessa käyttäjät tarvitsevat usein hakutulosten suodattamista useiden kriteerien perusteella. `ListBox` voi näyttää erilaisia suodatusvaihtoehtoja, kuten
>- Tuoteominaisuudet
>- Hintaluokat
>- Brändit.

  Käyttäjät voivat valita kohteita jokaiselta suodatuskategoriasta, mikä mahdollistaa hakutulosten tarkentamisen ja juuri sen löytäminen, mitä he etsivät.

4. **Sisällön luokittelu**: Sisällönhallintajärjestelmissä `ListBox`-komponentit auttavat artikkeleiden, kuvien tai tiedostojen luokittelussa. Käyttäjät voivat valita yhden tai useamman kategorian liittääkseen sen sisältöönsä, mikä helpottaa sisällön järjestämistä ja hakemista järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Oletuksena listalaatikko on määritetty sallimaan valinta vain yhdestä kohteesta kerrallaan. `ListBox` toteuttaa kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> -rajapinnan, joka voidaan määrittää sisäänrakennetulla menetelmällä, joka sallii käyttäjien valita useita kohteita ***käyttämällä `Shift`-näppäintä*** vierekkäisten valintojen tekemiseen ja ***`Control` (Windows) tai `Command` (Mac) näppäintä*** erillisten, useiden kohteiden valitsemiseen.

Käytä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> -toimintoa tämän ominaisuuden muuttamiseen. Tämä menetelmä hyväksyy joko `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteiden käyttäytyminen
Kosketuslaitteissa, kun monivalinta on käytössä, käyttäjät voivat valita useita kohteita ilman, että heidän tarvitsee pitää shift-näppäintä alhaalla.
:::

Lisäksi nuolinäppäimiä voidaan käyttää `ListBox`:n navigointiin, ja kirjaimen näppäimen kirjoittaminen, kun `ListBox` on aktiivisena, valitsee vaihtoehdon, joka alkaa kyseisellä kirjaimella, tai kiertää vaihtoehtojen läpi, jotka alkavat sillä kirjaimella, jos olemassa on useita vaihtoehtoja.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Tyylittely {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ChoiceBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Tietohierarkian priorisointi**: Käyttäessäsi `ListBox`:ia varmistaa, että kohteet on järjestetty loogiseen ja hierarkkiseen järjestykseen. Aseta tärkeimmät ja eniten käytetyt vaihtoehdot listan alkuun. Tämä helpottaa käyttäjien löytämistä tarvitsemansa ilman liiallista vieritystä.

2. **Listan pituuden rajoittaminen**: Vältä käyttäjien ylikuormittamista liian pitkällä `ListBox`:illa. Jos esitettävänä on suuri määrä kohteita, harkitse sivutusta, hakua tai suodatusvaihtoehtoja auttaaksesi käyttäjiä löytämään kohteita nopeasti. Voit myös ryhmitellä kohteet kategorioihin vähentääksesi listan pituutta.

3. **Selkeät ja kuvaavat tunnisteet**: Tarjoa selkeitä ja kuvaavia tunnisteita jokaiselle `ListBox`:in kohteelle. Käyttäjien tulisi pystyä ymmärtämään jokaisen vaihtoehdon tarkoitus ilman epäselvyyttä. Käytä tiiviitä ja merkityksellisiä kohdetunnisteita.

4. **Monivalintapalautteet**: Jos `ListBox`:isi sallii useita valintoja, tarjoa visuaalista tai tekstipohjaista palautetta, joka osoittaa, että listalta voidaan valita useita kohteita.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`:lle, erityisesti jos yksi vaihtoehto on yleisemmin käytetty kuin muut. Tämä voi yksinkertaistaa käyttäjäkokemusta ennakkovalitsemalla todennäköisimmin valitun vaihtoehdon.
