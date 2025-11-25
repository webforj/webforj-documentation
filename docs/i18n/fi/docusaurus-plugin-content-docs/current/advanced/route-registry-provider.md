---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# Reitinhallinnan tarjoaja <DocChip chip='since' label='25.11' />

`RouteRegistryProvider` on palveluntarjoajaliittymä (SPI), joka mahdollistaa integraalikehyksille omien reitin löytämismekanismien tarjoamisen. Tämä mahdollistaa kehysten integroimisen omien luokkahaun ja riippuvuuksien injektiojärjestelmien kanssa webforJ:n reititysinfrastruktuuriin.

## Yleiskatsaus {#overview}

webforJ löytää reitit skannaamalla paketteja `@Route`-annotoituja komponentteja varten. `RouteRegistryProvider` SPI mahdollistaa kehyksille tämän oletuskäytöksen ylittämisen omalla löytämismekanismilla.

Käytä tätä SPI:tä, kun:

- Integroit riippuvuuksien injektiojärjestelmiin (Spring, CDI, ...)
- Tuet erikoistuneita ympäristöjä (OSGi, mukautetut luokkakuormittimet, GraalVM)
- Rakennat kehysadaptereita, jotka tarvitsevat hallita reitti-komponenttien elinkaarta
- Hyödynnät olemassa olevia luokkahaun tuloksia käynnistysajan optimointiin

## Toimintaperiaate {#how-it-works}

Kun `RouteRegistry.ofPackage()` kutsutaan, webforJ tarkistaa rekisteröidyt tarjoajat Java:n `ServiceLoader`-mekanismin kautta. Jos tarjoaja löytyy, reitin löytämistoiminto delegoidaan tuolle tarjoajalle. Muussa tapauksessa oletuskäytänne skannausmekanismi on käytössä.

## Tarjoajan luominen {#building-your-provider}

Luodaksesi mukautetun reitin löytämistarjoajan, toteuta SPI-liittymä ja rekisteröi se Java:n ServiceLoader-mekanismin kautta.

### Toteuta SPI {#implement-the-spi}

Luo luokka, joka toteuttaa `RouteRegistryProvider`:

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // Skannaa paketteja ja rekisteröi @Route-komponentit
  }
}
```

### Aktivoi löytö {#enable-discovery}

Lisää tarjoajan täysin kvalifioitu luokan nimi tiedostoon `META-INF/services/com.webforj.router.RouteRegistryProvider`:

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
