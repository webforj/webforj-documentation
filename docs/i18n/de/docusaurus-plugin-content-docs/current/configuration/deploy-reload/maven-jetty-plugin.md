---
title: Jetty
sidebar_position: 40
description: >-
  Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin,
  with live reload and hotswap during development.
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
Das Maven Jetty-Plugin führt die Anwendung in einem eingebetteten Jetty-Server direkt aus dem Projekt aus. Ein Archetypen-Projekt setzt `compile webforj:watch jetty:run` als sein Standard-Maven-Ziel, sodass `mvn` ohne Argumente die App kompiliert, die [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) startet und die App auf Jetty bereitstellt.

## Anforderungen {#requirements}

Ein Jetty-Projekt erklärt die Entwicklungswerkzeuge selbst im Profil, das für Entwicklungsdurchläufe verwendet wird:

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

Die Version stammt aus dem webforJ Bill of Materials (BOM). Das Profil hält die Abhängigkeit aus dem gepackten WAR heraus. Ein aus einem [Archetypen](/docs/introduction/getting-started) erstelltes Projekt hat dieses Profil.

## Live-Reload aktivieren {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Die Schlüssel sind dieselben, die eine Spring Boot-App in `application.properties` setzt, wie in den [Einstellungen](/docs/configuration/deploy-reload/overview#settings) aufgelistet.

## Klassenänderungen {#class-changes}

Mit einem [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap), das konfiguriert ist, wendet das Tool Klassenänderungen an und Jetty redeployt nichts. Zwei Jetty-Eigenschaften unterstützen dies, und ein Archetypen-Projekt setzt beide:

- `scan` ist `0`, was die Dateiscannung von Jetty deaktiviert.
- `deployMode` bleibt ungesetzt. Hotswap erfordert den geforkten Modus, und das Plugin wählt ihn aus. Ein Build, der `deployMode` auf einen anderen Wert setzt, startet ohne das Tool und protokolliert dies.

Ohne ein Hotswap-Tool setzen Sie `scan` auf ein Intervall in Sekunden, und Jetty redeployt die App, wenn sich kompilierte Klassen oder Ressourcen ändern:

| Eigenschaft | Beschreibung | Standard |
|-------------|--------------|----------|
| `scan` | Intervall in Sekunden zwischen Scans des kompilierten Outputs, gesetzt als `jetty.scan`-Eigenschaft. `0` schaltet das Scannen aus. Längere Intervalle reduzieren die Last und verzögern das Redeploy. | `1` |

## Nutzungshinweise {#usage-considerations}

- **Speicher und CPU**: niedrige `scan`-Werte erhöhen den Ressourcenverbrauch bei großen Projekten. Längere Intervalle senken ihn und verzögern das Redeploy.
- **Nur Entwicklung**: Das Jetty-Plugin ist nicht für Produktionsbereitstellungen gedacht.
- **Sitzungen**: Ein Redeploy kann Benutzersitzungen unterbrechen. Ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) wendet Änderungen ohne ein Redeploy an, und die Sitzung bleibt bestehen.
