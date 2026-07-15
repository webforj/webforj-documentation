---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: d75bb3fcf3260672e15ef9acbb38e295
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout` -komponentti järjestää kohteita responsiiviseen sarakepohjaiseen asetteluun, joka mukautuu saatavilla olevan leveyden mukaan. Katkot ja kohdistukset hallitaan automaattisesti, joten monisarakkeisten lomakkeiden ja sisältöruutujen rakentaminen ei vaadi mukautettua responsiivista logiikkaa.

<!-- INTRO_END -->

## Oletuskäyttäytyminen {#default-behavior}

Oletuksena `ColumnsLayout` järjestää kohteet kahteen sarakkeeseen ja vie koko vanhemman leveyden. Näyttöä voidaan lisäksi säätää katkojen ja kohdistusasetusten avulla, jotka käsitellään seuraavissa osioissa.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Vaakasuorat asettelut
Tätä voidaan käyttää sijasta tai yhdessä [`FlexLayout`](./flex-layout) -komponentin kanssa, joka on yhtä voimakas työkalu vaakasuorille asetteluilla.
:::

## Katkot {#breakpoints}

`ColumnsLayout` on pääasiassa suunniteltu tarjoamaan joustava, ruudukkomuotoinen järjestelmä, joka mukautuu vanhemman säiliön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu sallii kehittäjien määrittää sarakkeiden määrän tietyssä leveydessä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien perusteella.

Tämä sallii `ColumnsLayout`-komponentin sopeutuvan sujuvasti pienissä näytöissä kapeammasta tilasta laajempaan tilaan suurilla näytöillä, tarjoten kehittäjälle responsiivisen suunnittelun ilman mukautettua toteutusta.

### Ymmärrys `Breakpoint`-kohdasta {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Katkovaiheen nimeäminen mahdollistaa sen viittaamisen tulevissa konfiguroinneissa.

2. **Minimi leveys**:
Jokaisella katkovaiheella on tietty alue, joka määrittää, milloin sen asettelu otetaan käyttöön. Minimi leveys määritellään nimenomaisesti, ja seuraava katkovaihe määrittää maksimi leveyden, jos se on olemassa. Voit käyttää kokonaislukuja määrittääksesi minimi leveyden pikseleinä tai käyttää `String`-tyyppiä määrittääksesi muita yksiköitä, kuten `vw`, `%`, tai `em`.

3. **Sarakkeiden määrä**:
Määritä, kuinka monta saraketta katkovaiheella tulisi olla tällä kokonaisluvulla.

:::info `Breakpoint`-evaluointi
Katkovaiheita arvioidaan koon mukaiseen nousevaan järjestykseen, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa katkovaihetta.
:::

### Katkojen soveltaminen {#applying-breakpoints}

Katkokset sovelletaan `ColumnsLayout`-komponenttiin yhdellä kahdesta tavalla: rakentamisen aikana tai `List`-listan avulla käyttämällä `setBreakpoints()`-menetelmää:

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

Alla oleva esimerkki näyttää, miten useita katkoja asetetaan rakentamisen aikana, käyttäen katkoja määrittämään komponentin [`Span`](#column-span-and-spans-per-breakpoint) ja havainnollistaa `ColumnsLayout`-komponentin koon muuttumismahdollisuuksia, kun sovellusta muutetaan:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Sarakkeen `Span` ja spanit per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Sarakkeiden laajuudet `ColumnsLayout`-komponentissa mahdollistavat valvoa, kuinka monta saraketta kohteet vievät, antaen enemmän hallintaa asettelun ulkoasuista eri leveydellä. Tämä on erityisen hyödyllistä, kun tietyt elementit tarvitsevat enemmän tai vähemmän tilaa näytön koosta tai suunnitteluvaatimuksista riippuen.

### Sarakelaajuuden hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`-komponentissa vie tarkalleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä asettamalla sarakkeen laajuuden yksittäisille kohteille. Laajuus määrittää, kuinka monta saraketta kohde tulisi viedä.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde kattaa kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä painike vie kaksi saraketta oletuksena olevan yhden sijasta. `setSpan()`-menetelmä antaa sinun määrittää, kuinka monta saraketta komponentti tulisi kattaa asettelussa.

