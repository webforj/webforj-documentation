---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: d948bababbedcfe831d4af62f8b6b088
---
Integratie van Webswing met webforJ omvat twee componenten: de Webswing-server die jouw Swing-app host, en de `WebswingConnector` component in jouw webforJ-app die deze embed.

## Vereisten {#prerequisites}

Voordat je begint, zorg ervoor dat je aan de volgende vereisten voldoet:

- **Java desktop app**: een Swing, JavaFX, of SWT-app verpakt als een JAR-bestand
- **Webswing-server**: te downloaden van [webswing.org](https://webswing.org)
- **webforJ versie `25.10` of later**: vereist voor ondersteuning van `WebswingConnector`

## Architectuur overzicht {#architecture-overview}

De integratiearchitectuur bestaat uit:

1. **Webswing Server**: draait jouw Swing-app, vangt de GUI-rendering en verwerkt gebruikersinvoer
2. **webforJ Toepassing**: host jouw webapp met de ingebedde `WebswingConnector`
3. **Browser Client**: toont zowel de webforJ UI als de ingebedde Swing-app

:::important Poortenconfiguratie
Webswing en webforJ moeten op verschillende poorten draaien om conflicten te vermijden. Zowel webforJ als Webswing draaien doorgaans op poort `8080`. Je moet ofwel de Webswing-poort of de webforJ-poort wijzigen.
:::

## Webswing server setup {#webswing-server-setup}

### Installatie en opstarten {#installation-and-startup}

1. **Download Webswing** van de [officiële website](https://www.webswing.org/en/downloads)
2. **Pak het archief uit** naar jouw voorkeurss locatie (bijv. `/opt/webswing` of `C:\webswing`)
3. **Start de server** met de platform-specifieke scripts:

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

4. **Controleer of de server draait** door `http://localhost:8080` te openen

### Toepassingsconfiguratie {#application-configuration}

Zodra de server draait, krijg toegang tot de adminconsole op `http://localhost:8080/admin` om jouw Swing-app toe te voegen en te configureren.

Configureer in de adminconsole:

- **Toepassingsnaam**: wordt onderdeel van het URL-pad (bijv. `myapp` → `http://localhost:8080/myapp/`)
- **Hoofdklasse**: het toegangspunt van jouw Swing-app
- **Classpath**: pad naar jouw app JAR en afhankelijkheden
- **JVM-argumenten**: geheugeninstellingen, systeemproperties, en andere JVM-opties
- **Thuisdirectory**: werkdirectory voor de app

Na de configuratie is jouw Swing-app toegankelijk op `http://localhost:8080/[app-name]/`

### CORS-configuratie {#cors-configuration}

Bij het embedden van Webswing in een webforJ-app die op een andere poort of domein draait, moet je Cross-Origin Resource Sharing (CORS) configureren in Webswing. Dit stelt de browser in staat om Webswing-inhoud te laden vanuit jouw webforJ-pagina.

Navigeer in de Webswing adminconsole naar de configuratie van jouw app en stel in:

- **Toegestane Oorsprongen**: Voeg de oorsprong van jouw webforJ-app toe (bijv. `http://localhost:8090` of `*` voor ontwikkeling)

Deze instelling komt overeen met de optie `allowedCorsOrigins` in de configuratie van de Webswing-app.

## webforJ integratie {#webforj-integration}

Zodra jouw Webswing-server draait met jouw Swing-app geconfigureerd en CORS ingeschakeld, kun je deze integreren in jouw webforJ-app.

### Voeg afhankelijkheid toe {#add-dependency}

Webswing-integratie is afhankelijk van de Webswing-integratiemodule van webforJ, die de `WebswingConnector` component en gerelateerde klassen biedt. Voeg het volgende toe aan jouw `pom.xml` bestand:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Basisimplementatie {#basic-implementation}

Maak een weergave die jouw Swing-app embed met behulp van de `WebswingConnector`:

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
    // Initialiseer de connector met jouw Webswing-applicatie-URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Stel de afbeeldingsafmetingen in
    connector.setSize("100%", "600px");

    // Voeg toe aan de weergave-container
    self.add(connector);
  }
}
```

De connector legt automatisch een verbinding met de Webswing-server zodra deze aan de DOM is toegevoegd. De UI van de Swing-app wordt dan weergegeven binnen de connectorcomponent.

## Configuratieopties {#configuration-options}

De `WebswingOptions` klasse stelt je in staat om het gedrag van de connector aan te passen. Standaard start de connector automatisch op wanneer deze wordt aangemaakt en gebruikt standaard verbindingseinstellingen. Je kunt dit gedrag wijzigen door een `WebswingOptions` instantie te maken en deze op de connector toe te passen.

Bijvoorbeeld, om de uitlogknop te verbergen in een productieomgeving waar je authenticatie beheert via jouw webforJ-app:

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

// Configureer en start wanneer gereed
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

De opties dekken verbindingsbeheer, authenticatie, debugging en monitoring.
