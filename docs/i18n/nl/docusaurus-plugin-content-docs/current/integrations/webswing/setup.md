---
title: Instelling en Configuratie
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
Webswing integreren met webforJ omvat twee componenten: de Webswing-server die je Swing-app host, en de `WebswingConnector` component in je webforJ-app die deze embed.

## Vereisten

Voordat je begint, zorg ervoor dat je de volgende vereisten hebt:

- **Java desktopapplicatie**: een Swing-, JavaFX- of SWT-app verpakt als een JAR-bestand
- **Webswing-server**: download van [webswing.org](https://webswing.org)
- **webforJ versie `25.10` of later**: vereist voor ondersteuning van `WebswingConnector`

## Architectuuroverzicht

De integratiearchitectuur bestaat uit:

1. **Webswing Server**: draait je Swing-app, legt de GUI-rendering vast en verwerkt gebruikersinvoer
2. **webforJ Applicatie**: host je webapp met de embedded `WebswingConnector`
3. **Browser Client**: toont zowel de webforJ UI als de ingebedde Swing-app

:::important Poortconfiguratie
Webswing en webforJ moeten op verschillende poorten draaien om conflicten te voorkomen. Zowel webforJ als Webswing draaien doorgaans op poort `8080`. Je moet ofwel de Webswing-poort of de webforJ-poort wijzigen.
:::

## Webswing server setup

### Installatie en opstarten

1. **Download Webswing** van de [officiële website](https://www.webswing.org/en/downloads)
2. **Pak het archief uit** op je voorkeurslocatie (bijv. `/opt/webswing` of `C:\webswing`)
3. **Start de server** met behulp van de platform-specifieke scripts:

<Tabs>
      <TabItem value="Linux" label="Linux" default>
        ```bash
        webswing.sh
        ```
      </TabItem>
      <TabItem value="macOS" label="macOS">
        ```bash
        webswing.command
        ```
      </TabItem>
      <TabItem value="Windows" label="Windows">
        ```bash
        webswing.bat
        ```
      </TabItem>
</Tabs>

4. **Controleer of de server draait** door toegang te krijgen tot `http://localhost:8080`

### Applicatieconfiguratie

Zodra de server draait, krijg je toegang tot de adminconsole op `http://localhost:8080/admin` om je Swing-app toe te voegen en te configureren.

Configureer in de adminconsole:

- **Applicatienaam**: wordt onderdeel van het URL-pad (bijv. `myapp` → `http://localhost:8080/myapp/`)
- **Hoofdkelas**: het toegangspunt van je Swing-app
- **Classpath**: pad naar je app JAR en afhankelijkheden
- **JVM-argumenten**: geheugenspecificaties, systeeminstellingen en andere JVM-opties
- **Thuisdirectory**: werkdirectory voor de app

Na configuratie is je Swing-app toegankelijk op `http://localhost:8080/[app-name]/`

## webforJ Integratie

Zodra je Webswing-server draait met je geconfigureerde Swing-app, kun je deze integreren in je webforJ-app. Dit omvat het toevoegen van de afhankelijkheid, het configureren van Cross-Origin Resource Sharing (CORS) en het maken van een weergave met de `WebswingConnector` component.

### Voeg afhankelijkheid toe

Voeg de Webswing integratiemodule toe aan je webforJ-project. Dit biedt de `WebswingConnector` component en gerelateerde klassen.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### CORS-configuratie

Cross-Origin Resource Sharing (CORS) configuratie is vereist wanneer Webswing en webforJ op verschillende poorten of domeinen draaien. Het same-origin beleid van de browser blokkeert aanvragen tussen verschillende oorsprongen zonder de juiste CORS-headers.

Maak een servletfilter om CORS-headers aan je webforJ-app toe te voegen:

```java title="CorsFilter.java"
package com.example.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // pass
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("Access-Control-Allow-Origin", "*");
      httpResponse.setHeader("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Authorization, X-Requested-With");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // pass
  }
}
```

Registreer de filter in je `web.xml`:

```xml
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>com.example.config.CorsFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

:::important Toegang in Productieomgevingen
Voor productieomgevingen, vervang de wildcard (`*`) in `Access-Control-Allow-Origin` met je specifieke Webswing-server URL voor veiligheid.
:::

### Basisimplementatie

Maak een weergave die je Swing-app embed via de `WebswingConnector`:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialiseer de connector met je Webswing applicatie URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Stel de weergavedimensies in
    connector.setSize("100%", "600px");

    // Voeg toe aan de weergavecontainer
    getBoundComponent().add(connector);
  }
}
```

De connector stelt automatisch een verbinding in met de Webswing-server wanneer deze aan de DOM wordt toegevoegd. De UI van de Swing-app wordt vervolgens binnen de connectorcomponent gerenderd.

## Configuratie-opties

De `WebswingOptions` klasse stelt je in staat om het gedrag van de connector aan te passen. Standaard start de connector automatisch wanneer deze wordt aangemaakt en gebruikt deze standaard verbindingsinstellingen. Je kunt dit gedrag aanpassen door een `WebswingOptions` instantie aan te maken en deze toe te passen op de connector.

Bijvoorbeeld, om de uitlogknop te verbergen in een productieomgeving waar je de authenticatie beheert via je webforJ-app:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Verberg de uitlogknop

connector.setOptions(options);
```

Of als je handmatige controle nodig hebt over wanneer de verbinding start:

```java
// Maak connector zonder automatische start
WebswingConnector connector = new WebswingConnector(url, false);

// Configureer en start wanneer klaar
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

De opties dekken verbiningsbeheer, authenticatie, foutopsporing en monitoring.
