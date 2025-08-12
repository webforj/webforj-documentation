---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: ec3f88523477bf08e92fe9153b014b91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Lista" />

`ComboBox`-komponentti on käyttöliittymän elementti, joka on suunniteltu esittämään käyttäjille luettelo vaihtoehdoista tai valinnoista sekä kenttä omien mukautettujen arvojen syöttämistä varten. Käyttäjät voivat valita yhden vaihtoehdon tästä luettelosta, yleensä napsauttamalla `ComboBox`:ia, mikä laukaisee alasvetoluettelon, joka sisältää saatavilla olevat valinnat, tai kirjoittaa mukautetun arvon. Käyttäjät voivat myös käyttää nuolinäppäimiä `ComboBox`:in kanssa. Kun käyttäjä tekee valinnan, valittu vaihtoehto näytetään sitten `ComboBox`:issa.

## Käytöt {#usages}

ComboBox-komponentti on monipuolinen syöte-elementti, joka yhdistää sekä alasvetoluettelon että tekstinsyöttökentän ominaisuudet. Se antaa käyttäjille mahdollisuuden valita kohteita ennalta määritetystä luettelosta tai syöttää mukautettuja arvoja tarpeen mukaan. Tässä osiossa tarkastellaan ComboBox-komponentin yleisiä käyttötapoja eri skenaarioissa:

1. **Tuotteen haku ja syöttö**: Käytä ComboBoxia toteuttaaksesi tuotteen hakemisen ja syöttämisen toiminnon. Käyttäjät voivat joko valita tuotteen ennalta määritetystä luettelosta tai kirjoittaa mukautetun tuotenimen. Tämä on hyödyllistä sovelluksille, kuten verkkokaupoille, joissa tuotteet ovat laajoja ja monipuolisia.

2. **Tagien valinta ja syöttö**: Sovelluksissa, joissa käsitellään sisältötageja, ComboBox voi olla erinomainen valinta. Käyttäjät voivat valita olemassa olevista tageista tai lisätä mukautettuja tageja kirjoittamalla niitä. Tämä on hyödyllistä sisällön organisoimiseksi ja ryhmittelyksi. Esimerkkejä tällaisista tageista ovat:
    >- Projektitunnisteet: Projektinhallintatyökalussa käyttäjät voivat valita tunnisteita tai tageja (esim. "Kiireellinen", "Käynnissä", "Valmis") tehtävien tai projektien luokittelemiseksi, ja he voivat luoda mukautettuja tunnisteita tarvittaessa.
    >- Reseptiaineet: Ruoka- tai reseptisovelluksessa käyttäjät voivat valita aineksia luettelosta (esim. "Tomaatit", "Sipulit", "Kana") tai lisätä omia aineksia mukautettuihin resepteihin.
    >- Sijaintitagit: Kartta- tai geotagging-sovelluksessa käyttäjät voivat valita ennalta määritettyjä sijaintitägejä (esim. "Ranta", "Kaupunki", "Puisto") tai luoda mukautettuja tageja merkitäkseen tiettyjä paikkoja kartalle.

3. **Tietojen syöttö ehdotetuilla arvoilla**: Tietojen syöttölomakkeissa ComboBoxia voidaan käyttää syöttöprosessin nopeuttamiseksi tarjoamalla luettelo ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoja tarkasti ja tehokkaasti.

    :::tip
    `ComboBox`:ia tulisi käyttää, kun käyttäjille sallitaan mukautettujen arvojen syöttäminen. Jos vain esiasetettuja arvoja halutaan, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
    :::

## Mukautettu arvo {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen mahdollistaa hallinnan siitä, voidaanko käyttäjän muuttaa arvoa `ComboBox`-komponentin syöttökentässä. Jos arvo on `true`, mikä on oletusarvo, käyttäjä voi muuttaa arvoa. Jos se asetetaan `false`:ksi, käyttäjä ei voi muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -metodia.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Paikkamerkki {#placeholder}

ComboBoxille voidaan asettaa paikkamerkki, joka näytetään komponentin syöttökentässä, kun se on tyhjillään käyttäjille kehottaakseen heitä syöttämään haluamansa arvon kenttään. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -metodia.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Alasvetotyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodia voidaan määrittää arvo `type`-attribuutille `ComboBox`:issa, ja vastaava arvo `data-dropdown-for` -attribuutille `ComboBox`:in alasvetoluettelossa. Tämä on hyödyllistä tyylittelyä varten, koska alasvetoluettelo siirretään DOM:in nykyisestä sijainnistaan sivun rungon loppuun avattaessa.

Tämä irrotus luo tilanteen, jossa alasvetoluetteloon voi olla haastavaa kohdistaa suoraan CSS:llä tai vanhemman komponentin varjopuolten valitsimilla, ellei käytetä alasvetotyypin attribuuttia.

