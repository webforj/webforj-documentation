---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools biedt automatische herstarts van de applicatie wanneer er codewijzigingen zijn. webforJ DevTools voegt automatische browservernieuwing toe - wanneer Spring uw applicatie herstart, ververst de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen veroorzaken verschillende herlaadgedragingen. Wijzigingen in Java-code veroorzaken een volledige Spring-herstart en browservernieuwing. Wijzigingen in CSS en afbeeldingen worden bijgewerkt zonder dat de pagina opnieuw wordt geladen, waarbij formuliergegevens en applicatiestatus behouden blijven.

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring bestandswijzigingen detecteert en herstart, ververst webforJ DevTools automatisch uw browser.

### Reload behavior {#reload-behavior}

Verschillende bestandstypen veroorzaken verschillende herlaadstrategieën:

- **Java-bestanden** - Volledige browserpagina herladen na Spring-herstart
- **CSS-bestanden** - Stijlupdates zonder pagina-herlaad  
- **JavaScript-bestanden** - Volledige browserpagina herladen na Spring-herstart
- **Afbeeldingen** - Vernieuwen ter plaatse zonder pagina-herlaad

## Dependencies {#dependencies}

Voeg zowel Spring DevTools als webforJ DevTools toe aan uw project:

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

Schakel webforJ DevTools in uw applicatie-eigenschappen in:

```Ini title="application.properties"
# Schakel webforJ browser-auto-herladen in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke afsluiting in voor snellere herstarts
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Configureer WebSocket-verbinding en herlaadgedrag:

```Ini title="application.properties"
# WebSocket-serverpoort (standaard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-eindpuntpad (standaard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Hartslaginterval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Schakel hot reload in voor statische bronnen (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
