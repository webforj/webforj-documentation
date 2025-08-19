---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: fb5b6ef5a20567d8a86d04c022a0449e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` -komponentti webforJ:ssä mahdollistaa kehittäjille joustavien ja responsiivisten pystylayoutien luomisen. Tämä layout tarjoaa dynaamiset sarakkeet, jotka mukautuvat käytettävissä olevan leveyden mukaan. Tämä komponentti yksinkertaistaa monisarakkeisten layoutien luontia hallitsemalla automaattisesti katkeamispisteet ja tasausasetukset.

:::info Vaakasuorat layoutit
Tätä voidaan käyttää FlexLayout -komponentin kanssa tai sen sijaan - yhtä tehokas työkalu vaakasuuntaisiin layoutteihin.
:::

## Perusteet {#basics}

Kun `ColumnsLayout` on ensimmäisen kerran instanssoitu, se käyttää kahta saraketta näyttääkseen layoutiin lisätyt kohteet. Oletuksena se vie koko laajuuden vanhempien elementtien sisällä ja kasvaa tarvittaessa lisäsisällön mahtumiseksi. Lisättyjen kohteiden näyttöä voidaan hienosäätää edelleen käyttämällä [`Breakpoint`](./columns-layout#breakpoints) ja [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) asetuksia, joita käsitellään yksityiskohtaisesti seuraavissa osioissa.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Katkeamispisteet {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan joustava, ruudukkomallinen järjestelmä, joka mukautuu vanhemman säilön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä layout mahdollistaa kehittäjien määrittää tietyn määrän sarakkeita tietyllä leveydellä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien mukaan.

Tämä mahdollistaa `ColumnsLayout`:in sujuvan mukautumisen kapeammasta tilasta pienillä näytöillä laajemmalle alueelle suuremmilla näytöillä, tarjoten responsiivisen suunnittelun kehittäjälle ilman tarvetta erityiselle toteutukselle.

### Ymmärtäminen `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, johon liittyy kolme parametria:

1. **Nimi (valinnainen)**:
Katkeamispisteen nimeäminen mahdollistaa sen viittaamisen tulevissa asetuksissa.

2. **Minimileveys**:
Jokaisella katkeamispisteellä on tietty alue, joka määrittää, milloin sen layoutia sovelletaan. Minimileveys määritellään tarkasti, ja seuraava katkeamispiste määrittää maksimi leveyden, jos sellainen on olemassa. Voit käyttää kokonaislukua minimoidaksesi leveyden pikseleinä tai käyttää merkkijonoa määrittääksesi muita yksiköitä, kuten `vw`, `%` tai `em`.

3. **Sarakemäärä**:
Määritä, kuinka monta saraketta katkeamispisteessä tulisi olla tämän kokonaisluvun avulla.


:::info `Breakpoint`-arviointi
Katkeamispisteet arvioidaan nousevassa järjestyksessä leveyden mukaan, mikä tarkoittaa, että layout käyttää ensimmäistä vastaavaa katkeamispistettä.
:::


### Katkeamispisteiden soveltaminen {#applying-breakpoints}

Katkeamispisteet voidaan soveltaa `ColumnsLayout`:iin kahdella tavalla: rakentamisen aikana tai käyttämällä `addBreakpoint(Breakpoint)` -menetelmää, kuten alla on esitetty. 

```java
ColumnsLayout layout = new ColumnsLayout()
    // Yksi sarake leveydellä >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Kaksi saraketta leveydellä >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Neljä saraketta leveydellä >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

Alla oleva esimerkki näyttää useiden katkeamispisteiden asettamisen rakentamisen aikana, käyttämällä katkeamispisteitä komponentin [`Span`](#column-span-and-spans-per-breakpoint) määrittelyyn, ja näyttää `ColumnsLayout`:n koon muutoksen, kun sovellusta muutetaan:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Sarakkeen `Span` ja spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Sarakkeiden spaneja `ColumnsLayout`:issa voidaan käyttää hallitsemaan, kuinka monta saraketta kohde vie, antaen sinulle enemmän hallintaa layoutin ulkoasusta eri leveysasioissa. Tämä on erityisen hyödyllistä, kun haluat tiettyjen elementtien vievän enemmän tai vähemmän tilaa näytön koosta tai suunnitteluvaatimuksista riippuen.

### Sarake-spanien hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`:issa vie tarkalleen yhden sarakkeen. Tämä käyttäytyminen voidaan kuitenkin mukauttaa asettamalla yksittäisten kohteiden sarakkeiden spanit. Span määrittää, kuinka monta saraketta kohde tulisi ottaa.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde vie kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä painike vie kaksi saraketta, eikä vain oletuksena yhtä. `setSpan()`-menetelmä antaa sinun määrittää, kuinka monta saraketta komponentin tulisi kattaa layoutissa.

### Sarakkeen spanien säätäminen katkeamispisteiden kanssa {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakkeen spanien dynaamisesti katkeamispisteiden mukaan. Tämä ominaisuus on hyödyllinen, kun haluat kohteen spanin olevan erilaisista sarakkeista riippuvainen näytön koosta. Esimerkiksi saatat haluta elementin vievän yhden sarakkeen mobiililaitteilla, mutta spanavan useita sarakkeita suuremmilla näytöillä.

