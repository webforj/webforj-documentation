---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox`-komponentti yhdistää pudotuslistan ja tekstikentän, jotta käyttäjät voivat valita ennalta määritellyistä vaihtoehdoista tai kirjoittaa oman arvonsa. Kun mukautettujen merkintöjen käyttö on sallittua yhdessä ehdotettujen vaihtoehtojen kanssa, se täyttää sen aukon, jota `ChoiceBox` ei kata.

<!-- INTRO_END -->

## Käytöt {#usages}

<ParentLink parent="List" />

ComboBox-komponentti on monipuolinen syöte-elementti, joka yhdistää pudotuslistan ja tekstikentän ominaisuudet. Se tuo käyttäjille mahdollisuuden valita kohteita ennalta määritellystä luettelosta tai syöttää mukautettuja arvoja tarpeen mukaan. Tässä osiossa tutkitaan ComboBox-komponentin yleisiä käyttötapoja eri tilanteissa:

1. **Tuotteen haku ja syöttö**: Käytä ComboBoxia toteuttaaksesi tuotteen haku- ja syöttötoiminto. Käyttäjät voivat valita tuotteen ennalta määritellystä luettelosta tai kirjoittaa oman tuotteen nimensä. Tämä on hyödyllistä sovelluksille, kuten verkkokaupoille, joissa tuotteet ovat laajoja ja monimuotoisia.

2. **Tagien valinta ja syöttö**: Sisältöjen merkitsemiseen liittyvissä sovelluksissa ComboBox voi olla erinomainen vaihtoehto. Käyttäjät voivat valita olemassa olevista tageista tai lisätä mukautettuja tageja kirjoittamalla ne. Tämä on hyödyllistä sisällön organisoimiseksi ja luokiteltavaksi. Esimerkkejä tällaisista tageista ovat:
    >- Projektitunnukset: Projektinhallintatyökalussa käyttäjät voivat valita merkintöjä tai tageja (esim. "Kiireellinen", "Käynnissä", "Valmistunut") luokitellakseen tehtävät tai projektit, ja he voivat luoda mukautettuja merkintöjä tarpeen mukaan.
    >- Reseptin ainekset: Ruoanlaitto- tai reseptisovelluksessa käyttäjät voivat valita aineksia luettelosta (esim. "Tomaatit", "Sipulit", "Kana") tai lisätä omia aineksiaan mukautettuihin resepteihin.
    >- Sijaintitunnukset: Kartta- tai geotagging-sovelluksessa käyttäjät voivat valita ennalta määriteltyjä sijaintitunnuksia (esim. "Ranta", "Kaupunki", "Puisto") tai luoda mukautettuja tageja merkitäkseen tiettyjä paikkoja kartalle.

3. **Tietojen syöttö ehdotetuilla arvoilla**: Tietojen syöttölomakkeissa ComboBoxia voidaan käyttää syötteen nopeuttamiseksi tarjoamalla luettelo ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoa tarkasti ja tehokkaasti.

    :::tip
    `ComboBox`-komponenttia tulisi käyttää, kun käyttäjien sallitaan syöttää mukautettuja arvoja. Jos halutaan vain ennalta määriteltyjä arvoja, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
    :::

## Mukautettu arvo {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen antaa hallita, voidaanko käyttäjän muuttaa arvona `ComboBox`-komponentin syötekentässä. Jos asetetaan `true`, mikä on oletusarvo, käyttäjä voi muuttaa arvoa. Jos asetetaan `false`, käyttäjän ei sallita muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -metodia.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Paikkamerkki {#placeholder}

ComboBoxille voidaan asettaa paikkamerkki, joka näkyy komponentin syötekentässä, kun se on tyhjillään, kehottaen käyttäjiä syöttämään halutun merkinnän kenttään. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -metodia.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Pudotuslistan tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodia määritetään arvo `type` -attribuutille `ComboBox`:ssa, ja vastaava arvo `data-dropdown-for` -attribuutille `ComboBox`:n pudotuslistassa. Tämä on hyödyllistä tyylittämisessä, koska pudotuslista poistetaan nykyisestä sijainnistaan DOM:ssa ja siirretään sivun rungon loppuun avattaessa.

Tämä irrottaminen luo tilanteen, jossa pudotuslistan suora kohdistaminen CSS- tai shadow part -valitsimilla vanhemmasta komponentista on haastavaa, ellei hyödynnä pudotuslistan tyyppiä.

Alla olevassa demossa pudotuslistan tyyppi on määritetty ja käytetty CSS-tiedostossa, jotta vaihtoehto suurenee sitä hoveroitaessa.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletusarvoisesti pudotuslistassa `ComboBox`-komponentissa näytetään rivimäärä, joka kasvatetaan sisällön mukaan. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodia voi hallita, kuinka monta kohdetta näytetään.

:::caution
Jos käytetään numeroa, joka on alle tai yhtä suuri kuin 0, tämä ominaisuus poistetaan käytöstä.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ComboBox`:n vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()`- ja `close()`-menetelmien avulla. Nämä menetelmät antavat mahdollisuuden näyttää tai piilottaa vaihtoehtojen lista valittavaksi tarpeen mukaan, tarjoten suuremman joustavuuden `ComboBox`:n käyttäytymisen hallinnassa.

Lisäksi webforJ:llä on tapahtumakuuntelijat, kun `ComboBox` on suljettu ja kun se avataan, mikä antaa lisää hallintaa tiettyjen toimintojen laukaisemiseksi.

```Java
//Siirrä fokus tai avaa seuraava komponentti lomakkeessa
ComboBox university = new ComboBox("Yliopisto");
ComboBox major = new ComboBox("Pääaine");
Button submit = new Button("Lähetä");

