---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: d948bababbedcfe831d4af62f8b6b088
---
Die Integration von Webswing mit webforJ umfasst zwei Komponenten: den Webswing-Server, der Ihre Swing-App hostet, und die Komponente `WebswingConnector` in Ihrer webforJ-App, die sie einbettet.

## Voraussetzungen {#prerequisites}

Bevor Sie beginnen, stellen Sie sicher, dass Sie die folgenden Voraussetzungen erfüllen:

- **Java-Desktop-App**: eine Swing-, JavaFX- oder SWT-App, die als JAR-Datei verpackt ist
- **Webswing-Server**: herunterladen von [webswing.org](https://webswing.org)
- **webforJ-Version `25.10` oder höher**: erforderlich für die Unterstützung des `WebswingConnector`

## Architekturübersicht {#architecture-overview}

Die Integrationsarchitektur besteht aus:

1. **Webswing-Server**: führt Ihre Swing-App aus, erfasst das GUI-Rendering und verarbeitet Benutzereingaben
2. **webforJ-Anwendung**: hostet Ihre Web-App mit dem eingebetteten `WebswingConnector`
3. **Browser-Client**: zeigt sowohl die webforJ-Benutzeroberfläche als auch die eingebettete Swing-App an

:::important Port-Konfiguration
Webswing und webforJ müssen auf unterschiedlichen Ports ausgeführt werden, um Konflikte zu vermeiden. Sowohl webforJ als auch Webswing laufen typischerweise auf dem Port `8080`. Sie sollten entweder den Webswing-Port oder den webforJ-Port ändern.
:::

## Einrichtung des Webswing-Servers {#webswing-server-setup}

### Installation und Start {#installation-and-startup}

1. **Laden Sie Webswing herunter** von der [offiziellen Website](https://www.webswing.org/en/downloads)
2. **Entpacken Sie das Archiv** an Ihren bevorzugten Speicherort (z.B. `/opt/webswing` oder `C:\webswing`)
3. **Starten Sie den Server** mit den plattformspezifischen Skripten:

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

4. **Überprüfen Sie, ob der Server läuft**, indem Sie `http://localhost:8080` aufrufen

### Anwendungs-Konfiguration {#application-configuration}

Sobald der Server läuft, greifen Sie auf die Admin-Konsole unter `http://localhost:8080/admin` zu, um Ihre Swing-App hinzuzufügen und zu konfigurieren.

In der Admin-Konsole konfigurieren Sie:

- **Anwendungsname**: wird Teil des URL-Pfades (z.B. `myapp` → `http://localhost:8080/myapp/`)
- **Hauptklasse**: der Einstiegspunkt Ihrer Swing-App
- **Classpath**: Pfad zu Ihrer App-JAR und Abhängigkeiten
- **JVM-Argumente**: Speichereinstellungen, Systemeigenschaften und andere JVM-Optionen
- **Home-Verzeichnis**: Arbeitsverzeichnis für die App

Nach der Konfiguration ist Ihre Swing-App unter `http://localhost:8080/[app-name]/` zugänglich.

### CORS-Konfiguration {#cors-configuration}

Wenn Sie Webswing in einer webforJ-App einbetten, die auf einem anderen Port oder einer anderen Domain läuft, müssen Sie Cross-Origin Resource Sharing (CORS) in Webswing konfigurieren. Dies ermöglicht es dem Browser, Webswing-Inhalte von Ihrer webforJ-Seite zu laden.

Navigieren Sie in der Webswing-Admin-Konsole zur Konfiguration Ihrer App und setzen Sie:

- **Erlaubte Ursprünge**: Fügen Sie den Ursprung Ihrer webforJ-App hinzu (z.B. `http://localhost:8090` oder `*` für die Entwicklung)

Diese Einstellung entspricht der Option `allowedCorsOrigins` in der App-Konfiguration von Webswing.

## webforJ-Integration {#webforj-integration}

Sobald Ihr Webswing-Server mit Ihrer konfigurierten Swing-App läuft und CORS aktiviert ist, können Sie ihn in Ihre webforJ-App integrieren.

### Abhängigkeit hinzufügen {#add-dependency}

Die Webswing-Integration hängt von dem Webswing-Integrationsmodul von webforJ ab, das die Komponente `WebswingConnector` und verwandte Klassen bereitstellt. Fügen Sie Folgendes zu Ihrer `pom.xml`-Datei hinzu:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Grundlegende Implementierung {#basic-implementation}

Erstellen Sie eine Ansicht, die Ihre Swing-App mithilfe des `WebswingConnector` einbettet:

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
    // Initialisieren Sie den Connector mit Ihrer Webswing-Anwendungs-URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Setzen Sie die Anzeigedimensionen
    connector.setSize("100%", "600px");

    // Zum Ansichtscontainer hinzufügen
    self.add(connector);
  }
}
```

Der Connector stellt automatisch eine Verbindung zum Webswing-Server her, wenn er in das DOM eingefügt wird. Die Benutzeroberfläche der Swing-App wird dann innerhalb der Connector-Komponente gerendert.

## Konfigurationsoptionen {#configuration-options}

Die Klasse `WebswingOptions` ermöglicht es Ihnen, das Verhalten des Connectors anzupassen. Standardmäßig wird der Connector automatisch gestartet, wenn er erstellt wird und verwendet die standardmäßigen Verbindungseinstellungen. Sie können dieses Verhalten ändern, indem Sie eine Instanz von `WebswingOptions` erstellen und auf den Connector anwenden.

Zum Beispiel, um den Logout-Button in einer Produktionsumgebung auszublenden, in der Sie die Authentifizierung über Ihre webforJ-App verwalten:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
  .setDisableLogout(true);  // Logout-Button ausblenden

connector.setOptions(options);
```

Oder wenn Sie manuelle Kontrolle darüber benötigen, wann die Verbindung gestartet wird:

```java
// Connector ohne automatischen Start erstellen
WebswingConnector connector = new WebswingConnector(url, false);

// Konfigurieren und starten, wenn bereit
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Die Optionen beziehen sich auf die Verwaltung von Verbindungen, Authentifizierung, Debugging und Überwachung.
