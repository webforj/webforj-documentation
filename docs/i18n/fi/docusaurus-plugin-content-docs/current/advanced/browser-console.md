---
sidebar_position: 5
title: Browser Console
_i18n_hash: 8a6d28f2824de2020cf5b225d9ff458e
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Selaimen konsolin käyttäminen arvokkaan ohjelmatiedon tulostamiseen on olennainen osa kehitysprosessia. <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-työkalu sisältää runsaasti ominaisuuksia lokitusmahdollisuuksien parantamiseksi.

<!-- :::info
Ennen `24.10`, `App.consoleLog()` ja `App.consoleError()` menetelmät mahdollistivat tämän toiminnallisuuden, mutta ne on sittemmin merkitty poistettavaksi.
::: -->

## Instance {#instance}

Hanki `BrowserConsole`-instanssi käyttämällä `App.console()`-menetelmää. Tulosta mikä tahansa `Object` haluamasi yhdellä viidestä lokityypistä: log, info, warn, error tai debug.

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

Käytä rakentajamenetelmiä lokiviestin ulkoasun määrittämiseen. Jokaisella rakentajalla on vaihtoehtoja tietyn ominaisuuden muuttamiseen. On myös mahdollista [sekoittaa useita tyylejä](#mixing-styles).
Kun konsoliviesti tulostuu, mitään sovellettua tyyliä ei siirretä seuraaviin viesteihin, ellei niitä *erikseen* määritellä uudelleen.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Käytä `setStyle`-menetelmää vaihtaaksesi `BrowserConsole`-lokin ominaisuuksia, joita rakentajat eivät määrittele.
:::

### Taustaväri {#background-color}

Aseta taustaväri `background()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>-rakentajan.
Käytä värillä nimettyjä menetelmiä, kuten `blue()`, tai valitse tietty arvo `colored(String color)`-menetelmällä.

```java
// Taustaesimerkit
console().background().blue().log("Sininen tausta");
console().background().colored("#031f8f").log("Mukautettu sininen tausta");
```

### Tekstiväri {#text-color}

Aseta tekstiväri `color()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>-rakentajan.
Käytä värillä nimettyjä menetelmiä, kuten `red()`, tai valitse tietty arvo `colored(String color)`-menetelmällä.

```java
// Väriesimerkit
console().background().red().log("Punainen teksti");
console().color().colored("#becad2").log("Mukautettu vaalean siniharmaa teksti");
```

### Fonttikoko {#font-size}

Aseta fonttikoko `size()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>-rakentajan.
Käytä kokoilla nimettyjä menetelmiä, kuten `small()`, tai valitse tietty arvo `from(String value)`-menetelmällä.

```java
// Kokoesimerkit
console().size().small().log("Pieni fontti");
console().size().from("30px").log("30px fontti");
```
:::tip
`from(String value)`-menetelmä voi ottaa muitakin fonttikokoarvoja, kuten rem ja vw.
:::

### Fonttityyli {#font-style}

Aseta fonttityyli `style()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>-rakentajan.
Esimerkiksi voit käyttää `italic()`-menetelmää konsolilokin kursivoimiseksi.

```java
// Tyyliesimerkit
console().style().italic().log("Kursivoitu fontti");
console().style().normal().log("Normaali fontti");
```

### Tekstin muunnos {#text-transformation}

Säädä merkkien kapitalisointia viestissä `transform()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>-rakentajan.
Esimerkiksi voit käyttää `capitalize()`-menetelmää muuntaaksesi jokaisen sanan ensimmäisen kirjaimen isoiksi.

```java
// Muunnos-esimerkit
// Kapitalisointi
console().transform().capitalize().log("Kapitalisointimuunnos");
// ISOILLA KIRJAIMILLA
console().transform().uppercase().log("Isot kirjaimet muunnos");
```

### Fonttipaino {#font-weight}

Aseta tekstin paksuus `weight()`-menetelmällä, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>-rakentajan.
Esimerkiksi voit käyttää `ligther()`-menetelmää tehdäksesi fontista kevyemmän kuin normaali.

```java
// Paino-esimerkit
console().weight().bold().log("Raskas fontti");
console().weight().lighter().log("Keveämpi fontti");
```

## Tyylien sekoittaminen {#mixing-styles}
On mahdollista sekoittaa ja yhdistää menetelmiä mukautetun lokiviestin näyttäminen varten.

```java
// Erilaisia vaihtoehtoja mukautettua lokiviestiä varten
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Tyylien sekoittaminen");
```