```java
TextField email = new TextField("Sähköposti");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email-kenttä vie kaksi saraketta, kun keskeinen katkeamispiste on aktiivinen
columnsLayout.setSpan(email, "medium", 2);
//...
```

Tämä mukautustaso varmistaa, että layoutisi pysyy responsiivisena ja rakenteeltaan asianmukaisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, antaen enemmän hallintaa elementtien järjestelyyn. Voit manuaalisesti määrittää, missä kohteen tulisi näkyä layoutissa varmistaen, että tärkeät komponentit näkyvät haluamallasi tavalla.

### Perussarakasijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkalleen, mihin sarakkeeseen kohde tulisi sijoittaa. Kohteen sijoittamiseksi tietyyn sarakkeeseen käytä `setColumn()`-menetelmää. Tässä esimerkissä painike sijoitetaan layoutin toiseen sarakkeeseen, riippumatta siitä, missä järjestyksessä se lisättiin suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoittamisen säätäminen katkeamispisteiden mukaan {#adjusting-placement-per-breakpoint}

Aivan kuten sarakkeen spanien kanssa, voit käyttää katkeamispisteitä säätämään kohteiden sijoittelua näytön koon mukaan. Tämä on hyödyllistä elementtien järjestyksen tai paikkaamisen muuttamiseksi layoutissa näkymän muuttuessa.

```java
TextField email = new TextField("Sähköposti");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//email-kenttä näkyy toisessa sarakkeessa, kun keskeinen katkeamispiste on aktiivinen
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Seuraavassa demonstraatiossa huomaa, että kun `"medium"` katkeamispiste aktivoituu, `email`-kenttä vie molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen, eikä sen oletuspaikkaan toiseen sarakkeeseen:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan layoutiin erilaisilla spanilla ja/tai sarakeasetuksilla, varmista, että kohteiden yhteenlasketut spanit ja sijoitukset rivillä eivät päällekkäin. Layout yrittää hallita välistä tilaa automaattisesti, mutta huolellinen suunnittelu spaneista ja katkeamispisteistä estää tahattoman kohteiden näyttämisen.
:::

## Vertikaaliset ja horisontaaliset kohteen tasaus {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`:issa voidaan tasaamaan sekä vaakasuunnassa että pystysuunnassa sarakkeensa sisällä, mikä antaa hallinnan siitä, miten sisältö sijoitetaan layoutin sisällä.

**Vaakasuuntainen tasaus** kohteelle hallitaan käyttämällä `setHorizontalAlignment()`-menetelmää. Tämä ominaisuus määrää, miten kohde on kohdistettu sarakkeensa sisällä vaakasuoralla akselilla.

**Pystysuuntainen tasaus** määrittää, kuinka kohde sijoitetaan sarakkeensa sisällä pystysuoralla akselilla. Tämä on hyödyllistä, kun sarakkeilla on vaihteleva korkeus ja haluat hallita, miten kohteet jakautuvat pystysuunnassa. 

Saatavilla olevat `Alignment`-vaihtoehdot sisältävät:

- `START`: Kohdistaa kohteen sarakkeen vasempaan laitaan (oletus).
- `CENTER`: Keskittää kohteen vaakasuunnassa sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikeaan laitaan.
- `STRETCH`: Venyttää komponentin täyttämään layoutin.
- `BASELINE`: Kohdistaa perustuen sarakkeessa olevaan tekstiin tai sisältöön, kohdistamalla kohteet tekstin alalaitaan muista tasausvaihtoehdoista poiketen.
- `AUTO`: Automaattinen tasaus.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Yllä olevassa esittelyssä `Lähetä`-painikkeelle on annettu `ColumnsLayout.Alignment.END`, jotta se näkyy sarakkeensa päässä, tai tässä tapauksessa oikealla.

## Kohteiden väli {#item-spacing}

Sarakkeiden välin hallinta `ColumnsLayout`:issa sarakkeiden (vaakaväli) ja rivien (pystyvli) välillä auttaa kehittäjiä hienosäätämään layoutia.

Aseta layoutin vaakaväli käyttämällä `setHorizontalSpacing()`-menetelmää:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Samaan tapaan käytä `setVerticalSpacing()`-menetelmää määrittääksesi välin rivien välillä layoutissa:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittämään minimileveyden pikseleinä tai käyttää merkkijonoa määrittääksesi muita yksiköitä, kuten `vw`, `%` tai `em`.
:::

## Vaakasuorat ja pystysuorat layoutit {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien layoutien rakentaminen on mahdollista sekä [`FlexLayout`](./flex-layout) -komponentilla että `ColumnsLayout` -komponentilla, sekä näiden yhdistelmällä. Alla on esimerkki [FlexLayout](./flex-layout#example-form) artikkelissa luodusta lomakkeesta, mutta käyttäen `ColumnLayout`-järjestelmää:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Tyylittely {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
