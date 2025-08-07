---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: ed7626149e8b31e663de874e83935567
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti webforJ:ssä mahdollistaa kehittäjien luoda asetteluja käyttämällä joustavaa ja reagoivaa pystyasettelua. Tämä asettelu tarjoaa dynaamiset sarakkeet, jotka säätävät itsensä käytettävissä olevan leveyden mukaan. Tämä komponentti yksinkertaistaa monisarakkeisten asettelujen luomista hallinnoimalla automaattisesti katkaisuja ja kohdistuksia.

:::info Vaakasuorat asettelut
Tätä voidaan käyttää sijastaan tai yhdessä [`FlexLayout`](./flex-layout) -komponentin kanssa - yhtä tehokas työkalu vaakasuoriin asetteluihin.
:::

## Perusteet {#basics}

Kun `ColumnsLayout` ensin alustetaan, se käyttää kahta saraketta näyttääkseen asetteluun lisättyjä kohteita. Oletuksena se vie koko vanhemman elementtinsä leveyden ja kasvaa tarpeen mukaan lisäsisällön mahtumiseen. Lisättyjen kohteiden näyttämistä voidaan hienosäätää edelleen käyttämällä [`Breakpoint`](./columns-layout#breakpoints) ja [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) asetuksia, joista keskustellaan seuraavissa osissa tarkemmin.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Katkaisut {#breakpoints}

`ColumnsLayout` on suunniteltu tarjoamaan nestemäinen, ruudukkomainen järjestelmä, joka mukautuu sen vanhemman säilön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu mahdollistaa kehittäjien määrittää sarakkeiden määrän tietyllä leveydellä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien perusteella.

Tämä mahdollistaa `ColumnsLayout`-komponentin sujuvan mukautumisen kapeammasta tilasta pienillä näytöillä laajempaan tilaan suurilla näytöillä, tarjoten kehittäjälle reagoivan suunnittelun ilman tarvetta räätälöidylle toteutukselle.

### Ymmärtäminen `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametriä:

1. **Nimi (valinnainen)**:
Ketjun nimeäminen mahdollistaa sen viittaamisen tulevissa konfiguraatioissa.

2. **Vähimmäisleveys**:
Jokaisella katkaisulla on erityinen alue, joka määrittää, milloin sen asettelu otetaan käyttöön. Vähimmäisleveys määritellään nimenomaisesti, ja seuraava katkaisu määrittää maksimileveyden, jos sellainen on. Voit käyttää kokonaislukua määrittämään vähimmäisleveyden pikseleinä tai käyttää `String`-arvoa muiden yksiköiden, kuten `vw`, `%` tai `em`, määrittämiseen.

3. **Sarakkeiden määrä**:
Määritä, kuinka monta saraketta katkaisulla pitäisi olla tämän kokonaisluvun avulla.

:::info `Breakpoint`-arviointi
Katkaisuja arvioidaan kasvavana leveyden mukaan, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa katkaisua.
:::

### Katkaisujen soveltaminen {#applying-breakpoints}

Katkaisuja sovelletaan `ColumnsLayout`-komponenttiin joko rakennusvaiheessa tai käyttämällä `addBreakpoint(Breakpoint)` -menetelmää, kuten alla on esitetty.

```java
ColumnsLayout layout = new ColumnsLayout()
    // Yksi sarake leveydellä >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Kaksi saraketta leveydellä >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Neljä saraketta leveydellä >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

Alla olevassa esimerkissä nähdään useiden katkaisujen asettamista rakentamisen aikana, käyttäen katkaisuja komponentin [`Span`](#column-span-and-spans-per-breakpoint) konfiguroimiseksi sekä esittelee `ColumnsLayout`-komponentin koon muuttamista sovelluksen koolla:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Sarakkeen `Span` ja spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Sarakkeiden spans `ColumnsLayout`-komponentissa mahdollistavat sen, kuinka monta saraketta kohde vie, antaen enemmän hallintaa asettelun ilmeeseen eri leveydillä. Tämä on erityisen hyödyllistä, kun tarvitset tiettyjä elementtejä vievän enemmän tai vähemmän tilaa näytön koon tai suunnitteluvaatimusten mukaan.

### Sarakkeiden spans hallinta {#managing-column-spans}

Oletuksena jokainen kohde `ColumnsLayout`-komponentissa vie täsmälleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä määrittämällä yksittäisten kohteiden sarake-span. Span määrittää, kuinka monta saraketta kohde tulisi viedä.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde kattaa kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä painike vie kaksi saraketta sen sijaan, että yksi on oletusarvoisesti. `setSpan()`-menetelmän avulla voit määrittää, kuinka monta saraketta komponentin tulisi kattaa asettelussa.

### Sarakkeiden spans säätö katkaisujen avulla {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakkeiden spans dynaamisesti katkaisujen perusteella. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde vie erilaisia sarakkeiden määriä näytön koon mukaan. Esimerkiksi saatat haluta, että elementti vie yhden sarakkeen mobiililaitteissa, mutta kattaa useita sarakkeita suuremmilla näytöillä.

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
//sähköpostikenttä kattaa kaksi saraketta, kun keskikokoinen katkaisu on aktiivinen
columnsLayout.setSpan(email, "keskikokoinen", 2);
//...
```

Tämä mukauttamisaste varmistaa, että asettelusi pysyy reagoivana ja asianmukaisesti rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tietyissä sarakkeissa, mikä antaa enemmän hallintaa elementtien sijoitteluun. Voit manuaalisesti määrittää, mihin kohtaan asettelua kohde tulisi sijoittaa, varmistaen, että tärkeät komponentit näkyvät suunnitellusti.

### Perussarake sijoittaminen {#basic-column-placement}

Oletuksena kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ohittaa tämän käyttäytymisen ja määrittää tarkan sarakkeen, johon kohde tulisi sijoittaa. Kohteen sijoittamiseksi tiettyyn sarakkeeseen käytä `setColumn()`-menetelmää. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen sen perusteella, missä järjestyksessä se on lisätty suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohte toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoittamisen säätäminen katkaisujen mukaan {#adjusting-placement-per-breakpoint}

Aivan kuten sarakkeiden spans kanssa, voit käyttää katkaisuja säätämään kohteiden sijoittamista näytön koon mukaan. Tämä on hyödyllistä, kun järjestetään tai siirretään elementtejä asettelussa, kun näkymä muuttuu.

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
//sähköpostikenttä näkyy toisessa sarakkeessa, kun keskikokoinen katkaisu on aktiivinen
columnsLayout.setColumn(email, "keskikokoinen", 2); 
//...
```

Alla olevassa esimerkissä huomaa, että kun `"keskikokoinen"` katkaisu aktivoituu, `email`-kenttä kattaa molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen, sen sijaan että se olisi oletusarvoisesti toisessa sarakkeessa:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vältä kolisioita
Kun useita kohteita sijoitetaan asetteluun erilaisilla span- ja/tai sarakeasetuksilla, varmista, että kohteiden yhdistetyt spans ja sijainnit rivillä eivät päällekkäin. Asettelu yrittää hallita välikkeiden tilaa automaattisesti, mutta huolellinen suunnittelu spansien ja katkaisujen osalta estää tahattomia näyttöongelmia.
:::

## Pystysuoritussijoittaminen ja vaakasuorituskohtien kohdistukset {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`-komponentissa voidaan kohdistaa sekä vaakasuunnassa että pystysuunnassa sen sarakkeessa, mikä antaa hallintaa sisällön sijoittamiselle asettelun sisällä.

**Vaakasuuntainen kohdistus** kohteen kohdalla hallitaan käyttämällä `setHorizontalAlignment()`-menetelmää. Tämä ominaisuus määrää, kuinka kohde kohdistuu sarakkeessaan vaakasuunnassa.

**Pystysuuntainen kohdistus** määrittelee, kuinka kohde sijoitetaan sarakkeessaan pystysuunnassa. Tämä on hyödyllistä, kun sarakkeilla on vaihtelevat korkeudet ja haluat hallita, kuinka kohteet jakautuvat pystysuunnassa.

Saatavilla olevat `Alignment`-vaihtoehdot ovat:

- `START`: Kohdistaa kohteen sarakkeen vasempaan laitaan (oletus).
- `CENTER`: Keskittää kohteen vaakasuunnassa sarakkeeseen.
- `END`: Kohdistaa kohteen sarakkeen oikeaan laitaan.
- `STRETCH`: Venyttää komponentin täyttämään asettelun.
- `BASELINE`: Kohdistaa tekstin tai sisällön mukaan sarakkeessa, kohdistamalla kohteet tekstin peruslinjaan muihin kohdistusvaihtoehtoihin verrattuna.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Yllä olevassa esimerkissä `Submit`-painikkeelle on annettu `ColumnsLayout.Alignment.END` varmistaakseen, että se ilmestyy sarakkeensa loppuun, tai tässä tapauksessa oikealle.

## Kohteiden välinen tila {#item-spacing}

Kohteiden välillä olevan tilan hallinta `ColumnsLayout`-komponentissa sarakkeiden (vaakasuuntainen tila) ja rivien (pystysuuntainen tila) välillä auttaa kehittäjiä hienosäätämään asettelua.

Asettelu tilan asettamiseksi käytä `setHorizontalSpacing()`-menetelmää:

```java
// Aseta 20 px tila sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Samoin käytä `setVerticalSpacing()`-menetelmää määrittääksesi sarjojen välinen tila asettelussa:

```java
// Aseta 15 px tila sarjojen väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittämään vähimmäisleveyden pikseleinä tai käyttää `String`-arvoa muiden yksiköiden, kuten `vw`, `%`, tai `em`, määrittämiseen.
:::

## Vaakasuorat ja pystysuorat asettelut {#horizontal-and-vertical-layouts}

Reagoivien ja houkuttelevien asettelujen rakentaminen on mahdollista käyttämällä sekä [`FlexLayout`](./flex-layout) -komponenttia että `ColumnsLayout` -komponenttia sekä yhdistelmää näistä kahdesta. Alla on esimerkki [lomakkeesta, joka on luotu FlexLayout](./flex-layout#example-form) -artikkelissa, mutta käyttäen `ColumnLayout`-kaavaa sen sijaan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Tyylit {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
