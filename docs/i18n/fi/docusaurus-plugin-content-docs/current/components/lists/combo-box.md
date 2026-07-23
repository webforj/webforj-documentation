---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

`ComboBox` -komponentti yhdistää pudotuslistan ja tekstikentän, joten käyttäjät voivat valita joko valmiista vaihtoehdoista tai kirjoittaa mukautetun arvon. Kun mukautetut merkinnät on sallittava yhdessä ehdotettujen vaihtoehtojen kanssa, se täyttää aukon, jota `ChoiceBox` ei kata.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

<ParentLink parent="List" />

ComboBox-komponentti on monipuolinen syötteenväline, joka yhdistää pudotuslistan ja tekstikentän ominaisuudet. Se mahdollistaa käyttäjien valita kohteita valmiista luettelosta tai syöttää mukautettuja arvoja tarpeen mukaan. Tässä osiossa tarkastellaan ComboBox-komponentin yleisiä käyttötapoja eri skenaarioissa:

1. **Tuotteen haku ja syöttö**: Käytä ComboBoxia toteuttaaksesi tuotteen hakemisen ja syöttämisen ominaisuuden. Käyttäjät voivat joko valita tuotteen valmiista luettelosta tai kirjoittaa mukautetun tuotteen nimen. Tämä on hyödyllistä sovelluksissa, kuten verkkokaupoissa, joissa tuotteet ovat laajoja ja monipuolisia.

2. **Tägien valinta ja syöttö**: Sisältöjen taggaamiseen liittyvissä sovelluksissa ComboBox voi olla erinomainen valinta. Käyttäjät voivat valita olemassa olevista tageista tai lisätä mukautettuja tageja kirjoittamalla ne. Tämä on hyödyllistä sisällön järjestämisessä ja luokittelussa. Esimerkkejä tällaisista tageista ovat:
    >- Projektitunnisteet: Projektinhallintatyökalussa käyttäjät voivat valita tunnisteita tai tageja (esim. "Kiireellinen," "Käynnissä," "Valmis") luokitellakseen tehtäviä tai projekteja, ja he voivat luoda mukautettuja tunnisteita tarpeen mukaan.
    >- Reseptiaineet: Ruokaa tai reseptejä käsittelevässä sovelluksessa käyttäjät voivat valita aineksia luettelosta (esim. "Tomaatit," "Sipulit," "Kana") tai lisätä omia aineksiaan mukautettuihin resepteihin.
    >- Sijaintitagit: Kartta- tai geotaggingsovelluksessa käyttäjät voivat valita ennalta määritettyjä sijaintitagsia (esim. "Ranta," "Kaupunki," "Puisto") tai luoda mukautettuja tageja merkitäkseen erityisiä paikkoja kartalle.

3. **Tietojen syöttö ehdotetuilla arvoilla**: Tietojen syöttömuodoissa ComboBoxia voidaan käyttää syötteiden nopeuttamiseksi tarjoamalla luettelo ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoja tarkasti ja tehokkaasti.

    :::tip
    `ComboBox`-komponenttia tulisi käyttää, kun käyttäjille sallitaan mukautettujen arvojen syöttäminen. Jos ainoastaan esiasetettuja arvoja halutaan, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
    :::

## Mukautettu arvo {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen mahdollistaa hallinnan siitä, voiko käyttäjä muuttaa arvoa `ComboBox`-komponentin syöttökentässä. Jos `true`, joka on oletusarvo, käyttäjä voi muuttaa arvoa. Jos se asetetaan `false`:ksi, käyttäjä ei voi muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -menetelmää.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Paikkamerkki {#placeholder}

