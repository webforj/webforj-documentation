---
sidebar_position: 15
title: Browser Console
_i18n_hash: 843587956991faa037138ce8e8563e7a
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Selaimen konsolin käyttö ohjelmatietojen tulostamiseen on olennainen osa kehitysprosessia. 
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-apuluokka tarjoaa ominaisuuksia, jotka parantavat lokituksen mahdollisuuksia lokityyppien ja tyylittelyn avulla.

## Instanssi {#instance}

Hanki `BrowserConsole`-instanssi käyttäen `App.console()`-metodia. Tulosta mikä tahansa `Object` haluttu yhdellä viidestä lokityypistä: loki, tieto, varoitus, virhe tai debug.

```java
import static com.webforj.App.console;
// Tyyppit
console().log("Loki viesti");
console().info("Tietoviesti");
console().warn("Varoitus viesti");
console().error("Virhe viesti");
console().debug("Debug viesti");
```

## Tyylittely {#styling}

Käytä builder-metodeja lokiviestin ulkonäön asettamiseen. Jokaisella builderilla on vaihtoehtoja tietyn ominaisuuden muuttamiseen. On myös mahdollista [sekoittaa useita tyylejä](#mixing-styles).
Kun konsoliviesti tulostuu, kaikki käytetyt tyylit eivät siirry seuraaviin viesteihin, ellei niitä *erityisesti* määritellä uudelleen.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Käytä `setStyle`-metodia muutettaaksesi `BrowserConsole`-lokin ominaisuuksia, joita builderit eivät määrittele.
:::

### Taustaväri {#background-color}

Aseta taustaväri `background()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>-luokan.
Käytä väreittäin nimettyjä metodeja, kuten `blue()`, tai valitse tietty arvo `colored(String color)`-metodilla.

```java
// Taustesimerkkejä
console().background().blue().log("Sininen tausta");
console().background().colored("#031f8f").log("Mukautettu sininen tausta");
```

### Tekstiväri {#text-color}

Aseta tekstiväri `color()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>-luokan.
Käytä väreittäin nimettyjä metodeja, kuten `red()`, tai valitse tietty arvo `colored(String color)`-metodilla.

```java
// Väri esimerkkejä
console().background().red().log("Punainen teksti");
console().color().colored("#becad2").log("Mukautettu vaalean siniharmaa teksti");
```

### Fonttikoko {#font-size}

Aseta fonttikoko `size()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>-luokan.
Käytä kokoja nimettyjä metodeja, kuten `small()`, tai valitse tietty arvo `from(String value)`-metodilla.

```java
// Koko esimerkkejä
console().size().small().log("Pieni fontti");
console().size().from("30px").log("30px fontti");
```
:::tip
`from(String value)`-metodi voi ottaa muita fonttikokoarvoja, kuten rem ja vw.
:::

### Fonttityyli {#font-style}

Aseta fonttityyli `style()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>-luokan.
Esimerkiksi käytä `italic()`-metodia saadaksesi konsolilokin kursiiviseksi.

```java
// Tyyli esimerkkejä
console().style().italic().log("Kursiivinen fontti");
console().style().normal().log("Normaali fontti");
```

### Tekstin muunnos {#text-transformation}

Hallinnoi merkkien suuria alkukirjaimia viestissä `transform()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>-luokan.
Esimerkiksi, käytä `capitalize()`-metodia muuttaaksesi jokaisen sanan ensimmäisen kirjaimen isoksi.

```java
// Muunnos esimerkkejä
// Suuraalkukirjainten tekstimuunnos
console().transform().capitalize().log("Suuraalkukirjainten tekstimuunnos");
// SUURIA KIRJAIMIA MUUNNOKSENA 
console().transform().uppercase().log("SUURIA KIRJAIMIA MUUNNOKSENA");
```

### Fonttipaino {#font-weight}

Aseta tekstin paksuus `weight()`-metodilla, joka palauttaa <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>-luokan.
Esimerkiksi, käytä `ligther()`-metodia saadaksesi fontin kevyemmäksi kuin normaali.

```java
// Paino esimerkkejä
console().weight().bold().log("Lihavoitu fontti");
console().weight().lighter().log("Keveämpi fontti");
```

## Tyylien sekoittaminen {#mixing-styles}
On mahdollista sekoittaa ja yhdistää metodeja räätälöityyn lokinäyttöön.

```java
// Erilaisia vaihtoehtoja mukautetulle lokinäytölle
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Tyylien sekoittaminen");
```
