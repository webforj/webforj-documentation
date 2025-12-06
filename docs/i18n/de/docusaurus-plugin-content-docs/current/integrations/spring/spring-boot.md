---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 23d00e916452c8a8f037d2e666b2300c
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen, die Dependency Injection, automatische Konfiguration und ein eingebettetes Servermodell bietet. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositorys und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über die Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, wird Ihre Anwendung als ausführbare JAR mit einem eingebetteten Tomcat-Server ausgeführt, anstatt eine WAR-Datei auf einem externen Anwendungsserver zu deployen. Dieses Verpackungsmodell vereinfacht das Deployment und entspricht den cloud-nativen Bereitstellungspraktiken. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-Anwendung mit Spring Boot zu erstellen: entweder mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

### Option 1: Verwendung von startforJ {#option-1-using-startforj}

Der einfachste Weg, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, so dass Sie sofort mit dem Bauen beginnen können.

Wenn Sie mit [startforJ](https://docs.webforj.com/startforj) eine Anwendung erstellen, können Sie diese anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Icon
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen wird startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp erstellen, das für Spring Boot konfiguriert ist. Sie haben die Möglichkeit, Ihr Projekt als ZIP-Datei herunterzuladen oder es direkt auf GitHub zu veröffentlichen.

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

Der Parameter `flavor` informiert das Archetyp, dass ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts generiert werden soll.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Spring Boot-Parent-POM-Konfiguration
- webforJ Spring Boot Starter-Abhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispielsichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-Anwendung haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Umwandeln Ihrer Hauptanwendungsklasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf neu erstellen.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Führen Sie die folgenden Änderungen in Ihrer POM-Datei durch:

1. Ändern Sie das Packaging von WAR auf JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als Parent-POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Entfernen Sie alle WAR-spezifischen Konfigurationen, wie:
   - `maven-war-plugin`
   - Verweise auf das `webapp`-Verzeichnis
   - `web.xml`-bezogene Konfiguration

Wenn Sie bereits eine Parent-POM haben, müssen Sie stattdessen das Spring Boot Bill of Materials (BOM) importieren:

```xml title="pom.xml"
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.5.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Schritt 2: Spring-Abhängigkeiten hinzufügen {#step-2-add-spring-dependencies}

Fügen Sie den webforJ Spring Boot Starter zu Ihren Abhängigkeiten hinzu. Behalten Sie Ihre bestehende webforJ-Abhängigkeit:

```xml title="pom.xml"
<dependencies>
    <!-- Ihre bestehende webforJ-Abhängigkeit -->
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

    <!-- Devtools hinzufügen -->
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

:::tip[webforJ DevTools für automatische Browser-Aktualisierung]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools mit automatischer Browser-Aktualisierung. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) Anleitung für Konfigurationsdetails.
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

### Schritt 4: Ihre Anwendungsklasse konvertieren {#step-4-convert-your-app-class}

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
    
    // Behalten Sie Ihre vorhandene run()-Methode, falls Sie eine haben
    @Override
    public void run() throws WebforjException {
      // Ihr vorhandener Initialisierungscode 
    }
}
```

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponentensuche von Spring. Die Annotation `@Routify` bleibt gleich und scannt weiterhin Ihre Sichtpakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständig qualifizierter Klassenname des Anwendungseinstiegspunkts
webforj.entry = org.example.Application

# App-Name
spring.application.name=Hello World Spring

# Serverkonfiguration
server.port=8080
server.shutdown=immediate

# webforJ DevTools-Konfiguration
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Führen Sie die Spring Boot-Anwendung aus {#run-the-spring-boot-app}

Sobald sie konfiguriert ist, führen Sie Ihre Anwendung mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre vorhandenen webforJ-Sichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans einfügen und Spring-Funktionen nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. 
Siehe [Property Configuration](/docs/configuration/properties) für Informationen zu webforJ-Konfigurationseigenschaften.

Die folgenden webforJ `application.properties`-Einstellungen sind spezifisch für Spring:

| Eigenschaft | Typ | Beschreibung | Standard |
|-------------|-----|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-Zuordnungsmuster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die nicht von webforJ behandelt werden sollen, wenn sie auf den Root-Bereich zugeordnet sind. Wenn webforJ dem Root-Kontext (`/*`) zugeordnet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dies ermöglicht es REST-Endpunkten und anderen Spring MVC-Zuordnungen, neben webforJ-Routen zu coexistieren. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|----------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In Plugin-Konfiguration | `server.port` in den Eigenschaften |
