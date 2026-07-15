---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen standardmäßig über `WebforjServlet`, das in web.xml auf `/*` abgebildet ist. Dieses Servlet verwaltet den Komponentenlebenszyklus, das Routing und die UI-Updates, die Ihre webforJ-App antreiben.

In einigen Szenarien müssen Sie möglicherweise zusätzliche Servlets neben Ihrer webforJ-App bereitstellen:
- Integration von Drittanbieter-Bibliotheken, die eigene Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Verarbeitung von Datei-Uploads mit benutzerdefinierter Verarbeitung
- Unterstützung von legacy-Servlet-basiertem Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets neben Ihrer App:

<AISkillTip skill="webforj-adding-servlets" />

## Ansatz 1: Umbenennung von `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz benennt das `WebforjServlet` von `/*` auf einen bestimmten Pfad wie `/ui/*` um, wodurch der URL-Namespace für benutzerdefinierte Servlets freigegeben wird. Während dies eine Änderung von web.xml erfordert, erhalten benutzerdefinierte Servlets direkten Zugriff auf ihre URL-Muster ohne Proxy-Überkopf.

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
- Benutzerdefiniertes Servlet verarbeitet Anfragen an `/hello-world`
- Kein Proxy-Mechanismus beteiligt - direktes Routing im Servlet-Container

:::tip Spring Boot-Konfiguration
Beim Einsatz von webforJ mit Spring Boot gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie die Servlet-Zuordnung in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Dieses Property benennt `WebforjServlet` vom Standard `/*` auf `/ui/*` um und gibt den URL-Namespace für Ihre benutzerdefinierten Servlets frei. Fügen Sie keine Anführungszeichen um den Wert hinzu - sie werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: Proxy-Konfiguration von `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz behält `WebforjServlet` bei `/*` und konfiguriert benutzerdefinierte Servlets in `webforj.conf`. Das `WebforjServlet` interceptet alle Anfragen und leitet übereinstimmende Muster an Ihre benutzerdefinierten Servlets weiter.

### Standard-konfiguration für web.xml {#standard-webxml-configuration}

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

### Konfiguration von webforJ.conf {#webforjconf-configuration}

```hocon
servlets: [
  {
    class: "com.example.HelloWorldServlet",
    name: "hello-world",
    config: {
      foo: "bar",
      baz: "bang"
    }
  }
]
```

Mit dieser Konfiguration:
- `WebforjServlet` verarbeitet alle Anfragen
- Anfragen an `/hello-world` werden an `HelloWorldServlet` weitergeleitet
- Der optionale `config`-Schlüssel bietet Name/Wert-Paare als Initialisierungsparameter für das Servlet
