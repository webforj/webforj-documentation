---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="Lista" />

`ListBox` komponentti on käyttöliittymäelementti, joka on suunniteltu näyttämään vieritettävä lista objekteista ja sallii käyttäjien valita yksittäisiä tai useita kohteita listalta. Käyttäjät voivat myös käyttää `ListBox`ia nuolinäppäimillä.

## Käytännöt {#usages}

1. **Käyttäjän roolien määrittäminen**: Sovelluksissa, joissa on käyttäjäoikeus hallinta, järjestelmänvalvojat voivat käyttää `ListBox`ia roolien ja oikeuksien määrittämiseen käyttäjille. Käyttäjiä valitaan listalta, ja roolit tai oikeudet määritellään valinnan perusteella. Tämä varmistaa tarkan ja hallitun pääsyn eri ominaisuuksiin ja tietoihin sovelluksessa.

2. **Projektitehtävien määrittäminen**: Projektinhallintaohjelmistoissa `ListBox` komponentit ovat hyödyllisiä tehtävien määrittämisessä tiimin jäsenille. Käyttäjät voivat valita tehtäviä listalta ja määrittää ne eri tiimin jäsenille. Tämä yksinkertaistaa tehtävien jakamista ja varmistaa, että vastuut ovat selkeästi määriteltyjä tiimissä.

3. **Monikategorinen suodatus**: Hakusovelluksessa käyttäjät tarvitsevat usein hakutulosten suodattamista useiden kriteerien perusteella. `ListBox` voi näyttää erilaisia suodatinvaihtoehtoja, kuten
>- Tuotteen ominaisuudet
>- Hintaluokat
>- Merkit.

 Käyttäjät voivat valita kohteita jokaisesta suodatinluokasta, jolloin he voivat tarkentaa hakutuloksia ja löytää täsmälleen mitä he etsivät.

4. **Sisällön luokittelu**: Sisällönhallintajärjestelmissä `ListBox` komponentit auttavat artikkeleiden, kuvien tai tiedostojen luokittelussa. Käyttäjät voivat valita yhden tai useamman kategorian yhdistääkseen ne sisältönsä kanssa, mikä helpottaa sisältöelementtien järjestämistä ja hakemista järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Oletusarvoisesti lista-aitta on määritetty sallimaan yhden kohteen valinta kerrallaan. `ListBox` toteuttaa kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink> -rajapinnan, joka voidaan määrittää sisäänrakennetun menetelmän avulla, joka sallii käyttäjien valita useita kohteita ***käyttämällä `Shift`-näppäintä*** peräkkäisten valintojen valitsemiseksi ja ***`Control` (Windows) tai `Command` (Mac) näppäintä*** erillisten, useiden kohteiden valitsemiseksi.

Käytä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> -toimintoa muuttaaksesi tätä ominaisuutta. Tämä menetelmä hyväksyy joko `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteen käyttäytyminen
Kosketuslaitteilla, kun monivalinta on käytössä, käyttäjät voivat valita useita kohteita ilman, että heidän tarvitsee pitää shift-näppäintä alhaalla.
:::

Lisäksi nuolinäppäimiä voidaan käyttää `ListBox`-elementissä navigoimiseen, ja kirjainnäppäimen kirjoittaminen, kun `ListBox` on aktiivinen, valitsee vaihtoehdon, joka alkaa sillä kirjaimella, tai kiertää vaihtoehtoja, jotka alkavat sillä kirjaimella, jos useita vaihtoehtoja on olemassa.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Tyylittely {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Jotta `ChoiceBox` komponentin käyttäjäkokemus olisi optimaalinen, harkitse seuraavia parhaita käytäntöjä:

1. **Tiedon hierarkian priorisointi**: `ListBox`ia käytettäessä varmista, että kohteet on järjestetty loogiseen ja hierarkkiseen järjestykseen. Aseta tärkeimmät ja yleisimmin käytetyt vaihtoehdot listan yläosaan. Tämä helpottaa käyttäjien löytää tarvitsemaansa ilman liiallista vieritystä.

2. **Listan pituuden rajoittaminen**: Vältä käyttäjien yliherkistämistä liiallisesti pitkällä `ListBox`:lla. Jos näyttämistä varten on suuri määrä kohteita, harkitse sivutusta, hakua tai suodatusvaihtoehtoja auttaaksesi käyttäjiä löytämään kohteita nopeasti. Alternatiivisesti voit ryhmitellä kohteita kategorioihin listan pituuden vähentämiseksi.

3. **Selkeät ja kuvaavat nimilaput**: Anna selkeät ja kuvaavat nimilaput jokaiselle `ListBox`:n kohteelle. Käyttäjien tulisi ymmärtää jokaisen vaihtoehdon tarkoitus ilman epäselvyyksiä. Käytä tiivistä ja merkityksellistä kohdenimien nimeämistä.

4. **Monivalinnan palaute**: Jos `ListBox`:si sallii monivalintoja, tarjoa visuaalista tai tekstimuotoista palautetta, joka osoittaa, että useita kohteita voidaan valita listalta.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`:lle, erityisesti jos jokin vaihtoehto on yleisemmin käytetty kuin muut. Tämä voi sujuvoittaa käyttäjäkokemusta esivalitsemalla todennäköisimman valinnan.
