---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Minifier-Plugin <DocChip chip='since' label='25.11' />

Das webforJ Minifier-Plugin minimiert und optimiert automatisch CSS- und JavaScript-Ressourcen während des Build-Prozesses. Das Plugin entdeckt Ressourcen, die durch webforJ [Ressourcen-Anmerkungen](/docs/managing-resources/importing-assets) referenziert werden, und minimiert sie im Build-Ausgang, wodurch die Dateigrößen verringert und die Ladezeiten verbessert werden, ohne die ursprünglichen Quelldateien zu ändern.

## Setup {#setup}

Wenn du dein Projekt mit [startforJ](https://docs.webforj.com/startforj) oder einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt hast, ist das Minifier-Plugin bereits konfiguriert und wird automatisch ausgeführt, wenn du mit dem `prod`-Profil mit `mvn package -Pprod` baust.

Für die manuelle Einrichtung benötigt der Minifier zwei Konfigurationen: einen Annotationsprozessor, um Ressourcen während der Kompilierung zu entdecken, und ein Plugin, um die Minimierung durchzuführen.

<Tabs>
<TabItem value="maven" label="Maven">

Füge Folgendes zu deiner `pom.xml` hinzu:

```xml
<build>
  <plugins>
    <!-- Konfiguration des Annotationsprozessors -->
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
        <!-- CSS-Minimierung -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript-Minimierung -->
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
    // Annotationsprozessor zur Entdeckung von Ressourcen während der Kompilierung
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Minifier-Implementierungen
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

Die Aufgabe `minify` wird automatisch vor den Aufgaben `jar` oder `war` ausgeführt. Du kannst sie auch manuell mit `./gradlew minify` ausführen.

</TabItem>
</Tabs>

## Verwendung des Plugins {#using-the-plugin}

Nach der Konfiguration funktioniert das Plugin automatisch. Verwende einfach webforJ-Ressourcen-Anmerkungen in deinem Code:

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

Wenn du dein Projekt baust, erledigt das Plugin automatisch:

1. Entdeckt Ressourcen, die in Anmerkungen während der Kompilierung referenziert werden
2. Minimiert die entdeckten CSS- und JavaScript-Dateien
3. Berichtet über die Größenreduzierung und die Verarbeitungszeit

### URL-Protokollauflösung {#url-protocol-resolution}

Das Plugin versteht webforJ [URL-Protokolle](/docs/managing-resources/assets-protocols) und löst sie in Dateisystempfade auf:

| Protokoll   | Löst sich auf zu                              | Beispiel                             |
|-------------|----------------------------------------------|-------------------------------------|
| `ws://`     | `src/main/resources/static/`                 | `ws://css/app.css` → `static/css/app.css` |
| `context://`| `src/main/resources/`                        | `context://styles/app.css` → `styles/app.css` |

URLs ohne Protokoll werden vom Minifier nicht unterstützt und werden übersprungen.

## Eingebaute Minifier {#built-in-minifiers}

webforJ umfasst zwei produktionsbereite Minifier für CSS und JavaScript.

| Minifier   | Funktionen                                                  | Überspringt               |
|------------|------------------------------------------------------------|---------------------------|
| CSS        | Entfernt Leerzeichen, Kommentare und optimiert Eigenschaften | `.min.css`                |
| JavaScript | Variablenumbenennung, Eliminierung toter Codes, Syntaxoptimierung | `.min.js`, `.min.mjs`     |

## Konfigurationsoptionen {#configuration-options}

Das Plugin bietet Optionen zur Deaktivierung der Minimierung, zur Anpassung der JavaScript-Optimierung und zur Verarbeitung zusätzlicher Dateien.

### Minimierung deaktivieren {#disabling-minification}

Du möchtest möglicherweise die Minimierung während der Entwicklung oder zu Debuggingzwecken deaktivieren.

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

### Optionen des JavaScript-Minifiers {#javascript-minifier-options}

Der JavaScript-Minifier bietet mehrere Konfigurationsoptionen zur Steuerung des Optimierungsverhaltens.

:::info Nur Maven
Die Optionen des JavaScript-Minifiers sind derzeit nur für Maven verfügbar. Gradle-Unterstützung verwendet die Standardeinstellungen.
:::

| Option            | Standard                       | Beschreibung |
|------------------|-------------------------------|--------------|
| `compilationLevel`| `SIMPLE_OPTIMIZATIONS`       | <ul><li>`WHITESPACE_ONLY` - entfernt nur Leerzeichen und Kommentare</li><li>`SIMPLE_OPTIMIZATIONS` - Variablenumbenennung und Eliminierung toter Codes</li><li>`ADVANCED_OPTIMIZATIONS` - aggressive Optimierung mit Umbenennung von Funktionen/Eigenschaften</li></ul> |
| `languageIn`     | `ECMASCRIPT_NEXT`             | Eingabewert für JavaScript-Version: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` bis `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut`    | `ECMASCRIPT5`                 | Ausgabewert für JavaScript-Version: gleich wie `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint`    | `false`                       | Setze auf `true`, um die Formatierung für Debugging zu erhalten |

Konfiguriere diese Optionen im Abschnitt `minifierConfigurations`:

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

### Minimierung zusätzlicher Dateien {#minifying-additional-files}

Um Dateien, die nicht über Anmerkungen entdeckt werden, zu minimieren, erstelle eine Konfigurationsdatei, die Glob-Muster angibt:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Einschlussmuster
**/*.css
**/*.js

# Ausschlussmuster (mit ! präfixen)
!**/*.min.css
!**/*.min.js
```

## Benutzerdefinierte Minifier {#custom-minifiers}

Das Plugin unterstützt benutzerdefinierte Minifier über die Java Service Provider Interface (SPI), die es dir ermöglichen, Unterstützung für zusätzliche Dateitypen oder alternative Minimierungsbibliotheken hinzuzufügen.

### Erstellen eines benutzerdefinierten Minifiers {#creating-a-custom-minifier}

Implementiere das `AssetMinifier`-Interface, um deinen eigenen Minifier zu erstellen. Das folgende Beispiel zeigt einen JSON-Minifier, der Gson verwendet, um Leerzeichen zu entfernen:

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
      logger.warning("Fehlerhaftes JSON in " + sourceFile + ", überspringe: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Fehler beim Minimieren der JSON-Datei: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Überspringe Konfigurationsdateien und bereits minimierte Dateien
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
    // Konfigurationsoptionen falls erforderlich
  }
}
```

### Registrieren deines Minifiers {#registering-your-minifier}

Erstelle eine Konfigurationsdatei für den Dienstanbieter:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Verwenden deines benutzerdefinierten Minifiers {#using-your-custom-minifier}

Paketiere deinen Minifier als separates JAR und füge ihn als Plugin-Abhängigkeit hinzu:

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
    <!-- Standardminifier (optional) -->
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
      [WARN] Keine Minifier über SPI registriert. Minimierung wird übersprungen.
      [WARN] Stellen Sie sicher, dass ph-css und/oder closure-compiler im Klassenpfad sind.
      ```

      Füge Minifier-Modulabhängigkeiten zur Plugin-Konfiguration hinzu. Für CSS füge `webforj-minify-phcss-css` hinzu. Für JavaScript füge `webforj-minify-closure-js` hinzu.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Keine Dateien verarbeitet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Wenn das Plugin `0 Dateien verarbeitet` meldet, überprüfe, ob:

      1. Der Annotationsprozessor im `maven-compiler-plugin` mit `webforj-minify-foundation` in `annotationProcessorPaths` konfiguriert ist
      2. webforJ-Ressourcen-Anmerkungen in deinem Quellcode vorhanden sind
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

      Überprüfe, ob die Datei am richtigen Pfad unter `src/main/resources/static` existiert und ob das URL-Protokoll mit der Verzeichnisstruktur übereinstimmt.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minimierungsfehler</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Fehler beim Minimieren der Datei /path/to/app.css: Parse-Fehler in Zeile 42
      ```

      Das Plugin warnt, setzt jedoch ohne einen Build-Fehler fort. Der ursprüngliche Inhalt wird beibehalten, wenn die Minimierung fehlschlägt. Um Syntaxfehler zu beheben, validiere CSS oder JavaScript mit einem Linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
