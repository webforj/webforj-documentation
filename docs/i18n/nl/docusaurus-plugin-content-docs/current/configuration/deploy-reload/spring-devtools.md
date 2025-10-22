---
title: Spring DevTools
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: 147474b17005c532723aacd8af9391ea
---
Spring DevTools biedt automatische herstarts van de applicatie wanneer de code verandert. webforJ DevTools voegt automatische browservernieuwing toe - wanneer Spring je applicatie herstart, ververst de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen activeren verschillende herlaadgedragingen. Wijzigingen in Java-code zorgen voor een volledige Spring-herstart en browservernieuwing. CSS- en afbeeldingswijzigingen worden bijgewerkt zonder een pagina-herlaad, waardoor formuliergegevens en de staat van de applicatie behouden blijven.

<!-- vale off -->
## Understanding webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring bestandswijzigingen detecteert en herstart, ververst webforJ DevTools automatisch je browser.

### Herlaadgedrag {#reload-behavior}

Verschillende bestandstypen activeren verschillende herlaadstrategieën:

- **Java-bestanden**: Volledige browserpagina-herlaad na Spring-herstart
- **JavaScript-bestanden**: Volledige browserpagina-herlaad na Spring-herstart
- **CSS-bestanden**: Stijlupdates zonder pagina-herlaad  
- **Afbeeldingen**: Vernieuwen ter plaatse zonder pagina-herlaad

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

Schakel webforJ DevTools in je `application.properties`-bestand in:

```Ini title="application.properties"
# Schakel webforJ browser auto-herlaad in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke beëindiging in voor snellere herstarts
server.shutdown=immediate
```

### Geavanceerde configuratie {#advanced-configuration}

Configureer WebSocket-verbinding en herlaadgedrag:

```Ini title="application.properties"
# WebSocket serverpoort (standaard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket eindpunt pad (standaard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Hartslaginterval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Schakel hot reload in voor statische bronnen (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Configureer het openen van de browser bij het opstarten van de app:

```Ini title="application.properties"
# Schakel het openen van de browser in (standaard: false)
webforj.devtools.browser.open=true

# localhost, hostnaam of IP-adres (standaard: localhost)
webforj.devtools.browser.host=localhost
```
