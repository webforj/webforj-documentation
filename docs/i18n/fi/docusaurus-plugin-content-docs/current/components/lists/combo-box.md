---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox`-komponentti yhdistää pudotusvalikon ja tekstikentän, jotta käyttäjät voivat joko valita ennalta määritellyistä vaihtoehdoista tai kirjoittaa mukautetun arvon. Kun mukautettuja syötteitä on sallittava ennalta määriteltyjen ehdotusten rinnalla, se täyttää aukon, jota `ChoiceBox` ei kata.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="List" />

ComboBox-komponentti on monipuolinen syöte-elementti, joka yhdistää sekä pudotusvalikon että tekstikentän ominaisuudet. Se mahdollistaa käyttäjien valita kohteita ennalta määritellystä luettelosta tai syöttää mukautettuja arvoja tarpeen mukaan. Tässä osiossa tarkastellaan ComboBox-komponentin yleisiä käyttötapoja erilaisten tilanteiden yhteydessä:

1. **Tuoteselvitys ja Syöttö**: Käytä ComboBoxia tuoteselvitys- ja syöttöominaisuuden toteuttamiseen. Käyttäjät voivat joko valita tuotteen ennalta määritellystä luettelosta tai kirjoittaa mukautetun tuotteen nimen. Tämä on hyödyllistä sovelluksille, kuten verkkokauppasivustoille, joissa tuotteet ovat laajoja ja monimuotoisia.

2. **Tagin Valinta ja Syöttö**: Sisältöä merkitseviin sovelluksiin ComboBox voi toimia erinomaisena valintana. Käyttäjät voivat valita olemassa olevista tageista tai lisätä mukautettuja tageja kirjoittamalla ne. Tämä on hyödyllistä sisällön organisoinnissa ja luokittelemisessa. Esimerkkejä tällaisista tageista ovat:
    >- Projektitunnukset: Projektinhallintatyökalussa käyttäjät voivat valita tunnuksia tai tageja (esim. "Kiireellinen", "Työn alla", "Valmis") tehtävien tai projektien luokittelemiseksi, ja he voivat luoda mukautettuja tunnuksia tarvittaessa.
    >- Reseptiaineet: Ruoka- tai resepti-sovelluksessa käyttäjät voivat valita aineksia luettelosta (esim. "Tomaatti", "Sipuli", "Kana") tai lisätä omia aineksiaan mukautettuja reseptejä varten.
    >- Sijaintitagit: Kartta- tai geotaggingssovelluksessa käyttäjät voivat valita ennalta määriteltyjä sijaintitagteja (esim. "Ranta", "Kaupunki", "Puisto") tai luoda mukautettuja tageja merkitäkseen tiettyjä paikkoja kartalle.

3. **Tietojen Syöttö Ehdotetuilla Arvoilla**: Tietojen syöttömuodoissa ComboBoxia voidaan käyttää syötön nopeuttamiseen tarjoamalla luettelo ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoja tarkasti ja tehokkaasti.

    :::tip
    `ComboBoxia` tulisi käyttää, kun käyttäjille sallitaan mukautettujen arvojen syöttäminen. Jos vain ennalta määriteltyjä arvoja halutaan, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
    :::

## Mukautettu arvo {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen antaa hallintaa siihen, voivatko käyttäjät muuttaa `ComboBox`-komponentin syötekentän arvoa. Jos arvo on `true`, joka on oletusarvo, käyttäjä voi muuttaa arvoa. Jos se on asetettu `false`:ksi, käyttäjä ei voi muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -menetelmää.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Paikkamerkki {#placeholder}

`ComboBoxille` voidaan asettaa paikkamerkki, joka näkyy komponentin tekstikentässä, kun se on tyhjillään, ja kannustaa käyttäjiä syöttämään halutun arvon kenttään. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -menetelmää.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Pudotusvalikon tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -menetelmää voidaan määrittää arvo `type`-attribuutille `ComboBox`:ssä ja vastaava arvo `data-dropdown-for`-attribuutille pudotusvalikossa. Tämä on hyödyllistä tyylittelyn kannalta, kun pudotusvalikko siirretään nykyisestä paikastaan DOM-rakenteessa sivun alareunaan avatessa.

Tämä irrotus luo tilanteen, jossa pudotusvalikon suora kohdistaminen CSS- tai varjo-osavalitsijoiden avulla vanhemmasta komponentista tulee haastavaksi, ellei käytetä dropdown-tyypin attribuuttia.

Alla olevassa demon esimerkissä Pudotusvalikon tyyppi on asetettu ja käytetty CSS-tiedostossa pudotusvalikon valitsemiseen ja taustavärin muuttamiseen.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maksimirivimäärä {#max-row-count}

Oletusarvoisesti näkyvien rivien määrä `ComboBox`-pudotusvalikossa kasvaa sisällön mukaan. Kuitenkin <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -menetelmän avulla voidaan hallita näkyvien kohteiden määrää.

:::caution
Jos käytetään numeroa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistetaan käytöstä.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Avaminen ja sulkeminen {#opening-and-closing}

`ComboBox`:n vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()`- ja `close()`-menetelmien avulla. Nämä menetelmät mahdollistavat vaihtoehtoluettelon näyttämisen valintaa varten tai sen piilottamisen tarpeen mukaan, tarjoten suurempaa joustavuutta `ComboBox`:n käytön hallinnassa.

