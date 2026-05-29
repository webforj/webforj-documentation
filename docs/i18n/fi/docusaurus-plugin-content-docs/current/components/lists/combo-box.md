---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox`-komponentti yhdistää alasvetoluettelon ja tekstikentän, jotta käyttäjät voivat valita joko ennaltamäärättyjä vaihtoehtoja tai kirjoittaa oman arvonsa. Kun mukautettujen merkintöjen salliminen on tarpeen yhdessä ehdotettujen vaihtoehtojen kanssa, se täyttää aukon, jota `ChoiceBox` ei kata.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="List" />

ComboBox-komponentti on monipuolinen syöttöelementti, joka yhdistää alasvetoluettelon ja tekstikentän ominaisuudet. Se sallii käyttäjien valita kohteita ennaltamäärätystä luettelosta tai syöttää mukautettuja arvoja tarpeen mukaan. Tässä osiossa käsitellään ComboBox-komponentin yleisiä käyttötarkoituksia eri tilanteissa:

1. **Tuotteen haku ja syöttö**: Käytä ComboBoxia toteuttaaksesi tuotteen haku- ja syöttötoiminnallisuuden. Käyttäjät voivat joko valita tuotteen ennaltamäärätystä luettelosta tai kirjoittaa mukautetun tuotteen nimen. Tämä on hyödyllistä sovelluksille, kuten verkkokaupoille, joissa tuotteet ovat laajoja ja monipuolisia.

2. **Tagien valinta ja syöttö**: Sisältöjen merkitsemiseen liittyvissä sovelluksissa ComboBox voi toimia erinomaisena valintana. Käyttäjät voivat valita olemassa olevista tageista tai lisätä mukautettuja tageja kirjoittamalla niitä. Tämä on hyödyllistä sisällön organisoinnissa ja luokittelussa. Esimerkkejä tällaisista tageista ovat:
    >- Projektitunnukset: Projektinhallintatyökalussa käyttäjät voivat valita tunnistetietoja tai tageja (esim. "Kiireellinen", "Käynnissä", "Valmis") tehtävien tai projektien luokittelemiseksi ja he voivat luoda mukautettuja tunnuksia tarpeen mukaan.
    >- Reseptin ainekset: Ruokasovelluksessa tai reseptisovelluksessa käyttäjät voivat valita aineksia luettelosta (esim. "Tomaatti", "Sipulit", "Kana") tai lisätä omia aineksiaan mukautettuja reseptejä varten.
    >- Sijaintitagit: Kartta- tai geotunnistus-sovelluksessa käyttäjät voivat valita ennaltamäärättyjä sijaintitagia (esim. "Ranta", "Kaupunki", "Puisto") tai luoda mukautettuja tageja tietyjen paikkojen merkitsemiseksi kartalla.

3. **Tietojen syöttö ehdotetuilla arvoilla**: Tietojen syöttölomakkeissa ComboBoxia voidaan käyttää syötön nopeuttamiseen tarjoamalla luettelo ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoja tarkasti ja tehokkaasti.

    :::tip
    `ComboBox`-komponenttia tulee käyttää, kun käyttäjiltä sallitaan mukautettujen arvojen syöttäminen. Jos vain ennalta määrättyjä arvoja halutaan, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
    :::

## Mukautettu arvo {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen mahdollistaa käyttäjän kyvyn muuttaa arvoa `ComboBox`-komponentin syöttökentässä. Jos se on `true`, mikä on oletusarvo, käyttäjä voi muuttaa arvoa. Jos se asetetaan `false`, käyttäjä ei voi muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -menetelmää.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Paikannus {#placeholder}

Paikannus voidaan asettaa `ComboBox`ille, joka näkyy komponentin tekstikentässä, kun se on tyhjillään ja kehottaa käyttäjiä syöttämään haluttua merkintää kenttään. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -menetelmää.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Alasvetoluokan tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -menetelmää voidaan määrittää arvo `type`-attribuutille `ComboBox`ille sekä vastaava arvo `data-dropdown-for`-attribuutille `ComboBox`in alasvetoluettelossa. Tämä on hyödyllistä tyylittelyn kannalta, koska alasvetoluettelo siirretään nykyisestä paikastaan DOM:issa sivun ruumiin loppuun avattaessa.

Tämä eristyminen luo tilanteen, jossa alasvetoluetteloon viittaaminen suoraan CSS:llä tai varjoparttivalitsimilla vanhempana komponenttina tulee haastavaksi, ellei käytetä alasvetoluokan tyyppi -attribuuttia.

Alla olevassa demossa alasvetoluokan tyyppi on määritetty ja käytetty CSS-tiedostossa alasvetoluettelon valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max rivimäärä {#max-row-count}

Oletusarvoisesti näytettävien rivien määrä `ComboBox`in alasvetoluettelossa kasvaa sisällön mukaan. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -menetelmää voidaan hallita, kuinka monta kohdetta näytetään.

:::caution
Jos käytetään numeroa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus poistetaan käytöstä.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ComboBox`in vaihtoehtojen näkyvyyttä voidaan ohjelmallisesti hallita `open()`- ja `close()`-menetelmillä. Nämä menetelmät mahdollistavat vaihtoehtoluettelon näyttämisen valintaa varten tai sen piilottamisen tarpeen mukaan, mikä tarjoaa enemmän joustavuutta `ComboBox`in käyttäytymisen hallintaan.