`ComboBox`-komponentille voidaan asettaa paikkamerkki, joka näkyy komponentin tekstikentässä, kun se on tyhjillään, jotta käyttäjät saavat kehotteen toivottuun syötteeseen kentässä. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -menetelmää.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Pudotuslistan tyyppi {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -menetelmää määritetään arvo `type`-attribuutille `ComboBox`-komponentissa ja vastaava arvo `data-dropdown-for` -attribuutille `ComboBox`-komponentin pudotuslistassa. Tämä on hyödyllistä tyylittelyn kannalta, sillä pudotuslista poistetaan nykyisestä sijainnistaan DOM:ista ja siirretään sivun ruumiin loppuun, kun se avataan.

Tämä irrottaminen luo tilanteen, jossa suoran CSS- tai shadow part -valitsimien käyttäminen pudotuslistan kohdistamiseen emokomponentista tulee haastavaksi, ellei käytännössä hyödynnetä pudotuslistan tyyppi -attribuuttia.

Alla olevassa demon kohdassa pudotuslistan tyyppi on asetettu ja sitä käytetään CSS-tiedostossa kasvattamaan vaihtoehtoa, kun hiiri on sen päällä.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Max rivimäärä {#max-row-count}

Oletusarvoisesti pudotuslistassa näkyvien rivien määrä `ComboBox`-komponentissa kasvaa sisällön mukaan. Kuitenkin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -menetelmää voidaan hallita, kuinka monta kohdetta näytetään.

:::caution
Jos käytetään numeroa, joka on pienempi tai yhtäsuuri kuin 0, tämän ominaisuuden käyttö poistuu.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Avaaminen ja sulkeminen {#opening-and-closing}

`ComboBox`-komponentin vaihtoehtojen näkyvyyttä voidaan hallita ohjelmallisesti `open()` ja `close()` -menetelmien avulla. Nämä menetelmät antavat mahdollisuuden näyttää vaihtoehtoluettelo valittavaksi tai piilottaa se tarpeen mukaan, jolloin hallinta `ComboBox`-komponentin käyttäytymiselle on joustavampaa.

Lisäksi webforJ:llä on tapahtumakuuntelijoita, jotka rekisteröityvät siihen, kun `ComboBox` suljetaan, ja kun se avataan, mikä antaa lisää hallintamahdollisuuksia erityisten toimintojen käynnistämiseen.

```Java
//Keskeytä tai avaa seuraava komponentti lomakkeessa
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

## Avaamisen mitat {#opening-dimensions}

`ComboBox`-komponentilla on menetelmiä, jotka mahdollistavat pudotuslistan mittojen muokkaamisen. Pudotuslistan **maksimikeveys** ja **minimileveys** voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -menetelmiä vastaavasti.

:::tip
Antamalla `String`-arvon jompaankumpaan näistä menetelmistä sallitaan [mikä tahansa voimassa oleva CSS-yksikkö](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) sovellettavaksi, kuten pikselit, näkymämitat tai muut voimassa olevat säännöt. `int`-arvon antaminen asettaa annettavan arvon pikseleinä.
:::

## Korostaminen {#highlighting}

Kun työskentelet `ComboBox`-komponentin kanssa, voit mukauttaa, milloin teksti korostuu riippuen siitä, miten komponentti saa fokus. Tämä ominaisuus voi vähentää syöttövirheitä käyttäjien täyttäessä lomakkeita ja voi parantaa yleistä navigointikokemusta. Muuta korostuskäyttäytymistä käyttämällä `setHighlightOnFocus()` -menetelmää yhdisytekunnolla `HasHighlightOnFocus.Behavior` enums:

- `ALL`
Komponentin sisältö korostuu aina automaattisesti, kun komponentti saa fokus.
- `FOCUS`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus ohjelmallisen ohjauksen alaisena.
- `FOCUS_OR_KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus joko ohjelmallisesti tai tabuloimalla siihen.
- `FOCUS_OR_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus joko ohjelmallisesti tai klikkaamalla sitä hiirellä.
- `KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus tabuloimalla siihen.
- `KEY_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus joko tabuloimalla siihen tai klikkaamalla sitä hiirellä.
- `MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokus klikkaamalla siihen hiirellä.
- `NONE`
Komponentin sisältöä ei koskaan automaattisesti korosteta, kun komponentti saa fokus.

:::note
Jos sisältö oli korostettuna menettäessään fokuksen, se korostuu jälleen, kun se saa fokuksen uudelleen, riippumatta asetetusta käyttäytymisestä.
:::

## Etuliite ja jälkiliite {#prefix-and-suffix}

Slots tarjoavat joustavia vaihtoehtoja `ComboBoxin` kyvykkyyksien parantamiseen. Voit lisätä kuvakkeita, etikettejä, latauspyörityksiä, tyhjennys/nollausominaisuuksia, avatar/profiilikuvia ja muita hyödyllisiä komponentteja `ComboBoxiin`, jotta käyttäjät ymmärtävät aiotun merkityksen paremmin. `ComboBox`-komponentilla on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen vaihtoehtojen `ComboBoxissa`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

<TableBuilder name="ComboBox" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ComboBox`-komponenttia käytettäessä, harkitse seuraavia parhaita käytäntöjä:

1. **Yleisimpien arvojen esilataaminen**: Jos on yleisiä tai usein käytettyjä kohteita, lataa ne etukäteen `ComboBox`-listaan. Tämä nopeuttaa valintaa usein valituista kohteista ja kannustaa johdonmukaisuuteen.

2. **Käyttäjäystävälliset etiketit**: Varmista, että jokaisen vaihtoehdon näytetyt etiketit ovat käyttäjäystävällisiä ja itsestään selviä. Varmista, että käyttäjät voivat helposti ymmärtää jokaisen valinnan tarkoituksen.

3. **Validointi**: Toteuta syötevalidointi mukautettujen merkintöjen käsittelemiseksi. Tarkista tietojen tarkkuus ja johdonmukaisuus. Saatat haluta ehdottaa korjauksia tai vahvistuksia epäselville merkinnöille.

4. **Oletusvalinta**: Aseta oletusvalinta, erityisesti jos on yleinen tai suositeltu vaihtoehto. Tämä parantaa käyttäjäkokemusta vähentämällä ylimääräisten napsautusten tarvetta.

5. **ComboBox vs. muut luettelokomponentit**: `ComboBox` on paras valinta, kun tarvitset käyttäjältä yksittäisen syötteen ja haluat tarjota heille ennalta määritettyjä vaihtoehtoja sekä mahdollisuuden mukauttaa syötteet. Toinen luettelokomponentti voi olla parempi, jos tarvitset seuraavia toimintoja:
    - Monivalinta ja kaikkien kohteiden näyttäminen kerrallaan: [ListBox](./list-box.md)
    - Mukautetun syötteen estäminen: [ChoiceBox](./choice-box.md)
