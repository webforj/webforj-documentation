---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Minifier plugin <DocChip chip='since' label='25.11' />

De webforJ Minifier Plugin minimaliseert automatisch [minifies](https://en.wikipedia.org/wiki/Minification_(programming)) en optimaliseert CSS- en JavaScript-assets tijdens het buildproces. De plugin ontdekt assets die worden vermeld via webforJ [assetannotaties](/docs/managing-resources/importing-assets) en minimaliseert deze in de buildoutput, wat de bestandsgroottes vermindert en de laadtijden verbetert zonder je oorspronkelijke bronbestanden te wijzigen.

## Setup {#setup}

Als je je project hebt aangemaakt met [startforJ](https://docs.webforj.com/startforj) of een webforJ [archetype](/docs/building-ui/archetypes/overview), is de minifier-plugin al geconfigureerd en draait deze automatisch wanneer je bouwt met het `prod` profiel met `mvn package -Pprod`.

Voor handmatige setup vereist de minifier twee configuraties: een annotatieprocessor om assets tijdens de compilatie te ontdekken, en een plugin om de minificatie uit te voeren.

<Tabs>
<TabItem value="maven" label="Maven">

Voeg het volgende toe aan je `pom.xml`:

```xml
<build>
  <plugins>
    <!-- Annotatieprocessor configuratie -->
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

    <!-- Minifier Plugin Configuratie -->
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
    // Annotatieprocessor voor het ontdekken van assets tijdens de compilatie
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Minifier-implementaties
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

De `minify` taak wordt automatisch uitgevoerd vóór de `jar` of `war` taken. Je kunt het ook handmatig uitvoeren met `./gradlew minify`.

</TabItem>
</Tabs>

## Gebruik van de plugin {#using-the-plugin}

Zodra de configuratie is voltooid, werkt de plugin automatisch. Gebruik eenvoudig webforJ assetannotaties in je code:

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

Wanneer je je project bouwt, doet de plugin automatisch:

1. Ontdekt assets die in annotaties tijdens de compilatie worden vermeld
2. Minimaliseert de ontdekte CSS- en JavaScript-bestanden
3. Rapporteert de groottevermindering en verwerkingstijd

### URL-protocolresolutie {#url-protocol-resolution}

De plugin begrijpt webforJ [URL-protocollen](/docs/managing-resources/assets-protocols) en lost deze op naar bestandsystempaden:

| Protocol | Lost op | Voorbeeld |
|----------|---------|-----------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URLs zonder een protocol worden niet door de minifier ondersteund en worden overgeslagen.

## Ingebouwde minifiers {#built-in-minifiers}

webforJ bevat twee productieklaar minifiers voor CSS en JavaScript.

| Minifier | Kenmerken | Slaat over |
|----------|-----------|------------|
| CSS | Verwijdert witruimtes, opmerkingen en optimaliseert eigenschapswaarden | `.min.css` |
| JavaScript | Variabele hernoeming, dode code eliminatie, syntaxisoptimalisatie | `.min.js`, `.min.mjs` |

## Configuratieopties {#configuration-options}

De plugin biedt opties voor het uitschakelen van minificatie, het aanpassen van JavaScript-optimalisatie en het verwerken van extra bestanden.

### Uitschakelen van minificatie {#disabling-minification}

Je wilt mogelijk de minificatie uitschakelen tijdens de ontwikkeling of voor foutopsporingsdoeleinden.

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

### Opties voor JavaScript-minificator {#javascript-minifier-options}

De JavaScript-minificator biedt verschillende configuratieopties om het optimalisatiegedrag te beheersen.

:::info Alleen Maven
Opties voor de JavaScript-minificator zijn momenteel alleen beschikbaar voor Maven. Gradle-ondersteuning gebruikt standaardinstellingen.
:::

| Optie | Standaard | Beschrijving |
|-------|-----------|--------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - verwijdert alleen witruimtes en opmerkingen</li><li>`SIMPLE_OPTIMIZATIONS` - hernoeming van variabelen en verwijdering van dode code</li><li>`ADVANCED_OPTIMIZATIONS` - agressieve optimalisatie met functie-/eigendomshernoeming</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Invoerversie van JavaScript: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` tot `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Uitvoerversie van JavaScript: dezelfde als `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Stel in op `true` om formattering voor foutopsporing te behouden |

Configureer deze opties in de sectie `minifierConfigurations`:

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

### Minificeren van extra bestanden {#minifying-additional-files}

Om bestanden te minimaliseren die niet via annotaties zijn ontdekt, maak je een configuratiebestand dat glob-patronen specificeert:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Inclusiepatronen
**/*.css
**/*.js

# Uitsluitingspatronen (prefix met !)
!**/*.min.css
!**/*.min.js
```

## Aangepaste minifiers {#custom-minifiers}

De plugin ondersteunt aangepaste minifiers via de Service Provider Interface (SPI) van Java, waarmee je ondersteuning kunt toevoegen voor extra bestandstypen of alternatieve minificatiebibliotheken.

### Een aangepaste minifier maken {#creating-a-custom-minifier}

Implementeer de `AssetMinifier` interface om je eigen minifier te maken. Het volgende voorbeeld toont een JSON-minifier die Gson gebruikt om witruimtes te verwijderen:

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
      logger.warning("Ongeldige JSON in " + sourceFile + ", overslaan: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Kon JSON-bestand niet minimaliseren: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Sla configuratiebestanden en al geminimaliseerde bestanden over
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

### Gebruik je aangepaste minifier {#using-your-custom-minifier}

Verpak je minifier als een aparte JAR en voeg deze toe als een plugin-afhankelijkheid:

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
      [WAARSCHUWING] Geen minifiers geregistreerd via SPI. Minificatie overslaan.
      [WAARSCHUWING] Zorg ervoor dat ph-css en/of closure-compiler op het classpath staan.
      ```

      Voeg minifier-modu afhankelijkheden toe aan de pluginconfiguratie. Voor CSS, voeg `webforj-minify-phcss-css` toe. Voor JavaScript, voeg `webforj-minify-closure-js` toe.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Geen bestanden verwerkt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Als de plugin meldt `0 bestanden verwerkt`, controleer dan of:

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
      [WAARSCHUWING] Bestand niet gevonden: /pad/naar/static/css/app.css (verwezen als 'ws://css/app.css')
      ```

      Controleer of het bestand op het juiste pad onder `src/main/resources/static` bestaat en of het URL-protocol overeenkomt met de bestandsstructuur.
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
      [WAARSCHUWING] Fout bij het minimaliseren van bestand /pad/naar/app.css: parse-fout bij regel 42
      ```

      De plugin waarschuwt maar gaat door zonder de build te laten mislukken. De originele inhoud blijft behouden wanneer de minificatie mislukt. Om syntaxisfouten te verhelpen, valideer CSS of JavaScript met een linter.
    </div>
  </AccordionDetails>
</Accordion>
<br />
