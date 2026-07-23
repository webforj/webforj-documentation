---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 4512bc42001e5f96301c60758cb0ca81
---
Spring Boot ist eine beliebte Wahl für den Bau von Java-Anwendungen und bietet Dependency Injection, Autokonfiguration und ein eingebettetes Server-Modell. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über die Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre Anwendung als ausführbare JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht cloud-nativen Bereitstellungspraktiken. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit Springs Anwendungs-Kontext zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-Anwendung mit Spring Boot zu erstellen: entweder mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp erstellt. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Bauen beginnen können.

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (Anwendungsname, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Anhand dieser Informationen erstellt startforJ ein Grundprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

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

Der Parameter `flavor` weist das Archetyp an, ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts zu generieren.

Dadurch wird ein vollständiges Spring Boot-Projekt mit erstellt:
- Spring Boot Eltern-POM-Konfiguration
- webforJ Spring Boot Starter-Abhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispielsichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Fügen Sie Spring Boot zu bestehenden Projekten hinzu {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-Anwendung haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Umwandeln Ihrer Hauptanwendungsklasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf neu erstellen. Diese Anleitung setzt **webforJ-Version 25.11 oder höher** voraus.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie das Packaging von WAR in JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als Eltern-POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>4.0.5</version>
       <relativePath/>
   </parent>
   ```

3. Entfernen Sie alle WAR-spezifischen Konfigurationen wie:
   - `maven-war-plugin`
   - Verweise auf das Verzeichnis `webapp`
   - `web.xml`-bezogene Konfigurationen

Wenn Sie bereits eine Eltern-POM haben, müssen Sie stattdessen das Spring Boot Bill of Materials (BOM) importieren:

```xml title="pom.xml"
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>4.0.5</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### Schritt 2: Spring-Abhängigkeiten hinzufügen {#step-2-add-spring-dependencies}

Fügen Sie den webforJ Spring Boot Starter zu Ihren Abhängigkeiten hinzu:

:::info[webforJ 25.11+ Vereinfachung]
Ab **webforJ-Version 25.11** enthält der `webforj-spring-boot-starter` alle Kern-abhängigkeiten von webforJ transitiv. Sie müssen die Abhängigkeit `com.webforj:webforj` nicht mehr ausdrücklich hinzufügen.

Für Versionen **vor 25.11** müssen Sie beide Abhängigkeiten separat einfügen.
:::

**Für webforJ 25.11 und höher:**

```xml title="pom.xml"
<dependencies>
  <!-- Fügen Sie den Spring Boot Starter hinzu (enthält webforJ transitiv) -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Fügen Sie Devtools hinzu -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-devtools</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

**Für Versionen vor 25.11:**

```xml title="pom.xml"
<dependencies>
  <!-- Fügen Sie die webforJ-Abhängigkeit explizit hinzu -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Fügen Sie den Spring Boot Starter hinzu -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Fügen Sie Devtools hinzu -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-devtools</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

:::tip[webforJ DevTools für automatisches Browser-Refresh]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools um automatisches Browser-Refresh. Wenn Sie Änderungen in Ihrer IDE speichern, lädt der Browser automatisch neu, ohne manuelles Eingreifen. Weitere Informationen finden Sie in der [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) Anleitung.
:::

### Schritt 3: Build-Plugins aktualisieren {#step-3-update-build-plugins}

Ersetzen Sie das Jetty-Plugin durch das Spring Boot Maven-Plugin. Entfernen Sie alle vorhandenen Jetty-Konfigurationen und fügen Sie hinzu:

```xml title="pom.xml"
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <excludeDevtools>true</excludeDevtools>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### Schritt 4: Konvertieren Sie Ihre Anwendungs-Klasse {#step-4-convert-your-app-class}

Transformieren Sie Ihre Hauptklasse `App` in eine Spring Boot-Anwendung, indem Sie die erforderlichen Spring-Anmerkungen und eine Hauptmethode hinzufügen:

```java title="Application.java"
package com.example;

import com.webforj.App;
import com.webforj.annotation.Routify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify(packages = "com.example.views")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  // Behalten Sie Ihre vorhandene run() Methode bei, falls Sie eine haben
  @Override
  public void run() throws WebforjException {
    // Ihr vorhandener Initialisierungscode
  }
}
```

Die Anmerkung `@SpringBootApplication` aktiviert die Autokonfiguration und das Komponenten-Scannen von Spring. Die Anmerkung `@Routify` bleibt unverändert und scannt weiterhin Ihre Sichtpakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständiger Klassenname des Anwendungseinstiegspunkts
webforj.entry = org.example.Application

# Anwendungsname
spring.application.name=Hallo Welt Spring

# Serverkonfiguration
server.port=8080
server.shutdown=immediate

# webforJ DevTools-Konfiguration
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Führen Sie die Spring Boot-Anwendung aus {#run-the-spring-boot-app}

Sobald alles konfiguriert ist, führen Sie Ihre Anwendung mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre vorhandenen webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration {#configuration}

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. Weitere Informationen zu den Konfigurationseigenschaften von webforJ finden Sie in [Property Configuration](/docs/configuration/properties).

Die folgenden Einstellungen in `application.properties` von webforJ sind spezifisch für Spring:

| Property | Typ | Beschreibung | Standard|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | URL-Zuordnungspattern für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Pattern, die nicht von webforJ behandelt werden sollen, wenn sie auf die Root-Zuordnung gemappt sind. Wenn webforJ auf den Root-Kontext (`/*`) gemappt ist, werden diese URL-Pattern von der Handhabung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dies ermöglicht es, dass REST-Endpunkte und andere Spring MVC-Zuordnungen neben webforJ-Routen koexistieren. | `[]` |

### Unterschiede in der Konfiguration {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt  | Standard webforJ | Spring Boot webforJ |
|---------|------------------|----------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Befehlsausführung** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Port-Konfiguration** | In Plugin-Konfiguration | `server.port` in Properties |
