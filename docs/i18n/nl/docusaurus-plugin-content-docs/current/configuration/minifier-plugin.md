---
sidebar_position: 40
title: Minifier-plug-in
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Minifierplug-in <DocChip chip='since' label='25.11' />

De webforJ Minifierplug-in [verkleint](https://en.wikipedia.org/wiki/Minification_(programming)) en optimaliseert CSS- en JavaScript-assets automatisch tijdens het buildproces. De plug-in ontdekt assets die worden vermeld via webforJ-[assetannotaties](/docs/managing-resources/importing-assets) en verkleint ze in de buildoutput. Zo worden bestanden kleiner en laden ze sneller, zonder dat de oorspronkelijke bronbestanden worden gewijzigd.

:::info Verkleinen is ingebouwd in de bundler
De [frontend-bundler](/docs/managing-resources/bundler/overview) verkleint CSS en JavaScript tijdens de productiebuild. Een project dat de bundler gebruikt, heeft deze plug-in dus niet nodig. De plug-in blijft beschikbaar en wordt ondersteund voor projecten die assets via assetannotaties laden zonder de bundler. Bestaande configuraties blijven werken zoals voorheen; er is geen wijziging nodig.
:::

## Instellen {#setup}

Voor de configuratie zijn twee onderdelen nodig: een annotatieprocessor om assets tijdens het compileren te ontdekken en een plug-in om ze te verkleinen.

<Tabs>
<TabItem value="maven" label="Maven">

Voeg het volgende toe aan je `pom.xml`:

```xml
<build>
  <plugins>
    <!-- Annotation Processor Configuration -->
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

    <!-- Minifier Plugin Configuration -->
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
        <!-- CSS minification -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript minification -->
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

Voeg het volgende toe aan je `build.gradle`:

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
  // Annotation processor for discovering assets during compilation
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier implementations
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

De taak `minify` wordt automatisch uitgevoerd vóór de taken `jar` of `war`. Je kunt de taak ook handmatig uitvoeren met `./gradlew minify`.

</TabItem>
</Tabs>

## De plug-in gebruiken {#using-the-plugin}

Na de configuratie werkt de plug-in automatisch. Gebruik webforJ-assetannotaties in je code:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Your application code
}
```

Wanneer je je project bouwt, voert de plug-in automatisch de volgende stappen uit:

1. Assets ontdekken die tijdens het compileren in annotaties worden vermeld
2. De ontdekte CSS- en JavaScript-bestanden verkleinen
3. De bestandsverkleining en verwerkingstijd rapporteren

### URL-protocol oplossen {#url-protocol-resolution}

De plug-in herkent webforJ-[URL-protocollen](/docs/managing-resources/assets-protocols) en vertaalt deze naar bestandssysteempaden:

| Protocol | Wordt vertaald naar | Voorbeeld |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URL's zonder protocol worden niet ondersteund door de minifier en worden overgeslagen.

## Ingebouwde minifiers {#built-in-minifiers}

webforJ bevat twee productierijpe minifiers voor CSS en JavaScript.

| Minifier | Functies | Wordt overgeslagen |
|----------|----------|-------|
| CSS | Verwijdert witruimte en opmerkingen en optimaliseert eigenschapswaarden | `.min.css` |
| JavaScript | Variabelen hernoemen, dode code verwijderen en syntaxis optimaliseren | `.min.js`, `.min.mjs` |

## Configuratieopties {#configuration-options}

De plug-in biedt opties om verkleinen uit te schakelen, JavaScript-optimalisatie aan te passen en extra bestanden te verwerken.

### Verkleinen uitschakelen {#disabling-minification}

Je kunt verkleinen uitschakelen tijdens de ontwikkeling of voor foutopsporing.

<Tabs>
<TabItem value="maven" label="Maven">

**Via de opdrachtregel:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Via de plug-inconfiguratie:**
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

**Via de buildconfiguratie:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### Opties voor de JavaScript-minifier {#javascript-minifier-options}

De JavaScript-minifier biedt verschillende configuratieopties om het optimalisatiegedrag te bepalen.

:::note Ondersteuning voor Gradle
Vanaf v25.12 ondersteunt de Gradle-plug-in het doorgeven van opties aan de JavaScript-minifier.
:::

| Optie | Standaard | Beschrijving |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - verwijdert alleen witruimte en opmerkingen</li><li>`SIMPLE_OPTIMIZATIONS` - hernoemt variabelen en verwijdert dode code</li><li>`ADVANCED_OPTIMIZATIONS` - agressieve optimalisatie met het hernoemen van functies en eigenschappen</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Invoer-JavaScript-versie: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` tot en met `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Uitvoer-JavaScript-versie: hetzelfde als `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Stel in op `true` om de opmaak te behouden voor foutopsporing |

Configureer deze opties in het configuratiegedeelte:

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
    skip = false  // Set to true to skip minification
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Extra bestanden verkleinen {#minifying-additional-files}

Als je bestanden wilt verkleinen die niet via annotaties zijn ontdekt, maak je een configuratiebestand met globpatronen:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## Aangepaste minifiers {#custom-minifiers}

De plug-in ondersteunt aangepaste minifiers via Java's Service Provider Interface (SPI), zodat je ondersteuning kunt toevoegen voor extra bestandstypen of alternatieve minificatiebibliotheken.

### Een aangepaste minifier maken {#creating-a-custom-minifier}

Implementeer de interface `AssetMinifier` om je eigen minifier te maken. Het volgende voorbeeld toont een JSON-minifier die Gson gebruikt om witruimte te verwijderen:

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
    // Skip config files and already minified files
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
    // Configuration options if needed
  }
}
```

### Je minifier registreren {#registering-your-minifier}

Maak een configuratiebestand voor de serviceprovider:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Je aangepaste minifier gebruiken {#using-your-custom-minifier}

Verpak je minifier als een aparte JAR en voeg deze toe als pluginafhankelijkheid:

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
    <!-- Standard minifiers (optional) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Veelvoorkomende problemen {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Geen minifiers geregistreerd</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      Voeg afhankelijkheden van minifier-modules toe aan de plug-inconfiguratie. Voeg voor CSS `webforj-minify-phcss-css` toe. Voeg voor JavaScript `webforj-minify-closure-js` toe.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Geen bestanden verwerkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Als de plug-in `Processed 0 files` meldt, controleer dan of:

      1. De annotatieprocessor is geconfigureerd in `maven-compiler-plugin`, met `webforj-minify-foundation` in `annotationProcessorPaths`
      2. Er webforJ-assetannotaties in je broncode staan
      3. `target/classes/META-INF/webforj-resources.json` na het compileren bestaat
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Bestand niet gevonden</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Controleer of het bestand zich op het juiste pad onder `src/main/resources/static` bevindt en of het URL-protocol overeenkomt met de mapstructuur.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Fouten bij het verkleinen</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      De plug-in geeft een waarschuwing, maar gaat verder zonder de build te laten mislukken. Als het verkleinen mislukt, blijft de oorspronkelijke inhoud behouden. Controleer CSS of JavaScript met een linter om syntaxisfouten op te lossen.
    </div>
  </AccordionDetails>
</Accordion>
<br />
