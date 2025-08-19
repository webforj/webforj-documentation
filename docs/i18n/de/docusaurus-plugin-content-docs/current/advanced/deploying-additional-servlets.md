---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen standardmäßig über `WebforjServlet`, das in web.xml auf `/*` gemappt ist. Dieser Servlet verwaltet den Lebenszyklus der Komponenten, das Routing und die UI-Updates, die Ihre webforJ-Anwendung antreiben.

In einigen Szenarien kann es erforderlich sein, zusätzliche Servlets neben Ihrer webforJ-Anwendung bereitzustellen:
- Integration von Drittanbieterbibliotheken, die eigene Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Verarbeitung von Datei-Uploads mit benutzerdefinierter Verarbeitung
- Unterstützung von älterem servlet-basierten Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets neben Ihrer Anwendung:

## Ansatz 1: Umbenennung von `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz ändert die Zuordnung von `WebforjServlet` von `/*` auf einen spezifischen Pfad wie `/ui/*`, wodurch der URL-Namespace für benutzerdefinierte Servlets freigegeben wird. Obwohl dies eine Modifikation der web.xml erfordert, ermöglicht es benutzerdefinierten Servlets den direkten Zugriff auf ihre URL-Muster ohne Proxy-Overhead.

```xml
<web-app>
  <!-- WebforjServlet umbenannt, um nur /ui/* zu verarbeiten -->
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
- Benutzerdefinierter Servlet verarbeitet Anfragen an `/hello-world`
- Kein Proxy-Mechanismus beteiligt - direkte Servlet-Container-Routing

:::tip Spring Boot-Konfiguration
Bei der Verwendung von webforJ mit Spring Boot gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie die Servlet-Zuordnung in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Diese Eigenschaft ändert die Zuordnung von `WebforjServlet` von der Standard-`/*` auf `/ui/*` und gibt den URL-Namespace für Ihre benutzerdefinierten Servlets frei. Fügen Sie keine Anführungszeichen um den Wert hinzu - sie werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: `WebforjServlet` Proxy-Konfiguration {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz behält `WebforjServlet` bei `/*` bei und konfiguriert benutzerdefinierte Servlets in `webforJ.conf`. Der `WebforjServlet` unterbricht alle Anfragen und leitet übereinstimmende Muster an Ihre benutzerdefinierten Servlets weiter.

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
- `WebforjServlet` verarbeitet alle Anfragen
- Anfragen an `/hello-world` werden an `HelloWorldServlet` weitergeleitet
