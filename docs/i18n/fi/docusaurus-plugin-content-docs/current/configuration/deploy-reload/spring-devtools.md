---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools tarjoaa automaattiset sovelluksen uudelleenkäynnistykset, kun koodia muutetaan. webforJ DevTools lisää automaattisen selainpäivityksen - kun Spring käynnistää sovelluksesi uudelleen, selain päivittyy automaattisesti webforJ:n LiveReload-palvelimen kautta.

Eri tiedostotyypit laukaisevat erilaisia uudelleenkäynnistyskäyttäytymisiä. Java-koodin muutokset aiheuttavat koko Springin uudelleenkäynnistyksen ja selaimen päivityksen. CSS- ja kuvamuutokset päivittävät ilman sivun uudelleenlatausta, säilyttäen lomakedatan ja sovellustilan.

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ laajentaa Spring DevToolsia selaimen synkronoinnilla. Kun Spring havaitsee tiedostomuutoksia ja käynnistää uudelleen, webforJ DevTools päivittää selaimesi automaattisesti.

### Reload behavior {#reload-behavior}

Eri tiedostotyypit laukaisevat erilaisia uudelleenkäynnistysstrategioita:

- **Java-tiedostot** - Koko selaimen sivun uudelleenlataus Springin uudelleenkäynnistyksen jälkeen
- **CSS-tiedostot** - Tyylipäivitykset ilman sivun uudelleenlatausta  
- **JavaScript-tiedostot** - Koko selaimen sivun uudelleenlataus Springin uudelleenkäynnistyksen jälkeen
- **Kuvat** - Päivitys paikan päällä ilman sivun uudelleenlatausta

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

Ota käyttöön webforJ DevTools sovelluksesi asetuksissa:

```Ini title="application.properties"
# Ota käyttöön webforJ:n selainautomaattinen uudelleenlataus
webforj.devtools.livereload.enabled=true

# Ota käyttöön välitön sammutus nopeampia uudelleenkäynnistyksiä varten
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Määritä WebSocket-yhteys ja uudelleenkäynnistyskäyttäytyminen:

```Ini title="application.properties"
# WebSocket-palvelimen portti (oletus: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-päätepisteen polku (oletus: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sykeväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Ota käyttöön kuuma uudelleenlataus staattisille resursseille (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true
```
