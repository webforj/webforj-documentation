---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: a1c9e9a41325f2f1ffb75fd07204106a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti asettaa elementtejä responsiiviseen sarakepohjaiseen asetteluun, joka mukautuu käytettävissä olevan leveyden mukaan. Murtopisteet ja kohdistukset hallitaan automaattisesti, joten monisarakkeisten lomakkeiden ja sisältöruudukkojen rakentaminen ei vaadi räätälöityä responsiivista logiikkaa.

<!-- INTRO_END -->

## Oletuskäytös {#default-behavior}

Oletusarvoisesti `ColumnsLayout` asettaa elementit kahteen sarakkeeseen ja vie koko vanhempansa leveyden. Näyttöä voidaan edelleen säätää murtopisteiden ja kohdistusasetusten avulla, jotka käsitellään jäljempänä.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Vaakasuorat asettelut 
Tätä voidaan käyttää vaihtoehtona tai yhdessä [`FlexLayout`](./flex-layout) -komponentin kanssa - yhtä voimakas työkalu vaaka-asetteluja varten.
:::

## Murtopisteet {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan sulava, ruudukkomuotoinen järjestelmä, joka mukautuu vanhempansa säilytykseen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu sallii kehittäjien määritellä tietyn määrän sarakkeita tietyssä leveydessä, ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien perusteella.

Tämä mahdollistaa `ColumnsLayout`:n sujuvan mukautumisen pieniä näyttöjä varten rajoitettuun tilaan laajemmalle alueelle suuremmilla näytöillä, tarjoten kehittäjille responsiivisen suunnittelun ilman räätälöityä toteutusta.

### Ymmärtäminen `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Murtopisteen nimeäminen mahdollistaa sen viittaamisen tulevissa asetuksissa.

2. **Minimi leveys**:
Jokaisella murtopisteellä on tietty alue, joka määrittelee, milloin sen asettelu otetaan käyttöön. Minimileveys määritellään selvästi, ja seuraava murtopiste määrittää maksimileveyden, jos sellainen on. Voit käyttää kokonaislukua määrittämään minimileveyden pikseleinä tai käyttää `String`-muotoa, jotta voit määrittää muita yksiköitä, kuten `vw`, `%` tai `em`.

3. **Sarakkeiden määrä**:
Määritä, kuinka monta saraketta murtopisteellä pitäisi olla tämän kokonaisluvun avulla.

:::info `Breakpoint`-arviointi
Murtopisteet arvioidaan leveysjärjestyksessä nousevasti, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa murtopistettä.
:::

### Murtopisteiden soveltaminen {#applying-breakpoints}

Murtopisteitä sovelletaan `ColumnsLayout`-asetteluun kahdella tavalla: rakentamisen aikana tai `List`-luettelossa käyttäen `setBreakpoints()`-metodia: 

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Yksi sarake, kun leveys >= 0px
  new Breakpoint(0, 1),
  // Kaksi saraketta, kun leveys >= 600px
  new Breakpoint(600, 2),
  // Neljä saraketta, kun leveys >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

