---
title: Reittirekisterin tarjoaja
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: 03f86cbc79737ca141cc9d2e1ad2e28f
---
# Reittirekisterin tarjoaja <DocChip chip='since' label='25.11' />

`RouteRegistryProvider` on palveluntarjoajaliittymä (SPI), joka mahdollistaa integra Frameworkien tarjoamaan mukautettuja reitin löytämismekanismeja. Tämä mahdollistaa Frameworkien integroimisen omien luokkakannan skannausten ja riippuvuuden injektointijärjestelmien kanssa webforJ:n reititysinfrastruktuuriin.

## Yhteenveto {#overview}

webforJ löytää reittejä skannaamalla paketteja `@Route`-annotoiduista komponenteista. `RouteRegistryProvider` SPI mahdollistaa Frameworkien ohittaa tämän oletuskäyttäytymisen omalla löytämismekanismillaan.

Käytä tätä SPI:tä, kun:

- Integroidaan riippuvuuden injektointikehyksiin, kuten Spring, tai konteksteihin ja riippuvuuden injektointiin (CDI)
- Tuetaan erikoistuneita ympäristöjä (OSGi, mukautetut luokkakuormaajat, GraalVM)
- Rakennetaan kehysadaptereita, jotka tarvitsevat hallita reittikomponenttien elinkaaren
- Hyödynnetään olemassa olevia luokkakannan skannauksia käynnistysaikojen optimointiin

## Miten se toimii {#how-it-works}

Kun `RouteRegistry.ofPackage()` kutsutaan, webforJ tarkistaa rekisteröidyt tarjoajat Java:n `ServiceLoader` avulla. Jos tarjoaja löytyy, reitin löytämiseen delegoidaan tälle tarjoajalle. Muuten käytetään oletusskannausta.

## Rakentaminen omalle tarjoajalle {#building-your-provider}

Luodaksesi mukautetun reitin löytämistarjoajan, toteuta SPI-rajapinta ja rekisteröi se Java:n ServiceLoader-mekanismilla.

### Toteuta SPI {#implement-the-spi}

Luo luokka, joka toteuttaa `RouteRegistryProvider`:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Skannaa paketit ja rekisteröi @Route-komponentit
  }
}
```

### Ota löytämiseen käyttöön {#enable-discovery}

Lisää tarjoajasi täydellinen luokan nimi tiedostoon `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
