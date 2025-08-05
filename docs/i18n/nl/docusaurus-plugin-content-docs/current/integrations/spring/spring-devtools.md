---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 3cbff575fa5d819ab8602aa97c58d5be
---
Spring DevTools biedt automatische herstarts van de app wanneer de code verandert. webforJ DevTools voegt automatische browserverversing toe - wanneer Spring je app herstart, ververst de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen veroorzaken verschillende herlaadgedragingen. Wijzigingen in Java-code veroorzaken een volledige Spring-herstart en browserverversing. Wijzigingen in CSS en afbeeldingen worden bijgewerkt zonder een pagina-herlaad, waardoor formuliergegevens en app-status behouden blijven.

## Begrijpen van webforJ DevTools {#understanding-webforj-devtools}

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring bestandwijzigingen detecteert en herstart, ververset webforJ DevTools automatisch je browser.

### Herlaadgedrag {#reload-behavior}

Verschillende bestandstypen veroorzaken verschillende herlaadstrategieën:

- **Java-bestanden** - Volledige browserpagina-herlaad na Spring-herstart
- **CSS-bestanden** - Stijlupdates zonder pagina-herlaad  
- **JavaScript-bestanden** - Volledige browserpagina-herlaad na Spring-herstart
- **Afbeeldingen** - Verversing ter plaatse zonder pagina-herlaad

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
# Schakel webforJ browser auto-verversing in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke afsluiting in voor snellere herstarts
server.shutdown=immediate
```

### Geavanceerde configuratie {#advanced-configuration}

Configureer de WebSocket-verbinding en herlaadgedrag:

```Ini title="application.properties"
# WebSocket-serverpoort (standaard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-eindpunt pad (standaard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Hartslaginterval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Schakel hete herlaad voor statische bronnen in (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
