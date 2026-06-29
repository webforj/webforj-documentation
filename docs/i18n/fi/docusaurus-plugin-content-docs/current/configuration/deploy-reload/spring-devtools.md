---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 3a552976cb9d962eb59dbfa25a10fb58
---
Spring DevTools tarjoaa automaattisen sovelluksen uudelleenkäynnistyksen, kun koodia muutetaan. webforJ DevTools lisää automaattisen selaimen päivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit aiheuttavat erilaisia uudelleenlatauskäyttäytymisiä. Java-koodimuutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selaimen päivityksen. CSS- ja kuvat muutokset päivittyvät ilman sivun uudelleenlatausta, säilyttäen lomakedatan ja sovellustilan.

:::tip Frontend-muutokset
Muutokset `src/main/frontend` -kansiossa käsitellään [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -ominaisuuden kautta, joka rakentaa ne uudelleen ja päivittää selaimen yhdessä palvelimen kanssa.
:::

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja uudelleenkäynnistää, webforJ DevTools päivittää selaimesi automaattisesti.

### Reload behavior {#reload-behavior}

Eri tiedostotyypit aiheuttavat erilaisia latausstrategioita:

- **Java-tiedostot**: Täysi selaimen sivun uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
- **JavaScript-tiedostot**: Täysi selaimen sivun uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot**: Tyylipäivitykset ilman sivun uudelleenlatausta  
- **Kuvat**: Päivitys paikallaan ilman sivun uudelleenlatausta

## Dependencies {#dependencies}

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

## Configuration {#configuration}

Ota webforJ DevTools käyttöön `application.properties` -tiedostossasi:

```Ini title="application.properties"
# Ota käyttöön webforJ selaimen automaattinen päivitys
webforj.devtools.livereload.enabled=true

# Ota käyttöön välitön sammutus nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Määritä WebSocket-yhteys ja uudelleenlatauskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätepisteen polku (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyöntiväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma uudelleenlataus staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Määritä selaimen avautuminen sovelluksen käynnistyksen yhteydessä:

```Ini title="application.properties"
# Ota käyttöön selaimen avautuminen (oletus: false)
webforj.devtools.browser.open=true

# localhost, isäntänimi tai IP-osoite (oletus: localhost)
webforj.devtools.browser.host=localhost
```