//... Lisää listat yliopistoista ja pääaineista

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Avausmitat {#opening-dimensions}

`ComboBox`-komponentilla on menetelmiä, jotka sallivat pudotuslistan mittojen muokkaamisen. **Maksimikorkeus** ja **minimileveys** pudotuslistalle voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -metodeja, vastaavasti.

:::tip
Antamalla `String`-arvon kummallekin näistä menetelmistä voit soveltaa [mitä tahansa voimassa olevaa CSS-yksikköä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikseleitä, näkymämittoja tai muita voimassa olevia sääntöjä. Jos annetaan `int`, määritetään arvo pikseleinä.
:::

## Korostaminen {#highlighting}

Työskennellessäsi `ComboBox`:n kanssa voit mukauttaa, milloin tekstiä korostetaan sen perusteella, miten komponentti saa fokuksen. Tämä ominaisuus voi vähentää syöttövirheitä, kun käyttäjät täyttävät lomakkeita, ja parantaa yleistä navigointikokemusta. Muuta korostuskäyttäytymistä käyttämällä `setHighlightOnFocus()`-metodia yhdellä sisäänrakennetuista `HasHighlightOnFocus.Behavior`-enumerointiarvoista:

- `ALL`
Komponentin sisältö korostuu aina automaattisesti, kun komponentti saa fokuksen.
- `FOCUS`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisesti.
- `FOCUS_OR_KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisesti tai tabuloinnin kautta.
- `FOCUS_OR_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisesti tai hiirellä napsauttamalla.
- `KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen tabuloinnin kautta.
- `KEY_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen tabuloinnin tai hiirellä napsauttamisen kautta.
- `MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen hiirellä napsauttamalla.
- `NONE`
Komponentin sisältöä ei koskaan automaattisesti korosteta, kun komponentti saa fokuksen.

:::note
Jos sisältöä korostettiin, kun fokus menetettiin, se korostuu jälleen, kun fokus palautuu, riippumatta asetetusta käyttäytymisestä.
:::

## Etuliite ja suffiksit {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ComboBox`-komponentin kykyjen parantamiseen. Voit käyttää ikoneita, merkintöjä, latauspurskeita, tyhjennys-/nollaustoimintoja, avatar-/profiilikuvia ja muita hyödyllisiä komponentteja sisäisesti `ComboBox`:ssa, jotta käyttäjille välittyisi selkeä tarkoitus.
`ComboBox`-komponentilla on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()`- ja `setSuffixComponent()`-menetelmiä lisätäksesi erilaisia komponentteja vaihtoehtojen eteen ja taakse `ComboBox`:ssa.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ComboBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ComboBox`-komponentin käytössä harkitse seuraavia parhaita käytäntöjä:

1. **Esilataa tavalliset arvot**: Jos on olemassa tavallisia tai usein käytettyjä kohteita, esilataa ne `ComboBox`-listalle. Tämä nopeuttaa tavallisesti valittujen kohteiden valintaa ja kannustaa johdonmukaisuuteen.

2. **Käyttäjäystävälliset merkinnät**: Varmista, että jokaisen vaihtoehdon näytettävät merkinnät ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät ymmärtävät helposti jokaisen valinnan tarkoituksen.

3. **Vahvistus**: Ota käyttöön syöttövalidointi mukautettujen merkintöjen käsittelemiseksi. Tarkista tietojen tarkkuus ja johdonmukaisuus. Saatat haluta ehdottaa korjauksia tai vahvistuksia epämääräisille merkinnöille.

4. **Oletusvalinta**: Aseta oletusvalinta, erityisesti jos on olemassa yleinen tai suositeltu vaihtoehto. Tämä parantaa käyttäjäkokemusta vähentämällä ylimääräisten napsautusten tarvetta.

5. **ComboBox vs. muut luettelosovellukset**: `ComboBox` on paras valinta, jos tarvitset käyttäjältä yhden syötteen ja haluat antaa heille ennalta määrättyjä valintoja sekä mahdollisuuden mukauttaa syöttöään. Toinen luettelosovellus voi olla parempi, jos tarvitset seuraavia käyttäytymisiä:
    - Monivalinta ja kaikkien kohteiden näyttäminen kerralla: [ListBox](./list-box.md)
    - Estä mukautettu syöttö: [ChoiceBox](./choice-box.md)
