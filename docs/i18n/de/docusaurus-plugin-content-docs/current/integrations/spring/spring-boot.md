---
title: Spring Boot
sidebar_position: 10
_i18n_hash: a482c057615b165ea1c9ff9270f34def
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen und bietet Dependency Injection, automatische Konfiguration und ein eingebettetes Servermodell. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten durch Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre Anwendung als ausführbares JAR mit einem eingebetteten Tomcat-Server anstelle von WAR-Dateien, die auf einem externen Anwendungsserver bereitgestellt werden. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den cloud-nativen Bereitstellungsmethoden. Das Komponentenmodell von webforJ und das Routing arbeiten zusammen mit dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-Anwendung mit Spring Boot zu erstellen: Entweder mit dem grafischen Tool startforJ oder der Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp erstellt. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen angeben:

- Basisprojektemetadaten (Anwendungsname, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farb-Theme und Symbol
- Archetyp
- **Geschmack** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen wird startforJ ein Basisprojekt aus dem gewählten Archetyp erstellen, das für Spring Boot konfiguriert ist. Sie können Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen.

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

Der Parameter `flavor` sagt dem Archetyp, ein Spring Boot-Projekt anstelle eines regulären webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Konfiguration des Spring Boot-Eltern-POM
- webforJ Spring Boot-Starterabhängigkeit
- Hauptanwendungsklasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-Anwendung haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Hauptanwendungsklasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf erstellen.
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
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Entfernen Sie alle WAR-spezifischen Konfigurationen wie:
   - `maven-war-plugin`
   - Verweise auf das Verzeichnis `webapp`
   - `web.xml` bezogene Konfiguration

Wenn Sie bereits einen Eltern-POM haben, müssen Sie stattdessen die Spring Boot Bill of Materials (BOM) importieren:

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

    <!-- Fügen Sie devtools hinzu -->
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
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools mit automatischem Browser-Refresh. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelles Eingreifen neu geladen. Weitere Informationen zur Konfiguration finden Sie im [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) Handbuch.
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
    
    // Behalten Sie Ihre vorhandene run()-Methode, falls Sie eine haben
    @Override
    public void run() throws WebforjException {
      // Ihr vorhandener Initialisierungs-Code 
    }
}
```

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponenten-Scan-Funktion von Spring. Die Annotation `@Routify` bleibt unverändert und scannt weiterhin Ihre Sichtpakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständig qualifizierter Klassenname des Einstiegspunkts der Anwendung
webforj.entry = org.example.Application

# Anwendungsname
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

Sobald alles konfiguriert ist, führen Sie Ihre Anwendung mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die Anwendung startet standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080. Ihre bestehenden webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Funktionen von Spring nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. 
 Siehe [Property-Konfiguration](/docs/configuration/properties) für Informationen zu webforJ-Konfigurationseigenschaften.

Die folgenden `application.properties`-Einstellungen von webforJ sind spezifisch für das Spring-Framework:

| Eigenschaft | Typ | Beschreibung | Standard|
|-------------|-----|--------------|--------|
| **`webforj.servletMapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.excludeUrls`** | Liste | URL-Muster, die nicht von webforJ behandelt werden sollten, wenn sie auf die Wurzel abgebildet sind. Wenn webforJ auf den Wurzelkontext (`/*`) abgebildet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dadurch können REST-Endpunkte und andere Spring MVC-Mappings mit webforJ-Routen koexistieren. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie auf Spring Boot umsteigen, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpackung** | WAR-Datei | Ausführbares JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Befehlszeile** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
