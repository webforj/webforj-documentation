---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: d0112ef19b8ef7b0b2621af5c500a6c9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="List" />

`ComboBox`-komponentti on käyttöliittymäelementti, joka on suunniteltu esittämään käyttäjille lista vaihtoehdoista tai valinnoista, sekä kenttä omien mukautettujen arvojensa syöttämistä varten. Käyttäjät voivat valita yhden vaihtoehdon tästä listasta, tyypillisesti napsauttamalla `ComboBox`-elementtiä, mikä aktivoi pudotuslistan, joka sisältää käytettävissä olevat valinnat tai kirjoittamalla mukautetun arvon. Käyttäjät voivat myös käyttää nuolinäppäimiä `ComboBox`-elementin kanssa. Kun käyttäjä tekee valinnan, valittu vaihtoehto näytetään sitten `ComboBox`-elementissä.

## Usages {#usages}

ComboBox-komponentti on monipuolinen syöteelementti, joka yhdistää pudotuslistan ja tekstikentän ominaisuudet. Se sallii käyttäjien valita kohteita ennalta määritetystä listasta tai syöttää mukautettuja arvoja tarpeen mukaan. Tämä osio tutkii ComboBox-komponentin yleisiä käyttötapoja eri tilanteissa:

1. **Tuotteen Haku ja Syöttö**: Käytä ComboBoxia tuotteen hakemis- ja syöttötoiminnon toteuttamiseen. Käyttäjät voivat joko valita tuotteen ennalta määritetystä listasta tai kirjoittaa mukautetun tuotteen nimen. Tämä on hyödyllistä sovelluksille, kuten verkkokauppasivustoille, joissa tuotteet ovat laajoja ja monipuolisia.

2. **Tunnisteiden Valinta ja Syöttö**: Sovelluksissa, jotka liittyvät sisällön merkintöihin, ComboBox voi toimia erinomaisena valintana. Käyttäjät voivat valita olemassa olevista tunnisteista tai lisätä mukautettuja tunnisteita kirjoittamalla niitä. Tämä on hyödyllistä sisällön järjestämisessä ja luokittelussa. Esimerkkejä tällaisista tunnisteista ovat:
   >- Projektin Tunnisteet: Projektinhallintatyökalussa käyttäjät voivat valita tunnisteita tai merkintöjä (esim. "Aikainen," "Käynnissä," "Valmis") tehtävien tai projektien luokittelemiseksi, ja he voivat luoda mukautettuja tunnisteita tarpeen mukaan.
   >- Reseptin Ainekset: Ruokaa tai reseptiä käsittelevässä sovelluksessa käyttäjät voivat valita ainesosia listasta (esim. "Tomaatit," "Sipulit," "Kana") tai lisätä omia ainesosia mukautetuille resepteille.
   >- Paikkatunnisteet: Kartta- tai geotunnistussovelluksessa käyttäjät voivat valita ennalta määritettyjä paikkatunnisteita (esim. "Ranta," "Kaupunki," "Puisto") tai luoda mukautettuja tunnisteita merkitäkseen erityisiä paikkoja kartalla.

3. **Tietojen Syöttö Ehdotetuilla Arvoilla**: Tietojen syöttölomakkeissa ComboBoxia voidaan käyttää syötteen nopeuttamiseksi tarjoamalla lista ehdotetuista arvoista käyttäjän syötteen perusteella. Tämä auttaa käyttäjiä syöttämään tietoja tarkasti ja tehokkaasti.

   :::tip
   `ComboBox`-elementtiä tulee käyttää silloin, kun käyttäjille sallitaan mukautettujen arvojen syöttäminen. Jos vain esiasetettuja arvoja halutaan, käytä [`ChoiceBox`](./choice-box.md) -komponenttia sen sijaan.
   :::

## Custom value {#custom-value}

