---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti järjestää kohteet responsiiviseen sarakepohjaiseen asetteluun, joka mukautuu käytettävissä olevaan leveyteen. Murtopisteet ja kohdistukset hallitaan automaattisesti, joten monisarakkeisten lomakkeiden ja sisältöruudukkojen rakentaminen ei vaadi erikoistoimintoja.

<!-- INTRO_END -->

## Oletuskäytös {#default-behavior}

Oletuksena `ColumnsLayout` järjestää kohteet kahteen sarakkeeseen ja vie koko sen vanhemman leveyden. Näyttöä voidaan säätää edelleen murtopisteiden ja kohdistusasetusten avulla, joita käsitellään alla olevissa osioissa.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Vaakasuorat asettelut 
Tätä voidaan käyttää sijastaan tai yhdessä [`FlexLayout`](./flex-layout) -komponentin kanssa, joka on yhtä voimakas työkalu vaakasuoria asetteluja varten.
:::

## Murtopisteet {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan joustava, ruudukon kaltaista järjestelmää, joka mukautuu sen vanhemman säiliön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu sallii kehittäjien määritellä sarakkeiden määrän tietyllä leveydellä ja laskee automaattisesti näytettävien sarakkeiden määrän määriteltyjen `Breakpoint`-objektien perusteella.

Tämä sallii `ColumnsLayout`-asettelun siirtyä joustavasti pieneen tilaan pienillä näytöillä laajemmalle alueelle suuremmilla näytöillä, tarjoten kehittäjille responsiivisen suunnittelun ilman tarvetta mukautetulle toteutukselle.

### Murtopisteen ymmärtäminen {#understanding-a-breakpoint}

Murtopiste määritellään käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Murtopisteen nimeäminen sallii sen viittaamisen tulevissa määrityksissä.

2. **Minimi leveys**:
Jokaisella murtopisteellä on tietty alue, joka määrää, milloin sen asettelu otetaan käyttöön. Minimileveys määritellään erikseen, ja seuraava murtopiste määrittää maksimi leveyden, jos se on olemassa. Voit käyttää kokonaislukua määrittämään minimi leveyden pikseleinä tai käyttää `String`-tyyppiä muiden yksiköiden, kuten `vw`, `%` tai `em`, määrittämiseen.

3. **Sarakkeiden määrä**:
Määrittele, kuinka monta saraketta murtopisteellä tulisi olla tämän kokonaisluvun avulla.

:::info `Breakpoint`-arviointi
Murtopisteet arvioidaan nousevassa kasvujärjestyksessä leveyden mukaan, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa murtopistettä.
:::

### Murtopisteiden soveltaminen {#applying-breakpoints}

Murtopisteitä sovelletaan `ColumnsLayout`-komponenttiin joko rakentamisen aikana tai `List`-listassa käyttämällä `setBreakpoints()`-menetelmää:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Yksi sarake leveydellä >= 0px
  new Breakpoint(0, 1),
  // Kaksi saraketta leveydellä >= 600px
  new Breakpoint(600, 2),
  // Neljä saraketta leveydellä >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

