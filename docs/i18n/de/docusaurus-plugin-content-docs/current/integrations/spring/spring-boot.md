---
title: Spring Boot
sidebar_position: 10
_i18n_hash: c70504d9cf2cae7a877a0deb0d5420c6
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen und bietet Abhängigkeitsinjektion, automatische Konfiguration und ein eingebettetes Servermodell. Bei der Verwendung von Spring Boot mit webforJ können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über die Konstruktorinjektion injizieren.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre Anwendung als ausführbares JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den Praktiken der cloud-nativen Bereitstellung. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungskontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-Anwendung mit Spring Boot zu erstellen: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

### Option 1: Verwendung von startforJ {#option-1-using-startforj}

Die einfachste Möglichkeit, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt umfasst alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

Wenn Sie mit [startforJ](https://docs.webforj.com/startforj) eine Anwendung erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektemetadaten (Projektname, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Basiskprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist.
Sie können Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen.

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

Der Parameter `flavor` gibt dem Archetyp an, ein Spring Boot-Projekt anstelle eines standardmäßigen webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Konfiguration für das Spring Boot-Eltern-POM
- webforJ Spring Boot-Starter-Abhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispiel-Views
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine vorhandene webforJ-Anwendung haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Hauptanwendungsklasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf neu erstellen.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie das Packaging von WAR auf JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als Eltern-POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Entfernen Sie alle WAR-spezifischen Konfigurationen wie:
   - `maven-war-plugin`
   - Verweise auf das Verzeichnis `webapp`
   - `web.xml`-bezogene Konfiguration

Wenn Sie bereits eine Eltern-POM haben, müssen Sie stattdessen die Bill of Materials (BOM) von Spring Boot importieren:

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

Fügen Sie den webforJ Spring Boot-Starter zu Ihren Abhängigkeiten hinzu. Behalten Sie Ihre vorhandene webforJ-Abhängigkeit:

```xml title="pom.xml"
<dependencies>
    <!-- Ihre vorhandene webforJ-Abhängigkeit -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Fügen Sie den Spring Boot-Starter hinzu -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Fügen Sie die devtools hinzu -->
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

:::tip[webforJ DevTools für automatische Browseraktualisierung]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools mit automatischer Browseraktualisierung. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) Anleitung für Konfigurationsdetails.
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

### Schritt 4: Ihre Anwendungs-Klasse konvertieren {#step-4-convert-your-app-class}

Transformieren Sie Ihre Hauptklasse `App` in eine Spring Boot-Anwendung, indem Sie die erforderlichen Spring-Annotationen und eine Hauptmethode hinzufügen:

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
    
    // Behalten Sie Ihre vorhandene run()-Methode, wenn Sie eine haben
    @Override
    public void run() throws WebforjException {
      // Ihr vorhandener Initialisierungscode 
    }
}
```

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponentensuche von Spring. Die Annotation `@Routify` bleibt gleich und scannt weiterhin Ihre View-Pakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständig qualifizierter Klassenname des Anwendungseintrittspunkts
webforj.entry = org.example.Application

# Projektname
spring.application.name=Hallo Welt Spring

# Serverkonfiguration
server.port=8080
server.shutdown=immediate

# Konfiguration für webforJ DevTools
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Führen Sie die Spring Boot-Anwendung aus {#run-the-spring-boot-app}

Sobald konfiguriert, führen Sie Ihre Anwendung mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre vorhandenen webforJ-Views und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. 
Siehe [Property Configuration](/docs/configuration/properties) für Informationen zu den Konfigurationseigenschaften von webforJ.

Die folgenden Einstellungen in `application.properties` für webforJ sind spezifisch für Spring:

| Property | Typ | Beschreibung | Standard|
|----------|------|-------------|--------|
| **`webforj.servletMapping`** | String | URL-Zuordnungs-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.excludeUrls`** | Liste | URL-Muster, die beim Mapping auf die Wurzel nicht von webforJ behandelt werden sollen. Wenn webforJ auf den Root-Kontext (`/*`) abgebildet ist, werden diese URL-Muster von der Behandlung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dies ermöglicht es, REST-Endpunkte und andere Spring MVC-Zuordnungen neben webforJ-Routen koexistieren zu lassen. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Packaging** | WAR-Datei | Ausführbares JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebettetes Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Port-Konfiguration** | In Plugin-Konfiguration | `server.port` in der properties |