Alla olevassa demonstraatiossa alasvetotyypin asetus on määritelty ja käytetty CSS-tiedostossa alasvetoluettelon valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Maksimi rivimäärä {#max-row-count}

Oletusarvoisesti alasvetoluettelossa näkyvien rivien määrä `ComboBox`:issa nostetaan vastaamaan sisältöä. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodia voidaan hallita, kuinka monta kohdetta näytetään.

:::caution
Jos käytetään arvoa, joka on pienempi tai yhtä suuri kuin 0, tämä ominaisuus tulkitaan pois käytöstä.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ComboBox`:in vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()` ja `close()` -menetelmillä. Nämä menetelmät mahdollistavat vaihtoehtoluettelon näyttämisen valintaa varten tai sen piilottamisen tarpeen mukaan, tarjoamalla suurempaa joustavuutta `ComboBox`:in käyttäytymisen hallinnassa.

Lisäksi webforJ:llä on tapahtumakuuntelijat, jotka aktivoituvat, kun `ComboBox` suljetaan ja avataan, antaen sinulle enemmän hallintaa erityisten toimintojen laukaisemiseksi.

```Java
//Kohdistaa tai avaa seuraavan komponentin lomakkeessa
ComboBox yliopisto = new ComboBox("Yliopisto");
ComboBox pääaine = new ComboBox("Pääaine");
Button lähetä = new Button("Lähetä");

//... Lisää yliopistojen ja pääaineiden luettelot

yliopisto.onClose( e ->{
  pääaine.open();
});

pääaine.onClose( e ->{
  lähetä.focus();
});
```

## Avausmitat {#opening-dimensions}

`ComboBox`-komponentilla on menetelmiä, jotka mahdollistavat alasvetoluettelon mittojen muuttamisen. **Maksimi korkeus** ja **minimi leveys** alasvetoluettelolle voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -menetelmiä.

:::tip
Mikäli toiseen näistä menetelmistä annetaan `String`-arvo, voidaan soveltaa [minkä tahansa voimassa olevan CSS-yksikön](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) sääntöjä, kuten pikseleitä, näkymämittoja tai muita voimassa olevia sääntöjä. `int`-arvon välittäminen asettaa arvon pikseleinä.
:::

## Korostaminen {#highlighting}

Työskennellessäsi `ComboBox`:in kanssa voit mukauttaa, milloin teksti korostuu sen mukaan, miten komponentti saa fokuksen. Tämä ominaisuus voi vähentää syöttöhäiriöitä, kun käyttäjät täyttävät lomakkeita, ja parantaa kokonaisvaltaista navigointikokemusta. Muuta korostuskäyttäytymistä käyttämällä `setHighlightOnFocus()` -menetelmää yhdessä sisäänrakennettujen `HasHighlightOnFocus.Behavior` -enumerointien kanssa:

- `ALL`
Komponentin sisällöt korostuvat aina automaattisesti, kun komponentti saa fokuksen.
- `FOCUS`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen ohjelmallisesti.
- `FOCUS_OR_KEY`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen ohjelmallisesti tai kun siihen siirrytään sormella.
- `FOCUS_OR_MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen ohjelmallisesti tai kun siihen klikataan hiirellä.
- `KEY`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen siirtymällä sormella.
- `KEY_MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen sekä sormella siirtymällä että hiirellä klikattaessa.
- `MOUSE`
Komponentin sisällöt korostuvat automaattisesti, kun komponentti saa fokuksen hiirellä klikattaessa.
- `NONE`
Komponentin sisältöjä ei koskaan automaattisesti korosteta, kun komponentti saa fokuksen.

:::note
Jos sisältö oli korostettu fokuksen menessä, se korostuu jälleen, kun fokus palautuu, riippumatta asetetusta käyttäytymisestä.
:::

## Edellä ja perässä {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja `ComboBox`:in kykyjen parantamiseksi. Voit lisätä ikoneita, etikettejä, latauspyöriä, tyhjennys/nollausmahdollisuuksia, avatar/käyttäjäkuvia ja muita hyödyllisiä komponentteja `ComboBox`:in sisälle selventämään käyttäjille tarkoitetun merkityksen. `ComboBox`:issa on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä eri komponenttien sijoittamiseksi ennen ja jälkeen vaihtoehtojen `ComboBox`:issa.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ComboBox" />

## Parhaat käytännöt {#best-practices}

Jotta varmistetaan optimaalinen käyttäjäkokemus käyttäessä `ComboBox`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

1. **Yhteisten arvojen esilataaminen**: Jos on yhteisiä tai usein käytettyjä kohteita, esilataa ne `ComboBox`-luetteloon. Tämä nopeuttaa valintaa yleisesti valituista kohteista ja kannustaa johdonmukaisuuteen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon näytettävät etiketit ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät ymmärtävät helposti kunkin valinnan tarkoituksen.

3. **Validointi**: Toteuta syötevalidointi käsitelläksesi mukautettuja syötteitä. Tarkista tietojen tarkkuus ja johdonmukaisuus. Voit haluta ehdottaa korjauksia tai vahvistuksia epäselville syötteille.

4. **Oletusvalinta**: Aseta oletusvalinta, erityisesti jos on yleinen tai suositeltava vaihtoehto. Tämä parantaa käyttäjäkokemusta vähentäen ylimääräisten klikkausten tarvetta.

5. **ComboBox vs. muut listakomponentit**: `ComboBox` on paras valinta, jos tarvitset käyttäjältä yksittäisen syötteen ja haluat tarjota heille ennalta määrättyjä valintoja sekä mahdollisuuden mukauttaa syötteensä. Toinen listakomponentti saattaa olla parempi, jos tarvitset seuraavia käyttäytymisiä:
    - Usean valinnan ja kaikkien kohteiden näyttäminen kerralla: [ListBox](./list-box.md)
    - Estää mukautettu syöttö: [ChoiceBox](./choice-box.md)
