---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: 3705994e0ddda1a54c0582b290942549
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti järjestää kohteet responsiiviseen sarakepohjaiseen asetteluun, joka mukautuu käytettävissä olevan leveyden mukaan. Katkaisupisteet ja kohdistukset hallitaan automaattisesti, joten monisarakkeisten lomakkeiden ja sisällön ruudukkojen rakentamiseen ei tarvita erikoista responsiivista logiikkaa.

## Oletuskäyttäytyminen {#default-behavior}

Oletuksena `ColumnsLayout` vie koko vanhemman leveydestä ja järjestää kohteet yhteen sarakkeeseen, vaihtaen kahteen sarakkeeseen leveyksillä `40em` ja enemmän.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Vaakasuuntaiset asettelut
Tätä voidaan käyttää FlexLayout-komponentin kanssa tai sen sijaan - yhtä voimakas työkalu vaakasuuntaisille asetteluille.
:::

## Katkaisupisteet {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan nesteen kaltaista, ruudukon kaltaista järjestelmää, joka mukautuu sen vanhemman säilön leveyteen. Toisin kuin perinteiset kiinteät ruudukot, tämä asettelu sallii kehittäjien määrittää tietyn määrän sarakkeita tietyllä leveydellä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien perusteella.

Tämä mahdollistaa `ColumnsLayout`:in sujuvan sopeutumisen kapeista tiloista pienillä näytöillä laajemmalle alueelle suuremmilla näytöillä, tarjoten responsiivisen suunnittelun kehittäjälle ilman tarvetta erikoiselle toteutukselle.

### Ymmärtäminen `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttäen `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Katkaisupisteen nimeäminen sallii sen viittaamisen tulevissa määrityksissä.

2. **Minimi leveys**:
Jokaisella katkaisupisteellä on erityinen alue, joka määrittää, milloin sen asettelu otetaan käyttöön. Minimi leveys määritellään nimenomaisesti, ja seuraava katkaisupiste määrittää maksimileveyden, jos se on olemassa. Voit käyttää kokonaislukua määrittämään minimi leveyden pikseleinä tai käyttää `String`:iä määrittämään muita yksiköitä, kuten `vw`, `%` tai `em`.

3. **Sarakkeiden määrä**:
Määrittele, kuinka monta saraketta katkaisupisteellä tulisi olla tällä kokonaisluvulla.

:::info `Breakpoint` arviointi
Katkaisupisteet arvioidaan nousevassa järjestyksessä leveyden mukaan, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa katkaisupistettä.
:::

### Katkaisupisteiden soveltaminen {#applying-breakpoints}

Katkaisupisteitä sovelletaan `ColumnsLayout`:iin yhdellä kahdesta tavalla: rakentamisen aikana tai Listan avulla käyttäen `setBreakpoints()`-metodia:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Yksi sarake leveyksillä >= 0px
  new Breakpoint(0, 1),
  // Kaksi saraketta leveyksillä >= 600px
  new Breakpoint(600, 2),
  // Neljä saraketta leveyksillä >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

