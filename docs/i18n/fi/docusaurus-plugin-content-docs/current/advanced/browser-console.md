---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Selaimen konsolin käyttäminen arvokkaiden ohjelmatietojen tulostamiseen on olennainen osa kehitysprosessia. <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> -apuluokka tarjoaa joukon ominaisuuksia lokitusten parantamiseksi.

<!-- :::info
Ennen `24.10`, `App.consoleLog()` ja `App.consoleError()` menetelmät mahdollistivat tämän käyttäytymisen, mutta ne on sittemmin merkitty poistettaviksi.
::: -->

## Instance {#instance}

Hanki `BrowserConsole`-instanssi käyttämällä `App.console()`-menetelmää. Tulosta mikä tahansa haluamasi `Object` yhdellä viidestä lokityypistä: loki, tieto, varoitus, virhe tai debug.

```java
import static com.webforj.App.console;
// Tyypit
console().log("Lokiviesti");
console().info("Tietoviesti");
console().warn("Varoitusviesti");
console().error("Virheviesti");
console().debug("Debug-viesti");
```

## Styling {#styling}

Käytä builder-menetelmiä lokiviestin ulkonäön määrittämiseen. Jokaisella builderilla on vaihtoehtoja tietyn ominaisuuden muuttamiseen. On myös mahdollista [sekoittaa useita tyylejä](#mixing-styles).
Kun konsoliviesti tulostuu, mitään sovellettuja tyylejä ei siirretä seuraaviin viesteihin, ellei niitä *erikseen* määritetä uudelleen.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Käytä `setStyle`-menetelmää `BrowserConsole`-lokin ominaisuuksien muuttamiseen, joita builderit eivät ole määrittäneet.
:::

### Taustaväri {#background-color}

Aseta taustaväri `background()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> -rakentajan.
Käytä väriin nimettyjä metodeja, kuten `blue()`, tai valitse tietty arvo `colored(String color)`-menetelmällä.

```java
// Taustaväri Esimerkkejä
console().background().blue().log("Sininen tausta");
console().background().colored("#031f8f").log("Mukautettu sininen tausta");
```

### Tekstin väri {#text-color}

Aseta tekstin väri `color()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> -rakentajan.
Käytä väriin nimettyjä metodeja, kuten `red()`, tai valitse tietty arvo `colored(String color)`-menetelmällä.

```java
// Väri Esimerkkejä
console().background().red().log("Punainen teksti");
console().color().colored("#becad2").log("Mukautettu vaalean siniharmaa teksti");
```

### Fonttikoko {#font-size}

Aseta fonttikoko `size()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> -rakentajan.
Käytä kokoon nimettyjä metodeja, kuten `small()`, tai valitse tietty arvo `from(String value)`-menetelmällä.

```java
// Koko Esimerkkejä
console().size().small().log("Pieni fontti");
console().size().from("30px").log("30px fontti");
```
:::tip
`from(String value)`-menetelmä voi ottaa muita fonttikokovaihtoehtoja, kuten rem ja vw.
:::

### Fonttityyli {#font-style}

Aseta fonttityyli `style()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> -rakentajan.
Esimerkiksi, käytä `italic()`-metodia tehdäksesi konsolilokista kursivoitun.

```java
// Tyyli Esimerkkejä
console().style().italic().log("Kursivoitu fontti");
console().style().normal().log("Normaali fontti");
```

### Tekstin muuntaminen {#text-transformation}

Säätää viestin merkistön suuria kirjaimia `transform()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> -rakentajan.
Esimerkiksi, käytä `capitalize()`-metodia muuttaaksesi jokaisen sanan ensimmäinen kirjain isoksi.

```java
// Muunnos Esimerkkejä
// Suurten Kirjainten Muuntaminen
console().transform().capitalize().log("Suurten kirjainten muuntaminen");
// ISOJEN KIRJAINTEN MUUNTAMINEN 
console().transform().uppercase().log("Isojen kirjainten muuntaminen");
```

### Fontin paino {#font-weight}

Aseta tekstin paksuus `weight()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> -rakentajan.
Esimerkiksi, käytä `lighter()`-metodia tehdäksesi fontista kevyemmän kuin normaali.

```java
// Paino Esimerkkejä
console().weight().bold().log("Lihavoitu fontti");
console().weight().lighter().log("Keveämpi fontti");
```

## Tyylien sekoittaminen {#mixing-styles}
On mahdollista sekoittaa ja yhdistää menetelmiä mukautetun lokinäytön luomiseksi.

```java
// Monia vaihtoehtoja mukautetulle lokinäytölle
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Tyylin sekoittaminen");
```
