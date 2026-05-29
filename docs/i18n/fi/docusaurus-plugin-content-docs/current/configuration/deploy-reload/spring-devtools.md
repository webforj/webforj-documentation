---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 8feae38bceaabbc49e058a8d2f56f3ba
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset koodin muuttuessa. webforJ DevTools lisää automaattisen selaimen päivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit laukaisevat erilaisen uudelleenlataus käyttäytymisen. Java-koodin muutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selaimen päivityksen. CSS- ja kuvamuutokset päivitetään ilman sivun uudelleenlatausta, säilyttäen lomaketiedot ja sovellustilan.

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää, webforJ DevTools päivittää automaattisesti selaimesi.

### Reload behavior {#reload-behavior}

Eri tiedostotyypit laukaisevat erilaisia uudelleenlatausstrategioita:

- **Java-tiedostot**: Täydellinen selaimen sivun uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
- **JavaScript-tiedostot**: Täydellinen selaimen sivun uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
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

Ota käyttöön webforJ DevTools `application.properties`-tiedostossasi:

```Ini title="application.properties"
# Ota käyttöön webforJ selaimen automaattinen uudelleenlataus
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

# Sydämen lyönti väli millisekunneissa (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma lataus staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Määritä selaimen avautuminen sovelluksen käynnistyksessä:

```Ini title="application.properties"
# Ota käyttöön selaimen avautuminen (oletus: false)
webforj.devtools.browser.open=true

# localhost, isännän nimi tai IP-osoite (oletus: localhost)
webforj.devtools.browser.host=localhost
```
