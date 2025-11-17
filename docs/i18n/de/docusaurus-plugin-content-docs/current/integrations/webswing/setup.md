---
title: Einrichtung und Konfiguration
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
Webswing mit webforJ zu integrieren umfasst zwei Komponenten: den Webswing-Server, der Ihre Swing-App hostet, und die `WebswingConnector`-Komponente in Ihrer webforJ-App, die sie einbettet.

## Voraussetzungen

Bevor Sie beginnen, stellen Sie sicher, dass Sie die folgenden Voraussetzungen haben:

- **Java-Desktop-App**: eine Swing-, JavaFX- oder SWT-App, die als JAR-Datei verpackt ist
- **Webswing-Server**: herunterladen von [webswing.org](https://webswing.org)
- **webforJ-Version `25.10` oder höher**: erforderlich für die Unterstützung des `WebswingConnector`

## Architekturübersicht

Die Integrationsarchitektur besteht aus:

1. **Webswing-Server**: führt Ihre Swing-App aus, erfasst das GUI-Rendering und verarbeitet Benutzereingaben
2. **webforJ-Anwendung**: hostet Ihre Web-App mit dem eingebetteten `WebswingConnector`
3. **Browser-Client**: zeigt sowohl die webforJ-Benutzeroberfläche als auch die eingebettete Swing-App an

:::important Port-Konfiguration
Webswing und webforJ müssen auf unterschiedlichen Ports ausgeführt werden, um Konflikte zu vermeiden. Sowohl webforJ als auch Webswing laufen typischerweise auf Port `8080`. Sie sollten entweder den Webswing-Port oder den webforJ-Port ändern.
:::

## Webswing-Server-Setup

### Installation und Start

1. **Laden Sie Webswing herunter** von der [offiziellen Website](https://www.webswing.org/en/downloads)
2. **Entpacken Sie das Archiv** an Ihrem bevorzugten Standort (z. B. `/opt/webswing` oder `C:\webswing`)
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

### Anwendungs-Konfiguration

Sobald der Server läuft, greifen Sie auf die Admin-Konsole unter `http://localhost:8080/admin` zu, um Ihre Swing-App hinzuzufügen und zu konfigurieren.

In der Admin-Konsole konfigurieren Sie:

- **Anwendungsname**: wird Teil des URL-Pfads (z. B. `myapp` → `http://localhost:8080/myapp/`)
- **Hauptklasse**: der Einstiegspunkt Ihrer Swing-App
- **Classpath**: Pfad zu Ihrer App-JAR und Abhängigkeiten
- **JVM-Argumente**: Speichereinstellungen, Systemeigenschaften und andere JVM-Optionen
- **Home-Verzeichnis**: Arbeitsverzeichnis für die App

Nach der Konfiguration ist Ihre Swing-App unter `http://localhost:8080/[app-name]/` zugänglich.

## webforJ-Integration

Sobald Ihr Webswing-Server mit Ihrer konfigurierten Swing-App läuft, können Sie ihn in Ihre webforJ-App integrieren. Dies umfasst das Hinzufügen der Abhängigkeit, die Konfiguration von Cross-Origin Resource Sharing (CORS) und die Erstellung einer Ansicht mit der `WebswingConnector`-Komponente.

### Abhängigkeit hinzufügen

Fügen Sie das Webswing-Integrationsmodul zu Ihrem webforJ-Projekt hinzu. Dadurch wird die `WebswingConnector`-Komponente und verwandte Klassen bereitgestellt.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### CORS-Konfiguration

Die Konfiguration von Cross-Origin Resource Sharing (CORS) ist erforderlich, wenn Webswing und webforJ auf unterschiedlichen Ports oder Domains ausgeführt werden. Die Same-Origin-Policy des Browsers blockiert Anfragen zwischen unterschiedlichen Ursprüngen ohne geeignete CORS-Header.

Erstellen Sie einen Servlet-Filter, um Ihrer webforJ-App CORS-Header hinzuzufügen:

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

Registrieren Sie den Filter in Ihrer `web.xml`:

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

:::important Zugriff in Produktionsumgebungen
Für Produktionsumgebungen ersetzen Sie das Sternchen (`*`) in `Access-Control-Allow-Origin` durch die spezifische URL Ihres Webswing-Servers zur Sicherheit.
:::

### Grundlegende Implementierung

Erstellen Sie eine Ansicht, die Ihre Swing-App mit dem `WebswingConnector` einbettet:

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

    // Setzen Sie die Anzeigeabmessungen
    connector.setSize("100%", "600px");

    // Zum Ansichtscontainer hinzufügen
    getBoundComponent().add(connector);
  }
}
```

Der Connector stellt automatisch eine Verbindung zum Webswing-Server her, wenn er zum DOM hinzugefügt wird. Die Benutzeroberfläche der Swing-App wird dann im Connector-Komponenten gerendert.

## Konfigurationsoptionen

Die Klasse `WebswingOptions` ermöglicht es Ihnen, das Verhalten des Connectors anzupassen. Standardmäßig beginnt der Connector automatisch, wenn er erstellt wird, und verwendet standardmäßige Verbindungseinstellungen. Sie können dieses Verhalten ändern, indem Sie eine `WebswingOptions`-Instanz erstellen und sie auf den Connector anwenden.

Zum Beispiel, um die Abmeldetaste in einer Produktionsumgebung zu verbergen, in der Sie die Authentifizierung über Ihre webforJ-App verwalten:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Abmeldetaste ausblenden

connector.setOptions(options);
```

Oder wenn Sie die manuelle Kontrolle darüber benötigen, wann die Verbindung startet:

```java
// Connector ohne automatischen Start erstellen
WebswingConnector connector = new WebswingConnector(url, false);

// Konfigurieren und starten, wenn bereit
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Die Optionen decken das Verbindungsmanagement, die Authentifizierung, das Debugging und die Überwachung ab.
