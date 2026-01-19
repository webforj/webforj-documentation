---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 620d9c2e8df79418d1e4902b970b42c8
---
Spring Boot ist eine beliebte Wahl zum Erstellen von Java-Anwendungen, da es Abhängigkeitsinjizierung, automatische Konfiguration und ein eingebettetes Servermodell bietet. Wenn Sie Spring Boot mit webforJ verwenden, können Sie Dienste, Repositories und andere von Spring verwaltete Beans direkt in Ihre UI-Komponenten über Konstruktorinjektion einfügen.

Wenn Sie Spring Boot mit webforJ verwenden, wird Ihre Anwendung als ausführbare JAR mit einem eingebetteten Tomcat-Server ausgeführt, anstatt eine WAR-Datei auf einem externen Anwendungsserver bereitzustellen. Dieses Verpackungsmodell vereinfacht die Bereitstellung und entspricht den Praktiken zur cloud-nativen Bereitstellung. Das Komponentenmodell und das Routing von webforJ arbeiten neben dem Anwendungskontext von Spring zur Verwaltung von Abhängigkeiten und Konfiguration.

## Erstellen Sie eine Spring Boot-Anwendung {#create-a-spring-boot-app}

Sie haben zwei Optionen, um eine neue webforJ-Anwendung mit Spring Boot zu erstellen: die grafische Tool-startforJ oder die Maven-Befehlszeile zu verwenden.

### Option 1: Verwendung von startforJ {#option-1-using-startforj}

Der einfachste Weg, eine neue webforJ-Anwendung zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starter-Projekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starter-Projekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und eine vorgefertigte Layout, sodass Sie sofort mit dem Aufbau beginnen können.

Wenn Sie eine Anwendung mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie folgende Informationen bereitstellen:

