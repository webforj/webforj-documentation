---
title: Spring Boot
sidebar_position: 10
_i18n_hash: ea7e45ae4100f45754621a1e3b9d2980
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen und bietet Dependency Injection, automatische Konfiguration und ein eingebettetes Servermodell. Bei der Verwendung von Spring Boot mit webforJ können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten durch Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, läuft Ihre App als ausführbares JAR mit einem eingebetteten Tomcat-Server anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den cloud-nativen Bereitstellungsmustern. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungs-Kontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen einer Spring Boot-App {#create-a-spring-boot-app}

Sie haben zwei Möglichkeiten, eine neue webforJ-App mit Spring Boot zu erstellen: mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp erstellt. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vordefiniertes Layout, sodass Sie sofort damit beginnen können, darauf aufzubauen.

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen angeben:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Icon
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein Grundprojekt aus dem von Ihnen gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, erstellen Sie ein Spring Boot webforJ-Projekt direkt mit den offiziellen webforJ-Archetypen:

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

Der `flavor`-Parameter sagt dem Archetyp, ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts zu generieren.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Spring Boot-Eltern-POM-Konfiguration
- webforJ Spring Boot Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien sowohl für Spring als auch für webforJ

## Spring Boot zu bestehenden Projekten hinzufügen {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-App haben, können Sie Spring Boot hinzufügen, indem Sie die Projektkonfiguration anpassen. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf erstellen. Diese Anleitung setzt **webforJ-Version 25.11 oder höher** voraus.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen in Ihrer POM-Datei vor:

1. Ändern Sie das Packaging von WAR in JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Legen Sie Spring Boot als Eltern-POM fest:
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
   - Verweise auf das `webapp`-Verzeichnis
   - Wartung der `web.xml`-bezogenen Konfiguration

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

Fügen Sie den webforJ Spring Boot Starter zu Ihren Abhängigkeiten hinzu:

:::info[webforJ 25.11+ Vereinfachung]
Ab der **webforJ-Version 25.11** enthält der `webforj-spring-boot-starter` alle zentralen webforJ-Abhängigkeiten transitiv. Sie müssen die `com.webforj:webforj`-Abhängigkeit nicht mehr explizit hinzufügen.

Für Versionen **vor 25.11** müssen Sie beide Abhängigkeiten separat einfügen.
:::

**Für webforJ 25.11 und höher:**

```xml title="pom.xml"
<dependencies>
  <!-- Fügen Sie den Spring Boot Starter hinzu (beinhaltet webforJ transitiv) -->
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

:::tip[webforJ DevTools für automatische Browseraktualisierung]
Die `webforj-spring-devtools`-Abhängigkeit erweitert Spring DevTools mit automatischer Browseraktualisierung. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch neu geladen, ohne manuelles Eingreifen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)-Anleitung für Konfigurationsdetails.
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

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und die Komponenten-Scannen von Spring. Die Annotation `@Routify` bleibt unverändert und scannt weiterhin Ihre View-Pakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständig qualifizierter Klassenname des Anwendungseinstiegspunktes
webforj.entry = org.example.Application

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

## Führen Sie die Spring Boot-App aus {#run-the-spring-boot-app}

Sobald konfiguriert, führen Sie Ihre App mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die App wird mit einem eingebetteten Tomcat-Server auf Port 8080 gestartet. Ihre vorhandenen webforJ-Ansichten und Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre App zu konfigurieren. 
Siehe [Eigenschaftskonfiguration](/docs/configuration/properties) für Informationen zu den Konfigurationseigenschaften von webforJ.

Die folgenden Einstellungen von `application.properties` für webforJ sind spezifisch für Spring:

| Property | Typ | Beschreibung | Standard|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die nicht von webforJ bearbeitet werden sollen, wenn sie auf die Wurzel abgebildet sind. Wenn webforJ an den Stammkontext (`/*`) abgebildet ist, werden diese URL-Muster von der Bearbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern bearbeitet werden. Dies ermöglicht es REST-Endpunkten und anderen Spring MVC-Mappings, neben den webforJ-Routen zu coexistieren. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Packaging** | WAR-Datei | Ausführbares JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Kommando zum Ausführen** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Port-Konfiguration** | In Plugin-Konfiguration | `server.port` in Eigenschaften |
