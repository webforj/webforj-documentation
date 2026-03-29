---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: updated-content
_i18n_hash: bbc598d57e4531fcd7f76fe117d2e49a
---
# Minifier-Plugin <DocChip chip='since' label='25.11' />

Das webforJ Minifier-Plugin minimiert automatisch [Minifizierung](https://en.wikipedia.org/wiki/Minification_(programming)) und optimiert CSS- und JavaScript-Ressourcen während des Build-Prozesses. Das Plugin entdeckt Ressourcen, die über webforJ [Asset-Annotationen](/docs/managing-resources/importing-assets) referenziert werden, und minimiert sie im Build-Ausgang, wodurch die Dateigrößen reduziert und die Ladezeiten verbessert werden, ohne die ursprünglichen Quelldateien zu verändern.

## Einrichtung {#setup}

Wenn Sie Ihr Projekt mit [startforJ](https://docs.webforj.com/startforj) oder einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt haben, ist das Minifier-Plugin bereits konfiguriert und wird automatisch ausgeführt, wenn Sie mit dem `prod`-Profil mit `mvn package -Pprod` bauen.

Für eine manuelle Einrichtung benötigt der Minifier zwei Konfigurationen: einen Annotation Processor, um Ressourcen während der Kompilierung zu entdecken, und ein Plugin, um die Minifizierung durchzuführen.

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
  // Annotation Processor zum Entdecken von Ressourcen während der Kompilierung
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier-Implementierungen
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

Die `minify`-Aufgabe wird automatisch vor den `jar`- oder `war`-Aufgaben ausgeführt. Sie können sie auch manuell mit `./gradlew minify` ausführen.

</TabItem>
</Tabs>

## Verwendung des Plugins {#using-the-plugin}

Sobald es konfiguriert ist, funktioniert das Plugin automatisch. Verwenden Sie einfach die webforJ-Asset-Annotationen in Ihrem Code:

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

Wenn Sie Ihr Projekt bauen, führt das Plugin automatisch die folgenden Schritte aus:

1. Entdeckt Ressourcen, die in Annotationen während der Kompilierung referenziert werden
2. Minifiziert die entdeckten CSS- und JavaScript-Dateien
3. Berichtet über die Größenreduzierung und die Verarbeitungszeit

### URL-Protokollauflösung {#url-protocol-resolution}

Das Plugin versteht die webforJ [URL-Protokolle](/docs/managing-resources/assets-protocols) und löst sie in Dateisystempfade auf:

| Protokoll | Löst auf | Beispiel |
|-----------|----------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs ohne Protokoll werden vom Minifier nicht unterstützt und werden übersprungen.

## Eingebaute Minifier {#built-in-minifiers}

webforJ enthält zwei produktionsbereite Minifier für CSS und JavaScript.

| Minifier | Funktionen | Überspringt |
|----------|------------|-------------|
| CSS | Entfernt Whitespace, Kommentare und optimiert Attributwerte | `.min.css` |
| JavaScript | Umbenennung von Variablen, Eliminierung ungenutzten Codes, Syntaxoptimierung | `.min.js`, `.min.mjs` |

## Konfigurationsoptionen {#configuration-options}

Das Plugin bietet Optionen zum Deaktivieren der Minifizierung, Anpassen der JavaScript-Optimierung und Verarbeiten zusätzlicher Dateien.

### Deaktivieren der Minifizierung {#disabling-minification}

Möglicherweise möchten Sie die Minifizierung während der Entwicklung oder zu Debugging-Zwecken deaktivieren.

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

### Optionen für den JavaScript-Minifier {#javascript-minifier-options}

Der JavaScript-Minifier bietet mehrere Konfigurationsoptionen, um das Optimierungsverhalten zu steuern.

:::note Gradle Unterstützung
Ab v25.12 unterstützt das Gradle-Plugin das Übergeben von JavaScript-Minifier-Optionen.
:::

| Option | Standard | Beschreibung |
|--------|----------|--------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - entfernt nur Whitespace und Kommentare</li><li>`SIMPLE_OPTIMIZATIONS` - Umbenennung von Variablen und Eliminierung ungenutzten Codes</li><li>`ADVANCED_OPTIMIZATIONS` - aggressive Optimierung mit Umbenennung von Funktionen/Eigenschaften</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Eingangs-JavaScript-Version: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` bis `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Ausgabedatei-Version von JavaScript: dieselbe wie `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Auf `true` setzen, um das Format für Debugging beizubehalten |

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

### Minifizieren zusätzlicher Dateien {#minifying-additional-files}

Um Dateien, die nicht durch Annotationen entdeckt wurden, zu minifizieren, erstellen Sie eine Konfigurationsdatei, die Glob-Muster angibt:

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

Implementieren Sie das `AssetMinifier`-Interface, um Ihren eigenen Minifier zu erstellen. Das folgende Beispiel zeigt einen JSON-Minifier, der Gson verwendet, um Whitespace zu entfernen:

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
      throw new MinificationException("Fehler beim Minifizieren der JSON-Datei: " + sourceFile, e);
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

### Registrieren Ihres Minifiers {#registering-your-minifier}

Erstellen Sie eine Service-Provider-Konfigurationsdatei:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Verwendung Ihres benutzerdefinierten Minifiers {#using-your-custom-minifier}

Verpacken Sie Ihren Minifier als separates JAR und fügen Sie ihn als Plugin-Abhängigkeit hinzu:

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
      [WARN] Stellen Sie sicher, dass ph-css und/oder closure-compiler im Klassenpfad vorhanden sind.
      ```

      Fügen Sie den Minifier-Modulabhängigkeiten zur Plugin-Konfiguration hinzu. Für CSS fügen Sie `webforj-minify-phcss-css` hinzu. Für JavaScript fügen Sie `webforj-minify-closure-js` hinzu.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Keine Dateien verarbeitet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Wenn das Plugin `0 Dateien verarbeitet` meldet, überprüfen Sie, dass:

      1. Der Annotation Processor im `maven-compiler-plugin` mit `webforj-minify-foundation` in `annotationProcessorPaths` konfiguriert ist
      2. WebforJ-Asset-Annotationen in Ihrem Quellcode vorhanden sind
      3. `target/classes/META-INF/webforj-resources.json` nach der Kompilierung vorhanden ist
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

      Stellen Sie sicher, dass die Datei im richtigen Pfad unter `src/main/resources/static` existiert und dass das URL-Protokoll mit der Verzeichnisstruktur übereinstimmt.
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
      [WARN] Fehler bei der Minifizierung der Datei /path/to/app.css: Analysefehler in Zeile 42
      ```

      Das Plugin warnt, fährt aber fort, ohne den Build fehlschlagen zu lassen. Der ursprüngliche Inhalt bleibt erhalten, wenn die Minifizierung fehlschlägt. Um Syntaxfehler zu beheben, validieren Sie CSS oder JavaScript mit einem Linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
