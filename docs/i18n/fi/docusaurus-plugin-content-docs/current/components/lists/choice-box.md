---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: 1c1224ca662a0e268606dc1cb6a0e96a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox`-komponentti esittää avattavan luettelon, josta käyttäjät valitsevat yhden vaihtoehdon. Kun valinta on tehty, valittu arvo näytetään napissa. Se on hyvä vaihtoehto, kun käyttäjien tarvitsee valita ennalta määritelty joukko vaihtoehtoja, ja nuolinäppäimiä voidaan käyttää luettelon selaamiseen.

<!-- INTRO_END -->

## Käytöt {#usages}

<ParentLink parent="List" />

`ChoiceBox`-komponentteja käytetään eri tarkoituksiin, kuten kohteiden valintaan valikosta, vaihtoehtojen valintaan kategorioista tai ennalta määrättyjen joukkojen valitsemiseen. Ne tarjoavat järjestelmällisen ja visuaalisesti miellyttävän tavan käyttäjien tehdä valintoja, erityisesti kun vaihtoehtoja on useita. Yleisiä käyttötarkoituksia ovat:

1. **Käyttäjän vaihtoehtojen valinta**: `ChoiceBox`-komponentin ensisijainen tarkoitus on sallia käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, joissa käyttäjien tarvitsee tehdä valintoja, kuten:
    - Valinta kategorioista
    - Vaihtoehtojen valinta ennalta määritellyistä joukoista

2. **Lomakekentät**: Kun suunnitellaan lomakkeita, jotka vaativat käyttäjiltä tiettyjen vaihtoehtojen syöttämistä, `ChoiceBox` yksinkertaistaa valintaprosessia. Olipa kyseessä maan, osavaltion tai minkä tahansa muun vaiheen valinta ennalta määritellystä luettelosta, `ChoiceBox` virtaviivaistaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox`-komponenttia voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteereitä tai lajittelupreferenssejä luettelosta, mikä helpottaa tietojen järjestämistä ja navigointia.

4. **Asetukset ja konfiguraatio**: Kun sovelluksessasi on asetuksia tai konfigurointivaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjille säätää mieltymyksiään. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen mukauttamista heidän tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun ennalta määritetty määrä vaihtoehtoja on saatavilla, eikä mukautettujen vaihtoehtojen salliminen ole suotavaa. Jos haluat sallia käyttäjien syöttää mukautettuja arvoja, käytä sen sijaan [`ComboBox`](./combo-box.md).
:::

## Avattavan tyypin määrittäminen {#dropdown-type}

`<JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>`-menetelmän avulla voidaan määrittää arvo `type`-attribuutille `ChoiceBox`:ssa sekä vastaava arvo `data-dropdown-for`-attribuutille `ChoiceBox`:n avattavassa osassa. Tämä on hyödyllistä tyylittämisessä, sillä avattava osa otetaan pois sen nykyisestä paikasta DOM:ssa ja siirretään sivun kehon loppuun avattaessa.

Tämä irrottaminen luo tilanteen, jossa avattavan osan suora kohdistaminen CSS- tai shadow part -valitsimilla vanhemmasta komponentista on haastavaa, ellei käytetä avattavan tyypin attribuuttia.

Alla olevassa esimerkissä avattavan tyyppi on asetettu ja käytetty CSS-tiedostossa suurentamaan vaihtoehtoa, kun hiiri viedään sen päälle.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletuksena avattavan `ChoiceBox`:n rivimäärä kasvaa sisällön mukaan. Kuitenkin käyttämällä `<JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>`-menetelmää voit hallita, kuinka monta kohdetta näytetään.

:::tip
Mikäli käytetään lukua, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistuu käytöstä.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ChoiceBox`:n vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()`- ja `close()`-menetelmillä. Nämä menetelmät mahdollistavat vaihtoehtoluettelon näyttämisen tai sen piilottamisen tarpeen mukaan, tarjoten suurempaa joustavuutta `ChoiceBox`:n käytökselle.

Lisäksi webforJ:llä on tapahtumakuuntelijat, kun `ChoiceBox` suljetaan ja avataan, mikä antaa sinulle enemmän hallintaa erityisten toimintojen käynnistämiseksi.

```Java
//Siirrä fokus seuraavaan komponenttiin lomakkeessa
ChoiceBox yliopisto = new ChoiceBox("Yliopisto");
ChoiceBox pääaine = new ChoiceBox("Pääaine");
Button lähetä = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelo

yliopisto.onClose(e -> {
  pääaine.focus();
});

pääaine.onClose(e -> {
  lähetä.focus();
});
```

## Avattavat mitat {#opening-dimensions}

`ChoiceBox`-komponentilla on menetelmiä, jotka mahdollistavat avattavan osan mittasuhteiden manipuloimisen. Avattavan korkeuden ja minimileveyden asettaminen voidaan tehdä käyttämällä `<JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>` ja `<JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>`-menetelmiä.

:::tip
Siirtämällä `String`-arvo joko näihin menetelmiin voidaan soveltaa [muita kelvollisia CSS-yksiköitä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikseleitä, näkymämittoja tai muita kelvollisia sääntöjä. Siirtämällä `int` asetetaan arvo pikseleinä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Paikat tarjoavat joustavia vaihtoehtoja `ChoiceBox`:n kyvykkyyden parantamiseksi. Voit lisätä ikoneita, etikettejä, latauskierroksia, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvasuhteita ja muita hyödyllisiä komponentteja `ChoiceBox`:n sisään, jotta käyttäjille voidaan selventää tarkoitettua merkitystä.
`ChoiceBox`-komponentilla on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()`-menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `ChoiceBox`:ssa.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `ChoiceBox`-komponentin käytössä harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajalliset vaihtoehdot**: Pidä valintalistat tiiviinä, kun se on mahdollista, ja relevantteina käyttäjän tehtävälle. `ChoiceBox` on ihanteellinen selkeän vaihtoehtojen luettelon esittämiseen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että näytettävät etiketit jokaiselle vaihtoehdolle ovat käyttäjäystävällisiä ja itseohjaavia. Varmista, että käyttäjät ymmärtävät helposti jokaisen valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` näytetään ensimmäisen kerran. Tämä varmistaa, että esivalittu vaihtoehto vähentää tarvittavien vuorovaikutusten määrää valinnan tekemiseksi.

4. **ChoiceBox vs. muut luettelo-komponentit**: `ChoiceBox` on paras vaihtoehto, jos haluat rajoittaa käyttäjän syötettä yhteen valintaan ennalta määrättyjen vaihtoehtojen luettelosta. Toinen luettelokomponentti saattaa olla parempi, jos tarvitset seuraavia käyttäytymiä:
    - Monivalinta ja kaikkien kohteiden näyttäminen kerralla: [`ListBox`](./list-box.md)
    - Sallitut mukautetut syötteet: [`ComboBox`](./combo-box.md)
