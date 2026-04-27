---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 116777dbeb2e6707e2ef867f0dd6d78c
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Apps und bietet Abhängigkeitsinjektion, automatische Konfiguration und ein eingebettetes Servermodell. Bei der Verwendung von Spring Boot mit webforJ können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über die Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, wird Ihre App als ausführbare JAR mit einem eingebetteten Tomcat-Server ausgeführt, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und stimmt mit cloud-nativen Bereitstellungsmethoden überein. Das Komponentenmodell und das Routing von webforJ arbeiten zusammen mit dem Anwendungskontext von Spring für die Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen Sie eine Spring Boot-App {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-App mit Spring Boot zu erstellen: Mit dem grafischen Tool startforJ oder über die Maven-Befehlszeile.

<!-- vale off -->
### Option 1: Verwendung von startforJ {#option-1-using-startforj}
<!-- vale on -->

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Erstellen beginnen können.

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein grundlegendes Projekt basierend auf dem von Ihnen gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen möchten.

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

Dadurch wird ein vollständiges Spring Boot-Projekt mit Folgendem erstellt:
- Konfiguration der Spring Boot-Eltern-POM
- webforJ Spring Boot Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien für sowohl Spring als auch webforJ

## Fügen Sie Spring Boot zu bestehenden Projekten hinzu {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-App haben, können Sie Spring Boot hinzufügen, indem Sie Ihre Projektkonfiguration ändern. Dieser Prozess beinhaltet das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und das Konvertieren Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf erstellen. Diese Anleitung setzt **webforJ Version 25.11 oder höher** voraus.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie die Verpackung von WAR zu JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als die Eltern-POM:
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
   - Konfigurationen, die `web.xml` betreffen

Wenn Sie bereits eine Eltern-POM haben, müssen Sie stattdessen die Bill of Materials (BOM) von Spring Boot importieren:

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

:::info[Vereinfachung für webforJ 25.11+]
Ab **webforJ Version 25.11** enthält der `webforj-spring-boot-starter` alle Kern-webforJ-Abhängigkeiten transitiv. Sie müssen die Abhängigkeit `com.webforj:webforj` nicht mehr ausdrücklich hinzufügen.

Für Versionen **vor 25.11** müssen Sie beide Abhängigkeiten separat einfügen.
:::

**Für webforJ 25.11 und höher:**

```xml title="pom.xml"
<dependencies>
  <!-- Fügen Sie den Spring Boot-Starter hinzu (enthält webforJ transitiv) -->
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
  <!-- Fügen Sie die webforJ-Abhängigkeit ausdrücklich hinzu -->
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

:::tip[webforJ DevTools für automatisches Browser-Update]
Die Abhängigkeit `webforj-spring-devtools` erweitert Spring DevTools um automatisches Browser-Refreshing. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser automatisch ohne manuelle Intervention neu geladen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)-Anleitung für Konfigurationsdetails.
:::

### Schritt 3: Build-Plugins aktualisieren {#step-3-update-build-plugins}

Ersetzen Sie das Jetty-Plugin durch das Spring Boot Maven-Plugin. Entfernen Sie jede vorhandene Jetty-Konfiguration und fügen Sie hinzu:

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

### Schritt 4: Konvertieren Sie Ihre App-Klasse {#step-4-convert-your-app-class}

Transformieren Sie Ihre Hauptklasse `App` in eine Spring Boot-App, indem Sie die erforderlichen Spring-Anmerkungen und eine Hauptmethode hinzufügen:

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

Die Annotation `@SpringBootApplication` aktiviert die automatische Konfiguration und das Komponentenscannen von Spring. Die Annotation `@Routify` bleibt gleich und scannt weiterhin Ihre Ansichtspakete nach Routen.

### Schritt 5: Fügen Sie die Spring-Konfiguration hinzu {#step-5-add-spring-configuration}

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

Nachdem Sie konfiguriert haben, führen Sie Ihre App mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die App startet mit einem eingebetteten Tomcat-Server standardmäßig auf Port 8080. Ihre vorhandenen webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre App zu konfigurieren. 
Siehe [Property Configuration](/docs/configuration/properties) für Informationen zu den webforJ-Konfigurationseigenschaften.

Die folgenden Einstellungen in `application.properties` von webforJ sind spezifisch für Spring:

| Eigenschaft | Typ | Beschreibung | Standard|
|-------------|-----|--------------|--------|
| **`webforj.servlet-mapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die nicht von webforJ bearbeitet werden sollen, wenn sie auf die Wurzel abgebildet sind. Wenn webforJ auf den Root-Kontext (`/*`) abgebildet ist, werden diese URL-Muster von der Verarbeitung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern bearbeitet werden. Dies ermöglicht das Koexistieren von REST-Endpunkten und anderen Spring MVC-Mappings mit webforJ-Routen. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpackung** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in den Eigenschaften |
