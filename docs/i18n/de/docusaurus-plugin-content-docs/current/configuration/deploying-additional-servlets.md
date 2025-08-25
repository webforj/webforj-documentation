---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen standardmäßig über `WebforjServlet`, das in der web.xml auf `/*` abgebildet ist. Dieses Servlet verwaltet den Lebenszyklus von Komponenten, das Routing und die UI-Aktualisierungen, die Ihre webforJ-App antreiben.

In einigen Szenarien müssen Sie möglicherweise zusätzliche Servlets neben Ihrer webforJ-App bereitstellen:
- Integration von Drittanbieterbibliotheken, die ihre eigenen Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Verarbeitung von Dateiuploads mit benutzerdefinierter Verarbeitung
- Unterstützung von legacy servlet-basiertem Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets neben Ihrer App:

## Ansatz 1: Remapping `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz remapped das `WebforjServlet` von `/*` auf einen bestimmten Pfad wie `/ui/*`, wodurch der URL-Namespace für benutzerdefinierte Servlets freigegeben wird. Obwohl dies eine Anpassung der web.xml erfordert, ermöglicht es benutzerdefinierten Servlets den direkten Zugriff auf ihre URL-Muster ohne Proxy-Overhead.

```xml
<web-app>
  <!-- WebforjServlet remapped um nur /ui/* zu behandeln -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Benutzerdefiniertes Servlet mit eigenem URL-Muster -->
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
- Das benutzerdefinierte Servlet bearbeitet Anfragen an `/hello-world`
- Kein Proxy-Mechanismus - direktes Routing des Servlet-Containers

:::tip Spring Boot-Konfiguration
Wenn Sie webforJ mit Spring Boot verwenden, gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie das Servlet-Mapping in der `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Dieses Property remapped `WebforjServlet` von dem Standard `/*` auf `/ui/*`, wodurch der URL-Namespace für Ihre benutzerdefinierten Servlets freigegeben wird. Fügen Sie keine Anführungszeichen um den Wert hinzu - sie werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: Proxy-Konfiguration von `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz behält `WebforjServlet` bei `/*` und konfiguriert benutzerdefinierte Servlets in `webforJ.conf`. Das `WebforjServlet` fängt alle Anfragen ab und leitet übereinstimmende Muster an Ihre benutzerdefinierten Servlets weiter.

### Standard-web.xml-Konfiguration {#standard-webxml-configuration}

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

<!-- Benutzerdefiniertes Servlet mit eigenem URL-Muster -->
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
