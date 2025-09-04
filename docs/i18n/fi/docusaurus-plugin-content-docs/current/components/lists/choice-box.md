---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

`ChoiceBox` -komponentti on käyttöliittymäelementti, joka on suunniteltu esittämään käyttäjille lista vaihtoehdoista tai valinnoista. Käyttäjät voivat valita yhden vaihtoehdon tästä listasta, yleensä napsauttamalla `ChoiceBox`:ia, mikä laukaisee alasvetoluettelon, joka sisältää saatavilla olevat vaihtoehdot. Käyttäjät voivat myös käyttää nuolinäppäimiä vuorovaikutukseen `ChoiceBox`:in kanssa. Kun käyttäjä tekee valinnan, valittu vaihtoehto näytetään sitten `ChoiceBox`:in napissa.

## Käytöt {#usages}
`ChoiceBox` -komponentteja käytetään monenlaisiin tarkoituksiin, kuten valikoiden valitseminen, kategorioista valitseminen tai vaihtoehtojen valinta ennalta määritellyistä joukoista. Ne tarjoavat käyttäjille organisoidun ja visuaalisesti miellyttävän tavan tehdä valintoja, erityisesti kun saatavilla on useita vaihtoehtoja. Yleisiä käytännöistä ovat:

1. **Käyttäjän vaihtoehtoihin valitseminen**: `ChoiceBox` -komponentin ensisijainen tarkoitus on sallia käyttäjien valita yksi vaihtoehto listasta. Tämä on arvokasta sovelluksissa, joissa käyttäjien on tehtävä valintoja, kuten:
    - Valitseminen kategorialuettelosta
    - Valitseminen ennalta määritellyistä vaihtoehdoista

2. **Lomakekentät**: Kun suunnitellaan lomakkeita, joissa käyttäjien on syötettävä tiettyjä vaihtoehtoja, `ChoiceBox` yksinkertaistaa valintaprosessia. Olipa kyseessä maan, osavaltion tai muun vaihtoehdon valitseminen ennalta määritellyistä luetteloista, `ChoiceBox` virtaviivaistaa syöttöprosessia.

3. **Suodatus ja lajittelu**: `ChoiceBox` voidaan käyttää suodatus- ja lajittelutehtävissä sovelluksissa. Käyttäjät voivat valita suodatinperusteet tai lajittelupreferenssit luettelosta, helpottaen tietojen organisointia ja navigointia.

4. **Konfigurointi ja asetukset**: Kun sovelluksessasi on asetuksia tai konfigurointivaihtoehtoja, `ChoiceBox` tarjoaa intuitiivisen tavan käyttäjille säätää mieltymyksiä. Käyttäjät voivat valita asetuksia luettelosta, mikä tekee sovelluksen mukauttamisesta helppoa.

:::tip
`ChoiceBox` on tarkoitettu käytettäväksi, kun ennaltamäärätty määrä vaihtoehtoja on saatavilla, eikä mukautettuja vaihtoehtoja tulisi sallia tai sisällyttää. Jos haluat sallia käyttäjien syöttävän mukautettuja arvoja, käytä sen sijaan [`ComboBox`](./combo-box.md) -komponenttia.
:::

## Alasvetotyyppi {#dropdown-type}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodin käyttäminen määrittää arvon `type` -attribuutille `ChoiceBox` -komponentissa, ja vastaava arvo `data-dropdown-for` -attribuutille `ChoiceBox`:in alasvetolistalla. Tämä on hyödyllistä tyylittelyssä, sillä alasvetovalikko siirretään pois nykyisestä paikastaan DOM:ssa ja siirretään sivun kehon loppuosaan avattaessa.

<!-- ![esimerkki tyyppi](/img/components/_images/choicebox/type.png)
![esimerkki tyyppi zoomattuna](/img/components/_images/choicebox/type_zoomed.png) -->

Tämä irrotus luo tilanteen, jossa alasvetovalikon suora kohdistaminen CSS- tai shadow part -valitsimilla vanhempikomponentista on haastavaa, ellei käytetä alasvetotyyppi attribuuttia.