Alla oleva esimerkki näyttää esimerkin useiden murtopisteiden asettamisesta rakentamisen aikana, murtopisteiden käyttäminen [`Span`](#column-span-and-spans-per-breakpoint) komponentin määrittämiseksi ja havainnollistaa `ColumnsLayout`:n uudelleensäädön mahdollisuuksia sovelluksen koon muuttuessa:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Sarakkeen `Span` ja spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Sarakkeiden osumat `ColumnsLayout`-asettelussa sallivat sinun hallita, kuinka monta saraketta kohde vie, mikä antaa enemmän kontrollia asettelun ulkoasusta eri leveyksillä. Tämä on erityisen hyödyllistä, kun tietyt elementit tarvitsevat enemmän tai vähemmän tilaa näytön koosta tai suunnittelutarpeista riippuen.

### Sarakespanien hallinta {#managing-column-spans}

Oletusarvoisesti jokainen kohde `ColumnsLayout`:issa vie tarkalleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä määrittämällä yksittäisten kohteiden sarake spanin. Span määrittää, kuinka monta saraketta kohde tulisi täyttää.

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// Kohde kattaa kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä painike kattaa kaksi saraketta oletusarvoisen yhden sijaan. `setSpan()`-metodi sallii sinulle määrittää, kuinka monta saraketta komponentin tulisi kattaa asettelussa.

### Sarakespanien säätäminen murtopisteiden avulla {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakespansosia dynaamisesti murtopisteiden perusteella. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde kattaa erilaisia sarake määriä näytön koon mukaan. Esimerkiksi saatat haluta, että jokin elementti vie vain yhden sarakkeen mobiililaitteilla, mutta kattaa useita sarakkeita suuremmilla näytöillä.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email-kenttä kattaa kaksi saraketta, kun keskipiste on aktiivinen
columnsLayout.setSpan(email, "medium", 2);
//...
```

Tämä mukautustaso varmistaa, että asettelusi pysyy responsiivisena ja asianmukaisesti rakennettuna eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, mikä antaa enemmän kontrollia elementtien järjestelyyn. Voit manuaalisesti määrittää, minne kohteen tulisi ilmestyä asettelussa, varmistaen, että tärkeät komponentit näytetään suunnitellusti.

### Perussaraket sijoittaminen {#basic-column-placement}

Oletusarvoisesti kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin korvata tämän käyttäytymisen ja määrittää tarkalleen, mihin sarakkeeseen kohteen tulisi sijoittua. Kohteen sijoittamiseksi tiettyyn sarakkeeseen käytä `setColumn()`-metodia. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen riippumatta siitä, missä järjestyksessä se lisättiin suhteessa muihin komponentteihin:

```java
Button button = new Button("Submit");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoituksen säätäminen murtopisteiden mukaan {#adjusting-placement-per-breakpoint}

Aivan kuten sarakespanien kanssa, voit käyttää murtopisteitä kohteiden sijoituksen säätämiseen näytön koon perusteella. Tämä on hyödyllistä, kun haluat muuttaa tai siirtää elementtejä asettelussa, kun näkymä muuttuu.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email-kenttä ilmestyy toiseen sarakkeeseen, kun keskipiste on aktiivinen
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Seuraavassa esimerkissä huomaa, että kun `"medium"`-murtopiste laukeaa, `email`-kenttä kattaa molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen sen sijaan, että se olisi oletussijoituksessa toisessa sarakkeessa:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan asetteluun, jossa on erikokoisia ja/tai sarakejärjestelyjä, varmista, että pystysuunnassa kohteiden yhteispituudet eivät mene päällekkäin. Asettelu yrittää hallita väliä automaattisesti, mutta huolellinen suunnittelu spaneista ja murtopisteistä estää odottamattoman elementtien näyttämisen.
:::

## Pystysuora ja vaakasuora kohde-kohdistus {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`:issa voidaan kohdistaa sekä vaaka- että pystysuunnassa sarakkeensa sisällä, antaen kontrollin siitä, kuinka sisältö asemoituu asettelun sisällä.

**Vaakasuuntainen kohdistus** kohteelle hallitaan käyttämällä `setHorizontalAlignment()`-metodia. Tämä ominaisuus määrittää, kuinka kohde kohdistuu sarakkeensa sisällä vaakasuoran akselin varrella.

**Pystysuuntainen kohdistus** määrittää, kuinka kohde sijoitetaan sarakkeensa sisällä pystysuoran akselin varrella. Tämä on hyödyllistä, kun sarakkeilla on vaihteleva korkeus ja haluat hallita, kuinka kohteet jakautuvat pystysuunnassa. 

Saatavilla olevat `Alignment` -vaihtoehdot ovat:

- `START`: Kohdistaa kohteen sarakkeen vasemmalle puolelle (oletusarvo).
- `CENTER`: Keskittää kohteen vaakasuoraan sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikealle puolelle.
- `STRETCH`: Venyttää komponentin täyttämään asettelu.
- `BASELINE`: Kohdistaa tekstin tai sisällön perusteella sarakkeessa, kohdistaa kohteet tekstin perustan mukaan jotenkin muiden kohdistusvaihtoehtojen mukaan.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Yllä olevassa demossa `Submit`-painikkeelle on annettu `ColumnsLayout.Alignment.END`, jotta se näkyy sarakkeen lopussa, tai tässä tapauksessa sen oikealla puolella.

## Kohteiden väli {#item-spacing}

Sarakkeiden välin hallinta `ColumnsLayout`-asettelussa (vaakasuora väli) ja rivien (pystysuora väli) välillä auttaa kehittäjiä hienosäätämään asettelua.

Asettaaksesi asettelun vaakasuoran välin, käytä `setHorizontalSpacing()`-metodia:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Samoin käytä `setVerticalSpacing()`-metodia konfiguroidaksesi tilan rivien välille asettelussa:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittämään minimileveyden pikseleinä tai `String`-muotoa, jotta voit määrittää muita yksiköitä, kuten `vw`, `%` tai `em`.
:::

## Vaakasuorat ja pystysuorat asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asettelujen rakentaminen on mahdollista käyttäen sekä [`FlexLayout`](./flex-layout) -komponenttia että `ColumnsLayout` -komponenttia sekä näiden yhdistelmää. Alla on esimerkki [FlexLayoutissa luodusta lomakkeesta](./flex-layout#example-form), mutta käyttämällä `ColumnLayout`-kaaviota sen sijaan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Tyylittäminen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
