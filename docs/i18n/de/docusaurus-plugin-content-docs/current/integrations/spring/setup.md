---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: c988b8fe49245d60c0e5f18c830595e3
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Apps und bietet Abhängigkeitsinjektion, automatische Konfiguration und ein Embedded-Server-Modell. Beim Einsatz von Spring Boot mit webforJ können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten durch Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre App als ausführbare JAR-Datei mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den praktischen Anforderungen für cloud-native Bereitstellungen. Das Komponentenmodell und die Routen von webforJ arbeiten zusammen mit dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-App {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-App mit Spring Boot zu erstellen: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

### Option 1: Verwendung von startforJ {#option-1-using-startforj}

Der einfachste Weg, um eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

Wenn Sie mit [startforJ](https://docs.webforj.com/startforj) eine App erstellen, können Sie diese anpassen, indem Sie folgende Informationen bereitstellen:

- Grundlegende Projektmetadaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Basisprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.

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

Der Parameter `flavor` teilt dem Archetyp mit, dass ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts generiert werden soll.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Konfiguration des Spring Boot-Eltern-POM
- webforJ Spring Boot-Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-App haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf erstellen.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie folgende Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie die Verpackung von WAR auf JAR:
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

3. Entfernen Sie spezifische WAR-Konfigurationen wie:
   - `maven-war-plugin`
   - Verweise auf das Verzeichnis `webapp`
   - Konfigurationen, die mit `web.xml` zusammenhängen

Wenn Sie bereits eine Eltern-POM haben, müssen Sie stattdessen das Spring Boot Bill of Materials (BOM) importieren:

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

Fügen Sie den webforJ Spring Boot-Starter zu Ihren Abhängigkeiten hinzu. Behalten Sie Ihre bestehende webforJ-Abhängigkeit bei:

```xml title="pom.xml"
<dependencies>
    <!-- Ihre bestehende webforJ-Abhängigkeit -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Spring Boot-Starter hinzufügen -->
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

:::tip[webforJ DevTools für automatisches Browser-Refresh]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools um ein automatisches Browser-Refresh. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Siehe die [Spring DevTools](spring-devtools)-Dokumentation für Konfigurationsdetails.
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
    
    // Behalten Sie Ihre vorhandene run()-Methode bei, wenn Sie eine haben
    @Override
    public void run() throws WebforjException {
      // Ihr vorhandener Initialisierungscode 
    }
}
```

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponenten-Scannung von Spring. Die Annotation `@Routify` bleibt gleich und scannt weiterhin Ihre Ansichtspakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# App Name
spring.application.name=Hallo Welt Spring

# Serverkonfiguration
server.port=8080
server.shutdown=immediate

# Konfiguration für webforJ DevTools
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Ihre vorhandene Datei `webforj.conf` funktioniert weiterhin. Verweisen Sie auf Ihre Hauptklasse:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Ausführen der Spring Boot-App {#run-the-spring-boot-app}

Sobald die Konfiguration abgeschlossen ist, führen Sie Ihre App mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die App wird standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080 gestartet. Ihre bestehenden webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und die Funktionen von Spring nutzen.

## Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
