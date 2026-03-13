---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: f4d9229ae204894cda7263a6dc09ba0c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti järjestää kohteet responsiiviseen sarakkeeseen perustuvaan asetteluun, joka mukautuu saatavilla olevan leveyden mukaan. Murtopisteet ja kohdistukset hallitaan automaattisesti, joten monisarakkeisten lomakkeiden ja sisältöruutujen rakentaminen ei vaadi räätälöityä responsiivista logiikkaa.

<!-- INTRO_END -->

## Oletuskäytös {#default-behavior}

Oletuksena `ColumnsLayout` järjestää kohteet kahteen sarakkeeseen ja vie koko vanhemman leveyden. Näyttöä voidaan edelleen säätää murtopisteiden ja kohdistusasetusten avulla, joita käsitellään tarkemmin alla.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Horisontaaliset asettelut 
Tätä voidaan käyttää FlexLayout-komponentin kanssa tai sen sijaan - yhtä tehokas työkalu horisontaalisiin asetteluisiin.
:::

## Murtopisteet {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan nesteinen, ruudukkomainen järjestelmä, joka mukautuu vanhemman säiliön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu antaa kehittäjille mahdollisuuden määrittää sarakkeiden määrä tietyllä leveydellä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objekvien perusteella. 

Tämä antaa `ColumnsLayout`:n sujuvasti mukautua kapeammasta tilasta pienillä näytöillä laajemmalle alueelle suuremmilla näytöillä, tarjoten kehittäjälle responsiivisen suunnittelun ilman erillistä toteutusta.

### Ymmärtäminen `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Murtopisteen nimeäminen mahdollistaa sen viittaamisen tulevissa asetuksissa.

2. **Vähimmäisleveys**:
Jokaisella murtopisteellä on erityinen alue, joka määrittää, milloin sen asettelu otetaan käyttöön. Vähimmäisleveys määritellään nimenomaisesti, ja seuraava murtopiste määrää enimmäisleveyden, jos sellainen on. Voit käyttää kokonaislukua määrittääksesi vähimmäisleveyden pikseleinä tai käyttää `String`-arvoa määrittääksesi muita yksiköitä, kuten `vw`, `%`, tai `em`.

3. **Sarakkeiden määrä**:
Määritä, kuinka monta saraketta murtopisteellä pitäisi olla tämän kokonaisluvun avulla.

:::info `Breakpoint`-arviointi
Murtopisteet arvioidaan nousevassa järjestyksessä leveyden mukaan, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa murtopistettä.
:::

### Murtopisteiden soveltaminen {#applying-breakpoints}

Murtopisteet sovelletaan `ColumnsLayout`-komponenttiin kahdella tavalla: rakentamisen aikana tai `List`-luettelossa käyttäen `setBreakpoints()`-menetelmää:

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