Demossa alla alasvetotyyppi on asetettu ja sitä käytetään CSS-tiedostossa alasvetovalikon valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletuksena `ChoiceBox` -komponentin alasvetoluettelossa näytettävien rivien määrä lisääntyy vastaamaan sisältöä. Kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodin käyttäminen mahdollistaa hallinnan siitä, kuinka monta kohdetta näytetään.

:::tip
Negatiivisen tai nollan arvon käyttäminen tämän ominaisuuden poistamiseen johtaa tämän ominaisuuden poistamiseen.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ChoiceBox` -komponentin vaihtoehtojen näkyvyyttä voidaan ohjelmallisesti hallita `open()` ja `close()` -metodien avulla. Nämä metodit antavat sinun näyttää valintalistat tai piilottaa ne tarpeen mukaan, tarjoten suurempaa joustavuutta `ChoiceBox` -komponentin käytössä.

Lisäksi webforJ:llä on tapahtumakuuntelijoita, jotka aktivoituvat silloin, kun `ChoiceBox` suljetaan ja avataan, jolloin sinulla on enemmän hallintaa tiettyjen toimintojen laukaisemiseksi.

```Java
//Tavoita tai avaa seuraava komponentti lomakkeessa
ChoiceBox university = new ChoiceBox("Yliopisto");
ChoiceBox major = new ChoiceBox("Pääaine");
Button submit = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelo

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Avausmitat {#opening-dimensions}

`ChoiceBox` -komponentilla on metodeja, joiden avulla voidaan manipuloida alasvetoluettelon mittoja. **Maksimikorkeus** ja **minimileveys** alasvetoluettelolle voidaan määrittää käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -metodeja.

:::tip
Mikä tahansa `String`-arvo, joka annetaan näiden metodien vaatimuksiin, sallii [minkä tahansa voimassa olevan CSS-yksikön](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) käytön, kuten pikselit, näkymämitat tai muut voimassa olevat säännöt. Jos annetaan `int`, niin annettu arvo asetetaan pikseleissä.
:::

## Etuliite ja suffiksi {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ChoiceBox` -komponentin kykyjen parantamiseksi. Voit käyttää kuvakkeita, etikettejä, lataussymboleita, tyhjentämis-/nollausominaisuuksia, avatar-/profiilikuvaa ja muita hyödyllisiä komponentteja `ChoiceBox`:in sisällä, jotta selvennät käyttäjille tarkoitettua merkitystä.
`ChoiceBox` -komponentissa on kaksi slottia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja eri komponenttien lisäämiseksi ennen ja jälkeen `ChoiceBox`:issa näytettävän vaihtoehdon.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ChoiceBox" />

## Parhaat käytännöt {#best-practices}

Jotta `ChoiceBox` -komponentin käyttö olisi optimaalinen käyttäjäkokemus, harkitse seuraavia parhaita käytäntöjä:

1. **Selkeät ja rajatut vaihtoehdot**: Pidä vaihtoehtoluettelo mahdollisimman tiiviinä ja merkityksellisenä käyttäjän tehtävään. `ChoiceBox` on ihanteellinen selkeän vaihtoehtoluettelon esittämiseen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että kunkin vaihtoehdon näytettävät etiketit ovat käyttäjäystävällisiä ja itse selittäviä. Varmista, että käyttäjät ymmärtävät helposti jokaisen valinnan tarkoituksen.

3. **Oletusvalinta**: Aseta oletusvalinta, kun `ChoiceBox` -komponentti näkyy ensimmäistä kertaa. Tämä varmistaa, että ennakkoon valittu vaihtoehto on olemassa, vähentäen tarvittavien vuorovaikutusten määrää valinnan tekemiseksi.

4. **ChoiceBox vs. muut listakomponentit**: `ChoiceBox` on paras vaihtoehto, jos sinun on rajoitettava käyttäjän syöttö yhdelle valinnalle ennalta määrätyistä vaihtoehdoista. Toinen listakomponentti voi olla parempi, jos tarvitset seuraavia toimintoja:
    - Usean valinnan ja kaikkien kohteiden näyttäminen kerralla: [`ListBox`](./list-box.md)
    - Mukautetun syötön salliminen: [`ComboBox`](./combo-box.md)
