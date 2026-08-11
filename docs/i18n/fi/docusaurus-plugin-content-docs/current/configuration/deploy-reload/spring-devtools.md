---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 183c4eb42a93904e03dff44faf2118e7
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset koodimuutosten yhteydessä. webforJ DevTools lisää automaattisen selainpäivityksen - kun Spring käynnistää uudelleen sovelluksesi, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit laukaisevat erilaisia uudelleenlatauskäyttäytymisiä. Java-koodimuutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selainpäivityksen. CSS- ja kuvamuutokset päivitetään ilman sivun lataamista, säilyttäen lomakedatan ja sovellustilan.

:::tip Frontend-muutokset
Muutokset, jotka sijaitsevat `src/main/frontend`-kansiossa, käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -työkalun avulla, joka kokoaa ne uudelleen ja päivittää selaimen yhdessä palvelimen kanssa.
:::

<!-- vale off -->
## Ymmärrä webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää uudelleen, webforJ DevTools päivittää selaimesi automaattisesti.

### Uudelleenlatauskäyttäytyminen {#reload-behavior}

Eri tiedostotyypit laukaisevat erilaisia uudelleenlatausstrategioita:

- **Java-tiedostot**: Täydellinen selainikkunan lataus Spring-uudelleenkäynnistyksen jälkeen
- **JavaScript-tiedostot**: Täydellinen selainikkunan lataus Spring-uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot**: Tyylipäivitykset ilman sivun lataamista
- **Kuvat**: Päivitetään paikallaan ilman sivun lataamista

## Riippuvuudet {#dependencies}

Lisää sekä Spring DevTools että webforJ DevTools projektiisi:

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>

<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-devtools</artifactId>
  <version>${webforj.version}</version>
  <optional>true</optional>
</dependency>
```

## Kokoonpano {#configuration}

Ota käyttöön webforJ DevTools `application.properties`-tiedostossasi:

```Ini title="application.properties"
# Ota käyttöön webforJ:n selaimen automaattinen päivitys
webforj.devtools.livereload.enabled=true

# Ota käyttöön välitön sammuttaminen nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Kehittynyt kokoonpano {#advanced-configuration}

Määritä WebSocket-yhteys ja uudelleenlatauskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätteentien, (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyöntiväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma uudelleenlataus staattisia resursseja varten (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Määritä selaimen avautuminen sovelluksen käynnistyessä:

```Ini title="application.properties"
# Ota käyttöön selaimen avautuminen (oletus: false)
webforj.devtools.browser.open=true

# localhost, isäntänimi tai IP-osoite (oletus: localhost)
webforj.devtools.browser.host=localhost
```
