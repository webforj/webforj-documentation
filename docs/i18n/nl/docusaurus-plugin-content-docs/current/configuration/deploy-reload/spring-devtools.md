---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 3a552976cb9d962eb59dbfa25a10fb58
---
Spring DevTools biedt automatische herstarts van de app wanneer de code verandert. webforJ DevTools voegt automatische verversing van de browser toe - wanneer Spring je app herstart, ververst de browser automatisch via de LiveReload-server van webforJ.

Verschillende bestandstypen.triggeren verschillende herlaadgedragingen. Wijzigingen in Java-code veroorzaken een volledige Spring-herstart en browserverversing. Wijzigingen in CSS en afbeeldingen worden bijgewerkt zonder een pagina-herlaad, waardoor formuliergegevens en app-status behouden blijven.

:::tip Frontend wijzigingen
Wijzigingen onder `src/main/frontend` worden afgehandeld door de [frontend watch](/docs/configuration/deploy-reload/frontend-watch), die deze opnieuw opbouwt en de browser ververst naast de server.
:::

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ breidt Spring DevTools uit met browsersynchronisatie. Wanneer Spring bestandswijzigingen detecteert en herstart, ververst webforJ DevTools automatisch je browser.

### Reload behavior {#reload-behavior}

Verschillende bestandstypen triggeren verschillende herlaadstrategieën:

- **Java-bestanden**: Volledige browserpagina-verversing na Spring-herstart
- **JavaScript-bestanden**: Volledige browserpagina-verversing na Spring-herstart
- **CSS-bestanden**: Stijlupdates zonder pagina-herlaad  
- **Afbeeldingen**: Ververs in plaats zonder pagina-herlaad

## Dependencies {#dependencies}

Voeg zowel Spring DevTools als webforJ DevTools aan je project toe:

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

Schakel webforJ DevTools in je `application.properties`-bestand in:

```Ini title="application.properties"
# Schakel webforJ browser auto-herlaad in
webforj.devtools.livereload.enabled=true

# Schakel onmiddellijke beëindiging in voor snellere herstarts
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Configureer WebSocket-verbinding en herlaadge gedrag:

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

<DocChip chip='since' label='25.03' /> Configureer het openen van de browser bij het opstarten van de app:

```Ini title="application.properties"
# Schakel het openen van de browser in (standaard: false)
webforj.devtools.browser.open=true

# localhost, hostnaam of IP-adres (standaard: localhost)
webforj.devtools.browser.host=localhost
```