Mukautetun arvon ominaisuuden muuttaminen sallii hallinnan siitä, saako käyttäjä muuttaa arvoa `ComboBox`-komponentin syöttökentässä. Jos arvo on `true`, mikä on oletusarvo, käyttäjä voi muuttaa arvon. Jos se asetetaan `false`, käyttäjä ei voi muuttaa arvoa. Tämä voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> -metodia.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Placeholder {#placeholder}

`ComboBox`-elementille voidaan asettaa oletusviesti, joka näkyy komponentin tekstikentässä, kun se on tyhjät, ohjeena käyttäjille kenttään syötettävästä toivottavasta arvosta. Tämä voidaan tehdä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> -metodia.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Dropdown type {#dropdown-type}

Käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> -metodia voidaan määrittää arvo `type`-attribuutille `ComboBox`:ssa, ja vastaava arvo `data-dropdown-for` attribuutille ComboBoxin pudotuslistassa. Tämä on hyödyllistä tyylittelyssä, sillä pudotuslista siirretään sen nykyisestä sijainnistaan DOM:ssa sivun rungon loppuun avattaessa.

Tämä irrottaminen luo tilanteen, jossa pudotuslistaan kohdistaminen suoraan CSS- tai varjo-osien valitsimilla vanhemmasta komponentista on haastavaa, ellei käytetä pudotuslista tyyppiä.

Alla olevassa demossa pudotuslistan tyyppi asetetaan ja käytetään CSS-tiedostossa pudotuslistan valitsemiseksi ja taustavärin muuttamiseksi.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Max row count {#max-row-count}

Oletuksena pudotuslistassa `ComboBox`-elementissä näytettävien rivien määrä kasvattaa sisällön mukaan. Kuitenkin, käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> -metodia voidaan säädellä, kuinka monta kohdetta näytetään.

:::caution
Numeron asettaminen, joka on pienempi tai yhtä suuri kuin 0, poistaa tämän ominaisuuden.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Opening and closing {#opening-and-closing}

`ComboBox`-elementin vaihtoehtojen näkyvyyttä voidaan ohjelmallisesti hallita `open()` ja `close()` -metodilla. Nämä metodit antavat mahdollisuuden näyttää valintalista vaihtoehdoista tai piilottaa se tarpeen mukaan, mikä tarjoaa enemmän joustavuutta `ComboBox`-elementin käyttäytymisen hallintaan.

Lisäksi webforJ:llä on tapahtumankuuntelijat silloin, kun `ComboBox`-elementti on suljettu ja kun se avataan, mikä antaa enemmän hallintaa tiettyjen toimintojen käynnistämiseen.

```Java
//Kohdistaa tai avaa seuraavan komponentin lomakkeessa
ComboBox yliopisto = new ComboBox("Yliopisto");
ComboBox pääaine = new ComboBox("Pääaine");
Button lähettää = new Button("Lähetä");

//... Lisää listoja yliopistoista ja pääaineista

yliopisto.onClose( e ->{
  pääaine.open();
});

pääaine.onClose( e ->{
  lähettää.focus();
});
```

## Opening dimensions {#opening-dimensions}

`ComboBox`-komponentilla on metodeja pudotuslistan ulottuvuuksien manipulointiin. Pudotuslistan **maksimi korkeus** ja **minimi leveys** voidaan asettaa käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> ja <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> -metodeja vastaavasti.

:::tip
Jos näille metodeille annetaan `String`-arvo, se sallii [kaikkien kelvollisten CSS-yksiköiden](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units) soveltamisen, kuten pikseleitä, näkymän kokoja tai muita kelvollisia sääntöjä. Jos annetaan `int`, arvo asetetaan pikseleinä.
:::

## Highlighting {#highlighting}

Työskennellessäsi `ComboBox`:n kanssa voit räätälöidä, milloin teksti korostuu sen perusteella, miten komponentti saa fokuksen. Tämä ominaisuus voi vähentää syöttövirheitä käyttäjien täydentäessä lomakkeita ja parantaa kokonaisvaltaista navigointikokemusta. Muuta korostuskäyttäytymistä käyttämällä `setHighlightOnFocus()`-metodia yhdellä sisäänrakennetuista `HasHighlightOnFocus.Behavior`-enumista:

- `ALL`
Komponentin sisältö korostuu aina automaattisesti, kun komponentti saa fokuksen.
- `FOCUS`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisessa hallinnassa.
- `FOCUS_OR_KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisessa hallinnassa tai tabuloimalla siihen.
- `FOCUS_OR_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen ohjelmallisessa hallinnassa tai napsauttamalla sitä hiirellä.
- `KEY`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen tabuloimalla siihen.
- `KEY_MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen tabuloimalla siihen tai napsauttamalla sitä hiirellä.
- `MOUSE`
Komponentin sisältö korostuu automaattisesti, kun komponentti saa fokuksen napsauttamalla siihen hiirellä.
- `NONE`
Komponentin sisältöä ei koskaan korosteta automaattisesti, kun komponentti saa fokuksen.

:::note
Jos sisältöä korostettiin, kun se menetti fokuksen, se korostuu jälleen, kun se saa fokuksen takaisin, riippumatta asetetusta käyttäytymisestä.
:::

## Prefix and suffix {#prefix-and-suffix}

Slots tarjoavat joustavia vaihtoehtoja `ComboBox`:n kyvykkyyksien parantamiseksi. Voit liittää kuvakkeita, etikettejä, lataussyklejä, tyhjennys/ resetointikykyjä, avatar/profiilikuvaa ja muita hyödyllisiä komponentteja `ComboBox`:n sisään, jotta käytettävyys olisi selvempää käyttäjille. `ComboBox`:lla on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja liittääksesi erilaisia komponentteja ennen ja jälkeen vaihtoehtojen `ComboBox`:ssa.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styling {#styling}

<TableBuilder name="ComboBox" />

## Best practices {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ComboBox`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

1. **Ennakoi Yleiset Arvot**: Jos on yleisiä tai usein käytettyjä kohteita, lataa ne etukäteen `ComboBox`-listaan. Tämä nopeuttaa valintaa usein valituista kohteista ja edistää johdonmukaisuutta.

2. **Käyttäjäystävälliset Etiketit**: Varmista, että jokaisen vaihtoehdon näkyvät etiketit ovat käyttäjäystävällisiä ja itse selittäviä. Varmista, että käyttäjät ymmärtävät helposti kunkin vaihtoehdon tarkoituksen.

3. **Validointi**: Ota käyttöön syötteen validointi mukautettujen syöttöjen käsittelemiseksi. Tarkista tietojen tarkkuus ja johdonmukaisuus. Saatat haluta ehdottaa korjauksia tai vahvistuksia epäselville syötteille.

4. **Oletusvalinta**: Aseta oletusvalinta, erityisesti jos on yleinen tai suositeltava vaihtoehto. Tämä parantaa käyttäjäkokemusta vähentämällä ylimääräisten napsautusten tarvetta.

5. **ComboBox vs. Muut Listakomponentit**: `ComboBox` on paras valinta, jos tarvitset yhden syötteen käyttäjältä ja haluat tarjota heille ennalta määrättyjä valintoja sekä mahdollisuuden mukauttaa syötteensä. Toinen listakomponentti voi olla parempi, jos tarvitset seuraavat käyttäytymiset:
   - Moninkertainen valinta ja kaikkien kohteiden näyttäminen kerralla: [ListBox](./list-box.md)
   - Estä mukautettu syöttö: [ChoiceBox](./choice-box.md)