Alla oleva esimerkkidemo näyttää useiden murtopisteiden asettamisen rakennusvaiheessa, murtopisteiden käyttäminen komponentin [`Span`](#column-span-and-spans-per-breakpoint) konfiguroimiseen ja osoittaa `ColumnsLayout`in koon muutoskyvyt sovelluksen koon muuttuessa:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Sarakkeen `Span` ja spanit murtopisteittäin {#column-span-and-spans-per-breakpoint}

Sarakkeiden spani `ColumnsLayout`-komponentissa mahdollistaa kontrolloida, kuinka monta saraketta kohde vie, antaen enemmän hallintaa asettelusi ulkonäöstä eri leveyksillä. Tämä on erityisen hyödyllistä, kun haluat tiettyjen elementtien vievän enemmän tai vähemmän tilaa näytön koosta tai suunnitteluvaatimuksista riippuen.

### Sarakkeen spanien hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`-komponentissa vie juuri yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä asettamalla yksittäisten kohteiden sarakkeen spanin. Span määrittää, kuinka monta saraketta kohteen tulisi viedä.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde vie kaksi saraketta
layout.setSpan(button, 2);
```

Edellä olevassa esimerkissä painike vie kaksi saraketta sen sijaan, että se veisi oletuksena yhden. `setSpan()`-menetelmä antaa sinun määrittää, kuinka monta saraketta komponentti tulisi kattaa asettelussa.

### Spanien säätäminen murtopisteiden avulla {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakkeen spania dynaamisesti murtopisteiden perusteella. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde kattaa eri määrän sarakkeita riippuen näytön koosta. Esimerkiksi saatat haluta, että elementti vie yhden sarakkeen mobiililaitteilla, mutta kattaa useita sarakkeita suuremmilla näytöillä.

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
//sähköpostikenttä kattaa kaksi saraketta kun keskikokoinen murtopiste on aktiivinen
columnsLayout.setSpan(email, "medium", 2);
//...
```

Tämä mukautuksen taso varmistaa, että asettelusi pysyy responsiivisena ja asianmukaisesti rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` mahdollistaa kohteiden sijoittamisen tiettyihin sarakkeisiin, antaen enemmän kontrollia elementtien järjestelyyn. Voit manuaalisesti määrittää, missä kohteen tulisi näkyä asettelussa varmistaen, että tärkeät komponentit näkyvät tarkoitetulla tavalla.

### Perussarakkeiden sijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkan sarakkeen, johon kohde tulisi sijoittaa. Liitä kohde tiettyyn sarakkeeseen käyttäen `setColumn()`-menetelmää. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen riippumatta siitä, missä järjestyksessä se lisättiin suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoituksen säätäminen murtopisteittäin {#adjusting-placement-per-breakpoint}

Aivan kuten sarakkeen spanien kanssa, voit käyttää murtopisteitä säätääksesi kohteiden sijoittamista näytön koon mukaan. Tämä on hyödyllistä elementtien järjestyksen tai sijoituksen muuttamiseksi asettelussa, kun näkymä muuttuu.

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
//sähköpostikenttä näkyy toisessa sarakkeessa, kun keskikokoinen murtopiste on aktiivinen
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Seuraavassa esimerkkidemoissa huomaa, että kun `"medium"` murtopiste aktivoituu, `email`-kenttä kattaa molemmat sarakkeet ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen, sen sijaan että se sijoitettaisiin oletusarvoisesti toiseen sarakkeeseen:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan asetteluun, jossa on eri spania ja/tai sarakeasetuksia, varmista, että yhdistetyt spanit ja kohteiden sijoitukset yhdessä rivissä eivät mene päällekkäin. Asettelu yrittää hallita tilantarvetta automaattisesti, mutta huolellinen suunnittelu spanien ja murtopisteiden osalta estää ei-toivottua esittämistä.
:::

## Vertical ja horisontaalinen kohdistaminen {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`-komponentissa voidaan kohdistaa sekä horisontaalisesti että vertikaalisesti sarakkeensa sisällä, antaen hallintaa siitä, miten sisältö on sijoitettu asettelussa.

**Kohteen horisontaalinen kohdistus** hallitaan `setHorizontalAlignment()`-menetelmällä. Tämä ominaisuus määrää, miten kohde kohdistuu sarakkeessaan horisontaalisella akselilla.

**Vertikaalinen kohdistus** määrittää, miten kohde sijoitetaan sarakkeessaan pystysuunnassa. Tämä on hyödyllistä, kun sarakkeilla on vaihteleva korkeus ja haluat ohjata, miten kohteet jakautuvat pystysuunnassa.

Saatavilla olevat `Alignment`-vaihtoehdot ovat:

- `START`: Kohdistaa kohteen sarakkeen vasempaan laitaan (oletus).
- `CENTER`: Keskittää kohteen vaakasuunnassa sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikeaan laitaan.
- `STRETCH`: Venyttää komponentin täyttämään asettelun.
- `BASELINE`: Kohdistaa tekstin tai sisällön perusteella sarakkeessa, kohdistaa kohteet tekstin peruslinjaan verrattuna muihin kohdistusvaihtoehtoihin.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Yllä olevassa demon esimerkissä `Lähetä`-painike on saanut `ColumnsLayout.Alignment.END`-asetuksen, jotta se näkyy sarakkeen lopussa, tässä tapauksessa oikealla.

## Elementtiväli {#item-spacing}

Sarakkeiden välin hallinta `ColumnsLayout`-komponentissa, joka kattaa sarakkeiden (horisontaalinen väli) ja riviin (vertikaalinen väli) välin, auttaa kehittäjiä hienosäätämään asettelua.

Aseta asettelun horisontaalinen väli käyttämällä `setHorizontalSpacing()`-menetelmää:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Vastaavasti käytä `setVerticalSpacing()`-menetelmää määrittääksesi tilan rivien väliin asettelussa:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaista lukua määrittääksesi vähimmäisleveyden pikseleinä tai käyttää `String`-arvoa määrittääksesi muita yksiköitä, kuten `vw`, `%`, tai `em`.
:::

## Horisontaaliset ja vertikaaliset asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asettelujen rakentaminen on mahdollista sekä [`FlexLayout`](./flex-layout) -komponentin että `ColumnsLayout` -komponentin käytön avulla, sekä näiden kahden yhdistelmällä. Alla on esimerkki [lomakkeesta, joka luotiin FlexLayout](./flex-layout#example-form) -artikkelissa, mutta käytetään `ColumnLayout`-järjestelmää sen sijaan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Tyylittely {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
