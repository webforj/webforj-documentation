---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 4506bcc85ddfa8698f4f8138fe6b4e33
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen über `WebforjServlet`, das standardmäßig in web.xml auf `/*` zugeordnet ist. Dieser Servlet verwaltet den Komponentenlebenszyklus, das Routing und die UI-Aktualisierungen, die Ihre webforJ-App unterstützen.

In einigen Szenarien müssen Sie möglicherweise zusätzliche Servlets zusammen mit Ihrer webforJ-App bereitstellen:
- Integration von Drittanbieterbibliotheken, die ihre eigenen Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Verarbeiten von Datei-Uploads mit eigener Verarbeitung
- Unterstützung von legacy servlet-basiertem Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets zusammen mit Ihrer App:

## Ansatz 1: Umleitung von `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz leitet den `WebforjServlet` von `/*` auf einen spezifischen Pfad wie `/ui/*` um und gibt den URL-Namensraum für benutzerdefinierte Servlets frei. Obwohl dies eine Änderung von `web.xml` erfordert, erhalten benutzerdefinierte Servlets direkten Zugriff auf ihre URL-Muster ohne Proxy-Überhead.

```xml
<web-app>
  <!-- WebforjServlet umgeleitet, um nur /ui/* zu bearbeiten -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Benutzerdefinierter Servlet mit eigenem URL-Muster -->
  <servlet>
    <servlet-name>HelloWorldServlet</servlet-name>
    <servlet-class>com.example.HelloWorldServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>HelloWorldServlet</servlet-name>
    <url-pattern>/hello-world</url-pattern>
  </servlet-mapping>
</web-app>
```

Mit dieser Konfiguration:
- webforJ-Komponenten sind unter `/ui/` zugänglich
- Benutzerdefinierter Servlet bearbeitet Anfragen an `/hello-world`
- Kein Proxy-Mechanismus beteiligt - Direktes Routing des Servlet-Containers

:::tip Spring Boot-Konfiguration
Bei der Verwendung von webforJ mit Spring Boot gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie die Servlet-Zuordnung in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Diese Eigenschaft leitet `WebforjServlet` von den Standardwert `/*` auf `/ui/*` um und gibt den URL-Namensraum für Ihre benutzerdefinierten Servlets frei. Fügen Sie keine Anführungszeichen um den Wert ein - diese werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: `WebforjServlet` Proxy-Konfiguration {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz belässt `WebforjServlet` bei `/*` und konfiguriert benutzerdefinierte Servlets in `webforJ.conf`. Der `WebforjServlet` intercepts alle Anfragen und versucht, die übereinstimmenden Muster an Ihre benutzerdefinierten Servlets weiterzuleiten.

### Standard web.xml-Konfiguration {#standard-webxml-configuration}

```xml
<servlet>
  <servlet-name>WebforjServlet</servlet-name>
  <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>WebforjServlet</servlet-name>
  <url-pattern>/*</url-pattern>
</servlet-mapping>

<!-- Benutzerdefinierter Servlet mit eigenem URL-Muster -->
<servlet>
  <servlet-name>HelloWorldServlet</servlet-name>
  <servlet-class>com.example.HelloWorldServlet</servlet-class>
</servlet>
<servlet-mapping>
  <servlet-name>HelloWorldServlet</servlet-name>
  <url-pattern>/hello-world</url-pattern>
</servlet-mapping>
</web-app>
```

### webforJ.conf-Konfiguration {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Mit dieser Konfiguration:
- `WebforjServlet` bearbeitet alle Anfragen
- Anfragen an `/hello-world` werden an `HelloWorldServlet` weitergeleitet
