---
sidebar_position: 40
title: Minifiointilisäosa
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Minifiointilaajennus <DocChip chip='since' label='25.11' />

webforJ-minifiointilaajennus [minifioi](https://en.wikipedia.org/wiki/Minification_(programming)) CSS- ja JavaScript-resurssit automaattisesti ja optimoi ne koontiprosessin aikana. Laajennus löytää webforJ-[resurssimerkintöjen](/docs/managing-resources/importing-assets) kautta viitatut resurssit ja minifioi ne koontitulosteessa. Näin tiedostokoot pienenevät ja latausajat lyhenevät, eikä alkuperäisiä lähdetiedostoja muuteta.

:::info Minifiointi sisältyy koontityökaluun
[Frontend-koontityökalu](/docs/managing-resources/bundler/overview) minifioi CSS:n ja JavaScriptin osana tuotantokoontia, joten koontityökalua käyttävä projekti ei tarvitse tätä laajennusta. Laajennus on edelleen saatavilla ja tuettu projekteissa, jotka lataavat resursseja resurssimerkintöjen avulla ilman koontityökalua. Nykyiset määritykset toimivat kuten ennenkin, eikä muutoksia tarvita.
:::

## Käyttöönotto {#setup}

Minifiointilaajennuksen käyttöönotto edellyttää kahta määritystä: annotaatioprosessoria, joka löytää resurssit käännöksen aikana, ja laajennusta, joka suorittaa minifioinnin.

<Tabs>
<TabItem value="maven" label="Maven">

Lisää seuraava `pom.xml`-tiedostoon:

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

Lisää seuraava `build.gradle`-tiedostoon:

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

`minify`-tehtävä suoritetaan automaattisesti ennen `jar`- tai `war`-tehtävää. Voit suorittaa sen myös manuaalisesti komennolla `./gradlew minify`.

</TabItem>
</Tabs>

## Laajennuksen käyttäminen {#using-the-plugin}

Kun laajennus on määritetty, se toimii automaattisesti. Käytä vain webforJ-resurssimerkintöjä koodissasi:

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

Kun koostat projektisi, laajennus suorittaa automaattisesti seuraavat toiminnot:

1. Löytää merkinnöissä viitatut resurssit käännöksen aikana
2. Minifioi löydetyt CSS- ja JavaScript-tiedostot
3. Raportoi tiedostokokojen pienenemisen ja käsittelyajan

### URL-protokollien ratkaiseminen {#url-protocol-resolution}

Laajennus ymmärtää webforJ-[URL-protokollat](/docs/managing-resources/assets-protocols) ja ratkaisee ne tiedostojärjestelmän poluiksi:

| Protokolla | Ratkeaa poluksi | Esimerkki |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Minifiointilaajennus ei tue URL-osoitteita, joissa ei ole protokollaa, ja ohittaa ne.

## Sisäänrakennetut minifioijat {#built-in-minifiers}

webforJ sisältää kaksi tuotantokäyttöön valmista minifioijaa CSS:lle ja JavaScriptille.

| Minifioija | Ominaisuudet | Ohittaa |
|----------|----------|-------|
| CSS | Poistaa tyhjemerkit ja kommentit sekä optimoi ominaisuusarvoja | `.min.css` |
| JavaScript | Muuttujien uudelleennimeäminen, käyttämättömän koodin poistaminen ja syntaksin optimointi | `.min.js`, `.min.mjs` |

## Määritysasetukset {#configuration-options}

Laajennuksen asetuksilla voit poistaa minifioinnin käytöstä, mukauttaa JavaScriptin optimointia ja käsitellä lisätiedostoja.

### Minifioinnin poistaminen käytöstä {#disabling-minification}

Voit poistaa minifioinnin käytöstä kehityksen aikana tai virheenkorjausta varten.

<Tabs>
<TabItem value="maven" label="Maven">

**Komentoriviltä:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Laajennuksen määrityksessä:**
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

**Koontimäärityksessä:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### JavaScript-minifioijan asetukset {#javascript-minifier-options}

JavaScript-minifioijassa on useita asetuksia, joilla voit hallita optimoinnin toimintaa.

:::note Gradle-tuki
Versiosta v25.12 alkaen Gradle-laajennus tukee JavaScript-minifioijan asetusten välittämistä.
:::

| Asetus | Oletusarvo | Kuvaus |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - poistaa vain tyhjemerkit ja kommentit</li><li>`SIMPLE_OPTIMIZATIONS` - nimeää muuttujat uudelleen ja poistaa käyttämätöntä koodia</li><li>`ADVANCED_OPTIMIZATIONS` - aggressiivinen optimointi, jossa funktioiden ja ominaisuuksien nimet muutetaan</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Syötteen JavaScript-versio: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015`–`ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Tulosteen JavaScript-versio: samat kuin `languageIn`-asetuksessa sekä `NO_TRANSPILE` |
| `prettyPrint` | `false` | Aseta arvoksi `true`, jos haluat säilyttää muotoilun virheenkorjausta varten |

Määritä nämä asetukset määritysosiossa:

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

### Lisätiedostojen minifiointi {#minifying-additional-files}

Jos haluat minifioida tiedostoja, joita ei löydy merkintöjen avulla, luo määritystiedosto, jossa määritetään glob-kuviot:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## Mukautetut minifioijat {#custom-minifiers}

Laajennus tukee mukautettuja minifioijia Java Service Provider Interface (SPI) -rajapinnan kautta. Näin voit lisätä tuen muille tiedostotyypeille tai vaihtoehtoisille minifiointikirjastoille.

### Mukautetun minifioijan luominen {#creating-a-custom-minifier}

Toteuta `AssetMinifier`-rajapinta luodaksesi oman minifioijan. Seuraavassa esimerkissä luodaan Gsonia käyttävä JSON-minifioija, joka poistaa tyhjemerkit:

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

### Minifioijan rekisteröinti {#registering-your-minifier}

Luo palveluntarjoajan määritystiedosto:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Mukautetun minifioijan käyttäminen {#using-your-custom-minifier}

Paketoi minifioijasi erilliseksi JAR-tiedostoksi ja lisää se laajennuksen riippuvuudeksi:

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

## Yleisiä ongelmia {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minifioijia ei ole rekisteröity</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      Lisää minifioijamoduulien riippuvuudet laajennuksen määritykseen. Lisää CSS:ää varten `webforj-minify-phcss-css` ja JavaScriptiä varten `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Tiedostoja ei käsitelty</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Jos laajennus ilmoittaa `Processed 0 files`, tarkista seuraavat asiat:

      1. Annotaatioprosessori on määritetty `maven-compiler-plugin`-laajennuksessa siten, että `annotationProcessorPaths` sisältää `webforj-minify-foundation`-riippuvuuden
      2. Lähdekoodissasi on webforJ-resurssimerkintöjä
      3. `target/classes/META-INF/webforj-resources.json` on olemassa käännöksen jälkeen
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Tiedostoa ei löydy</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      Varmista, että tiedosto on oikeassa polussa hakemiston `src/main/resources/static` alla ja että URL-protokolla vastaa hakemistorakennetta.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minifiointivirheet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      Laajennus antaa varoituksen, mutta jatkaa koontia keskeyttämättä sitä. Jos minifiointi epäonnistuu, alkuperäinen sisältö säilytetään. Korjaa syntaksivirheet tarkistamalla CSS tai JavaScript lintterillä.
    </div>
  </AccordionDetails>
</Accordion>
<br />
