---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Apps und bietet Dependency Injection, automatische Konfiguration und ein eingebettetes Servermodell. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten durch Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre App als ausführbares JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und stimmt mit cloud-nativen Bereitstellungspraktiken überein. Das Komponentenmodell und das Routing von webforJ arbeiten neben dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-App {#create-a-spring-boot-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-App mit Spring Boot zu erstellen: über das grafische Tool startforJ oder über die Maven-Befehlszeile.

### Option 1: Verwendung von startforJ {#option-1-using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektmetadaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Themenfarbe und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Grundprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie lieber die Befehlszeile verwenden, generieren Sie ein Spring Boot webforJ-Projekt direkt mit den offiziellen webforJ-Archetypen:

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

Der Parameter `flavor` teilt dem Archetyp mit, ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Spring Boot-Parent-POM-Konfiguration
- webforJ Spring Boot-Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien sowohl für Spring als auch für webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-App haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst die Aktualisierung Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf neu erstellen.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie das Packaging von WAR zu JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als Parent POM:
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

Wenn Sie bereits eine Parent-POM haben, müssen Sie stattdessen die Spring Boot Bill of Materials (BOM) importieren:

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
    <!-- Ihre bestehende webforJ-Abhängigkeit -->
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

:::tip[webforJ DevTools für automatische Browseraktualisierung]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools mit automatischer Browseraktualisierung. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Weitere Informationen finden Sie im Leitfaden zu [Spring DevTools](spring-devtools).
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

### Schritt 4: Ihre App-Klasse konvertieren {#step-4-convert-your-app-class}

Transformieren Sie Ihre Hauptklasse `App` in eine Spring Boot-App, indem Sie die erforderlichen Spring-Annotationen und eine Hauptmethode hinzufügen:

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

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponentensuche von Spring. Die Annotation `@Routify` bleibt unverändert und scannt weiterhin Ihre Ansichtspakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# App-Name
spring.application.name=Hallo Welt Spring

# Serverkonfiguration
server.port=8080
server.shutdown=immediate

# webforJ DevTools-Konfiguration
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Ihre vorhandene Datei `webforj.conf` funktioniert weiterhin. Verweisen Sie auf Ihre Hauptklasse:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Führen Sie die Spring Boot-App aus {#run-the-spring-boot-app}

Sobald Sie konfiguriert sind, führen Sie Ihre App mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die App wird standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080 gestartet. Ihre bestehenden webforJ-Ansichten und Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Funktionen von Spring nutzen.

## Konfigurationsunterschiede {#configuration-differences}

Beim Wechsel zu Spring Boot ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Packaging** | WAR-Datei | Ausführbares JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
