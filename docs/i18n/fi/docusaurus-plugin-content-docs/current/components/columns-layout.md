---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 25558ea9869bae96974e292e7cc1939d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

`ColumnsLayout`-komponentti webforJ:ssä antaa kehittäjille mahdollisuuden luoda asetteluja käyttämällä joustavaa ja responsiivista pystyasettelua. Tämä asettelu tarjoaa dynaamisesti mukautuvia sarakkeita, jotka säätävät itseään käytettävissä olevan leveyden mukaan. Tämä komponentti yksinkertaistaa monisarjaisten asetteluiden luomista hallitsemalla automaattisesti katkaisuja ja kohdistuksia.

:::info Vaakasuorat asettelut
Tätä voidaan käyttää FlexLayout-komponentin kanssa tai sen sijaan - yhtä tehokas työkalu vaakasuoriin asetteluisiin.
:::

## Perusasiat {#basics}

Kun `ColumnsLayout` instansioidaan ensimmäisen kerran, se käyttää kahta saraketta näyttääkseen asetteluun lisätyt kohteet. Oletusarvoisesti se vie koko vanhempiensa elementtien leveyden ja kasvaa tarpeen mukaan lisäämään sisältöä. Lisättyjen kohteiden näyttöä voidaan kalibroida entisestään käyttäen [`Breakpoint`](./columns-layout#breakpoints) ja [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) asetuksia, joista keskustellaan seuraavissa osioissa tarkemmin.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Katkaisut {#breakpoints}

Perustaltaan `ColumnsLayout` on suunniteltu tarjoamaan nesteenä toimiva, ruudukkomainen järjestelmä, joka mukautuu vanhemman säiliön leveyteen. Toisin kuin perinteiset kiinteät ruudukkojärjestelmät, tämä asettelu sallii kehittäjien määrittää sarakkeiden määrän tietyllä leveydellä ja laskee dynaamisesti näytettävien sarakkeiden määrän asetettujen `Breakpoint`-objektien perusteella.

Tämä mahdollistaa `ColumnsLayout`:n mukautuvan sulavasti kapeammasta tilasta pienillä näytöillä laajempaan alueeseen suuremmilla näytöillä, tarjoten responsiivisen suunnittelun kehittäjälle ilman tarvetta mukautettuun toteutukseen.

### Ymmärrä `Breakpoint` {#understanding-a-breakpoint}

`Breakpoint` voidaan määrittää käyttämällä `Breakpoint`-luokkaa, joka ottaa kolme parametria:

1. **Nimi (valinnainen)**:
Katkaisu-nimittäminen mahdollistaa siihen viittaamisen tulevissa asetuksissa.

2. **Minimi leveys**:
Jokaisella katkaisulla on erityinen alue, joka määrittää, milloin sen asettelu otetaan käyttöön. Minimi leveys määritellään nimenomaisesti, ja seuraava katkaisu määrittää maksimi leveyden, jos sellainen on. Voit käyttää kokonaislukua määrittämään minimileveyden pikseleinä tai käyttää merkkijonoa määrittämään muita yksikköjä, kuten `vw`, `%` tai `em`.

3. **Sarakemäärä**:
Määrittele, kuinka monta saraketta katkaisulla tulisi olla tällä kokonaisluvulla.

:::info `Breakpoint`-arviointi
Katkaisut arvioidaan nousevassa järjestyksessä leveydestä, mikä tarkoittaa, että asettelu käyttää ensimmäistä vastaavaa katkaisua.
:::

### Katkaisujen soveltaminen {#applying-breakpoints}

Katkaisut sovelletaan `ColumnsLayout`:iin yhdellä kahdesta tavalla: rakennusvaiheessa tai `List`-listassa käyttäen `setBreakpoints()`-metodia: 

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

Esittely alla näyttää esimerkin useiden katkaisujen asettamisesta rakennusvaiheessa, käyttäen katkaisuja määrittämään komponentin [`Span`](#column-span-and-spans-per-breakpoint) ja osoittaa `ColumnsLayout`:n koon muutoskyvyt, kun sovellus koon muuttuessa:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Sarakkeen `Span` ja spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Sarakkeiden spans `ColumnsLayout`-komponentissa antavat sinulle kontrollin siitä, kuinka monta saraketta kohde vie, mikä antaa enemmän hallintaa asettelun ulkonäköön erilaisilla leveydellä. Tämä on erityisen hyödyllistä, kun sinun on tarvittava tiettyjen elementtien ottaa enemmän tai vähemmän tilaa näyttön koosta tai suunnittelutarpeista riippuen.

### Sarakkeiden spans hallinta {#managing-column-spans}

Oletusarvoisesti jokainen kohde `ColumnsLayout`:ssa vie tarkalleen yhden sarakkeen. Voit kuitenkin mukauttaa tätä käyttäytymistä asettamalla yksittäisten kohteiden sarakkeen spanin. Span määrittelee, kuinka monta saraketta kohde tulisi viedä.

```java
Button button = new Button("Klikkaa minua");
layout.addComponent(button);
// Kohde vie kaksi saraketta
layout.setSpan(button, 2);
```

Yllä olevassa esimerkissä painike vie kaksi saraketta sen sijaan, että se veisi oletusarvoisesti vain yhden. `setSpan()`-metodi mahdollistaa sinulle määrittämään, kuinka monta saraketta komponentin tulisi kattaa asettelussa.

### Sarakkeiden spans säätäminen katkaisujen mukaan {#adjusting-column-spans-with-breakpoints}

Voit myös säätää sarakkeiden spans dynaamisesti katkaisujen mukaan. Tämä ominaisuus on hyödyllinen, kun haluat, että kohde kattaa eri määrän sarakkeita näyttökoon mukaan. Esimerkiksi saatat haluta, että elementti vie yhden sarakkeen mobiililaitteilla mutta kattaa useita sarakkeita suuremmilla näytöillä.

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

Tämä mukauttamisen taso varmistaa, että asettelusi pysyy responsiivisena ja asianmukaisesti rakenteellisena eri laitteilla.

## Kohteiden sijoittaminen sarakkeisiin {#placing-items-within-columns}

`ColumnsLayout` tarjoaa mahdollisuuden sijoittaa kohteita tiettyihin sarakkeisiin, mikä antaa enemmän kontrollia elementtien järjestelyyn. Voit manuaalisesti määrittää, mihin kohtaan kohde tulisi ilmestyä asettelussa, varmistaen, että tärkeät komponentit näkyvät halutulla tavalla.

### Perussaraket sijoittaminen {#basic-column-placement}

Oletusarvoisesti kohteet sijoitetaan seuraavaan saatavilla olevaan sarakkeeseen, täyttäen vasemmalta oikealle. Voit kuitenkin ylittää tämän käyttäytymisen ja määrittää tarkka sarake, johon kohde tulisi sijoittaa. Kohteen sijoittamiseksi tiettyyn sarakkeeseen käytä `setColumn()`-metodia. Tässä esimerkissä painike sijoitetaan asettelun toiseen sarakkeeseen, riippumatta siitä, missä järjestyksessä se on lisätty suhteessa muihin komponentteihin:

```java
Button button = new Button("Lähetä");
layout.addComponent(button);
// Sijoita kohde toiseen sarakkeeseen
layout.setColumn(button, 2);  
```

### Sijoittamisen säätäminen katkaisujen mukaan {#adjusting-placement-per-breakpoint}

Aivan kuten sarakkeiden spansissa, käytät katkaisuja säätääksesi kohteiden sijoittamista näyttökoon mukaan. Tämä on hyödyllistä elementtien järjestyksen tai siirtämisen yhteydessä asettelussa, kun näkymä muuttuu.

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
//sähköpostikenttä ilmestyy toiseen sarakkeeseen, kun keskikokoinen katkaisu on aktiivinen
columnsLayout.setColumn(email, "keskikokoinen", 2); 
//...
```

Seuraavassa esityksessä huomaa, että kun `"keskikokoinen"` katkaisu aktivoituu, sähköpostikenttä kattaa molemmat sarakkeet, ja `confirmPassword`-kenttä sijoitetaan ensimmäiseen sarakkeeseen sen sijaan, että se olisi oletusarvoisesti sijoitettu toiseen sarakkeeseen:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vältä törmäyksiä
Kun useita kohteita on sijoitettu asetteluun erilaisilla spans- ja/tai sarakeasetuksilla, varmista, että koottujen spans- ja sijoitusyhdistelmät sarakkeessa eivät mene päällekkäin. Asettelu yrittää hallita väliä automaattisesti, mutta huolellinen suunnittelu spansien ja katkaisujen kanssa estää ei-toivottua esitystä.
:::

## Kohteiden pysty- ja vaakakohdistukset {#vertical-and-horizontal-item-alignments}

Jokainen kohde `ColumnsLayout`:issa voi olla kohdistettu sekä vaakasuunnassa että pystysuunnassa sarakkeessaan, mikä antaa kontrollia siitä, kuinka sisältöä sijoitetaan asettelussa.

**Vaakasuuntainen kohdistus** kohteelle hallitaan käyttämällä `setHorizontalAlignment()`-metodia. Tämä ominaisuus määrittää, kuinka kohde kohdistuu sarakkeessaan vaakasuoralla akselilla.

**Pystysuuntainen kohdistus** määrittää, kuinka kohde jaetaan sarakkeessaan pystysuunnassa. Tämä on hyödyllistä, kun sarakkeilla on vaihtelevat korkeudet ja haluat hallita, kuinka kohteet jakautuvat pystysuunnassa.

Saatavilla olevat `Alignment`-vaihtoehdot ovat:

- `START`: Kohdistaa kohteen sarakkeen vasempaan reunaan (oletus).
- `CENTER`: Keskittyy kohde vaakasuunnassa sarakkeessa.
- `END`: Kohdistaa kohteen sarakkeen oikeaan reunaan.
- `STRETCH`: Venyttää komponentin täyttämään asettelu
- `BASELINE`: Kohdistaa tekstin tai sisällön mukaan sarakkeessa, kohdistaa kohteet tekstin perustan mukaan muihin kohdistusvaihtoehtoihin nähden.
- `AUTO`: Automaattinen kohdistus.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Yllä olevassa esityksessä `Lähetä`-painikkeelle on asetettu `ColumnsLayout.Alignment.END`, jotta se ilmestyisi sarakkeensa loppuun tai tässä tapauksessa oikealle.

## Kohteiden väli {#item-spacing}

Sarakkeiden välin hallitseminen `ColumnsLayout`:ssa sarakkeiden välillä (vaakaväli) ja rivien välillä (pystyvälit) auttaa kehittäjiä hienosäätämään asettelua.

Asettaaksesi asettelun vaakavälin, käytä `setHorizontalSpacing()`-metodia:

```java
// Aseta 20px väli sarakkeiden väliin
layout.setHorizontalSpacing(20);  
```

Samalla tavalla käytä `setVerticalSpacing()`-metodia määrittääksesi välin asettelun rivien välillä:

```java
// Aseta 15px väli rivien väliin
layout.setVerticalSpacing(15);  
```

:::tip CSS-yksiköt
Voit käyttää kokonaislukua määrittämään minimileveyden pikseleinä tai käyttää merkkijonoa määrittämään muita yksiköitä, kuten `vw`, `%` tai `em`.
:::

## Vaakasuorat ja pystysuorat asettelut {#horizontal-and-vertical-layouts}

Responsiivisten ja houkuttelevien asetteluiden luominen on mahdollista käyttää sekä [`FlexLayout`](./flex-layout) komponenttia että `ColumnsLayout` komponenttia, sekä yhdistelmää näistä kahdesta. Alla on esimerkki [FlexLayoutissa luodusta muodosta](./flex-layout#example-form), mutta käyttäen `ColumnLayout`-kaavaa sen sijaan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Tyylit {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