- Grundlegende Projektdaten (Anwendungsname, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- **Flavor** - Wählen Sie **webforJ Spring**, um ein Spring Boot-Projekt zu erstellen

Mit diesen Informationen erstellt startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp, das für Spring Boot konfiguriert ist. Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

### Option 2: Verwendung der Befehlszeile {#option-2-using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, generieren Sie ein Spring Boot webforJ-Projekt direkt mithilfe der offiziellen webforJ-Archetypen:

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

Der Parameter `flavor` gibt dem Archetyp an, dass ein Spring Boot-Projekt anstelle eines Standard-webforJ-Projekts generiert werden soll.

Dies erstellt ein vollständiges Spring Boot-Projekt mit:
- Spring Boot-Eltern-POM-Konfiguration
- webforJ Spring Boot-Starter-Abhängigkeit
- Haupt-App-Klasse mit `@SpringBootApplication` und `@Routify`
- Beispielansichten
- Konfigurationsdateien sowohl für Spring als auch für webforJ

## Fügen Sie Spring Boot bestehenden Projekten hinzu {#add-spring-boot-to-existing-projects}

Wenn Sie eine bestehende webforJ-Anwendung haben, können Sie Spring Boot hinzufügen, indem Sie die Projektkonfiguration ändern. Dieser Prozess umfasst das Aktualisieren Ihrer Maven-Konfiguration, das Hinzufügen von Spring-Abhängigkeiten und die Konvertierung Ihrer Haupt-App-Klasse.

:::info[Nur für bestehende Projekte]
Überspringen Sie diesen Abschnitt, wenn Sie ein neues Projekt von Grund auf erstellen. Diese Anleitung geht von **webforJ Version 25.11 oder später** aus.
:::

### Schritt 1: Maven-Konfiguration aktualisieren {#step-1-update-maven-configuration}

Nehmen Sie die folgenden Änderungen an Ihrer POM-Datei vor:

1. Ändern Sie das Packaging von WAR zu JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Setzen Sie Spring Boot als das Eltern-POM:
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

Fügen Sie den webforJ Spring Boot-Starter zu Ihren Abhängigkeiten hinzu:

:::info[Vereinfachung in webforJ 25.11+]
Seit **webforJ Version 25.11** enthält der `webforj-spring-boot-starter` alle Kern-webforJ-Abhängigkeiten transitiv. Sie müssen die Abhängigkeit `com.webforj:webforj` nicht mehr explizit hinzufügen.

Für Versionen **vor 25.11** müssen Sie beide Abhängigkeiten separat einfügen.
:::

**Für webforJ 25.11 und später:**

```xml title="pom.xml"
<dependencies>
    <!-- Fügen Sie den Spring Boot-Starter hinzu (beinhaltet webforJ transitiv) -->
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

:::tip[webforJ DevTools für automatische Browseraktualisierung]
Die `webforj-spring-devtools`-Abhängigkeit erweitert Spring DevTools mit automatischer Browseraktualisierung. Wenn Sie Änderungen in Ihrer IDE speichern, wird der Browser ohne manuelles Eingreifen automatisch neu geladen. Siehe die [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)-Anleitung für Konfigurationsdetails.
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

Transformieren Sie Ihre Hauptklasse `App` in eine Spring Boot-Anwendung, indem Sie die notwendigen Spring-Anmerkungen und eine Hauptmethode hinzufügen:

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
      // Ihr bestehender Initialisierungscode 
    }
}
```

Die Anmerkung `@SpringBootApplication` aktiviert die automatische Konfiguration und Komponenten-Scannen von Spring. Die Anmerkung `@Routify` bleibt gleich und scannt weiterhin Ihre Ansichts-Pakete nach Routen.

### Schritt 5: Spring-Konfiguration hinzufügen {#step-5-add-spring-configuration}

Erstellen Sie `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Vollständig qualifizierter Klassenname des Anwendungseinstiegspunktes
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

Sobald die Konfiguration abgeschlossen ist, führen Sie Ihre Anwendung mit folgendem Befehl aus:

```bash
mvn spring-boot:run
```

Die Anwendung wird standardmäßig mit einem eingebetteten Tomcat-Server auf Port 8080 gestartet. Ihre vorhandenen webforJ-Ansichten und -Routen funktionieren genau wie zuvor, aber jetzt können Sie Spring-Beans injizieren und Spring-Funktionen nutzen.

## Konfiguration

Verwenden Sie die Datei `application.properties` in `src/main/resources`, um Ihre Anwendung zu konfigurieren. 
Siehe [Property Configuration](/docs/configuration/properties) für Informationen zu webforJ-Konfigurationseigenschaften.

Die folgenden webforJ `application.properties`-Einstellungen sind spezifisch für Spring:

| Eigenschaft | Typ | Beschreibung | Standard|
|-------------|-----|--------------|--------|
| **`webforj.servlet-mapping`** | String | URL-Mapping-Muster für das webforJ-Servlet. | `/*` |
| **`webforj.exclude-urls`** | Liste | URL-Muster, die von webforJ nicht behandelt werden sollten, wenn sie auf die Wurzel abgebildet sind. Wenn webforJ auf den Wurzelkontext (`/*`) abgebildet ist, werden diese URL-Muster von der Behandlung durch webforJ ausgeschlossen und können stattdessen von Spring MVC-Controllern behandelt werden. Dies ermöglicht es REST-Endpunkten und anderen Spring MVC-Mappings, neben den webforJ-Routen zu coexistieren. | `[]` |

### Konfigurationsunterschiede {#configuration-differences}

Wenn Sie zu Spring Boot wechseln, ändern sich mehrere Konfigurationsaspekte:

| Aspekt | Standard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Packaging** | WAR-Datei | Ausführbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Eingebetteter Tomcat |
| **Ausführungsbefehl** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hauptkonfiguration** | Nur `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profile** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-Profile mit `application-{profile}.properties` |
| **Portkonfiguration** | In der Plugin-Konfiguration | `server.port` in Eigenschaften |
