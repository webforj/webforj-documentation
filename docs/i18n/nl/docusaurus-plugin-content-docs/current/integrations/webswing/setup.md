---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: e3af6f7983bbd6ed7db57428412466c8
---
Integreren van Webswing met webforJ omvat twee componenten: de Webswing-server die uw Swing-app host en de `WebswingConnector`-component in uw webforJ-app die deze inbedt.

## Voorwaarden {#prerequisites}

Voordat u begint, zorg ervoor dat u aan de volgende vereisten voldoet:

- **Java desktopapplicatie**: een Swing-, JavaFX- of SWT-app verpakt als een JAR-bestand
- **Webswing-server**: download van [webswing.org](https://webswing.org)
- **webforJ-versie `25.10` of hoger**: vereist voor ondersteuning van `WebswingConnector`

## Architectuuroverzicht {#architecture-overview}

De integratiearchitectuur bestaat uit:

1. **Webswing Server**: draait uw Swing-app, vangt de GUI-rendering en verwerkt gebruikersinvoer
2. **webforJ-toepassing**: host uw webapp met de ingebedde `WebswingConnector`
3. **Browser Client**: toont zowel de webforJ UI als de ingebedde Swing-app

:::important Poortconfiguratie
Webswing en webforJ moeten op verschillende poorten draaien om conflicten te voorkomen. Zowel webforJ als Webswing draaien doorgaans op poort `8080`. U moet ofwel de Webswing-poort of de webforJ-poort wijzigen.
:::

## Webswing-serverinstelling {#webswing-server-setup}

### Installatie en opstarten {#installation-and-startup}

1. **Download Webswing** van de [officiële website](https://www.webswing.org/en/downloads)
2. **Pak de archiefbestanden uit** naar uw voorkeur locatie (bijv. `/opt/webswing` of `C:\webswing`)
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

4. **Verifieer of de server draait** door `http://localhost:8080` te openen

### Toepassingsconfiguratie {#application-configuration}

Zodra de server draait, kunt u de adminconsole openen op `http://localhost:8080/admin` om uw Swing-app toe te voegen en te configureren.

Configureer in de adminconsole:

- **Applicatienaam**: vormt een deel van het URL-pad (bijv. `myapp` → `http://localhost:8080/myapp/`)
- **Hoofdklasse**: het toegangspunt van uw Swing-app
- **Classpath**: pad naar uw app JAR en afhankelijkheden
- **JVM-argumenten**: geheugensinstellingen, systeemparameters en andere JVM-opties
- **Huisdirectory**: werkdirectory voor de app

Na configuratie is uw Swing-app toegankelijk op `http://localhost:8080/[app-name]/`

### CORS-configuratie {#cors-configuration}

Bij het inbedden van Webswing in een webforJ-app die op een andere poort of domein draait, moet u Cross-Origin Resource Sharing (CORS) in Webswing configureren. Dit stelt de browser in staat om Webswing-inhoud vanuit uw webforJ-pagina te laden.

Navigeer in de Webswing-adminconsole naar de configuratie van uw app en stel in:

- **Toegestane Oorsprongen**: Voeg de oorsprong van uw webforJ-app toe (bijv. `http://localhost:8090` of `*` voor ontwikkeling)

Deze instelling komt overeen met de optie `allowedCorsOrigins` in de app-configuratie van Webswing.

## webforJ-integratie {#webforj-integration}

Zodra uw Webswing-server draait met uw geconfigureerde Swing-app en CORS is ingeschakeld, kunt u het integreren in uw webforJ-app.

### Voeg afhankelijkheid toe {#add-dependency}

Voeg de Webswing-integratiemodule toe aan uw webforJ-project. Dit biedt de `WebswingConnector`-component en verwante klassen.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### Basisimplementatie {#basic-implementation}

Maak een weergave die uw Swing-app inbedt met de `WebswingConnector`:

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

    // Stel de weergave-afmetingen in
    connector.setSize("100%", "600px");

    // Voeg toe aan de weergavecontainer
    getBoundComponent().add(connector);
  }
}
```

De connector stelt automatisch een verbinding met de Webswing-server tot stand wanneer deze aan de DOM wordt toegevoegd. De UI van de Swing-app wordt vervolgens weergegeven binnen het connectorcomponent.

## Configuratieopties {#configuration-options}

De `WebswingOptions`-klasse stelt u in staat om het gedrag van de connector aan te passen. Standaard start de connector automatisch wanneer deze wordt gemaakt en gebruikt deze standaard verbindingsinstellingen. U kunt dit gedrag wijzigen door een `WebswingOptions`-instantie aan te maken en deze op de connector toe te passen.

Bijvoorbeeld, om de uitlogknop te verbergen in een productiescenario waar u authenticatie beheert via uw webforJ-app:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Verberg de uitlogknop

connector.setOptions(options);
```

Of als u handmatige controle nodig heeft over wanneer de verbinding start:

```java
// Maak connector zonder automatische start
WebswingConnector connector = new WebswingConnector(url, false);

// Configureer en start wanneer klaar
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

De opties dekken verbindingsbeheer, authenticatie, foutopsporing en monitoring.
