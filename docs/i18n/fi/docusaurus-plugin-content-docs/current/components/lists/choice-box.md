---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox`-komponentti esittää avattavan valikon, josta käyttäjät voivat valita yhden vaihtoehdon. Kun valinta on tehty, valittu arvo näytetään napissa. Se on hyvä vaihtoehto, kun käyttäjät tarvitsevat valita ennalta määrättyjen vaihtoehtojen joukosta, ja nuolinäppäimiä voidaan käyttää luettelossa navigoimiseen.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

<ParentLink parent="List" />

`ChoiceBox`-komponentteja käytetään erilaisiin tarkoituksiin, kuten valikoimien valintaan, kategorioiden valintaan tai vaihtoehtojen valintaan ennalta määritellyistä joukkoista. Ne tarjoavat järjestelmällisen ja visuaalisesti miellyttävän tavan, jolla käyttäjät voivat tehdä valintoja, erityisesti silloin, kun vaihtoehtoja on useita. Yleisiä käyttötarkoituksia ovat:

1. **Käyttäjän vaihtoehtojen valinta**: `ChoiceBox`-komponentin pääasiallinen tarkoitus on sallia käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, joissa käyttäjien on tehtävä valintoja, kuten:
    - Valinta kategorioista
    - Vaihtoehtojen valinta ennalta määritellyistä joukoista

2. **Lomaketiedot**: Kun suunnitellaan lomakkeita, jotka vaativat käyttäjiltä tiettyjen vaihtoehtojen syöttämistä, `ChoiceBox` helpottaa valintaprosessia. Olipa kyseessä maan, osavaltion tai muun vaihtoehdon valinta ennalta määritellystä luettelosta, `ChoiceBox` yksinkertaistaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox`-komponenttia voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteereitä tai lajitteluvalintoja luettelosta, mikä helpottaa tietojen organisointia ja navigointia.

4. **Määritykset ja asetukset**: Kun sovelluksessasi on asetuksia tai määritysvaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan, jolla käyttäjät voivat säätää asetuksia. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen mukauttamista heidän tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun tarjolla on ennalta määrätty määrä vaihtoehtoja, eikä käyttäjien tulisi sallia tai sisällyttää mukautettuja vaihtoehtoja. Jos haluat sallia käyttäjien syöttää mukautettuja arvoja, käytä sen sijaan [`ComboBox`](./combo-box.md) -komponenttia.
:::

## Pudotustyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -menetelmää määritetään arvo `type`-attribuutille `ChoiceBox`-komponentissa ja vastaava arvo `data-dropdown-for` -attribuutille `ChoiceBox`-komponentin pudotusvalikossa. Tämä on hyödyllistä tyylittelyssä, koska pudotusvalikko siirretään sen nykyisestä sijainnistaan DOM:ssa kohdasta sivun loppuosa, kun se avataan.

<!-- ![esimerkkityyppi](/img/components/_images/choicebox/type.png)
![esimerkkityyppi](/img/components/_images/choicebox/type_zoomed.png) -->

Tämä irrotus luo tilanteen, jossa pudotusvalikkoa on vaikea kohdistaa suoraan CSS- tai varjoparttivalitsimilla vanhemmasta komponentista, ellei käytä pudotustyypin attribuuttia.

Alla olevassa demonäytetään, että pudotustyyppi on asetettu ja käytetään CSS-tiedostossa suurentamaan vaihtoehtoa, kun sitä korostaa.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletuksena avattavassa `ChoiceBox`-komponentissa näytettävien rivien määrä kasvaa sisällön mukaan. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -menetelmää voit hallita, kuinka monta kohdetta näytetään. 

:::tip
Jos käytät numeroa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistetaan käytöstä.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Avauksen ja sulkemisen hallinta {#opening-and-closing}

`ChoiceBox`-komponentin vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti käyttäen `open()` ja `close()` -menetelmiä. Nämä menetelmät mahdollistavat avattavien vaihtoehtojen näyttämisen tai piilottamisen tarpeen mukaan, mikä antaa suuremman joustavuuden `ChoiceBox` -komponentin käytön hallintaan.

Lisäksi webforJ tarjoaa tapahtumakuuntelijoita, kun `ChoiceBox` suljetaan ja kun se avataan, jolloin voit hallita erityisten toimintojen laukaisevia toimintoja.

```Java
//Tuo tai avaa seuraava komponentti lomakkeessa
ChoiceBox university = new ChoiceBox("Yliopisto");
ChoiceBox major = new ChoiceBox("Pääaine");
Button submit = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelot

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Avoimen koko {#opening-dimensions}

`ChoiceBox`-komponentilla on menetelmiä, jotka mahdollistavat pudotusvalikon ulottuvuuksien manipuloimisen. Pudotusvalikon **maksimi korkeus** ja **minimi leveys** voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -menetelmiä.

:::tip
Siirtäminen `String`-arvoksi kummallekin menetelmälle antaa mahdollisuuden soveltaa [mikä tahansa voimassa oleva CSS-yksikkö](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikseleitä, näkymämittoja tai muita voimassa olevia sääntöjä. Jos siirrät `int`-arvoa, se asettaa siirretyn arvon pikseleinä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ChoiceBox`-komponentin kyvykkyyden parantamiseksi. Voit liittää kuvakkeita, etikettejä, latauspörriäisiä, tyhjennys/ palautusmahdollisuuksia, avatar/profiilikuvaa ja muita hyödyllisiä komponentteja `ChoiceBox`-komponentin sisään, jotta käyttäjille selkeytyisi tarkoitus.
`ChoiceBox`-komponentissa on kaksi slottia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä erilaisten komponenttien lisäämiseksi ennen tai jälkeen näytettävän vaihtoehdon `ChoiceBox`-komponentissa.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Jotta varmistettaisiin optimaalinen käyttäjäkokemus `ChoiceBox`-komponentin käytön yhteydessä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajoitetut vaihtoehdot**: Pidä valintojen luettelo mahdollisimman lyhyenä ja käyttäjän tehtävään liittyvänä. `ChoiceBox` on ihanteellinen selkeiden vaihtoehtojen esittämiseen.

2. **Käyttäjäystävälliset etikettit**: Varmista, että jokaisen vaihtoehdon näyttämät etikettit ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät ymmärtävät helposti jokaisen valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` esitetään ensimmäisen kerran. Tämä varmistaa ennakkoon valitun vaihtoehdon, mikä vähentää tarvittavien vuorovaikutusten määrää valinnan tekemiseksi.

4. **ChoiceBox vs. muut luettelokomponentit**: `ChoiceBox` on paras vaihtoehto, jos haluat rajoittaa käyttäjän syötteen yhteen valintaan ennalta määrättyjen vaihtoehtojen joukosta. Toinen luettelokomponentti voi olla parempi, jos tarvitset seuraavia toimintoja:
    - Useita valintoja ja kaikkien kohteiden näyttäminen kerralla: [`ListBox`](./list-box.md)
    - Sallia mukauttaminen: [`ComboBox`](./combo-box.md)
