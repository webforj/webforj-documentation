---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools biedt automatische herstarts van de app wanneer er wijzigingen in de code zijn. webforJ DevTools voegt automatische verversing van de browser toe - wanneer Spring je app herstart, vernieuwt de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen activeren verschillende herlaadgedragingen. Wijzigingen in Java-code veroorzaken een volledige Spring-herstart en verversing van de browser. Wijzigingen in CSS en afbeeldingen worden bijgewerkt zonder een pagina-herlading, waardoor formuliergegevens en app-status behouden blijven.

## Begrijpen van webforJ DevTools {#understanding-webforj-devtools}

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring wijzigingen in bestanden detecteert en herstart, ververst webforJ DevTools automatisch je browser.

### Herlaadgebragin {#reload-behavior}

Verschillende bestandstypen activeren verschillende herlaadstrategieën:

- **Java-bestanden** - Volledige browserpagina-herlading na Spring-herstart
- **CSS-bestanden** - Stijlupdates zonder pagina-herlading  
- **JavaScript-bestanden** - Volledige browserpagina-herlading na Spring-herstart
- **Afbeeldingen** - Ververs in plaats zonder pagina-herlading

## Afhankelijkheden {#dependencies}

Voeg zowel Spring DevTools als webforJ DevTools toe aan je project:

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

## Configuratie {#configuration}

Schakel webforJ DevTools in je app-eigenschappen in:

```Ini title="application.properties"
# Schakel automatische verversing van de browser in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke afsluiting in voor snellere herstarts
server.shutdown=immediate
```

### Geavanceerde configuratie {#advanced-configuration}

Configureer de WebSocket-verbinding en herlaadgebraging:

```Ini title="application.properties"
# WebSocket-serverpoort (standaard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-eindpunt pad (standaard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Hartslaginterval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Schakel hot reload in voor statische bronnen (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
