---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 3cbff575fa5d819ab8602aa97c58d5be
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset koodimuutosten yhteydessä. webforJ DevTools lisää automaattisen selainpäivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Erilaiset tiedostotyypit laukaisevat erilaisia uudelleenkäynnistyskäyttäytymisiä. Java-koodimuutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selainpäivityksen. CSS- ja kuvamuutokset päivittävät ilman sivun uudelleenlatausta, säilyttäen lomaketiedot ja sovellustilan.

## Ymmärtäminen webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää, webforJ DevTools päivittää automaattisesti selaimesi.

### Uudelleenkäynnistyskäyttäytyminen {#reload-behavior}

Erilaiset tiedostotyypit laukaisevat erilaisia uudelleenkäynnistysstrategioita:

- **Java-tiedostot** - Täydellinen selainikkunan uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot** - Tyylipäivitykset ilman sivun uudelleenlatausta  
- **JavaScript-tiedostot** - Täydellinen selainikkunan uudelleenlataus Spring-uudelleenkäynnistyksen jälkeen
- **Kuvat** - Uudelleenlataa paikallaan ilman sivun uudelleenlatausta

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

## Määritykset {#configuration}

Ota käyttöön webforJ DevTools sovelluksen asetuksissa:

```Ini title="application.properties"
# Ota käyttöön webforJ-selaimen automaattinen uudelleenkäynnistys
webforj.devtools.livereload.enabled=true

# Ota käyttöön välitön sammutus nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Edistyneet määritykset {#advanced-configuration}

Määritä WebSocket-yhteys ja uudelleenkäynnistyskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätepisteen polku (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyöntiväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma uudelleenkäynnistys staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```