### Sarakelaajuuden säätö katkojen avulla {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakelaajuuksia dynaamisesti katkojen perusteella. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde kattaa eri määrän sarakkeita näyttökoon mukaan. Esimerkiksi, saatat haluta elementin vievän yhden sarakkeen mobiililaitteissa, mutta kattaa useita sarakkeita suuremmilla näytöillä.

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
//sähköpostikenttä kattaa kaksi saraketta, kun keskikokoinen katkovaihe on aktiivinen
columnsLayout.setSpan(email, "keskikokoinen", 2);
//...
```

Tämä mukauttamisaste takaa, että asettelusi pysyy responsiivisena ja asianmukaisesti rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, mikä antaa enemmän hallintaa elementtien järjestelyyn. Voit manuaalisesti määrittää, mihin kohtaan kohde tulee asettelussa, varmistaen tärkeiden komponenttien näkyvän tarkoitetulla tavalla.

### Perussarakkeiden sijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkan sarakkeen, johon kohteen tulisi tulla sijoitetuksi. Sijoittaaksesi kohteen tiettyyn sarakkeeseen, käytä `setColumn()`-menetelmää. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen, riippumatta siitä, missä järjestyksessä se on lisätty suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);
```

### Sijoittamisen säätö katkojen mukaan {#adjusting-placement-per-breakpoint}

Aivan kuten sarakelaajuuden kanssa, voit käyttää katkoja säätämään sijoittelua näyttökoon mukaan. Tämä on hyödyllistä elementtien uudelleenjärjestämisessä tai siirtämisessä asettelussa, kun näkymä muuttuu.

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
//sähköpostikenttä ilmestyy toiseen sarakkeeseen, kun keskikokoinen katkovaihe on aktiivinen
columnsLayout.setColumn(email, "keskikokoinen", 2);
//...
```

Seuraavassa esityksessä huomaa, että kun `"keskikokoinen"` katkovaihe aktivoituu, `sähköposti`-kenttä kattaa molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen sen sijaan, että se olisi oletuksena sijoitettu toiseen sarakkeeseen:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Vältä törmäyksiä
Kun useita kohteita sijoitetaan asetteluun eri laajuuksilla ja/tai sarakeassignoinneilla, varmista, että sarakkeessa olevien kohteiden yhdistetyt laajuudet ja sijoitukset eivät mene päällekkäin. Asettelu yrittää hallita väliä automaattisesti, mutta huolellinen suunnittelu laajuuksista ja katkoista estää ei-toivottua kohteiden näyttöä.
:::

## Pystysuorat ja vaakasuorat kohdistukset {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`-komponentissa voidaan kohdistaa sekä vaakasuoraan että pystysuoraan sen sarakkeessa, antamalla hallintaa siitä, miten sisältö sijoitetaan asetteluun.

**Vaakasuuntaista kohdistusta** ohjataan käyttämällä `setHorizontalAlignment()`-menetelmää. Tämä ominaisuus määrittää, kuinka kohde kohdistuu sarakkeessaan vaakasuoralla akselilla.

**Pystysuora kohdistus** määrittää, miten kohde sijoitetaan sarakkeessa pystysuoralla akselilla. Tämä on hyödyllistä, kun sarakkeilla on vaihteleva korkeus ja haluat hallita, kuinka kohteet jakautuvat pystysuunnassa.

Saatavilla olevat `Alignment`-vaihtoehdot ovat:

- `START`: Kohdistaa kohteen sarakkeen vasempaan reunaan (oletus).
- `CENTER`: Keskittää kohteen vaakasuoraan sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikeaan reunaan.
- `STRETCH`: Venyttää komponentin täyttämään asettelun.
- `BASELINE`: Kohdistaa tekstin tai sisällön mukaan sarakkeessa, kohdistamalla kohteita tekstin peruslinjaan sen sijaan, että käytettäisiin muita kohdistusvaihtoehtoja.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

Yllä olevassa esityksessä `Lähetä`-painikkeelle on annettu `ColumnsLayout.Alignment.END` varmistaakseen, että se ilmestyy vain sarakkeen loppuun, tai tässä tapauksessa sen oikealle puolelle.

## Kohteiden väli {#item-spacing}

Sarakkeiden välin hallinta `ColumnsLayout`-komponentissa sarakkeiden välillä (vaakasuora väli) ja rivien välillä (pystysuora väli) auttaa kehittäjiä hienosäätämään asettelua.

Asettaaksesi asettelun vaakasuoran välin, käytä `setHorizontalSpacing()`-menetelmää:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);
```

Samoin käytä `setVerticalSpacing()`-menetelmää määrittääksesi tilan rivien välillä asettelussa:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittääksesi minimi leveyden pikseleinä tai käyttää `String`-tyyppiä määrittääksesi muita yksiköitä, kuten `vw`, `%`, tai `em`.
:::

## Vaakasuorat ja pystysuorat asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asettelujen luominen on mahdollista käyttämällä sekä [`FlexLayout`](./flex-layout) -komponenttia että `ColumnsLayout` -komponenttia sekä niiden yhdistelmää. Alla on esimerkki [FlexLayout](./flex-layout#example-form) -artikkelissa luodusta lomakkeesta, mutta käytetään `ColumnLayout`-kaavaa sen sijaan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Tyylit {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
