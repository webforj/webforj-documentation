---
sidebar_position: 40
title: Minifier-Plugin
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Minifier-Plugin <DocChip chip='since' label='25.11' />

Das webforJ-Minifier-Plugin [minifiziert](https://en.wikipedia.org/wiki/Minification_(programming)) CSS- und JavaScript-Assets während des Build-Prozesses automatisch und optimiert sie. Das Plugin ermittelt Assets, auf die über webforJ-[Asset-Annotationen](/docs/managing-resources/importing-assets) verwiesen wird, und minifiziert sie in der Build-Ausgabe. Dadurch werden die Dateigrößen reduziert und die Ladezeiten verbessert, ohne die ursprünglichen Quelldateien zu verändern.

:::info Minifizierung ist im Bundler integriert
Der [Frontend-Bundler](/docs/managing-resources/bundler/overview) minifiziert CSS und JavaScript im Rahmen seines Produktions-Builds. Daher benötigt ein Projekt, das den Bundler verwendet, dieses Plugin nicht. Für Projekte, die Assets über Asset-Annotationen ohne den Bundler laden, bleibt das Plugin verfügbar und wird unterstützt. Bestehende Setups funktionieren unverändert weiter, es sind keine Anpassungen erforderlich.
:::

## Einrichtung {#setup}

Für die Einrichtung benötigt der Minifier zwei Konfigurationen: einen Annotation-Processor, der während der Kompilierung Assets ermittelt, und ein Plugin, das die Minifizierung ausführt.

<Tabs>
<TabItem value="maven" label="Maven">

Füge Folgendes zu deiner `pom.xml` hinzu:

```xml
<build>
  <plugins>
    <!-- Konfiguration des Annotation-Processors -->
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

Füge Folgendes zu deiner `build.gradle` hinzu:

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
  // Annotation-Processor zum Ermitteln von Assets während der Kompilierung
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier-Implementierungen
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

Die Aufgabe `minify` wird automatisch vor den Aufgaben `jar` oder `war` ausgeführt. Du kannst sie auch manuell mit `./gradlew minify` ausführen.

</TabItem>
</Tabs>

## Plugin verwenden {#using-the-plugin}

Nach der Konfiguration funktioniert das Plugin automatisch. Verwende einfach webforJ-Asset-Annotationen in deinem Code:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Dein Anwendungscode
}
```

Beim Erstellen deines Projekts führt das Plugin automatisch folgende Schritte aus:

1. Es ermittelt während der Kompilierung Assets, auf die in Annotationen verwiesen wird.
2. Es minifiziert die ermittelten CSS- und JavaScript-Dateien.
3. Es gibt die Größenreduzierung und die Verarbeitungsdauer aus.

### Auflösung von URL-Protokollen {#url-protocol-resolution}

Das Plugin kennt webforJ-[URL-Protokolle](/docs/managing-resources/assets-protocols) und löst sie in Dateisystempfade auf:

| Protokoll | Wird aufgelöst zu | Beispiel |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs ohne Protokoll werden vom Minifier nicht unterstützt und übersprungen.

## Integrierte Minifier {#built-in-minifiers}

webforJ enthält zwei produktionsreife Minifier für CSS und JavaScript.

| Minifier | Funktionen | Wird übersprungen |
|----------|----------|-------|
| CSS | Entfernt Leerzeichen und Kommentare und optimiert Eigenschaftswerte | `.min.css` |
| JavaScript | Variablenumbenennung, Entfernung von nicht verwendetem Code, Syntaxoptimierung | `.min.js`, `.min.mjs` |

## Konfigurationsoptionen {#configuration-options}

Das Plugin bietet Optionen zum Deaktivieren der Minifizierung, Anpassen der JavaScript-Optimierung und Verarbeiten zusätzlicher Dateien.

### Minifizierung deaktivieren {#disabling-minification}

Du kannst die Minifizierung während der Entwicklung oder zu Debugging-Zwecken deaktivieren.

<Tabs>
<TabItem value="maven" label="Maven">

**Über die Befehlszeile:**
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

### Optionen des JavaScript-Minifiers {#javascript-minifier-options}

Der JavaScript-Minifier bietet verschiedene Konfigurationsoptionen, mit denen sich das Optimierungsverhalten steuern lässt.

:::note Gradle-Unterstützung
Ab v25.12 unterstützt das Gradle-Plugin die Übergabe von Optionen an den JavaScript-Minifier.
:::

| Option | Standardwert | Beschreibung |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` – entfernt nur Leerzeichen und Kommentare</li><li>`SIMPLE_OPTIMIZATIONS` – benennt Variablen um und entfernt nicht verwendeten Code</li><li>`ADVANCED_OPTIMIZATIONS` – aggressive Optimierung mit Umbenennung von Funktionen und Eigenschaften</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Eingabeversion von JavaScript: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` bis `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Ausgabeversion von JavaScript: wie bei `languageIn`, zusätzlich `NO_TRANSPILE` |
| `prettyPrint` | `false` | Auf `true` setzen, um die Formatierung für das Debugging beizubehalten |

Konfiguriere diese Optionen im Konfigurationsabschnitt:

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

### Zusätzliche Dateien minifizieren {#minifying-additional-files}

Um Dateien zu minifizieren, die nicht über Annotationen ermittelt wurden, erstelle eine Konfigurationsdatei mit Glob-Mustern:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Einschlussmuster
**/*.css
**/*.js

# Ausschlussmuster (mit ! als Präfix)
!**/*.min.css
!**/*.min.js
```

## Benutzerdefinierte Minifier {#custom-minifiers}

Das Plugin unterstützt benutzerdefinierte Minifier über die Java Service Provider Interface (SPI). Dadurch kannst du weitere Dateitypen oder alternative Minifizierungsbibliotheken unterstützen.

### Benutzerdefinierten Minifier erstellen {#creating-a-custom-minifier}

Implementiere die Schnittstelle `AssetMinifier`, um deinen eigenen Minifier zu erstellen. Das folgende Beispiel zeigt einen JSON-Minifier, der mit Gson Leerzeichen entfernt:

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
      logger.warning("Malformed JSON in " + sourceFile + ", skipping: " + e.getMessage());
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
    // Konfigurationsdateien und bereits minifizierte Dateien überspringen
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
    // Konfigurationsoptionen bei Bedarf
  }
}
```

### Minifier registrieren {#registering-your-minifier}

Erstelle eine Service-Provider-Konfigurationsdatei:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Benutzerdefinierten Minifier verwenden {#using-your-custom-minifier}

Packe deinen Minifier als separates JAR und füge es als Plugin-Abhängigkeit hinzu:

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
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      Füge die Abhängigkeiten der Minifier-Module zur Plugin-Konfiguration hinzu. Für CSS füge `webforj-minify-phcss-css` hinzu. Für JavaScript füge `webforj-minify-closure-js` hinzu.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Keine Dateien verarbeitet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Wenn das Plugin `Processed 0 files` meldet, überprüfe Folgendes:

      1. Der Annotation-Processor ist in `maven-compiler-plugin` mit `webforj-minify-foundation` unter `annotationProcessorPaths` konfiguriert.
      2. Dein Quellcode enthält webforJ-Asset-Annotationen.
      3. Die Datei `target/classes/META-INF/webforj-resources.json` ist nach der Kompilierung vorhanden.
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
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Überprüfe, ob die Datei unter `src/main/resources/static` am richtigen Pfad vorhanden ist und ob das URL-Protokoll zur Verzeichnisstruktur passt.
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
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      Das Plugin gibt eine Warnung aus, setzt den Build jedoch fort. Wenn die Minifizierung fehlschlägt, bleibt der ursprüngliche Inhalt erhalten. Um Syntaxfehler zu beheben, überprüfe CSS oder JavaScript mit einem Linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
