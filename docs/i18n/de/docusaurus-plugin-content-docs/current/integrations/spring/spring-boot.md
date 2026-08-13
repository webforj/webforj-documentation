---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 8664ccf60a8cd3a84330aabbc75c3a3b
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen und bietet Dependency Injection, Auto-Konfiguration und ein eingebettetes Servermodell. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre Anwendung als ausführbare JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und orientiert sich an cloud-nativen Bereitstellungsmethoden. Das Komponentenmodell und die Routen von webforJ arbeiten neben dem Anwendungs-Kontext von Spring, um Abhängigkeiten und Konfiguration zu verwalten.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Optionen zum Erstellen einer neuen webforJ-Anwendung mit Spring Boot: die Verwendung des grafischen Tools startforJ oder der Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Die einfachste Möglichkeit, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort darauf aufbauen können.

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Basisprojekt-Metadaten (App-Name, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring** aus, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Grundprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können Ihr Projekt als ZIP-Datei herunterladen oder direkt bei GitHub veröffentlichen.

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, generieren Sie ein Spring Boot webforJ-Projekt direkt mit den offiziellen webforJ-Archetypen:

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

Der Parameter `flavor` weist das Archetyp an, ein Spring Boot-Projekt anstelle eines standardmäßigen webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Spring Boot übergeordnete POM-Konfiguration
- webforJ Spring Boot Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien sowohl für Spring als auch für webforJ

## Führen Sie die Spring Boot-Anwendung aus {#run-the-spring-boot-app}

Ein Archetyp-Projekt setzt sein Standard-Maven-Ziel, sodass `mvn` ohne Argumente die Anwendung kompiliert, die [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) startet und die Anwendung ausführt:

```bash
mvn
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre bestehenden webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration {#configuration}

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. Weitere Informationen zu webforJ-Konfigurationsparametern finden Sie unter [Property Configuration](/docs/configuration/properties).

Die folgenden webforJ `application.properties`-Einstellungen sind spezifisch für Spring:

| Eigenschaft | Typ | Beschreibung | Standard|
|-------------|-----|--------------|---------|
| **`webforj.servlet-mapping`** | String | URL-Zuordnungs-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die nicht von webforJ behandelt werden sollen, wenn sie im Stammverzeichnis zugeordnet sind. Wenn webforJ im Stammkontext (`/*`) zugeordnet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dadurch können REST-Endpunkte und andere Spring MVC-Zuordnungen mit webforJ-Routen koexistieren. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profil}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
