---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 6e04ceea1fadc5f159b8d4dd9645e014
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox`-komponentti esittää avattavan luettelon, josta käyttäjät valitsevat yhden vaihtoehdon. Kun valinta on tehty, valittu arvo näkyy painikkeessa. Se on hyvä valinta, kun käyttäjien on valittava kiinteä joukko ennalta määriteltyjä vaihtoehtoja, ja nuolinäppäimiä voidaan käyttää luettelon selaamiseen.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="List" />

`ChoiceBox`-komponentteja käytetään eri tarkoituksiin, kuten valintojen tekemiseen valikosta, kategorioista valitsemiseen tai vaihtoehtojen valitsemiseen ennalta määritellyistä aineistoista. Ne tarjoavat käyttäjille järjestäytyneen ja visuaalisesti miellyttävän tavan tehdä valintoja, erityisesti kun vaihtoehtoja on paljon. Yleisiä käyttötapoja ovat:

1. **Käyttäjän valintojen tekeminen**: `ChoiceBox`-komponentin ensisijainen tarkoitus on sallia käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, joissa käyttäjiltä vaaditaan valintoja, kuten:
    - Valitseminen kategorioista
    - Ennalta määriteltyjen vaihtoehtojen valitseminen

2. **Lomakekentät**: Kun suunnitellaan lomakkeita, jotka vaativat käyttäjiä syöttämään tiettyjä vaihtoehtoja, `ChoiceBox` yksinkertaistaa valintaprosessia. Olipa kyseessä maan, osavaltion tai minkä tahansa muun vaihtoehdon valitseminen ennalta määritellystä luettelosta, `ChoiceBox` sujuvoittaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox`-komponenttia voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteerit tai lajitteluvaihtoehdot luettelosta, mikä helpottaa tietojen järjestämistä ja selaamista.

4. **Konfigurointi ja asetukset**: Kun sovelluksesi sisältää asetuksia tai konfigurointivaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjien säätää mieltymyksiään. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen mukauttamista tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun käytettävissä on ennalta määritetty määrä vaihtoehtoja, eikä käytön aikana tulisi sallia tai sisällyttää mukautettuja vaihtoehtoja. Jos haluat sallia käyttäjien syöttävän mukautettuja arvoja, käytä [`ComboBox`](./combo-box.md) -komponenttia sen sijaan.
:::

## Avattavan tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>-metodia määritetään arvo `type`-attribuutille `ChoiceBox`-komponentissa, ja vastaava arvo `data-dropdown-for`-attribuutille `ChoiceBox`-komponentin avattavassa. Tämä on hyödyllistä tyylittelyn kannalta, sillä avattava siirretään pois nykyiseltä sijainniltaan DOM:ssa ja sijoitetaan sivun rungon loppuun avattaessa.

<!-- ![esimerkkityyppi](/img/components/_images/choicebox/type.png)
![esimerkkityyppi](/img/components/_images/choicebox/type_zoomed.png) -->

Tämä irrottaminen luo tilanteen, jossa avattavaa on vaikeaa kohdistaa suoraan CSS:n tai varjopartioiden valitsimien avulla vanhempi komponentti, ellei käytetä avattavan tyyppiattribuuttia.

Alla olevassa demossa avattavan tyyppi asetetaan ja sitä käytetään CSS-tiedostossa avattavan valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maksimi rivimäärä {#max-row-count}

Oletuksena `ChoiceBox`-komponentin avattavassa näkyvien rivien määrä kasvaa sisällön mukaan. Kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>-metodin avulla voidaan hallita, kuinka monta kohdetta näytetään.

:::tip
Numero, joka on pienempi tai yhtä suuri kuin 0, johtaa tämän ominaisuuden poistamiseen käytöstä.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ChoiceBox`-komponentin vaihtoehtojen näkyvyyttä voidaan ohjelmallisesti hallita `open()`- ja `close()`-metodien avulla. Nämä metodit sallivat valintaluettelon näyttämisen tai piilottamisen tarpeen mukaan, mikä tarjoaa suurempaa joustavuutta `ChoiceBox`-komponentin käytön hallintaan.

Lisäksi webforJ:llä on tapahtuman kuuntelijat, joita käytetään, kun `ChoiceBox` suljetaan tai avataan, jolloin voit paremmin hallita erityisten toimintojen laukaisemista.

```Java
// Keskity tai avaa seuraava komponentti lomakkeessa
ChoiceBox yliopisto = new ChoiceBox("Yliopisto");
ChoiceBox pääaine = new ChoiceBox("Pääaine");
Button lähetä = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelo

yliopisto.onClose( e ->{
  pääaine.focus();
});

pääaine.onClose( e ->{
  lähetä.focus();
});
```

## Avaamisen mitat {#opening-dimensions}

`ChoiceBox`-komponentilla on metodeja, jotka mahdollistavat avattavan mittojen muokkaamisen. Avattavan **maksimi korkeus** ja **minimi leveys** voidaan määrittää käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink>- ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>-metodeja.

:::tip
Mikäli näille metodeille annetaan `String`-arvo, voidaan käyttää [mikä tahansa voimassaoleva CSS-yksikkö](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikselit, näkymämitat tai muut voimassaolevat säännöt. Jos annetaan `int`, tämä asettaa arvon pikseleinä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ChoiceBox`-komponentin kykyjen parantamiseksi. Voit lisätä kuvakkeita, etikettejä, latauspyöröitä, tyhjennys/palautusmahdollisuuksia, avatar/profiilikuvaa ja muita hyödyllisiä komponentteja `ChoiceBox`-komponentin sisään, jotta käyttäjille voidaan selventää tarkoitettua merkitystä.
`ChoiceBox`-komponentilla on kaksi slottia: `prefix` ja `suffix`. Käytä `setPrefixComponent()`- ja `setSuffixComponent()`-metodeja sisällyttääksesi erilaisia komponentteja ennen ja jälkeen näytetyn vaihtoehdon `ChoiceBox`-komponentissa.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaaliset käyttäjäkokemukset `ChoiceBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajalliset vaihtoehdot**: Pidä vaihtoehtoluettelo tiiviinä, kun se on mahdollista, ja relevanttina käyttäjän tehtävän kannalta. `ChoiceBox` on ihanteellinen esittämään selkeä luettelo vaihtoehdoista.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon näytetyt etiketit ovat käyttäjäystävällisiä ja itse selittäviä. Varmista, että käyttäjät voivat helposti ymmärtää kunkin valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` esitetään ensimmäisen kerran. Tämä takaa ennakkovalinnan, vähentäen valintaan tarvittavien vuorovaikutusten määrää.

4. **ChoiceBox vs. muut luettelo- komponentit**: `ChoiceBox` on paras valinta, jos tarvitset käyttäjäsyötteen rajoittamista yhteen valintaan ennalta määrättyjen vaihtoehtojen luettelosta. Toinen luettelokomponentti saattaa olla parempi, jos tarvitset seuraavia käyttäytymisiä:
    - Useita valintoja ja kaikkien kohteiden näyttäminen kerralla: [`ListBox`](./list-box.md)
    - Sallia mukautettu syöttö: [`ComboBox`](./combo-box.md)
