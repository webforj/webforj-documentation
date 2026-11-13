---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot ist eine beliebte Wahl für die Entwicklung von Java-Anwendungen und bietet Dependency Injection, automatische Konfiguration und ein eingebettetes Servermodell. Wenn du Spring Boot mit webforJ verwendest, kannst du Services, Repositories und andere von Spring verwaltete Beans direkt über Konstruktor-Injection in deine UI-Komponenten injizieren.

Wenn du Spring Boot mit webforJ verwendest, läuft deine Anwendung als ausführbare JAR-Datei mit einem eingebetteten Tomcat-Server, anstatt als WAR-Datei auf einem externen Anwendungsserver bereitgestellt zu werden. Dieses Packaging-Modell vereinfacht die Bereitstellung und entspricht Cloud-nativen Bereitstellungspraktiken. Das Komponentenmodell und das Routing von webforJ arbeiten mit dem Spring-Anwendungskontext zusammen, um Abhängigkeiten und Konfigurationen zu verwalten.

## Spring-Boot-Anwendung erstellen {#create-a-spring-boot-app}

Du hast zwei Möglichkeiten, eine neue webforJ-Anwendung mit Spring Boot zu erstellen: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: startforJ verwenden {#option-1-using-startforj}
<!-- vale on -->

Am einfachsten erstellst du eine neue webforJ-Anwendung mit [startforJ](https://docs.webforj.com/startforj). Das Tool generiert ein minimales Starterprojekt auf Grundlage eines ausgewählten webforJ-Archetyps. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass du sofort mit der Entwicklung beginnen kannst.

Wenn du mit [startforJ](https://docs.webforj.com/startforj) eine Anwendung erstellst, kannst du sie mit den folgenden Angaben anpassen:

- Grundlegende Projektmetadaten (Anwendungsname, Group ID, Artifact ID)
- webforJ-Version und Java-Version
- Theme-Farbe und Symbol
- Archetyp
- **Läuft auf** – Wähle **Spring Boot**, um ein Spring-Boot-Projekt zu erstellen

Auf Grundlage dieser Angaben erstellt startforJ ein einfaches Projekt aus dem ausgewählten Archetyp, das für Spring Boot konfiguriert ist.
Du kannst dein Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen.

### Option 2: Befehlszeile verwenden {#option-2-using-the-command-line}

Wenn du lieber die Befehlszeile verwendest, kannst du mit den offiziellen webforJ-Archetypen direkt ein webforJ-Projekt mit Spring Boot generieren:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

Der Parameter `flavor` weist den Archetyp an, ein Spring-Boot-Projekt anstelle eines standardmäßigen webforJ-Projekts zu generieren.

Dadurch wird ein vollständiges Spring-Boot-Projekt erstellt mit:
- Spring-Boot-Parent-POM-Konfiguration
- webforJ-Spring-Boot-Starter-Abhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für Spring und webforJ

## Spring-Boot-Anwendung ausführen {#run-the-spring-boot-app}

Ein Archetyp-Projekt legt sein standardmäßiges Maven-Ziel fest. Daher kompiliert `mvn` ohne Argumente die Anwendung, startet den [Frontend-Watch-Modus](/docs/configuration/deploy-reload/frontend-watch) und führt die Anwendung aus:

```bash
mvn
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Deine vorhandenen webforJ-Ansichten und Routen funktionieren wie zuvor. Jetzt kannst du jedoch Spring-Beans injizieren und Spring-Funktionen verwenden.

## Konfiguration {#configuration}

Verwende die Datei `application.properties` in `src/main/resources`, um deine Anwendung zu konfigurieren.
Weitere Informationen zu den Konfigurationseigenschaften von webforJ findest du unter [Property Configuration](/docs/configuration/properties).

Die folgenden webforJ-Einstellungen in `application.properties` gelten speziell für Spring:

| Eigenschaft | Typ | Beschreibung | Standardwert|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | URL-Zuordnungsmuster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die nicht von webforJ verarbeitet werden sollen, wenn webforJ auf den Root-Kontext gemappt ist. Wenn webforJ dem Root-Kontext (`/*`) zugeordnet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgenommen und können stattdessen von Spring-MVC-Controllern verarbeitet werden. So können REST-Endpunkte und andere Spring-MVC-Zuordnungen neben webforJ-Routen bestehen. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Beim Wechsel zu Spring Boot ändern sich mehrere Aspekte der Konfiguration:

| Aspekt | Standardmäßiges webforJ | webforJ mit Spring Boot |
|--------|-----------------|-------------------|
| **Packaging** | WAR-Datei | Ausführbare JAR-Datei |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn` | `mvn` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Properties |

:::info Mit `mvn` statt `mvn spring-boot:run` ausführen
`mvn spring-boot:run` startet nur die Anwendung. Der Befehl startet nicht den [Frontend-Watch-Modus](/docs/configuration/deploy-reload/frontend-watch). Daher werden Quellen unter `src/main/frontend` – sofern dein Projekt welche enthält – während der Laufzeit der Anwendung nicht kompiliert oder neu erstellt. Ein einfaches `mvn` führt das standardmäßige Ziel des Projekts aus, das zunächst den Watch-Modus und anschließend die Anwendung startet.
:::
