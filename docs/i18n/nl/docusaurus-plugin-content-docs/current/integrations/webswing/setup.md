---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: cd2108b15ca8583794ddb366329c3638
---
Integratie van Webswing met webforJ omvat twee componenten: de Webswing-server die uw Swing-app host, en de `WebswingConnector` component in uw webforJ-app die deze inbedt.

## Vereisten {#prerequisites}

Voordat u begint, zorg ervoor dat u aan de volgende vereisten voldoet:

- **Java desktopapplicatie**: een Swing, JavaFX of SWT-app verpakt als een JAR-bestand
- **Webswing-server**: download van [webswing.org](https://webswing.org)
- **webforJ versie `25.10` of hoger**: vereist voor ondersteuning van `WebswingConnector`

## Architectuuroverzicht {#architecture-overview}

De integratiearchitectuur bestaat uit:

1. **Webswing Server**: draait uw Swing-app, vangt de GUI-rendering, en verwerkt gebruikersinvoer
2. **webforJ Toepassing**: host uw webapp met de ingebedde `WebswingConnector`
3. **Browserclient**: toont zowel de webforJ UI als de ingesloten Swing-app

:::important Poortconfiguratie
Webswing en webforJ moeten op verschillende poorten draaien om conflicten te voorkomen. Zowel webforJ als Webswing draaien doorgaans op poort `8080`. U moet ofwel de Webswing-poort of de webforJ-poort wijzigen.
:::

## Webswing-serverconfiguratie {#webswing-server-setup}

### Installatie en opstarten {#installation-and-startup}

1. **Download Webswing** van de [officiële website](https://www.webswing.org/en/downloads)
2. **Pak het archief uit** naar uw voorkeur locatie (bijv. `/opt/webswing` of `C:\webswing`)
3. **Start de server** met behulp van de platform specifieke scripts:

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

Zodra de server draait, verkrijg toegang tot de adminconsole op `http://localhost:8080/admin` om uw Swing-app toe te voegen en te configureren.

Configureer in de adminconsole:

- **Applicatienaam**: wordt onderdeel van het URL-pad (bijv. `myapp` → `http://localhost:8080/myapp/`)
- **Hoofdklasse**: het toegangspunt van uw Swing-app
- **Classpath**: pad naar uw app JAR en afhankelijkheden
- **JVM Argumenten**: geheuginstellingen, systeem eigenschappen en andere JVM-opties
- **Thuisdirectory**: werkdirectory voor de app

Na configuratie is uw Swing-app toegankelijk op `http://localhost:8080/[app-name]/`

### CORS-configuratie {#cors-configuration}

Wanneer u Webswing in een webforJ-app blinkt die op een andere poort of domein draait, moet u Cross-Origin Resource Sharing (CORS) in Webswing configureren. Dit stelt de browser in staat om Webswing-inhoud te laden vanuit uw webforJ-pagina.

Navigeer in de Webswing-adminconsole naar de configuratie van uw app en stel in:

- **Toegestane Oorsprongen**: Voeg de oorsprong van uw webforJ-app toe (bijv. `http://localhost:8090` of `*` voor ontwikkeling)

Deze instelling komt overeen met de optie `allowedCorsOrigins` in de app-configuratie van Webswing.

## webforJ-integratie {#webforj-integration}

Zodra uw Webswing-server draait met uw Swing-app geconfigureerd en CORS ingeschakeld, kunt u het integreren in uw webforJ-app.

### Voeg afhankelijkheid toe {#add-dependency}

Webswing-integratie is afhankelijk van de Webswing-integratiemodule van webforJ, die de `WebswingConnector` component en gerelateerde klassen biedt.
Voeg het volgende toe aan uw `pom.xml` bestand:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Basisimplementatie {#basic-implementation}

Maak een weergave die uw Swing-app inbedt met behulp van de `WebswingConnector`:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialiseer de connector met de URL van uw Webswing-applicatie
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Stel de weergave-afmetingen in
    connector.setSize("100%", "600px");

    // Voeg toe aan de weergavecontainer
    self.add(connector);
  }
}
```

De connector stelt automatisch een verbinding met de Webswing-server in wanneer deze aan de DOM wordt toegevoegd. De UI van de Swing-app wordt dan weergegeven binnen de connector component.

## Configuratie-opties {#configuration-options}

De `WebswingOptions` klasse stelt u in staat om het gedrag van de connector aan te passen. Standaard start de connector automatisch wanneer deze wordt aangemaakt en gebruikt het standaard verbsettings. U kunt dit gedrag wijzigen door een `WebswingOptions` instantie aan te maken en deze op de connector toe te passen.

Bijvoorbeeld, om de uitlogknop te verbergen in een productieomgeving waar u authenticatie beheert via uw webforJ-app:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Verberg de uitlogknop

connector.setOptions(options);
```

Of als u handmatige controle nodig heeft over wanneer de verbinding start:

```java
// Maak connector zonder auto-start
WebswingConnector connector = new WebswingConnector(url, false);

// Configureer en start wanneer gereed
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

De opties omvatten verbindingsbeheer, authenticatie, debugging en monitoring.
