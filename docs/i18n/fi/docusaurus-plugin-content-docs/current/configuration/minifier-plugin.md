---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: updated-content
_i18n_hash: bbc598d57e4531fcd7f76fe117d2e49a
---
# Minifier-laajennus <DocChip chip='since' label='25.11' />

webforJ Minifier -laajennus automaattisesti [minifioi](https://en.wikipedia.org/wiki/Minification_(programming)) ja optimoi CSS- ja JavaScript-resurssit rakentamisprosessin aikana. Laajennus löytää resurssit, joita viitataan webforJ [resurssien annotaatioissa](/docs/managing-resources/importing-assets) ja minifioi ne rakennustulosta, vähentäen tiedostokokoja ja parantaen latausaikoja ilman, että alkuperäisiä lähdetiedostojasi muutetaan.

## Asetus {#setup}

Jos loit projektisi käyttäen [startforJ](https://docs.webforj.com/startforj) tai webforJ [arkkitehtuuria](/docs/building-ui/archetypes/overview), minifier-laajennus on jo konfiguroitu ja se suoritetaan automaattisesti, kun rakennat `prod`-profiililla käyttäen `mvn package -Pprod`.

Manuaalista asetusta varten minifier vaatii kaksi konfiguraatiota: annotaatio-prosessori löytämään resurssit käännöksen aikana ja laajennuksen suorittamaan minifikaation.

<Tabs>
<TabItem value="maven" label="Maven">

Lisää seuraava `pom.xml`-tiedostoon:

```xml
<build>
  <plugins>
    <!-- Annotaatio Prosessorin Konfiguraatio -->
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

    <!-- Minifier Laajennuksen Konfiguraatio -->
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
        <!-- CSS minifikaatio -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript minifikaatio -->
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
  // Annotaatio prosessori löytämään resurssit käännöksen aikana
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier toteutuksia
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify`-tehtävä suoritetaan automaattisesti ennen `jar`- tai `war`-tehtäviä. Voit myös suorittaa sen manuaalisesti komennolla `./gradlew minify`.

</TabItem>
</Tabs>

## Laajennuksen käyttäminen {#using-the-plugin}

Kun laajennus on konfiguroitu, se toimii automaattisesti. Käytä vain webforJ-resurssien annotaatioita koodissasi:

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Sovelluskoodisi
}
```

Kun rakennat projektisi, laajennus automaattisesti:

1. Löytää resurssit, joihin viitataan annotaatioissa käännöksen aikana
2. Minifioi löydetyt CSS- ja JavaScript-tiedostot
3. Raportoi koon alentamisen ja käsittelyajan

### URL-protokollan ratkaisus {#url-protocol-resolution}

Laajennus ymmärtää webforJ [URL-protokollat](/docs/managing-resources/assets-protocols) ja ratkaisee ne tiedostojärjestelmän poluiksi:

| Protokolla | Ratkaisee | Esimerkki |
|------------|-----------|-----------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Protokollatta olevia URL-osoitteita ei tueta minifierissa ja ne ohitetaan.

## Sisäänrakennetut minifierit {#built-in-minifiers}

webforJ sisältää kaksi tuotantovalmiita minifieria CSS:lle ja JavaScriptille.

| Minifier | Ominaisuudet | Ohittaa |
|----------|--------------|---------|
| CSS | Poistaa tyhjät tilat, kommentit ja optimoi ominaisuusarvot | `.min.css` |
| JavaScript | Muuttujan uudelleennimeäminen, kuollut koodi eliminointi, syntaksin optimointi | `.min.js`, `.min.mjs` |

## Konfigurointivaihtoehdot {#configuration-options}

Laajennus tarjoaa vaihtoehtoja minifikaation poistamiseksi, JavaScriptin optimoinnin mukauttamiseksi ja lisätiedostojen käsittelemiseksi.

### Minifikaation poistaminen {#disabling-minification}

Halutaan ehkä poistaa minifikaatio käytön aikana tai vianmääritystarkoituksissa.

<Tabs>
<TabItem value="maven" label="Maven">

**Komentoriviltä:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Laajennuksen konfiguraation kautta:**
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

**Rakennuskonfiguraation kautta:**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### JavaScript minifierin vaihtoehdot {#javascript-minifier-options}

JavaScript-minifier tarjoaa useita konfigurointivaihtoehtoja optimointikäyttäytymisen hallintaan.

:::note Gradle-tuki
Versiosta v25.12 alkaen Gradle-laajennus tukee JavaScript-minifierin vaihtoehtojen välittämistä.
:::

| Vaihtoehto | Oletus | Kuvaus |
|------------|--------|--------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - vain tyhjien tilojen ja kommenttien poistaminen</li><li>`SIMPLE_OPTIMIZATIONS` - muuttujan uudelleennimeäminen ja kuolleen koodin poistaminen</li><li>`ADVANCED_OPTIMIZATIONS` - aggressiivinen optimointi toimintojen/ominaisuuksien uudelleennimeämisellä</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Syöte JavaScript -versio: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` - `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Tuloste JavaScript -versio: sama kuin `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Aseta `true`, jos haluat säilyttää muotoilun vianmääritystä varten |

Määritä nämä vaihtoehdot konfigurointiosiossa:

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
    skip = false  // Aseta true, jos haluat ohittaa minifikaation
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### Lisätiedostojen minifioiminen {#minifying-additional-files}

Minifioi tiedostoja, joita ei löydy annotaatioiden kautta, luo konfiguraatiotiedosto, joka määrittää glob-kaavat:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Sisällytä kaavat
**/*.css
**/*.js

# Poissulku kaavat (edellä !)
!**/*.min.css
!**/*.min.js
```

## Mukautetut minifierit {#custom-minifiers}

Laajennus tukee mukautettuja minifiereitä Java'n Palveluntarjoaja-rajapinnan (SPI) kautta, jolloin voit lisätä tuen lisätiedostotyypeille tai vaihtoehtoisille minifikaatiokirjastoille.

### Mukautetun minifierin luominen {#creating-a-custom-minifier}

Toteuta `AssetMinifier`-rajapinta luodaksesi oman minifierisi. Seuraava esimerkki näyttää JSON-minifierin, joka käyttää Gsonia tyhjien tilojen poistamiseen:

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
      logger.warning("Viallisen JSON-tiedosto " + sourceFile + ", ohitetaan: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("JSON-tiedoston minifiointi epäonnistui: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Ohita konfigurointitiedostot ja jo minifioidut tiedostot
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
    // Konfigurointivaihtoehtoja, jos tarpeen
  }
}
```

### Minifierin rekisteröinti {#registering-your-minifier}

Luo palveluntarjoajan konfiguraatiotiedosto:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Mukautetun minifierin käyttäminen {#using-your-custom-minifier}

Pakkaa minifierisi erilliseksi JAR:ksi ja lisää se laajennuksen riippuvuudeksi:

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
    <!-- Vakiominifierit (valinnainen) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Yleiset ongelmat {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ei rekisteröityjä minifiereitä</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Ei rekisteröityjä minifiereitä SPI:n kautta. Minifikaatio ohitetaan.
      [WARN] Varmista, että ph-css ja/tai closure-compiler ovat luettelossa.
      ```

      Lisää minifier-moduulin riippuvuudet laajennuksen konfiguraatioon. CSS:lle, lisää `webforj-minify-phcss-css`. JavaScriptille, lisää `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ei käsiteltyjä tiedostoja</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Jos laajennus raportoi `Käsitelty 0 tiedostoa`, tarkista, että:

      1. Annotoituprosessori on konfiguroitu `maven-compiler-plugin`-yhteydessä ja `webforj-minify-foundation` on `annotationProcessorPaths`-kentässä
      2. webforJ-resurssien annotaatioita on olemassa lähdekoodissasi
      3. `target/classes/META-INF/webforj-resources.json` on olemassa käännöksen jälkeen
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Tiedostoa ei löytynyt</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Tiedostoa ei löytynyt: /path/to/static/css/app.css (viitattu nimellä 'ws://css/app.css')
      ```

      Varmista, että tiedosto löytyy oikeasta sijainnista `src/main/resources/static`-hakemistosta ja että URL-protokolla vastaa hakemistorakennetta.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minifikaatio-ongelmat</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Virhe minifioitaessa tiedostoa /path/to/app.css: jäsentelyvirhe rivillä 42
      ```

      Laajennus varoittaa mutta jatkaa ilman, että rakentaminen epäonnistuu. Alkuperäinen sisältö säilytetään minifikaation epäonnistumisen jälkeen. Korjataksesi syntaks virheet, varmista CSS tai JavaScript linterillä.
    </div>
  </AccordionDetails>
</Accordion>
<br />
