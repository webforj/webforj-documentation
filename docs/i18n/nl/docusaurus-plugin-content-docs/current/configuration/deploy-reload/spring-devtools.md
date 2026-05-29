---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 8feae38bceaabbc49e058a8d2f56f3ba
---
Spring DevTools biedt automatische herstarts van de app wanneer codewijzigingen plaatsvinden. webforJ DevTools voegt automatische browserverversing toe - wanneer Spring je app herstart, ververst de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen veroorzaken verschillende herlaadgewoonten. Wijzigingen in Java-code veroorzaken een volledige Spring-herstart en browserverversing. Wijzigingen in CSS en afbeeldingen worden bijgewerkt zonder een pagina-herlaad, waardoor formuliergegevens en de status van de app behouden blijven.

## Begrijpen van webforJ DevTools {#understanding-webforj-devtools}

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring bestandwijzigingen detecteert en opnieuw start, vernieuwt webforJ DevTools automatisch je browser.

### Herlaadgewoonten {#reload-behavior}

Verschillende bestandstypen veroorzaken verschillende herlaadstrategieën:

- **Java-bestanden**: Volledige browserpagina-herlaad na Spring-herstart
- **JavaScript-bestanden**: Volledige browserpagina-herlaad na Spring-herstart
- **CSS-bestanden**: Stijlupdates zonder pagina-herlaad  
- **Afbeeldingen**: Ververs in plaats zonder pagina-herlaad

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
# Schakel webforJ browser auto-verversing in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke afsluiting in voor snellere herstarts
server.shutdown=immediate
```

### Geavanceerde configuratie {#advanced-configuration}

Configureer WebSocket-verbinding en herlaadgewoonten:

```Ini title="application.properties"
# WebSocket serverpoort (standaard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket eindpunt pad (standaard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Heartbeat-interval in milliseconden (standaard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Schakel hot reload in voor statische middelen (standaard: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Configureer het openen van de browser bij het opstarten van de app:

```Ini title="application.properties"
# Schakel het openen van de browser in (standaard: false)
webforj.devtools.browser.open=true

# localhost, hostnaam of IP-adres (standaard: localhost)
webforj.devtools.browser.host=localhost
```