Lisäksi webforJ:llä on tapahtumakuuntelijat sen mukaan, kun `ComboBox` suljetaan ja avataan, mikä antaa enemmän hallintaa tiettyjen toimintojen laukaisemiseksi.

```Java
//Fokusoidaan tai avataan seuraava komponentti lomakkeessa
ComboBox yliopisto = new ComboBox("Yliopisto");
ComboBox pääaine = new ComboBox("Pääaine");
Button lähettää = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luetteloita

yliopisto.onClose( e ->{
  pääaine.open();
});

pääaine.onClose( e ->{
  lähettää.focus();
});
```

## Avaaminen ulottuvuudet {#opening-dimensions}

`ComboBox`-komponentilla on menetelmiä, jotka sallivat alasvetoluettelon ulottuvuuksien muokkaamisen. **Maksimi korkeus** ja **minimi leveys** alasvetoluettelo voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -menetelmiä vastaavasti.

:::tip
Syötettäessä `String` -arvo joko kehys käsittelysääntö estää kaikkien hyväksyttyjen CSS-yksiköiden soveltamisen, kuten pikselit, näytön ulottuvuudet tai muut voimassa olevat säännöt. Syötettäessä `int` asetetaan siirretty arvo pikseleinä.
:::

## Korostaminen {#highlighting}

Työskentelyssä `ComboBox`in kanssa voit mukauttaa, milloin teksti korostuu komponenttiin keskittymisen perusteella. Tämä ominaisuus voi vähentää syöttövirheitä, kun käyttäjät täyttävät lomakkeita ja parantaa yleistä navigointikokemusta. Vaihda korostuskäyttäytyminen käyttämällä `setHighlightOnFocus()` -menetelmää, johon liittyy eräs sisäänrakennetuista `HasHighlightOnFocus.Behavior` -enumista:

- `ALL`
Komponentin sisällöt korostuvat aina automaattisesti, kun komponentti saa keskittymisen.
- `FOCUS`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen ohjelmallisesti.
- `FOCUS_OR_KEY`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen ohjelmallisesti tai tabuloimalla siihen.
- `FOCUS_OR_MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen ohjelmallisesti tai napsauttamalla siihen hiirellä.
- `KEY`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen tabuloimalla siihen.
- `KEY_MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen tabuloimalla siihen tai napsauttamalla siihen hiirellä.
- `MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa keskittymisen napsauttamalla siihen hiirellä.
- `NONE`
Komponentin sisällöt eivät koskaan korostu automaattisesti, kun komponentti saa keskittymisen.

:::note
Jos sisältö oli korostettuna keskittymisen menettämisen jälkeen, se korostuu uudelleen, kun se saa keskittymisen takaisin, riippumatta asetetusta käyttäytymisestä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Slots tarjoavat joustavia vaihtoehtoja `ComboBox`in kyvyn parantamiseksi. Voit lisätä ikoneita, etikettejä, lataussymboleita, tyhjennys/nollausominaisuuksia, avatar/profiilikuva, sekä muita hyödyllisiä komponentteja `ComboBox`iin, jotka selkeyttävät käyttäjille tarkoitetun merkityksen. `ComboBox`illa on kaksi slotia: `prefix` ja `suffix` slotit. Käytä `setPrefixComponent()` ja `setSuffixComponent()` menetelmiä, jotta voit lisätä erilaisia komponentteja ennen ja jälkeen vaihtoehtojen `ComboBox`issa.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ComboBox" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `ComboBox`-komponentin käytössä on syytä harkita seuraavia parhaita käytäntöjä:

1. **Esikuormita yleiset arvot**: Jos on olemassa yleisiä tai usein käytettyjä kohteita, esikuormita ne `ComboBox`-luetteloon. Tämä nopeuttaa yleisten valintojen tekemistä ja kannustaa johdonmukaisuuteen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon näyttämät etiketit ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät ymmärtävät helposti jokaisen valinnan tarkoituksen.

3. **Vahvistus**: Ota käyttöön syötteen vahvistus mukautettujen merkintöjen käsittelemiseksi. Tarkista tietojen tarkkuus ja johdonmukaisuus. Saatat haluta ehdottaa korjauksia tai vahvistuksia epäselville merkinnöille.

4. **Oletusvalinta**: Aseta oletusvalinta, varsinkin jos on olemassa yleinen tai suositeltava valinta. Tämä parantaa käyttäjäkokemusta vähentämällä ylimääräisten napsautusten tarvetta.

5. **ComboBox vs. muut luettelokomponentit**: `ComboBox` on paras valinta, jos tarvitset käyttäjältä yhden syötteen ja haluat tarjota heille ennalta määrättyjä vaihtoehtoja sekä mahdollisuuden mukauttaa syötteitään. Toinen luettelokomponentti voisi olla parempi, jos tarvitset seuraavia käyttäytymisiä:
    - Monivalinta ja kaikkien kohteiden näyttäminen kerralla: [ListBox](./list-box.md)
    - Estää mukautetu syöttäminen: [ChoiceBox](./choice-box.md)
