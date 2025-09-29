---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 46d6f92d638a214c8b5a0a8632ef0150
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Apps, da es Dependency Injection, Auto-Configuration und ein Embedded-Server-Modell bietet. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Services, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über die Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre App als ausführbare JAR mit einem eingebetteten Tomcat-Server, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und steht im Einklang mit cloud-nativen Bereitstellungsmethoden. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungskontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-App {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-App mit Spring Boot zu erstellen: mit dem grafischen Tool startforJ oder der Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem ausgewählten webforJ-Archetyp generiert. Dieses Starterprojekt umfasst alle benötigten Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, so dass Sie sofort mit dem Bauen beginnen können.

Wenn Sie mit [startforJ](https://docs.webforj.com/startforj) eine App erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Themenfarbe und Icon
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Basisprojekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist.
Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen möchten.

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

Der `flavor`-Parameter weist das Archetyp an, ein Spring Boot-Projekt anstelle eines standardmäßigen webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Konfiguration des Spring Boot-Eltern-POM
- webforJ Spring Boot-Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-App haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Umwandeln Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf neu erstellen.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Führen Sie die folgenden Änderungen an Ihrer POM-Datei durch:

1. Ändern Sie das Packaging von WAR in JAR:
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
   - `webapp`-Verzeichniseinträge
   - `web.xml`-bezogene Konfiguration

Wenn Sie bereits ein Eltern-POM haben, müssen Sie stattdessen das Spring Boot Bill of Materials (BOM) importieren:

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

Fügen Sie den webforJ Spring Boot-Starter zu Ihren Abhängigkeiten hinzu. Behalten Sie Ihre bestehende webforJ-Abhängigkeit:

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

    <!-- Entwicklungswerkzeuge hinzufügen -->
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

:::tip[webforJ DevTools für automatischen Browser-Refresh]
Die `webforj-spring-devtools`-Abhängigkeit erweitert Spring DevTools mit automatischem Browser-Refresh. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)-Anleitung für Konfigurationsdetails.
:::

### Schritt 3: Build-Plugins aktualisieren {#step-3-update-build-plugins}

Ersetzen Sie das Jetty-Plugin durch das Spring Boot Maven-Plugin. Entfernen Sie alle bestehenden Jetty-Konfigurationen und fügen Sie hinzu:

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

### Schritt 4: Ihre App-Klasse umwandeln {#step-4-convert-your-app-class}

Verwandeln Sie Ihre Haupt-`App`-Klasse in eine Spring Boot-App, indem Sie die erforderlichen Spring-Anmerkungen und eine Hauptmethode hinzufügen:

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
    
    // Behalten Sie Ihre bestehende run() Methode, wenn Sie eine haben
    @Override
    public void run() throws WebforjException {
      // Ihr bestehender Initialisierungscode 
    }
}
```

Die `@SpringBootApplication`-Annotation aktiviert die Auto-Configuration und die komponentenbasierte Suche von Spring. Die `@Routify`-Annotation bleibt gleich und scannt weiterhin Ihre Ansichts-Pakete nach Routen.

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

## Führen Sie die Spring Boot-App aus {#run-the-spring-boot-app}

Sobald Sie konfiguriert haben, führen Sie Ihre App mit aus:

```bash
mvn spring-boot:run
```

Die App startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre bestehenden webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Funktionen von Spring verwenden.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre App zu konfigurieren.
Siehe [Property Configuration](/docs/configuration/properties) für Informationen über die Konfigurationseigenschaften von webforJ.

Die folgenden Einstellungen in `application.properties` von webforJ sind spezifisch für das Spring-Framework:

| Eigenschaft | Typ | Beschreibung | Standard |
|-------------|-----|--------------|----------|
| **`webforj.servletMapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.excludeUrls`** | Liste | URL-Muster, die nicht von webforJ verarbeitet werden sollen, wenn sie auf die Wurzel abgebildet sind. Wenn webforJ auf dem Wurzelkontext (`/*`) abgebildet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können von Spring MVC-Controllern stattdessen verarbeitet werden. Dadurch können REST-Endpunkte und andere Spring MVC-Mappings neben den webforJ-Routen coexistieren. | `[]` |

### Unterschiede in der Konfiguration {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Aspekte der Konfiguration:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Packaging** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführen-Befehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Port-Konfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