Lisäksi webforJ:llä on tapahtumakuuntelijoita `ComboBox`-komponentin sulkemiselle ja avaamiselle, mikä antaa sinulle enemmän kontrollia spottikohtaisten toimintojen laukaisemiseen.

```Java
//Siirrä fokus tai avaa seuraava komponentti muodossa
ComboBox yliopisto = new ComboBox("Yliopisto");
ComboBox pääaine = new ComboBox("Pääaine");
Button lähettää = new Button("Lähetä");

//... Lisää yliopistoja ja pääaineita sisältävät luettelot

yliopisto.onClose( e ->{
  pääaine.open();
});

pääaine.onClose( e ->{
  lähettää.focus();
});
```

## Avausmitat {#opening-dimensions}

`ComboBox`-komponentilla on menetelmiä, jotka mahdollistavat pudotusvalikon mittojen muokkaamisen. **Maksimi korkeus** ja **minimi leveys** pudotusvalikolle voidaan määrittää käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -menetelmiä.

:::tip
Mikäli jompikumpi näistä menetelmistä saa `String`-arvon, voidaan käyttää [mikä tahansa voimassa oleva CSS-yksikkö](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), kuten pikselit, näkymämitat tai muut voimassa olevat säännöt. `int`-arvon siirtäminen asettaa siirretyn arvon pikseleinä.
:::

## Korostaminen {#highlighting}

Kun työskennellään `ComboBoxin` kanssa, voit mukauttaa, milloin teksti korostuu sen perusteella, miten komponentti saa fokuksen. Tämä ominaisuus voi vähentää syöttövirheitä käyttäjien täyttäessä lomakkeita ja parantaa yleistä navigointikokemusta. Muuta korostuskäyttäytymistä käyttämällä `setHighlightOnFocus()`-menetelmää ja yhtä sisäänrakennetuista `HasHighlightOnFocus.Behavior` -enumista:

- `ALL`
Komponentin sisältö korostuu automaattisesti aina, kun komponenttiin kohdistuu fokus.
- `FOCUS`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus ohjelmallisesti.
- `FOCUS_OR_KEY`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus ohjelmallisesti tai tabuloimalla siihen.
- `FOCUS_OR_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus ohjelmallisesti tai klikkaamalla siihen hiirellä.
- `KEY`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus tabuloimalla siihen.
- `KEY_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus tabuloimalla siihen tai klikkaamalla siihen hiirellä.
- `MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponenttiin kohdistuu fokus klikkaamalla siihen hiirellä.
- `NONE`
Komponentin sisältöä ei koskaan automaattisesti korosteta, kun komponenttiin kohdistuu fokus.

:::note
Jos sisältö oli korostettu fokuksen menetyksen yhteydessä, se korostuu jälleen, kun fokus palautuu, riippumatta asetetusta käyttäytymisestä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ComboBoxin` kyvykkyyden parantamiseksi. Voit lisätä ikoneita, etikettejä, latauspyöröjä, tyhjennys/ palautusmahdollisuuksia, avatar/ profiilikuvaa ja muita hyödyllisiä komponentteja `ComboBoxin` sisään lisätäkseen käyttäjille tarkoitettua selkeyttä.
`ComboBox`-komponentilla on kaksi slottia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä erilaisten komponenttien lisäämiseksi ennen ja jälkeen vaihtoehtojen `ComboBoxissa`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ComboBox" />

## Parhaat käytännöt {#best-practices}

Jotta saat optimaalisen käyttäjäkokemuksen käyttäessäsi `ComboBox`-komponenttia, ota huomioon seuraavat parhaat käytännöt:

1. **Esilataa Yhteiset Arvot**: Jos on yhteisiä tai usein käytettyjä kohteita, esilataa ne `ComboBox`-luetteloon. Tämä nopeuttaa yleisesti valittujen kohteiden valintaa ja kannustaa johdonmukaisuuteen.

2. **Käyttäjäystävälliset Etiketit**: Varmista, että jokaisen vaihtoehdon näytettävät etiketit ovat käyttäjäystävällisiä ja helposti ymmärrettäviä. Varmista, että käyttäjät ymmärtävät helposti kunkin valinnan tarkoituksen.

3. **Validointi**: Toteuta syötteen validointi mukautettujen syötteiden käsittelemiseksi. Tarkista tietojen tarkkuus ja johdonmukaisuus. Saatat haluta ehdottaa korjauksia tai vahvistuksia epäselville syötteille.

4. **Oletusvalinta**: Aseta oletusvalinta, erityisesti jos on yleinen tai suositeltu vaihtoehto. Tämä parantaa käyttäjäkokemusta vähentämällä ylimääräisten klikkausten tarvetta.

5. **ComboBox vs. Muiden Luettelokomponenttien**: `ComboBox` on paras valinta, jos tarvitset käyttäjältä yhden syötteen ja haluat tarjota heille ennalta määrättyjä valintoja sekä mahdollisuuden mukauttaa syötteensä. Toinen luettelokomponentti voi olla parempi valinta, jos tarvitset seuraavia käyttäytymisiä:
    - Monivalinta ja kaikkien kohteiden näyttäminen kerralla: [ListBox](./list-box.md)
    - Mukautetun syötteen estäminen: [ChoiceBox](./choice-box.md)
