---
sidebar_position: 40
title: Minifier Plugin
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: a570df6cd0876073e88e7b38b6357b10
---
# Minifier-Plugin <DocChip chip='since' label='25.11' />

Das webforJ Minifier-Plugin minimiert automatisch [Minifizierung](https://de.wikipedia.org/wiki/Minification_(Programming)) und optimiert CSS- und JavaScript-Ressourcen während des Build-Prozesses. Das Plugin entdeckt Ressourcen, die über webforJ [Ressourcenannotierungen](/docs/managing-resources/importing-assets) referenziert werden, und minimiert sie im Build-Ausgang, wodurch die Dateigrößen reduziert und die Ladezeiten verbessert werden, ohne die ursprünglichen Quell Dateien zu ändern.

:::info Minifizierung ist in den Bundler integriert
Der [Frontend-Bundler](/docs/managing-resources/bundler/overview) minimiert CSS und JavaScript als Teil seines Produktions-Builds, sodass ein Projekt, das den Bundler verwendet, dieses Plugin nicht benötigt. Das Plugin bleibt jedoch für Projekte, die Ressourcen über die Ressourcenannotierungen ohne den Bundler laden, verfügbar und unterstützt. Bestehende Setups funktionieren weiterhin wie zuvor, ohne dass Änderungen erforderlich sind.
:::

## Einrichtung {#setup}

Wenn Sie Ihr Projekt mit [startforJ](https://docs.webforj.com/startforj) oder einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt haben, ist das Minifier-Plugin bereits konfiguriert und wird automatisch ausgeführt, wenn Sie mit dem Profil `prod` mit `mvn package -Pprod` bauen.

Für die manuelle Einrichtung benötigt der Minifier zwei Konfigurationen: einen Annotation Processor, um Ressourcen während der Kompilierung zu entdecken, und ein Plugin, um die Minifizierung durchzuführen.

<Tabs>
<TabItem value="maven" label="Maven">

Fügen Sie Folgendes zu Ihrer `pom.xml` hinzu:

```xml
<build>
  <plugins>
    <!-- Konfiguration des Annotation Processors -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <configuration>
        <annotationProcessorPaths>
          <path>
            <groupId>com.webforj</groupId>
            <artifactId>webforj-minify-foundation</artifactId>
            <version>${webforj.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>

    <!-- Konfiguration des Minifier-Plugins -->
    <plugin>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-maven-plugin</artifactId>
      <version>${webforj.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>minify</goal>
          </goals>
        </execution>
      </executions>
      <dependencies>
        <!-- CSS-Minifizierung -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript-Minifizierung -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-closure-js</artifactId>
          <version>${webforj.version}</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Fügen Sie Folgendes zu Ihrer `build.gradle` hinzu:

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-minify-gradle-plugin:${webforjVersion}"
  }
}

plugins {
  id 'java'
}

apply plugin: 'com.webforj.minify'

dependencies {
  // Annotation Processor zur Entdeckung von Ressourcen während der Kompilierung
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier-Implementierungen
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

Die Aufgabe `minify` wird automatisch vor den Aufgaben `jar` oder `war` ausgeführt. Sie können sie auch manuell mit `./gradlew minify` ausführen.

</TabItem>
</Tabs>

## Verwendung des Plugins {#using-the-plugin}

Sobald es konfiguriert ist, funktioniert das Plugin automatisch. Verwenden Sie einfach webforJ-Ressourcenannotationen in Ihrem Code:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Ihr Anwendungs-Code
}
```

Wenn Sie Ihr Projekt bauen, geschieht Folgendes automatisch durch das Plugin:

1. Es entdeckt die in den Annotationen referenzierten Ressourcen während der Kompilierung
2. Es minimiert die entdeckten CSS- und JavaScript-Dateien
3. Es berichtet über die Größenreduzierung und die Verarbeitungszeit

### URL-Protokollauflösung {#url-protocol-resolution}

Das Plugin versteht die webforJ [URL-Protokolle](/docs/managing-resources/assets-protocols) und löst sie in Dateisystempfade auf:

| Protokoll | Wird auf | Beispiel |
|-----------|----------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs ohne Protokoll werden vom Minifier nicht unterstützt und werden übersprungen.

## Eingebaute Minifier {#built-in-minifiers}

webforJ enthält zwei produktionsbereite Minifier für CSS und JavaScript.

| Minifier | Funktionen | Überspringt |
|----------|------------|-------------|
| CSS | Entfernt Leerzeichen, Kommentare und optimiert Property-Werte | `.min.css` |
| JavaScript | Variablenumbenennung, Eliminierung von totem Code, Syntaxoptimierung | `.min.js`, `.min.mjs` |

## Konfigurationsoptionen {#configuration-options}

Das Plugin bietet Optionen zum Deaktivieren der Minifizierung, zum Anpassen der JavaScript-Optimierung und zur Verarbeitung zusätzlicher Dateien.

### Minifizierung deaktivieren {#disabling-minification}

Sie möchten möglicherweise die Minifizierung während der Entwicklung oder zu Debugging-Zwecken ausschalten.

<Tabs>
<TabItem value="maven" label="Maven">

**Über die Kommandozeile:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Über die Plugin-Konfiguration:**
```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <configuration>
    <skip>true</skip>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

**Über die Build-Konfiguration:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### JavaScript-Minifier-Optionen {#javascript-minifier-options}

Der JavaScript-Minifier bietet mehrere Konfigurationsoptionen zur Steuerung des Optimierungsverhaltens.

:::note Gradle-Unterstützung
Beginnen mit v25.12 unterstützt das Gradle-Plugin das Übergeben von JavaScript-Minifier-Optionen.
:::

| Option | Standard | Beschreibung |
|--------|----------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - entfernt nur Leerzeichen und Kommentare</li><li>`SIMPLE_OPTIMIZATIONS` - Variablenumbenennung und Eliminierung von totem Code</li><li>`ADVANCED_OPTIMIZATIONS` - aggressive Optimierung mit Funktions-/Property-Umbenennung</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Eingabesprachen-Version: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` bis `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Ausgabe-JavaScript-Version: dieselbe wie `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Auf `true` setzen, um die Formatierung zum Debuggen zu erhalten |

Konfigurieren Sie diese Optionen im Konfigurationsbereich:

<Tabs>
<TabItem value="maven" label="Maven">

```xml {7-12}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <configuration>
    <minifierConfigurations>
      <closureJs>
        <compilationLevel>SIMPLE_OPTIMIZATIONS</compilationLevel>
        <languageIn>ECMASCRIPT_2020</languageIn>
        <languageOut>ECMASCRIPT5</languageOut>
        <prettyPrint>false</prettyPrint>
      </closureJs>
    </minifierConfigurations>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>minify</goal>
      </goals>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-closure-js</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy
webforjMinify {
    skip = false  // Auf true setzen, um die Minifizierung zu überspringen
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Zusätzliche Dateien minimieren {#minifying-additional-files}

Um Dateien zu minifizieren, die nicht über Annotationen entdeckbar sind, erstellen Sie eine Konfigurationsdatei, die Glob-Muster angibt:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Einschlussmuster
**/*.css
**/*.js

# Ausschlussmuster (mit ! prefix)
!**/*.min.css
!**/*.min.js
```

## Benutzerdefinierte Minifier {#custom-minifiers}

Das Plugin unterstützt benutzerdefinierte Minifier über die Java Service Provider Interface (SPI), sodass Sie Unterstützung für zusätzliche Dateitypen oder alternative Minifizierungsbibliotheken hinzufügen können.

### Erstellen eines benutzerdefinierten Minifiers {#creating-a-custom-minifier}

Implementieren Sie das Interface `AssetMinifier`, um Ihren eigenen Minifier zu erstellen. Das folgende Beispiel zeigt einen JSON-Minifier, der Gson verwendet, um Leerzeichen zu entfernen:

```java title="src/main/java/com/example/minifier/JsonMinifier.java"
package com.example.minifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.webforj.minify.common.AssetMinifier;
import com.webforj.minify.common.MinificationException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class JsonMinifier implements AssetMinifier {

  private static final Logger logger = Logger.getLogger(JsonMinifier.class.getName());
  private final Gson gson = new GsonBuilder().create();

  @Override
  public String minify(String content, Path sourceFile) throws MinificationException {
    try {
      JsonElement element = gson.fromJson(content, JsonElement.class);
      return gson.toJson(element);
    } catch (JsonSyntaxException e) {
      logger.warning("Fehlerhaftes JSON in " + sourceFile + ", wird übersprungen: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Failed to minify JSON file: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Konfigurationsdateien und bereits minimierte Dateien überspringen
    if (filename.equals("package.json") || filename.equals("tsconfig.json")) {
      return false;
    }
    if (filename.endsWith("-lock.json") || filename.endsWith(".min.json")) {
      return false;
    }
    return true;
  }

  @Override
  public void configure(Map<String, Object> options) {
    // Konfigurationsoptionen, falls erforderlich
  }
}
```

### Registrieren Ihres Minifiers {#registering-your-minifier}

Erstellen Sie eine Konfigurationsdatei für den Dienstanbieter:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Verwenden Ihres benutzerdefinierten Minifiers {#using-your-custom-minifier}

Packen Sie Ihren Minifier als separates JAR und fügen Sie ihn als Plugin-Abhängigkeit hinzu:

```xml {5-9}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>json-minifier</artifactId>
      <version>1.0.0</version>
    </dependency>
    <!-- Standard-Minifier (optional) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Häufige Probleme {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Keine Minifier registriert</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Keine Minifier über SPI registriert. Minifizierung wird übersprungen.
      [WARN] Stellen Sie sicher, dass ph-css und/oder closure-compiler im Klassenpfad sind.
      ```

      Fügen Sie dem Plugin-Konfigurationsabhängigkeiten der Minifier-Module hinzu. Für CSS fügen Sie `webforj-minify-phcss-css` hinzu. Für JavaScript fügen Sie `webforj-minify-closure-js` hinzu.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Keine Dateien verarbeitet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Wenn das Plugin `0 Dateien verarbeitet` meldet, überprüfen Sie, ob:

      1. Der Annotation Processor in `maven-compiler-plugin` mit `webforj-minify-foundation` in `annotationProcessorPaths` konfiguriert ist
      2. webforJ-Ressourcenannotationen in Ihrem Quellcode existieren
      3. `target/classes/META-INF/webforj-resources.json` nach der Kompilierung existiert
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Datei nicht gefunden</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Datei nicht gefunden: /path/to/static/css/app.css (referenziert als 'ws://css/app.css')
      ```

      Überprüfen Sie, ob die Datei am richtigen Pfad unter `src/main/resources/static` existiert und ob das URL-Protokoll mit der Verzeichnisstruktur übereinstimmt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minifizierungsfehler</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Fehler bei der Minifizierung der Datei /path/to/app.css: Parse-Fehler in Zeile 42
      ```

      Das Plugin gibt eine Warnung aus, fährt jedoch fort, ohne den Build zu beenden. Der ursprüngliche Inhalt bleibt erhalten, wenn die Minifizierung fehlschlägt. Um Syntaxfehler zu beheben, validieren Sie CSS oder JavaScript mit einem Linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
