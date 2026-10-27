---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot ist eine beliebte Wahl für den Bau von Java-Anwendungen und bietet Dependency Injection, automatische Konfiguration und ein embedded Server-Modell. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere Spring-verwalte Beans direkt in Ihre UI-Komponenten über Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre Anwendung als ausführbare JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver zu bereitstellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den cloud-nativen Bereitstellungspraktiken. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Es gibt zwei Optionen, um eine neue webforJ-Anwendung mit Spring Boot zu erstellen: die grafische startforJ-Anwendung oder die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starter-Projekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starter-Projekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Bau beginnen können.

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie diese anpassen, indem Sie folgende Informationen angeben:

- Grundlegende Projektdaten (Anwendungsname, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Themenfarbe und Symbol
- Archetyp
- **Funktioniert mit** - Wählen Sie **Spring Boot**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Grundprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie die Befehlszeile verwenden möchten, generieren Sie ein Spring Boot webforJ-Projekt direkt mit den offiziellen webforJ-Archetypen:

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

Der Parameter `flavor` teilt dem Archetyp mit, ein Spring Boot-Projekt zu generieren, anstatt ein Standard-webforJ-Projekt.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Konfiguration des Spring Boot-Eltern-POM
- webforJ Spring Boot-Starter-Abhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Führen Sie die Spring Boot-Anwendung aus {#run-the-spring-boot-app}

Ein Archetyp-Projekt setzt sein Standard-Maven-Ziel, sodass `mvn` ohne Argumente die App kompiliert, den [Frontend-Watch](/docs/configuration/deploy-reload/frontend-watch) startet und die App ausführt:

```bash
mvn
```

Die App startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre vorhandenen webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und die Funktionen von Spring nutzen.

## Konfiguration {#configuration}

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre App zu konfigurieren. Weitere Informationen zu den Konfigurationseigenschaften von webforJ finden Sie unter [Eigenschaftskonfiguration](/docs/configuration/properties).

Die folgenden Einstellungen von webforJ in der `application.properties` sind spezifisch für Spring:

| Eigenschaft | Typ | Beschreibung | Standard |
|-------------|-----|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die beim Mapping auf die Root nicht von webforJ verarbeitet werden sollen. Wenn webforJ auf dem Root-Kontext (`/*`) gemappt ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern verarbeitet werden. Dies ermöglicht das Zusammenwirken von REST-Endpunkten und anderen Spring MVC-Mappings mit webforJ-Routen. | `[]` |

### Unterschiede in der Konfiguration {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Startbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