Alla olevassa esimerkissä on asetettu useita murtopisteitä rakentamisen aikana, käytetään murtopisteitä määrittämään komponentin [`Span`](#column-span-and-spans-per-breakpoint) ja osoitetaan `ColumnsLayout`-asettelun koon muuttamismahdollisuuksia, kun sovellus koonmuutoksija:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Sarakkeen `Span` ja sarakkeet murtopisteen mukaan {#column-span-and-spans-per-breakpoint}

Sarakkeiden suuret määrät `ColumnsLayout`-komponentissa sallii sinun hallita, kuinka monta saraketta kohde vie, jolloin voit paremmin hallita asettelusi ulkoasua vaihtelevissa leveydissä. Tämä on erityisen hyödyllistä, kun tarvitset tiettyjen elementtien vievän enemmän tai vähemmän tilaa näytön koosta tai suunnitteluvaatimuksista riippuen.

### Sarakkeiden hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`-komponentissa vie tarkalleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä asettamalla sarakkeen suureen yksittäisille kohteille. Suure määrittää, kuinka monta saraketta kohteen tulisi kattaa.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde kattaa kaksi saraketta
layout.setSpan(button, 2);
```

Edellisessä esimerkissä painike kattaa kaksi saraketta oletuksena yhden sijaan. `setSpan()`-menetelmällä voit määrittää, kuinka monta saraketta komponentin tulisi kattaa asettelussa.

### Sarakkeiden suurten säätäminen murtopisteiden mukaan {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakkeiden suuria määrää dynaamisesti murtopisteiden perusteella. Tämä ominaisuus on hyödyllinen, kun haluat kohteen kattavan eri sarakkeiden määriä näytön koon mukaan. Esimerkiksi saatat haluta elementin vievän yhden sarakkeen mobiililaitteilla mutta kattavan useita sarakkeita suuremmilla näytöillä.

```java
TextField email = new TextField("Sähköposti");
//...
List.of(
  new ColumnsLayout.Breakpoint("oletus", 0 , 1),
  new ColumnsLayout.Breakpoint("pieni", "20em", 1),
  new ColumnsLayout.Breakpoint("keskikokoinen", "40em", 2),
  new ColumnsLayout.Breakpoint("suuri", "60em", 3)
)
//...
//sähköpostikenttä kattaa kaksi saraketta kun keskikokoiset murtopisteet ovat aktiivisia
columnsLayout.setSpan(email, "medium", 2);
//...
```

Tämä mukautusaste varmistaa, että asettelusi pysyy responsiivisena ja oikein rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, tarjoten enemmän hallintaa elementtien järjestämisessä. Voit manuaalisesti määrittää, mihin kohtaan kohde tulisi sijoittaa asettelussa varmistaen, että tärkeät komponentit näkyvät halutulla tavalla.

### Perussarakkeen sijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkan sarakkeen, johon kohde tulisi sijoittaa. Sijoittaaksesi kohteen tiettyyn sarakkeeseen, käytä `setColumn()`-menetelmää. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen riippumatta siitä, missä järjestyksessä se on lisätty suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoituksen säätäminen murtopisteen mukaan {#adjusting-placement-per-breakpoint}

Juuri kuten sarakkeiden suurilla, voit käyttää murtopisteitä säätääksesi kohteiden sijoittamista näytön koon mukaan. Tämä on hyödyllistä elementtien järjestyksen muuttamiseen tai paikoiltaan siirtämiseen asettelussa, kun näkymä muuttuu.

```java
TextField email = new TextField("Sähköposti");
//...
List.of(
  new ColumnsLayout.Breakpoint("oletus", 0 , 1),
  new ColumnsLayout.Breakpoint("pieni", "20em", 1),
  new ColumnsLayout.Breakpoint("keskikokoinen", "40em", 2),
  new ColumnsLayout.Breakpoint("suuri", "60em", 3)
)
//...
//sähköpostikenttä näkyy toisessa sarakkeessa, kun keskikokoiset murtopisteet ovat aktiivisia
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Seuraavassa esimerkissä huomaa, että kun `"medium"` murtopiste aktivoituu, `email`-kenttä kattaa molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen sen sijaan, että se olisi oletuksena sijoitettu toiseen sarakkeeseen:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan asetteluun eri suurilla ja/tai sarakeasetuksilla, varmista, että kohteiden yhdistetyt suuret ja sijoitukset rivillä eivät mene päällekkäin. Asettelu yrittää automaattisesti hallita tilaa kauniisti, mutta huolellinen suunnittelu suuriin ja murtopisteisiin estää ei-toivottua esitystä.
:::

## Vaakasuorat ja pystysuorat kohdistukset {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`-komponentissa voidaan kohdistaa sekä vaakasuunnassa että pystysuunnassa sen sarakkeessa, antamalla hallintaa sen yli, miten sisältö sijoitetaan asettelussa.

**Vaakasuora kohdistus** kohteelle hallitaan käyttämällä `setHorizontalAlignment()`-menetelmää. Tämä ominaisuus määrittää, miten kohde kohdistuu sen sarakkeessa vaakasuoralla akselilla.

**Pystysuora kohdistus** määrittää, miten kohde sijoitetaan sen sarakkeessa pystysuoralla akselilla. Tämä on hyödyllistä, kun sarakkeilla on vaihtelevia korkeuksia ja haluat hallita, miten kohteet jakautuvat pystysuunnassa.

Saatavilla olevat `Alignment`-vaihtoehdot sisältävät:

- `START`: Kohdistaa kohteen sarakkeen vasempaan reunaan (oletus).
- `CENTER`: Keskittyy kohde vaakasuunnassa sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikeaan reunaan.
- `STRETCH`: Venyttää komponenttia täyttämään asettelu.
- `BASELINE`: Kohdistuu sarakkeessa tekstin tai sisällön perusteella, mikä tarkoittaa, että kohteet kohdistuvat tekstin peruslinjaan muihin kohdistusvaihtoehtoihin verrattuna.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

Yllä olevassa esimerkissä `Lähetä`-painikkeelle on annettu `ColumnsLayout.Alignment.END` varmistaakseen, että se näkyy sen sarakkeen lopussa tai tässä tapauksessa sarakkeen oikeassa reunassa.

## Kohteiden väli {#item-spacing}

Hallitsemalla sarakkeiden väliä `ColumnsLayout`-komponentissa (vaakasuora väli) ja rivien väliä (pystysuora väli) auttaa kehittäjiä hienosäätämään asettelua.

Aseta asettelun vaakasuora väli käyttämällä `setHorizontalSpacing()`-menetelmää:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Samoin käytä `setVerticalSpacing()`-menetelmää määrittämään asettelun rivien väli:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittämään minimi leveyden pikseleinä tai käyttää `String`-tietotyyppiä muiden yksiköiden määrittämiseen, kuten `vw`, `%` tai `em`.
:::

## Vaakasuorat ja pystysuorat asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asettelujen rakentaminen on mahdollista käyttämällä sekä [`FlexLayout`](./flex-layout) -komponenttia että `ColumnsLayout` -komponenttia, mukaan lukien yhdistelmä näistä kahdesta. Alla on esimerkki [lomakkeesta, joka on luotu FlexLayout](./flex-layout#example-form) -artikkelissa, mutta käytetään `ColumnLayout`-mallia sen sijaan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Tyylit {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