Alla oleva esimerkki näyttää, kuinka useita katkaisupisteitä asetetaan rakentamisen aikana, käyttäen katkaisupisteitä komponentin [`Span`](#column-span-and-spans-per-breakpoint) määrittämiseen ja havainnollistaa `ColumnsLayout`:in koon muutosmahdollisuuksia, kun sovellusta pienennetään:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Sarake `Span` ja spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

`ColumnsLayout`:issa sarake-sillat antavat sinun hallita, kuinka monta saraketta kohde vie, antaen enemmän kontrollia asettelusi ulkoasuun eri leveyksillä. Tämä on erityisen hyödyllistä, kun haluat joidenkin elementtien vievän enemmän tai vähemmän tilaa näytön koon tai suunnittelutarpeiden mukaan.

### Sarake-siltojen hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`:issa vie täsmälleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä asettamalla sarake-sillan yksittäisille kohteille. Silta määrittää, kuinka monta saraketta kohteen tulisi viedä.

```java
Button button = new Button("Napsauta minua");
layout.add(button);
// Kohde vie kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä nappi vie kaksi saraketta sen sijaan, että se olisi oletusarvoisesti yksi. `setSpan()`-metodi mahdollistaa sen määrittämisen, kuinka monta saraketta komponentti tulisi kattaa asettelussa.

### Sarake-siltojen säätäminen katkaisupisteiden mukaan {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarake-siltoja dynaamisesti katkaisupisteiden mukaan. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde kattaa erikokoisia sarakkeita näytön koosta riippuen. Esimerkiksi haluat, että elementti vie yhden sarakkeen mobiililaitteissa, mutta kattaa useita sarakkeita suuremmilla näytöillä.

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
//sähköpostikenttä kattaa kaksi saraketta, kun keskikokoinen katkaisupiste on aktiivinen
columnsLayout.setSpan(email, "keskikokoinen", 2);
//...
```

Tämä mukautusvarmuus varmistaa, että asettelusi pysyy responsiivisena ja asianmukaisesti rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, mikä antaa enemmän kontrollia elementtien järjestämisestä. Voit manuaalisesti määrittää, mihin kohteet tulisi sijoittaa asettelussa, varmistaen, että tärkeät komponentit näkyvät halutulla tavalla.

### Perussarakkeen sijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkan sarakkeen, johon kohde tulisi sijoittaa. Sijoittaaksesi kohteen tiettyyn sarakkeeseen, käytä `setColumn()`-metodia. Tässä esimerkissä nappi sijoitetaan asettelun toiseen sarakkeeseen riippumatta siitä, missä järjestyksessä se on lisätty suhteessa muihin komponenteihin:

```java
Button button = new Button("Lähetä");
layout.add(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);
```

### Sijoituksen säätäminen katkaisupisteittäin {#adjusting-placement-per-breakpoint}

Aivan kuten sarake-siltojen kanssa, voit käyttää katkaisupisteitä säätääksesi kohteiden sijoittamista näytön koon mukaan. Tämä on hyödyllistä, kun on tarpeen järjestää tai siirtää elementtejä asettelussa, kun näyttö muuttuu.

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
//sähköpostikenttä näkyy toisessa sarakkeessa, kun keskikokoinen katkaisupiste on aktiivinen
columnsLayout.setColumn(email, "keskikokoinen", 2);
//...
```

Seuraavassa demonstraatiossa huomaa, että kun `"keskikokoinen"` katkaisupiste aktivoituu, `email`-kenttä kattaa molemmat sarakkeet:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan asetteluun eri siltamäärillä ja/tai sarake-asetuksilla, varmista, että kohteiden yhdistetyt sillat ja sijoitukset yhdessä rivissä eivät mene päällekkäin. Asettelu pyrkii hallitsemaan välistystä automaattisesti, mutta siltamäärien ja katkaisupisteiden huolellinen suunnittelu estää kohteiden ei-toivottuja näyttöjä.
:::

## Vertikaalinen ja horisontaalinen kohde-allokointi {#vertical-and-horizontal-item-alignments}

Jokainen `ColumnsLayout`:in kohde voidaan kohdistaa sekä horisontaalisesti että vertikaalisesti sen sarakkeessa, mikä antaa kontrollin siitä, miten sisältö sijoitetaan asettelun sisään.

**Horisontaalista kohdistusta** ohjataan käyttämällä `setHorizontalAlignment()`-metodia. Tämä ominaisuus määrittää, kuinka kohde kohdistuu sen sarakkeessa horisontaalisella akselilla.

**Vertikaalinen kohdistus** määrittää, miten kohde sijoitetaan sen sarakkeessa vertikaalisella akselilla. Tämä on hyödyllistä, kun sarakkeilla on vaihtelevaa korkeutta ja haluat hallita, miten kohteet jakautuvat vertikaalisesti.

Saatavilla olevat `Alignment`-vaihtoehdot sisältävät:

- `START`: Kohdistaa kohteen sarakkeen vasemmalle puolelle.
- `CENTER`: Keskittää kohteen horisontaalisesti sarakkeeseen.
- `END`: Kohdistaa kohteen sarakkeen oikealle puolelle.
- `STRETCH`: Venyttää komponentin täyttämään asettelun.
- `BASELINE`: Kohdistaa kohteet sarakkeen tekstin tai sisällön mukaan, kohdistamalla kohteet tekstin perustason mukaan, ei muihin kohdistusvaihtoehtoihin.
- `AUTO`: Automaattinen kohdistus (oletus).

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

Yllä olevassa demossa `Submit`-nappiin on annettu `ColumnsLayout.Alignment.END`, jotta se näkyy sarakkeen lopussa, tai tässä tapauksessa oikealla.

## Kohteiden väli {#item-spacing}

`ColumnsLayout`:issa sarakkeiden (horisontaalinen väli) ja rivien (vertikaalinen väli) välin hallitseminen auttaa kehittäjiä hienosäätämään asettelua.

Asettaaksesi asettelun horisontaalisen välin, käytä `setHorizontalSpacing()`-metodia:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);
```

Vastaavasti käytä `setVerticalSpacing()`-metodia määrittämään riviin jäävä väli asettelussa:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);
```

:::tip CSS-yksiköt
Väli hyväksyy kokonaisluvun pikseleinä tai `String`:in muissa yksiköissä, kuten `vw` tai `%`.
:::

## Vaakasuuntaiset ja pystyasuettud asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asettelujen rakentaminen on mahdollista sekä [`FlexLayout`](./flex-layout)-komponentin että `ColumnsLayout`-komponentin avulla sekä niiden yhdistelmällä. Alla on esimerkki [FlexLayoutin](./flex-layout#example-form) artikkelissa luodusta lomakkeesta, mutta käyttämällä `ColumnLayout`-kaavaa sen sijaan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Tyylit {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
