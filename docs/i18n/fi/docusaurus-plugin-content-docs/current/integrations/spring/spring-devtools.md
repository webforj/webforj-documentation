---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset, kun koodissa tapahtuu muutoksia. webforJ DevTools lisää automaattisen selaimen päivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivitys tapahtuu automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit laukaisevat eri latauskäyttäytymisiä. Java-koodimuutokset aiheuttavat täydellisen Spring-uudelleenkäynnistyksen ja selaimen päivityksen. CSS- ja kuvamuutokset päivitetään ilman sivun lataamista, säilyttäen lomaketiedot ja sovellustilan.

## Ymmärrä webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää uudelleen, webforJ DevTools päivittää automaattisesti selaimesi.

### Latauskäyttäytyminen {#reload-behavior}

Eri tiedostotyypit laukaisevat eri latausstrategioita:

- **Java-tiedostot** - Täydellinen selaimen sivun lataus Spring-uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot** - Tyylipäivitykset ilman sivun latausta  
- **JavaScript-tiedostot** - Täydellinen selaimen sivun lataus Spring-uudelleenkäynnistyksen jälkeen
- **Kuvat** - Päivitys paikallaan ilman sivun latausta

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

Ota webforJ DevTools käyttöön sovelluksesi asetuksissa:

```Ini title="application.properties"
# Ota käyttöön webforJ-selaimen automaattinen päivitys
webforj.devtools.livereload.enabled=true

# Ota käyttöön välitön sammutus nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Edistynyt kokoonpano {#advanced-configuration}

Määritä WebSocket-yhteys ja latauskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätepisteen polku (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyöntiväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma lataus staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```
