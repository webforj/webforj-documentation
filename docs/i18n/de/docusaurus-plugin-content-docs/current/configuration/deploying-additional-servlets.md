---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen standardmäßig über `WebforjServlet`, das in web.xml auf `/*` abgebildet ist. Dieses Servlet verwaltet den Lebenszyklus der Komponenten, das Routing und die UI-Aktualisierungen, die Ihre webforJ-App antreiben.

In einigen Szenarien müssen Sie möglicherweise zusätzliche Servlets zusammen mit Ihrer webforJ-App bereitstellen:
- Integration von Drittanbieterbibliotheken, die eigene Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Handhabung von Datei-Uploads mit benutzerdefinierten Verarbeitungen
- Unterstützung von legacy servlet-basiertem Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets zusammen mit Ihrer App:

## Ansatz 1: Neuausbildung von `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz bildet das `WebforjServlet` von `/*` auf einen spezifischen Pfad wie `/ui/*` um und gibt den URL-Namespace für benutzerdefinierte Servlets frei. Obwohl dies eine Änderung von `web.xml` erfordert, haben benutzerdefinierte Servlets direkten Zugriff auf ihre URL-Muster ohne Proxy-Überkopf.

```xml
<web-app>
  <!-- WebforjServlet umgeleitet, um nur /ui/* zu verarbeiten -->
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
- Benutzerdefiniertes Servlet bearbeitet Anfragen an `/hello-world`
- Kein Proxy-Mechanismus beteiligt - direkte Servlet-Container-Routing

:::tip Spring Boot-Konfiguration
Beim Einsatz von webforJ mit Spring Boot gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie das Servlet-Mapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Dieses Property bildet das `WebforjServlet` von dem Standard `/*` auf `/ui/*` um und gibt den URL-Namespace für Ihre benutzerdefinierten Servlets frei. Schließen Sie keine Anführungszeichen um den Wert ein - diese werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: `WebforjServlet` Proxy-Konfiguration {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz behält das `WebforjServlet` bei `/*` und konfiguriert benutzerdefinierte Servlets in `webforj.conf`. Das `WebforjServlet` interceptiert alle Anfragen und protokolliert übereinstimmende Muster an Ihre benutzerdefinierten Servlets.

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
