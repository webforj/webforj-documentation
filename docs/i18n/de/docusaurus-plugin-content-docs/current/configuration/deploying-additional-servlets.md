---
sidebar_position: 35
title: Deploying Additional Servlets
_i18n_hash: e7f1a0bcf3986ff50dcfd89281ab3339
---
<!-- vale off -->
# Bereitstellung zusätzlicher Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ leitet alle Anfragen standardmäßig über den `WebforjServlet`, der in web.xml auf `/*` gemappt ist. Dieser Servlet verwaltet den Lebenszyklus von Komponenten, Routing und UI-Aktualisierungen, die Ihre webforJ-Anwendung antreiben.

In einigen Szenarien müssen Sie möglicherweise zusätzliche Servlets neben Ihrer webforJ-Anwendung bereitstellen:
- Integration von Drittanbieterbibliotheken, die eigene Servlets bereitstellen
- Implementierung von REST-APIs oder Webhooks
- Verarbeitung von Datei-Uploads mit benutzerdefinierten Prozessen
- Unterstützung von veraltetem servlet-basiertem Code

webforJ bietet zwei Ansätze zur Bereitstellung benutzerdefinierter Servlets alongside Ihrer Anwendung:

<AISkillTip skill="webforj-adding-servlets" />

## Ansatz 1: Neumapping von `WebforjServlet` {#approach-1-remapping-webforjservlet}

Dieser Ansatz remappt den `WebforjServlet` von `/*` auf einen spezifischen Pfad wie `/ui/*`, wodurch der URL-Namespace für benutzerdefinierte Servlets freigegeben wird. Während dies eine Änderung von `web.xml` erfordert, erhalten benutzerdefinierte Servlets direkten Zugriff auf ihre URL-Muster ohne Proxy-Überkopf.

```xml
<web-app>
  <!-- WebforjServlet neu gemappt zur Handhabung nur von /ui/* -->
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
- Kein Proxy-Mechanismus involviert - direkte Routing im Servlet-Container

:::tip Spring Boot-Konfiguration
Wenn Sie webforJ mit Spring Boot verwenden, gibt es keine `web.xml`-Datei. Stattdessen konfigurieren Sie das Servlet-Mapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Diese Eigenschaft remappt `WebforjServlet` von dem Standard `/*` auf `/ui/*`, wodurch der URL-Namespace für Ihre benutzerdefinierten Servlets freigegeben wird. Schließen Sie keine Anführungszeichen um den Wert ein - diese werden als Teil des URL-Musters interpretiert.
:::

## Ansatz 2: `WebforjServlet` Proxy-Konfiguration {#approach-2-webforjservlet-proxy-configuration}

Dieser Ansatz behält den `WebforjServlet` bei `/*` und konfiguriert benutzerdefinierte Servlets in `webforj.conf`. Der `WebforjServlet` fängt alle Anfragen ab und proxied Übereinstimmungen zu Ihren benutzerdefinierten Servlets.

### Standard web.xml Konfiguration {#standard-webxml-configuration}

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

### webforJ.conf Konfiguration {#webforjconf-configuration}

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
- Der optionale Schlüssel `config` bietet Name/Wert-Paare als Initialisierungsparameter für den Servlet
