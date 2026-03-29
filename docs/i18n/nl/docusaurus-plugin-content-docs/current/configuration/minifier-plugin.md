---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: updated-content
_i18n_hash: bbc598d57e4531fcd7f76fe117d2e49a
---
# Minifier-plugin <DocChip chip='since' label='25.11' />

De webforJ Minifier-plugin minimaliseert automatisch [minifiëren](https://en.wikipedia.org/wiki/Minification_(programming)) en optimaliseert CSS- en JavaScript-bestanden tijdens het bouwproces. De plugin ontdekt bestanden die zijn verwezen via webforJ [assetannotaties](/docs/managing-resources/importing-assets) en minimaliseert deze in de bouwuitvoer, waardoor de bestandsgroottes worden verminderd en de laadtijden worden verbeterd zonder je oorspronkelijke bronbestanden te wijzigen.

## Setup {#setup}

Als je je project hebt aangemaakt met [startforJ](https://docs.webforj.com/startforj) of een webforJ [archetype](/docs/building-ui/archetypes/overview), is de minifier-plugin al geconfigureerd en draait deze automatisch wanneer je bouwt met het `prod`-profiel met `mvn package -Pprod`.

Voor handmatige setup vereist de minifier twee configuraties: een annotatieprocessor om bestanden tijdens compilatie te ontdekken en een plugin om de minificatie uit te voeren.

<Tabs>
<TabItem value="maven" label="Maven">

Voeg het volgende toe aan je `pom.xml`:

```xml
<build>
  <plugins>
    <!-- Configuratie van de annotatieprocessor -->
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

    <!-- Configuratie van de minifier-plugin -->
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
        <!-- CSS-minificatie -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript-minificatie -->
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
  // Annotatieprocessor voor het ontdekken van bestanden tijdens compilatie
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier-implementaties
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

De taak `minify` draait automatisch vóór de `jar` of `war` taken. Je kunt deze ook handmatig uitvoeren met `./gradlew minify`.

</TabItem>
</Tabs>

## De plugin gebruiken {#using-the-plugin}

Zodra deze is geconfigureerd, werkt de plugin automatisch. Gebruik eenvoudigweg de webforJ assetannotaties in je code:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Je applicatiecode
}
```

Wanneer je je project bouwt, ontdekt de plugin automatisch:

1. Bestanden die zijn verwezen in annotaties tijdens de compilatie
2. Minimaliseert de ontdekte CSS- en JavaScript-bestanden
3. Rapporteert de grootte reductie en verwerkingstijd

### URL-protocol oplossing {#url-protocol-resolution}

De plugin begrijpt webforJ [URL-protocollen](/docs/managing-resources/assets-protocols) en lost deze op naar bestandspaden:

| Protocoll | Lost op | Voorbeeld |
|-----------|---------|-----------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs zonder een protocol worden niet ondersteund door de minifier en worden overgeslagen.

## Ingebouwde minifiers {#built-in-minifiers}

webforJ bevat twee productieklare minifiers voor CSS en JavaScript.

| Minifier | Kenmerken | Slaat over |
|----------|-----------|------------|
| CSS | Verwijdert witruimtes, opmerkingen en optimaliseert eigenschapswaarden | `.min.css` |
| JavaScript | Variabelen hernoemen, dode code elimineren, syntaxisoptimalisatie | `.min.js`, `.min.mjs` |

## Configuratieopties {#configuration-options}

De plugin biedt opties voor het uitschakelen van minificatie, het aanpassen van JavaScript-optimalisatie en het verwerken van extra bestanden.

### Minificatie uitschakelen {#disabling-minification}

Je wilt misschien de minificatie uitschakelen tijdens de ontwikkeling of voor debugdoeleinden.

<Tabs>
<TabItem value="maven" label="Maven">

**Via de opdrachtregel:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Via pluginconfiguratie:**
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

**Via buildconfiguratie:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### Opties voor de JavaScript-minifier {#javascript-minifier-options}

De JavaScript-minifier biedt verschillende configuratieopties om het optimalisatiegedrag te controleren.

:::note Gradle Ondersteuning
Vanaf v25.12 ondersteunt de Gradle-plugin het doorgeven van opties voor de JavaScript-minifier.
:::

| Optie | Standaard | Beschrijving |
|-------|-----------|--------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - verwijdert alleen witruimtes en opmerkingen</li><li>`SIMPLE_OPTIMIZATIONS` - variabelen hernoemen en dode code verwijderen</li><li>`ADVANCED_OPTIMIZATIONS` - agressieve optimalisatie met functie-/eigenschapshernoeming</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Ingang JavaScript-versie: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` tot `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Uitvoer JavaScript-versie: hetzelfde als `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Stel in op `true` om de opmaak voor debuggen te behouden |

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
    skip = false  // Stel in op true om minificatie over te slaan
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Extra bestanden minificeren {#minifying-additional-files}

Om bestanden te minificeren die niet zijn ontdekt via annotaties, maak je een configuratiebestand dat glob-patronen specificeert:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Insluitpatronen
**/*.css
**/*.js

# Uitsluitpatronen (prefix met !)
!**/*.min.css
!**/*.min.js
```

## Aangepaste minifiers {#custom-minifiers}

De plugin ondersteunt aangepaste minifiers via de Service Provider Interface (SPI) van Java, waardoor je ondersteuning kunt toevoegen voor extra bestandtypen of alternatieve minificatiebibliotheken.

### Een aangepaste minifier maken {#creating-a-custom-minifier}

Implementeer de interface `AssetMinifier` om je eigen minifier te maken. Het volgende voorbeeld toont een JSON-minifier die Gson gebruikt om witruimtes te verwijderen:

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
      logger.warning("Ongeldig JSON in " + sourceFile + ", overslaan: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Kon JSON-bestand niet minificeren: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Sla configuratiebestanden en al minified bestanden over
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
    // Configuratieopties indien nodig
  }
}
```

### Je minifier registreren {#registering-your-minifier}

Maak een configuratiebestand voor de serviceprovider:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Je aangepaste minifier gebruiken {#using-your-custom-minifier}

Verpak je minifier als een aparte JAR en voeg deze toe als een pluginafhankelijkheid:

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
    <!-- Standaard minifiers (optioneel) -->
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
      [WARN] Geen minifiers geregistreerd via SPI. Minificatie overslaan.
      [WARN] Zorg ervoor dat ph-css en/of closure-compiler op het classpath staan.
      ```

      Voeg afhankelijkheden van minifier-modules toe aan de pluginconfiguratie. Voor CSS, voeg `webforj-minify-phcss-css` toe. Voor JavaScript, voeg `webforj-minify-closure-js` toe.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Geen bestanden verwerkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Als de plugin rapporteert `0 bestanden verwerkt`, controleer dan of:

      1. De annotatieprocessor is geconfigureerd in `maven-compiler-plugin` met `webforj-minify-foundation` in `annotationProcessorPaths`
      2. webforJ assetannotaties aanwezig zijn in je broncode
      3. `target/classes/META-INF/webforj-resources.json` bestaat na compilatie
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
      [WARN] Bestand niet gevonden: /path/to/static/css/app.css (verwezen als 'ws://css/app.css')
      ```

      Controleer of het bestand bestaat op het juiste pad onder `src/main/resources/static` en of het URL-protocol overeenkomt met de directorystructuur.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minificatiefouten</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Fout bij minificatie van bestand /path/to/app.css: parse-fout op regel 42
      ```

      De plugin waarschuwt maar gaat door zonder de build te laten falen. De originele inhoud wordt behouden wanneer minificatie mislukt. Om syntaxisfouten te verhelpen, valideer CSS of JavaScript met een linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
