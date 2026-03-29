---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 1da4824585c11423d72c2b75b451a6db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

`ChoiceBox` -komponentti esittelee alasvetoluettelon, josta käyttäjät valitsevat yhden vaihtoehdon. Kun valinta on tehty, valittu arvo näytetään painikkeessa. Tämä on hyvä vaihtoehto, kun käyttäjien on valittava kiinteä joukko ennalta määriteltyjä vaihtoehtoja, ja nuolinäppäimiä voidaan käyttää luettelon navigointiin.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

<ParentLink parent="List" />

`ChoiceBox` -komponentteja käytetään erilaisiin tarkoituksiin, kuten valintojen tekemiseen valikosta, luokkien valitsemiseen tai ennalta määritettyjen joukkojen vaihtoehtojen valitsemiseen. Ne tarjoavat järjestelmällisen ja visuaalisesti miellyttävän tavan käyttäjien tehdä valintoja, erityisesti silloin, kun vaihtoehtoja on useita. Yleisiä käyttötarkoituksia ovat:

1. **Käyttäjän valintojen tekeminen**: `ChoiceBox` -komponentin pääasiallinen tarkoitus on antaa käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, jotka vaativat käyttäjiltä valintoja, kuten:
    - Valitseminen luokkien luettelosta
    - Valitsemalla vaihtoehtoja ennalta määritetyistä joukoista

2. **Lomakekentät**: Kun suunnitellaan lomakkeita, jotka vaativat käyttäjiltä tiettyjen vaihtoehtojen syöttämistä, `ChoiceBox` yksinkertaistaa valintaprosessia. Olipa kyseessä maan, osavaltion tai minkä tahansa muun vaihtoehdon valitseminen ennalta määritetystä luettelosta, `ChoiceBox` virtaviivaistaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox` voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteereitä tai lajittelupreferenssejä luettelosta, joka helpottaa datan organisointia ja navigointia.

4. **Määritykset ja asetukset**: Kun sovelluksessasi on asetuksia tai määrittelyvaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjien säätää mieltymyksiä. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen räätälöintiä heidän tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi silloin, kun ennalta määrätty numero vaihtoehtoja on saatavilla, eikä mukautettuja vaihtoehtoja tulisi sallia tai sisällyttää. Jos käyttäjiltä halutaan sallia mukautettujen arvojen syöttäminen, käytä sen sijaan [`ComboBox`](./combo-box.md).
:::

## Alasvetotyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodia määritetään arvo `type`-attribuutille `ChoiceBox`-komponentissa, ja vastaava arvo attribuutille `data-dropdown-for` `ChoiceBox`-alasvetovalikossa. Tämä on hyödyllistä tyyliä varten, koska alasvetovalikko otetaan pois nykyisestä sijainnistaan DOM:issa ja siirretään sivun rungon loppuun avattaessa.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Tämä irrottaminen luo tilanteen, jossa alasvetovalikon suora kohdistaminen CSS:llä tai varjopuolen valitsimilla vanhempikomponentista on haastavaa, ellei käytetä alasvetotyypin attribuuttia.

Alla olevassa demoissa alasvetotyypin asettaminen ja käyttäminen CSS-tiedostossa alasvetovalikon valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maksimi rivimäärä {#max-row-count}

Oletusarvoisesti alasvetovalikossa näkyvien rivien määrä kasvatetaan sisällön mukaiseksi. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodia voidaan hallita näyttävien kohteiden määrää.

:::tip
Jos käytetään numeroa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistetaan käytöstä.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ChoiceBox` -komponentin vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()`- ja `close()`-metodien avulla. Nämä metodit mahdollistavat vaihtoehtoluettelon näyttämisen valintaa varten tai sen piilottamisen tarpeen mukaan, mikä tarjoaa enemmän joustavuutta `ChoiceBox` -komponentin käytön hallinnassa.

Lisäksi webforJ:llä on tapahtumakuuntelijat, kun `ChoiceBox` on suljettu ja kun se on avattu, antaen sinulle enemmän hallintaa erityisten toimintojen laukaisemiseksi.

```Java
//Siirrä tai avaa seuraava komponentti lomakkeessa
ChoiceBox yliopisto = new ChoiceBox("Yliopisto");
ChoiceBox pääaine = new ChoiceBox("Pääaine");
Button lähettää = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelot

yliopisto.onClose(e -> {
  pääaine.focus();
});

pääaine.onClose(e -> {
  lähettää.focus();
});
```

## Avoimet mitat {#opening-dimensions}

`ChoiceBox` -komponentilla on menetelmiä, jotka mahdollistavat alasvetovalikon mittojen muokkaamisen. **Maksimikorkeus** ja **minimileveys** alasvetovalikolle voidaan määrittää käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -metodeja.

:::tip
Jos molemmille menetelmille annetaan `String`-arvo, voidaan soveltaa [mikä tahansa pätevä CSS-yksikkö](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikselit, näkymämitat tai muut pätevät säännöt. Kokonaisluvun antaminen asettaa arvon pikseleinä.
:::

## Prefiksi ja suffiksi {#prefix-and-suffix}

Paikat tarjoavat joustavia vaihtoehtoja `ChoiceBox` -komponentin kyvykkyyden parantamiseksi. Voit lisätä kuvakkeita, etikettejä, latauspyöröitä, tyhjennys/nollausmahdollisuuden, avatar/profiilikuvia ja muita hyödyllisiä komponentteja `ChoiceBox` -komponentin sisään tarkentaaksesi käyttäjän tarkoittamia merkityksiä.
`ChoiceBox` -komponentilla on kaksi paikkaa: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä erilaisten komponenttien lisäämiseksi näytetyn vaihtoehdon eteen ja taakse `ChoiceBox` -komponentissa.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittäminen {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Jotta `ChoiceBox` -komponentin käytössä saavutettaisiin optimaalinen käyttäjäkokemus, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajalliset vaihtoehdot**: Pidä valintaluettelo tiiviinä niin paljon kuin mahdollista, ja varmista, että se on relevantti käyttäjän tehtävälle. `ChoiceBox` on ihanteellinen, kun halutaan esittää selkeä luettelo vaihtoehdoista.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon näytettävät etiketit ovat käyttäjäystävällisiä ja itse selittäviä. Varmista, että käyttäjät voivat helposti ymmärtää kunkin valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` näytetään aluksi. Tämä varmistaa, että valittu vaihtoehto on esivalittu, jolloin valinnan tekemiseen tarvittavien vuorovaikutusten määrä vähenee.

4. **ChoiceBox vs. muut luettelokomponentit**: `ChoiceBox` on paras valinta, jos haluat rajoittaa käyttäjän syötteen yhteen valintaan ennalta määrättyjen vaihtoehtojen luettelosta. Toinen luettelokomponentti voi olla parempi, jos tarvitset seuraavia käyttäytymismalleja:
    - Usean valinnan ja kaikkien kohteiden näyttäminen kerralla: [`ListBox`](./list-box.md)
    - Anna mukautettu syöttö: [`ComboBox`](./combo-box.md)
