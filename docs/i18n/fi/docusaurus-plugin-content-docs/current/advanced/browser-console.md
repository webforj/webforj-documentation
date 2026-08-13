---
sidebar_position: 15
title: Browser Console
description: >-
  Log messages from Java to the browser console with typed levels and styled
  output using the BrowserConsole utility.
_i18n_hash: 5900eaf4e7be19839d40784d6532bff1
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Selaimen konsolin käyttäminen ohjelmatietojen tulostamiseen on olennainen osa kehitysprosessia. 
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-apuluokka tarjoaa ominaisuuksia, jotka parantavat lokitusta lokityyppien ja tyylittelyn avulla.

## Instanssi {#instance}

Hanki `BrowserConsole`-instanssi käyttämällä `App.console()`-metodia. Tulosta mikä tahansa `Object` haluamallasi viidestä lokityypistä: loki, tieto, varoitus, virhe tai debug.

```java
import static com.webforj.App.console;
// Tyypit
console().log("Lokin viesti");
console().info("Tiedon viesti");
console().warn("Varoituksen viesti");
console().error("Virheen viesti");
console().debug("Debug-viesti");
```

## Tyylitys {#styling}

Käytä rakentajametodeja lokiviestin ulkoasun määrittämiseen. Jokaisella rakentajalla on vaihtoehtoja tietyn ominaisuuden muuttamiseen. Myös [useiden tyylien yhdistäminen](#mixing-styles) on mahdollista. 
Kun konsoliviesti tulostetaan, siihen sovelletut tyylit eivät siirry seuraaviin viesteihin, ellei niitä *erikseen* määritetä uudelleen.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Käytä `setStyle`-metodia muuttaaksesi `BrowserConsole`-lokin ominaisuuksia, joita rakentajat eivät määrittäneet.
:::

### Taustaväri {#background-color}

Aseta taustaväri `background()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>:n. 
Käytä väreihin liittyviä menetelmiä, kuten `blue()`, tai valitse tietty arvo `colored(String color)`-metodilla.

```java
// Taustaesimerkit
console().background().blue().log("Sininen tausta");
console().background().colored("#031f8f").log("Mukautettu sininen tausta");
```

### Tekstiväri {#text-color}

Aseta tekstiväri `color()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>:n. 
Käytä väreihin liittyviä menetelmiä, kuten `red()`, tai valitse tietty arvo `colored(String color)`-metodilla.

```java
// Väri-esimerkit
console().background().red().log("Punainen teksti");
console().color().colored("#becad2").log("Mukautettu vaalean siniharmaa teksti");
```

### Fonttikoko {#font-size}

Aseta fonttikoko `size()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>:n. 
Käytä kokoihin liittyviä menetelmiä, kuten `small()`, tai valitse tietty arvo `from(String value)`-metodilla.

```java
// Koko-esimerkit
console().size().small().log("Pieni fontti");
console().size().from("30px").log("30px fontti");
```
:::tip
`from(String value)`-metodi voi ottaa muitakin fonttikokoarvoja, kuten rem ja vw.
:::

### Fonttityyli {#font-style}

Aseta fonttityyli `style()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>:n. 
Esimerkiksi käytä `italic()`-metodia saadaksesi konsolilokin kursiiviksi.

```java
// Tyyli-esimerkit
console().style().italic().log("Kursiivinen fontti");
console().style().normal().log("Normaali fontti");
```

### Tekstin muuntaminen {#text-transformation}

Hallinnoi merkkien pääkirjainkäyttöä viestissä `transform()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>:n. 
Esimerkiksi käytä `capitalize()`-metodia muuttaaksesi jokaisen sanan ensimmäinen kirjain isoksi.

```java
// Muunnos-esimerkit
// Pääkirjainmuunnos
console().transform().capitalize().log("Pääkirjainmuunnos");
// ISOJEN KIRJAIMIEN MUUNTO
console().transform().uppercase().log("ISOJEN KIRJAIMIEN MUUNTO");
```

### Fontin paksuus {#font-weight}

Aseta tekstin paksuus `weight()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>:n. 
Esimerkiksi käytä `ligther()`-metodia saadaksesi fontin kevyemmäksi kuin normaali.

```java
// Paksuus-esimerkit
console().weight().bold().log("Rohkea fontti");
console().weight().lighter().log("Keveämpi fontti");
```

## Tyylien yhdistäminen {#mixing-styles}
On mahdollista sekoittaa ja yhdistellä menetelmiä mukautetun lokinäytön luomiseksi.

```java
// Eri vaihtoehtoja mukautetulle lokinäytölle
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Tyylien yhdistely");
```
