---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: 76bc55d5b841ae3c06bcd2cd9e8b2632
---
Integratie van Webswing met webforJ omvat twee componenten: de Webswing-server die uw Swing-app host, en de `WebswingConnector`-component in uw webforJ-app die deze insluit.

## Vereisten {#prerequisites}

Voordat u begint, zorg ervoor dat u voldoet aan de volgende vereisten:

- **Java desktop-app**: een Swing-, JavaFX- of SWT-app verpakt als een JAR-bestand
- **Webswing-server**: download van [webswing.org](https://webswing.org)
- **webforJ versie `25.10` of later**: vereist voor ondersteuning van `WebswingConnector`

## Architectuuroverzicht {#architecture-overview}

De integratiearchitectuur bestaat uit:

1. **Webswing Server**: draait uw Swing-app, vastlegt de GUI-rendering en verwerkt gebruikersinvoer
2. **webforJ Applicatie**: host uw web-app met de ingesloten `WebswingConnector`
3. **Browser Client**: toont zowel de webforJ UI als de ingesloten Swing-app

:::important Poortconfiguratie
Webswing en webforJ moeten op verschillende poorten draaien om conflicten te voorkomen. Zowel webforJ als Webswing draaien doorgaans op poort `8080`. U moet de poort van Webswing of de poort van webforJ wijzigen.
:::

## Webswing-serverconfiguratie {#webswing-server-setup}

### Installatie en opstarten {#installation-and-startup}

1. **Download Webswing** van de [officiële website](https://www.webswing.org/en/downloads)
2. **Pak het archief uit** naar uw voorkeurslocatie (bijv. `/opt/webswing` of `C:\webswing`)
3. **Start de server** met behulp van de platformspecifieke scripts:

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

### Applicatieconfiguratie {#application-configuration}

Zodra de server draait, krijgt u toegang tot de adminconsole op `http://localhost:8080/admin` om uw Swing-app toe te voegen en te configureren.

Configureer in de adminconsole:

- **Applicatienaam**: wordt onderdeel van het URL-pad (bijv. `myapp` → `http://localhost:8080/myapp/`)
- **Hoofdklasse**: het startpunt van uw Swing-app
- **Classpath**: pad naar uw app JAR en afhankelijkheden
- **JVM-argumenten**: geheuginstellingen, systeeminstellingen en andere JVM-opties
- **Huisdirectory**: werkdirectory voor de app

Na configuratie is uw Swing-app toegankelijk op `http://localhost:8080/[app-name]/`

### CORS-configuratie {#cors-configuration}

Wanneer u Webswing insluit in een webforJ-app die op een andere poort of domein draait, moet u Cross-Origin Resource Sharing (CORS) configureren in Webswing. Hiermee kan de browser Webswing-inhoud laden vanuit uw webforJ-pagina.

Navigeer in de Webswing-adminconsole naar de configuratie van uw app en stel in:

- **Toegestane oorsprongen**: voeg de oorsprong van uw webforJ-app toe (bijv. `http://localhost:8090` of `*` voor ontwikkeling)

Deze instelling komt overeen met de optie `allowedCorsOrigins` in de app-configuratie van Webswing.


## webforJ-integratie {#webforj-integration}

Zodra uw Webswing-server draait met uw Swing-app geconfigureerd en CORS ingeschakeld, kunt u deze integreren in uw webforJ-app.

### Voeg afhankelijkheid toe {#add-dependency}

De integratie van Webswing is afhankelijk van de Webswing-integratiemodule van webforJ, die de `WebswingConnector`-component en bijbehorende klassen biedt. Voeg het volgende toe aan uw `pom.xml`-bestand:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Basisimplementatie {#basic-implementation}

Maak een weergave die uw Swing-app insluit met behulp van de `WebswingConnector`:

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
    // Initialiseer de connector met uw Webswing-applicatie-URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Stel de afmetingen in
    connector.setSize("100%", "600px");

    // Voeg toe aan de weergavecontainer
    getBoundComponent().add(connector);
  }
}
```

De connector maakt automatisch verbinding met de Webswing-server wanneer deze aan de DOM wordt toegevoegd. De UI van de Swing-app wordt vervolgens weergegeven binnen de connectorcomponent.

## Configuratie-opties {#configuration-options}

De `WebswingOptions`-klasse stelt u in staat om het gedrag van de connector aan te passen. Standaard start de connector automatisch wanneer deze wordt gemaakt en gebruikt het standaard verbindingsinstellingen. U kunt dit gedrag wijzigen door een instantie van `WebswingOptions` te maken en deze toe te passen op de connector.

Bijvoorbeeld, om de uitlogknop verborgen te houden in een productieomgeving waarin u de authenticatie via uw webforJ-app beheert:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Verberg de uitlogknop

connector.setOptions(options);
```

Of als u handmatige controle nodig heeft over wanneer de verbinding begint:

```java
// Maak connector zonder automatisch starten
WebswingConnector connector = new WebswingConnector(url, false);

// Configureer en start wanneer gereed
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

De opties dekken verbindingsbeheer, authenticatie, debugging en monitoring.
