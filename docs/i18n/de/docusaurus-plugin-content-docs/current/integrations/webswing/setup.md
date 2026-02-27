---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: 76bc55d5b841ae3c06bcd2cd9e8b2632
---
Die Integration von Webswing mit webforJ umfasst zwei Komponenten: den Webswing-Server, der Ihre Swing-Anwendung hostet, und die `WebswingConnector`-Komponente in Ihrer webforJ-Anwendung, die sie einbettet.

## Voraussetzungen {#prerequisites}

Bevor Sie beginnen, stellen Sie sicher, dass Sie die folgenden Voraussetzungen haben:

- **Java-Desktopanwendung**: eine Swing-, JavaFX- oder SWT-Anwendung, die als JAR-Datei verpackt ist
- **Webswing-Server**: herunterladen von [webswing.org](https://webswing.org)
- **webforJ Version `25.10` oder höher**: erforderlich für die Unterstützung von `WebswingConnector`

## Architekturübersicht {#architecture-overview}

Die Integrationsarchitektur besteht aus:

1. **Webswing-Server**: führt Ihre Swing-Anwendung aus, erfasst die GUI-Darstellung und verarbeitet Benutzereingaben
2. **webforJ-Anwendung**: hostet Ihre Webanwendung mit dem eingebetteten `WebswingConnector`
3. **Browser-Client**: zeigt sowohl die webforJ-Benutzeroberfläche als auch die eingebettete Swing-Anwendung an

:::important Portkonfiguration
Webswing und webforJ müssen auf verschiedenen Ports laufen, um Konflikte zu vermeiden. Sowohl webforJ als auch Webswing laufen typischerweise auf Port `8080`. Sie sollten entweder den Webswing-Port oder den webforJ-Port ändern.
:::

## Einrichtung des Webswing-Servers {#webswing-server-setup}

### Installation und Start {#installation-and-startup}

1. **Webswing herunterladen** von der [offiziellen Website](https://www.webswing.org/en/downloads)
2. **Das Archiv extrahieren** an Ihren bevorzugten Speicherort (z. B. `/opt/webswing` oder `C:\webswing`)
3. **Den Server starten** mit den plattformspezifischen Skripten:

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

4. **Überprüfen, ob der Server läuft**, indem Sie auf `http://localhost:8080` zugreifen

### Anwendungsconfigurations {#application-configuration}

Sobald der Server läuft, greifen Sie auf die Administrationskonsole unter `http://localhost:8080/admin` zu, um Ihre Swing-Anwendung hinzuzufügen und zu konfigurieren.

In der Administrationskonsole konfigurieren Sie:

- **Anwendungsname**: wird Teil des URL-Pfads (z. B. `myapp` → `http://localhost:8080/myapp/`)
- **Hauptklasse**: der Einstiegspunkt Ihrer Swing-Anwendung
- **Classpath**: Pfad zu Ihrer App-JAR und den Abhängigkeiten
- **JVM-Argumente**: Speichereinstellungen, Systemeigenschaften und andere JVM-Optionen
- **Arbeitsverzeichnis**: Arbeitsverzeichnis für die Anwendung

Nach der Konfiguration ist Ihre Swing-Anwendung unter `http://localhost:8080/[app-name]/` zugänglich.

### CORS-Konfiguration {#cors-configuration}

Wenn Sie Webswing in einer webforJ-Anwendung einbetten, die auf einem anderen Port oder einer anderen Domain läuft, müssen Sie Cross-Origin Resource Sharing (CORS) in Webswing konfigurieren. Dies ermöglicht dem Browser, Webswing-Inhalte von Ihrer webforJ-Seite zu laden.

Navigieren Sie in der Webswing-Administrationskonsole zur Konfiguration Ihrer Anwendung und setzen Sie:

- **Erlaubte Ursprünge**: Fügen Sie den Ursprung Ihrer webforJ-Anwendung hinzu (z. B. `http://localhost:8090` oder `*` für die Entwicklung)

Diese Einstellung entspricht der Option `allowedCorsOrigins` in der App-Konfiguration von Webswing.

## webforJ-Integration {#webforj-integration}

Sobald Ihr Webswing-Server mit Ihrer konfigurierten Swing-Anwendung läuft und CORS aktiviert ist, können Sie ihn in Ihre webforJ-Anwendung integrieren.

### Abhängigkeit hinzufügen {#add-dependency}

Die Webswing-Integration hängt vom Webswing-Integrationsmodul von webforJ ab, das die `WebswingConnector`-Komponente und verwandte Klassen bereitstellt. Fügen Sie Folgendes zu Ihrer `pom.xml`-Datei hinzu:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Grundlegende Implementierung {#basic-implementation}

Erstellen Sie eine Ansicht, die Ihre Swing-Anwendung mit dem `WebswingConnector` einbettet:

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
    // Initialisieren Sie den Connector mit der URL Ihrer Webswing-Anwendung
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Setzen Sie die Anzeigedimensionen
    connector.setSize("100%", "600px");

    // Fügen Sie es dem Ansichtscontainer hinzu
    getBoundComponent().add(connector);
  }
}
```

Der Connector stellt automatisch eine Verbindung zum Webswing-Server her, wenn er zum DOM hinzugefügt wird. Die Benutzeroberfläche der Swing-Anwendung wird dann innerhalb der Connector-Komponente gerendert.

## Konfigurationsoptionen {#configuration-options}

Die Klasse `WebswingOptions` ermöglicht es Ihnen, das Verhalten des Connectors anzupassen. Standardmäßig startet der Connector automatisch, wenn er erstellt wird, und verwendet die Standardeinstellungen für die Verbindung. Sie können dieses Verhalten ändern, indem Sie eine Instanz von `WebswingOptions` erstellen und sie dem Connector zuweisen.

Zum Beispiel, um die Abmelde-Schaltfläche in einer Produktionsumgebung, in der Sie die Authentifizierung über Ihre webforJ-Anwendung verwalten, auszublenden:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Abmelde-Schaltfläche ausblenden

connector.setOptions(options);
```

Oder wenn Sie manuelle Kontrolle darüber benötigen, wann die Verbindung startet:

```java
// Connector ohne automatischen Start erstellen
WebswingConnector connector = new WebswingConnector(url, false);

// Konfigurieren und starten, wenn bereit
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Die Optionen decken das Verbindungsmanagement, die Authentifizierung, das Debugging und das Monitoring ab.
