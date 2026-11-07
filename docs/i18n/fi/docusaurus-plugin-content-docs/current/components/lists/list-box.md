---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

`ListBox`-komponentti näyttää vieritettävän luettelon kohteista, joka pysyy näkyvissä ilman avattavan valikon avaamista. Se tukee sekä yksittäistä että usean kohteen valintaa, ja sopii hyvin tilanteisiin, joissa käyttäjien täytyy nähdä kaikki käytettävissä olevat vaihtoehdot kerralla.

<!-- INTRO_END -->

## Käyttökohteet {#usages}

<ParentLink parent="List" />

1. **Käyttäjäroolien määrittäminen**: Käyttöoikeuksien hallintaa sisältävissä sovelluksissa järjestelmänvalvojat voivat käyttää `ListBox`-komponenttia roolien ja käyttöoikeuksien määrittämiseen käyttäjille. Käyttäjät valitaan luettelosta, ja roolit tai käyttöoikeudet määritetään heidän valintojensa perusteella. Näin sovelluksen eri ominaisuuksien ja tietojen käyttöoikeuksia voidaan hallita tarkasti ja valvotusti.

2. **Projektitehtävien jakaminen**: Projektinhallintaohjelmistoissa `ListBox`-komponentit ovat hyödyllisiä tehtävien jakamisessa tiimin jäsenille. Käyttäjät voivat valita tehtäviä luettelosta ja jakaa niitä eri tiimin jäsenille. Tämä helpottaa tehtävien jakamista ja varmistaa, että vastuut on määritelty selkeästi tiimin sisällä.

3. **Suodattaminen usean luokan perusteella**: Hakusovelluksissa käyttäjien täytyy usein suodattaa hakutuloksia useiden ehtojen perusteella. `ListBox` voi näyttää erilaisia suodatusvaihtoehtoja, kuten
>- Tuotteen ominaisuudet
>- Hintaluokat
>- Tuotemerkit.

  Käyttäjät voivat valita kohteita kustakin suodatusluokasta, jolloin he voivat rajata hakutuloksia ja löytää juuri etsimänsä.

4. **Sisällön luokittelu**: Sisällönhallintajärjestelmissä `ListBox`-komponentit auttavat luokittelemaan artikkeleita, kuvia tai tiedostoja. Käyttäjät voivat valita sisällölleen yhden tai useamman luokan, mikä helpottaa sisältökohteiden järjestämistä ja etsimistä järjestelmässä.

## Valintavaihtoehdot {#selection-options}

Luetteloruutu sallii oletusarvoisesti yhden kohteen valitsemisen kerrallaan. `ListBox` toteuttaa kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>-rajapinnan, jonka voi määrittää sisäänrakennetulla menetelmällä sallimaan useiden kohteiden valitsemisen. ***Yhtenäisten kohteiden valintaan käytetään <kbd>Shift</kbd>-näppäintä*** ja ***erillisten kohteiden valintaan <kbd>Ctrl</kbd>-näppäintä (Windows) tai <kbd>Cmd</kbd>-näppäintä (Mac)***.

Voit muuttaa tätä ominaisuutta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink>-funktiota. Tämä metodi hyväksyy arvon `SelectionMode.SINGLE` tai `SelectionMode.MULTIPLE`.

:::info Kosketuslaitteiden toiminta
Kun usean kohteen valinta on käytössä, kosketuslaitteiden käyttäjät voivat valita useita kohteita pitämättä Shift-näppäintä painettuna.
:::

Lisäksi `ListBox`-komponentissa voi siirtyä nuolinäppäimillä. Kun `ListBox` on kohdistettuna, kirjaimen painaminen valitsee kyseisellä kirjaimella alkavan vaihtoehdon. Jos useita vaihtoehtoja alkaa samalla kirjaimella, näppäimen painaminen kierrättää vaihtoehtoja.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Tyylit {#styling}

<TableBuilder name="ListBox" />

## Parhaat käytännöt {#best-practices}

Jotta käyttökokemus olisi `ChoiceBox`-komponenttia käytettäessä mahdollisimman hyvä, huomioi seuraavat parhaat käytännöt:

1. **Aseta tiedot tärkeysjärjestykseen**: Kun käytät `ListBox`-komponenttia, varmista, että kohteet on järjestetty loogisesti ja hierarkkisesti. Sijoita tärkeimmät ja yleisimmin käytetyt vaihtoehdot luettelon alkuun. Näin käyttäjät löytävät tarvitsemansa helpommin ilman runsasta vierittämistä.

2. **Rajoita luettelon pituutta**: Älä kuormita käyttäjiä liian pitkällä `ListBox`-luettelolla. Jos näytettäviä kohteita on paljon, harkitse sivutuksen, haun tai suodatusvaihtoehtojen lisäämistä, jotta käyttäjät löytävät kohteet nopeasti. Voit myös lyhentää luetteloa ryhmittelemällä kohteet luokkiin.

3. **Selkeät ja kuvaavat tunnisteet**: Anna jokaiselle `ListBox`-kohteelle selkeä ja kuvaava tunniste. Käyttäjien pitäisi ymmärtää kunkin vaihtoehdon tarkoitus yksiselitteisesti. Käytä ytimekkäitä ja kuvaavia tunnisteita.

4. **Usean valinnan palaute**: Jos `ListBox` sallii useiden kohteiden valitsemisen, anna visuaalista tai tekstimuotoista palautetta, joka ilmaisee, että luettelosta voi valita useita kohteita.

5. **Oletusvalinta**: Harkitse oletusvalinnan asettamista `ListBox`-komponentille etenkin silloin, jos jokin vaihtoehto on muita yleisemmin käytetty. Tämä voi sujuvoittaa käyttökokemusta valitsemalla etukäteen todennäköisimmän vaihtoehdon.
