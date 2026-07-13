---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox`-komponentti esittää avattavan valikon, josta käyttäjät voivat valita yhden vaihtoehdon. Kun valinta on tehty, valittu arvo näkyy napissa. Se on hyvä valinta, kun käyttäjien tarvitsee valita kiinteä joukko ennalta määriteltyjä vaihtoehtoja, ja nuolinäppäimiä voidaan käyttää luettelossa navigointiin.

<!-- INTRO_END -->

## Käytöt {#usages}

<ParentLink parent="List" />

`ChoiceBox`-komponentteja käytetään erilaisiin tarkoituksiin, kuten kohteiden valitsemiseen valikosta, luokkien valitsemiseen tai vaihtoehtojen valitsemiseen ennalta määritellyistä joukosta. Ne tarjoavat organisoidun ja visuaalisesti miellyttävän tavan käyttäjille tehdä valintoja, erityisesti kun vaihtoehtoja on useita. Yleisiä käyttötapoja ovat:

1. **Käyttäjän valintojen tekeminen**: `ChoiceBox`:n ensisijainen tarkoitus on sallia käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, jotka vaativat käyttäjiä tekemään valintoja, kuten:
    - Valitseminen luokkien luettelosta
    - Vaihtoehtojen valitseminen ennalta määritellyistä joukoista

2. **Lomakeinputit**: Kun suunnitellaan lomakkeita, jotka vaativat käyttäjiltä tiettyjen vaihtoehtojen syöttämistä, `ChoiceBox` yksinkertaistaa valintaprosessia. Olipa kyseessä maan, osavaltion tai minkä tahansa muun vaihtoehdon valitseminen ennalta määritellystä luettelosta, `ChoiceBox` virtaviivaistaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox`-komponenttia voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteerejä tai lajitteluvaihtoehtoja luettelosta, mikä helpottaa tietojen järjestelyä ja navigointia.

4. **Määrittäminen ja asetukset**: Kun sovelluksessasi on asetuksia tai konfigurointivaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjille säätää mieltymyksiä. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen muokkaamista heidän tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun ennalta määrätty määrä vaihtoehtoja on saatavilla, eikä mukautettujen vaihtoehtojen sallimista pitäisi sisällyttää. Jos haluat sallia käyttäjien syöttävän mukautettuja arvoja, käytä sen sijaan [`ComboBox`](./combo-box.md).
:::

## Avattavan tyypit {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-metodia voit määrittää arvon `type`-attribuutille `ChoiceBox`:ssa ja vastaavan arvon `data-dropdown-for`-attribuutille `ChoiceBox`:n avattavassa valikossa. Tämä on hyödyllistä tyylittelyssä, koska avattava valikko viedään nykyisestä sijainnistaan DOM:issa ja siirretään sivun rungon loppuun avattaessa.

<!-- ![esimerkki tyyppi](/img/components/_images/choicebox/type.png)
![esimerkki tyyppi](/img/components/_images/choicebox/type_zoomed.png) -->

Tämä irrottaminen aiheuttaa tilanteen, jossa avattavaa valikkoa on vaikea kohdistaa suoraan CSS- tai varjoselainvalitsimilla vanhempikomponentista, ellei käytä avattavan tyypin attribuuttia.

Alla olevassa demonstraatiossa avattavan tyyppi on asetettu ja sitä käytetään CSS-tiedostossa suurentamaan vaihtoehtoa hiiren osoittimen ollessa sen päällä.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletuksena avattavassa valikossa `ChoiceBox`:n rivimäärä näytetään sisällön mukaisena. Kuitenkin käyttäen <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-metodia voit hallita, kuinka monta kohdetta näytetään.

:::tip
Käyttämällä numeroa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistuu käytöstä.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Avaa ja sulje {#opening-and-closing}

`ChoiceBox`:n vaihtoehtojen näkyvyyttä voidaan ohjata ohjelmallisesti `open()`- ja `close()`-metodien avulla. Nämä metodit mahdollistavat vaihtoehtojen näyttämisen tai piilottamisen tarpeen mukaan, mikä tarjoaa suurempaa joustavuutta `ChoiceBox`:n käyttäytymisen hallinnassa.

Lisäksi webforJ:llä on tapahtumakuuntelijat, jotka reagoivat, kun `ChoiceBox` on suljettu ja kun se on avattu, antaen sinulle enemmän hallintaa erityisten toimintojen käynnistämiseen.

```Java
//Kohdista tai avaa seuraava komponentti lomakkeessa
ChoiceBox yliopisto = new ChoiceBox("Yliopisto");
ChoiceBox pääaine = new ChoiceBox("Pääaine");
Button lähetä = new Button("Lähetä");

//... Lisää luetteloita yliopistoista ja pääaineista

yliopisto.onClose( e ->{
  pääaine.focus();
});

pääaine.onClose( e ->{
  lähetä.focus();
});
```

## Avaa mitat {#opening-dimensions}

`ChoiceBox`-komponentilla on metodeja, jotka mahdollistavat avattavan valikon mittojen manipuloinnin. **Maksimikehys** ja **minimileveys** avattavalle valikolle voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-metodeja.

:::tip
Mikäli kumpaankin näistä metodeista annetaan `String`-arvo, se mahdollistaa [minkä tahansa voimassa olevan CSS-yksikön](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) soveltamisen, kuten pikseleitä, näkymän mittoja tai muita voimassa olevia sääntöjä. Jos annetaan `int`, se asettaa arvojen pikselimäärässä.
:::

## Prefiksi ja suffiksi {#prefix-and-suffix}

Paikat tarjoavat joustavia vaihtoehtoja `ChoiceBox`:n kyvykkyyden parantamiseen. Voit lisätä ikoneita, etikettejä, latauspyöröjä, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuva-ja muita hyödyllisiä komponentteja `ChoiceBox`:n sisälle, mikä selkeyttää tarkoitettua merkitystä käyttäjille.
`ChoiceBox`:ssä on kaksi paikkaa: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()`-metodeja insertätäksesi erilaisia komponentteja ennen ja jälkeen näytetyn vaihtoehdon `ChoiceBox`:ssa.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `ChoiceBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajoitetut vaihtoehdot**: Pidä valintaluettelo tiiviinä, kun se on mahdollista, ja käyttäjän tehtävään liittyvänä. `ChoiceBox` on ihanteellinen selkeäluetteloisten vaihtoehtojen esittelemiseen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon esitettävät etiketit ovat käyttäjäystävällisiä ja itse selittäviä. Varmista, että käyttäjät voivat helposti ymmärtää kunkin valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` näytetään ensimmäisen kerran. Tämä varmistaa ennalta valitun vaihtoehdon, vähentäen tarvittavien vuorovaikutusten määrää valinnan tekemiseksi.

4. **ChoiceBox vs. Muut luettelo-komponentit**: `ChoiceBox` on paras valinta, jos tarvitset käyttäjän syötteen rajoittamista yhteen valintaan ennalta määrätyistä vaihtoehdoista. Toinen luettelo-komponentti saattaa olla parempi, jos tarvitset seuraavia toimintatapoja:
    - Useita valintoja ja kaikkien kohteiden esittäminen kerralla: [`ListBox`](./list-box.md)
    - Mahdollistaa mukautetun syötteen: [`ComboBox`](./combo-box.md)
