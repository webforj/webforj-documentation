---
title: Spring DevTools
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: 147474b17005c532723aacd8af9391ea
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset koodimuutosten yhteydessä. webforJ DevTools lisää automaattisen selaimen päivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit laukaisevat erilaisia päivityskäyttäytymisiä. Java-koodimuutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selaimen päivityksen. CSS- ja kuvamuutokset päivittyvät ilman sivun uudelleenlataamista, säilyttäen lomaketiedot ja sovelluksen tilan.

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolseja selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää uudelleen, webforJ DevTools päivittää selaimesi automaattisesti.

### Reload behavior {#reload-behavior}

Eri tiedostotyypit laukaisevat erilaisia latausstrategioita:

- **Java-tiedostot**: Täysi selaimen sivun lataus Spring-uudelleenkäynnistyksen jälkeen
- **JavaScript-tiedostot**: Täysi selaimen sivun lataus Spring-uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot**: Tyylipäivitykset ilman sivun uudelleenlataamista  
- **Kuvat**: Päivitys paikalla ilman sivun uudelleenlataamista

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
# Ota webforJ-selaimen automaattinen uudelleentäyttö käyttöön
webforj.devtools.livereload.enabled=true

# Ota välitön sammutus käyttöön nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Määritä WebSocket-yhteys ja päivityskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätepisteen polku (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyönnin väli millisekunneissa (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota kuuma uudelleenlataus käyttöön staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Määritä selaimen avautuminen sovelluksen käynnistyessä:

```Ini title="application.properties"
# Ota selaimen avautuminen käyttöön (oletus: false)
webforj.devtools.browser.open=true

# localhost, isäntänimi tai IP-osoite (oletus: localhost)
webforj.devtools.browser.host=localhost
```
