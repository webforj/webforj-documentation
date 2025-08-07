---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: d2e1c4ceeb6346a98d03075f19f5ee1c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

`ChoiceBox`-komponentti on käyttöliittymäelementti, joka on suunniteltu esittämään käyttäjille luettelo vaihtoehdoista tai valinnoista. Käyttäjät voivat valita yhden vaihtoehdon tästä luettelosta, yleensä napsauttamalla `ChoiceBox`-elementtiä, mikä käynnistää avattavan luettelon näyttämisen, josta käyttäjä voi valita käytettävissä olevat vaihtoehdot. Käyttäjät voivat myös käyttää nuolinäppäimiä vuorovaikutuksissa `ChoiceBox`-elementin kanssa. Kun käyttäjä tekee valinnan, valittu vaihtoehto näytetään sitten `ChoiceBox`-painikkeessa.

## Käyttötarkoitukset {#usages}
`ChoiceBox`-komponentteja käytetään moniin tarkoituksiin, kuten kohteiden valitsemiseen valikosta, kategorioiden listaamiseksi tai määriteltyjen vaihtoehtojen valitsemiseksi. Ne tarjoavat järjestäytyneen ja visuaalisesti miellyttävän tavan käyttäjille tehdä valintoja, erityisesti kun vaihtoehtoja on useita. Yleisiä käyttötarkoituksia ovat:

1. **Käyttäjän valinnat**: `ChoiceBox`-komponentin ensisijainen tarkoitus on sallia käyttäjien valita yksi vaihtoehto luettelosta. Tämä on arvokasta sovelluksissa, jotka vaativat käyttäjiltä valintojen tekemistä, kuten:
    - Valinta kategorioiden luettelosta
    - Määriteltyjen vaihtoehtojen valinta

2. **Lomakekentät**: Kun suunnitellaan lomakkeita, joissa käyttäjien on syötettävä tiettyjä vaihtoehtoja, `ChoiceBox` yksinkertaistaa valintaprosessia. Oli kyseessä sitten maan, osavaltion tai minkä tahansa muun vaihtoehdon valitseminen määritellystä luettelosta, `ChoiceBox` helpottaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox`-elementtiä voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatuskriteerit tai lajitteluvaihtoehdot luettelosta, mikä helpottaa tiedon järjestämistä ja navigointia.

4. **Asetukset ja konfiguraatio**: Kun sovelluksessasi on asetuksia tai konfigurointivaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjille säätää mieltymyksiään. Käyttäjät voivat valita asetuksia luettelosta, mikä helpottaa sovelluksen räätälöintiä heidän tarpeidensa mukaan.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun saatavilla on ennalta määrätty määrä vaihtoehtoja, eikä mukautettujen vaihtoehtojen salliminen ole suotavaa tai mahdollista. Jos haluat sallia käyttäjien syöttää mukautettuja arvoja, käytä sen sijaan [`ComboBox`](./combo-box.md) -elementtiä.
:::

## Pudotusvalikon tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodia voit määrittää arvon `type`-attribuutille `ChoiceBox`-elementissä, ja vastaavan arvon `data-dropdown-for`-attribuutille `ChoiceBox`-elementin pudotusvalikossa. Tämä on hyödyllistä tyylittelyssä, koska pudotusvalikko siirretään pois nykyisestä paikastaan DOM:ssa ja sijoitetaan sivun rungon loppuun, kun se avataan.

Tämä irrottaminen luo tilanteen, jossa pudotusvalikon suora kohdistaminen CSS- tai shadow-part-selektoreilla ylimpään komponenttiin muuttuu haastavaksi, ellei käytetä pudotusvalikon tyyppiä.

Alempana demossa pudotusvalikon tyyppi on asetettu ja käytetty CSS-tiedostossa pudotusvalikon valinnassa ja taustavärin muuttamisessa.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletusarvoisesti pudotusvalikossa näkyvien `ChoiceBox`-elementin rivien määrä nostetaan sisällön mukaan. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodia voit hallita, kuinka monta kohdetta näkyy.

:::tip
Numero, joka on pienempi tai yhtä suuri kuin 0, poistaa tämän ominaisuuden.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ChoiceBox`-elementin valikoiman näkyvyyttä voidaan hallita ohjelmallisesti `open()`- ja `close()`-metodien avulla. Nämä metodit mahdollistavat vaihtoehtoluettelon näyttämisen tai piilottamisen tarpeen mukaan, mikä antaa enemmän joustavuutta `ChoiceBox`-elementin käyttäytymisen hallintaan.

Lisäksi webforJ:llä on tapahtumakuuntelijat silloin, kun `ChoiceBox` suljetaan ja avataan, mikä antaa sinulle enemmän hallintaa laukaista erityisiä toimintoja.

```Java
//Suuntaa tai avaa seuraava komponentti lomakkeessa
ChoiceBox university = new ChoiceBox("Yliopisto");
ChoiceBox major = new ChoiceBox("Pääaine");
Button submit = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden listat

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Avattavat mitat {#opening-dimensions}

`ChoiceBox`-komponentilla on metodeja, jotka mahdollistavat pudotusvalikon mittojen manipuloinnin. **Maksimi korkeus** ja **minimi leveys** pudotusvalikolle voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -metodeja. 

:::tip
Jos siirrät `String`-arvon jompaankumpaan metodeista, voidaan soveltaa [mitä tahansa voimassa olevaa CSS-yksikköä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikseleitä, näkymämittoja tai muita voimassa olevia sääntöjä. Jos lähetät `int`-arvon, se asettaa lähetetyn arvon pikseleinä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Paikat tarjoavat joustavia vaihtoehtoja `ChoiceBox`-komponentin kyvykkyyden parantamiseen. Voit liittää ikoneita, etikettejä, lataussiruja, tyhjennys-/nollausmahdollisuuksia, avatar-/profiilikuvia ja muita hyödyllisiä komponentteja `ChoiceBox`-elementtiin, jotta käyttäjille voidaan selventää tarkoitusta.
`ChoiceBox`-elementillä on kaksi paikkaa: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja erilaisten komponenttien lisäämiseksi näytettävän vaihtoehdon eteen ja taakse `ChoiceBox`-elementissä.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Jotta varmistetaan optimaalinen käyttäjäkokemus `ChoiceBox`-komponentin käytössä, ota huomioon seuraavat parhaat käytännöt:

1. **Selkeät ja rajoitetut vaihtoehdot**: Pidä valintaluettelo mahdollisimman tiiviinä ja käyttäjän tehtävään liittyvänä. `ChoiceBox` on ihanteellinen selkeän vaihtoehtoluettelon esittämiseen.

2. **Käyttäjäystävälliset etiketti**: Varmista, että jokaisen vaihtoehdon näytettävät etiketit ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät ymmärtävät helposti kunkin valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` näytetään ensimmäisen kerran. Tämä varmistaa esivalitun vaihtoehdon, mikä vähentää tarvittavien vuorovaikutusten määrää valinnan tekemiseen.

4. **ChoiceBox vs. muut luettelokomponentit**: `ChoiceBox` on paras vaihtoehto, jos haluat rajoittaa käyttäjäsyöttöä yhteen valintaan ennalta määrättyjen vaihtoehtojen listasta. Toinen luettelokomponentti voi olla parempi, jos tarvitset seuraavia käyttäytymisiä:
    - Monivalinta ja kaikki kohteet näkyvissä kerralla: [`ListBox`](./list-box.md)
    - Salli mukautettu syöttö: [`ComboBox`](./combo-box.md)
