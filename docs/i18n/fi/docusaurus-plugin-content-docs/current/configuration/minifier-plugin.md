---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Minifikaajaliitin <DocChip chip='since' label='25.11' />

webforJ Minifikaajaliitin automaattisesti [minifioi](https://en.wikipedia.org/wiki/Minification_(programming)) ja optimoi CSS- ja JavaScript-resursseja rakennusprosessin aikana. Liitin löytää webforJ [resurssimerkinnöistä](/docs/managing-resources/importing-assets) viitattuja lähteitä ja minifioi ne rakennustulokseen, pienentäen tiedostokokoja ja parantaen latausaikoja muuttamatta alkuperäisiä lähdetiedostojasi.

## Asennus {#setup}

Jos loit projektisi käyttäen [startforJ](https://docs.webforj.com/startforj) tai webforJ [archetypea](/docs/building-ui/archetypes/overview), minifikaajaliitin on jo konfiguroitu ja se toimii automaattisesti, kun rakennat `prod`-profiililla käyttäen `mvn package -Pprod`.

Manuaalista asentamista varten minifikaaja vaatii kaksi konfiguraatiota: merkintäprosessorin löytämään lähteet rakentamisen aikana ja liittimen suorittamaan minifiointi.

<Tabs>
<TabItem value="maven" label="Maven">

Lisää seuraava `pom.xml`-tiedostoon:

```xml
<build>
  <plugins>
    <!-- Merkintäprosessorin konfiguraatio -->
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

    <!-- Minifikaajaliittimen konfiguraatio -->
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
        <!-- CSS-minifiointi -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript-minifiointi -->
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
    // Merkintäprosessorin, joka löytää resurssit rakentamisen aikana
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // Minifikaajaliitännät
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify`-tehtävä suoritetaan automaattisesti ennen `jar`- tai `war`-tehtäviä. Voit myös suorittaa sen manuaalisesti komennolla `./gradlew minify`.

</TabItem>
</Tabs>

## Liittimen käyttäminen {#using-the-plugin}

Kun liitin on konfiguroitu, se toimii automaattisesti. Käytä vain webforJ-resurssimerkintöjä koodissasi:

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

Kun rakennat projektisi, liitin automaattisesti:

1. Löytää merkintöihin viitattuja resursseja rakentamisen aikana
2. Minifioi löydetyt CSS- ja JavaScript-tiedostot
3. Ilmoittaa koon vähenemisestä ja käsittelyajasta

### URL-protokollan ratkaisu {#url-protocol-resolution}

Liitin ymmärtää webforJ [URL-protokollia](/docs/managing-resources/assets-protocols) ja ratkaisee ne tiedostojärjestelmän poluiksi:

| Protokolla | Ratkaisee | Esimerkki |
|------------|-----------|-----------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

URL-osoitteet ilman protokollaa eivät ole tuettuja minifikaajassa ja ne ohitetaan.

## Sisäänrakennetut minifikaajat {#built-in-minifiers}

webforJ sisältää kaksi tuotantovalmiita minifikaajaa CSS:lle ja JavaScriptille.

| Minifikaaja | Ominaisuudet | Ohittaa |
|-------------|--------------|---------|
| CSS | Poistaa valkoiset tilat, kommentit ja optimoi ominaisuuksien arvot | `.min.css` |
| JavaScript | Muuttujan nimeäminen, kuollut koodi poistaminen, syntaksin optimointi | `.min.js`, `.min.mjs` |

## Konfigurointivaihtoehdot {#configuration-options}

Liitin tarjoaa vaihtoehtoja minifikaation poistamiseksi, JavaScript-optimoinnin mukauttamiseksi ja lisätiedostojen käsittelemiseksi.

### Minifikaation poistaminen {#disabling-minification}

Saatat haluta poistaa minifikaation käytöstä kehitysvaiheessa tai virheiden korjausta varten.

<Tabs>
<TabItem value="maven" label="Maven">

**Komentoriviltä:**
```bash
mvn package -Dwebforj.minify.skip=true
```

**Liittimen konfiguraation kautta:**
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

### JavaScript-minifikaajan vaihtoehdot {#javascript-minifier-options}

JavaScript-minifikaaja tarjoaa useita konfiguraatioita optimointikäyttäytymisen hallitsemiseksi.

:::info Vain Maven
JavaScript-minifikaajan vaihtoehdot ovat tällä hetkellä saatavilla vain Mavenille. Gradle-tuki käyttää oletusasetuksia.
:::

| Vaihtoehto | Oletusarvo | Kuvaus |
|------------|------------|--------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - poistaa vain valkoiset tilat ja kommentit</li><li>`SIMPLE_OPTIMIZATIONS` - muuttujan nimeäminen ja kuollut koodi poistetaan</li><li>`ADVANCED_OPTIMIZATIONS` - aggressiivinen optimointi funktio-/ominaisuuden nimeämisellä</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Syöte-JavaScript-versio: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` - `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Lähtö-JavaScript-versio: sama kuin `languageIn`, lisättynä `NO_TRANSPILE` |
| `prettyPrint` | `false` | Aseta `true`, jotta muotoilu säilyy virheiden korjausta varten |

Määritä nämä vaihtoehdot `minifierConfigurations`-osiossa:

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

### Lisätiedostojen minifiointi {#minifying-additional-files}

Jos haluat minifioida tiedostoja, joita ei löydy merkinnöistä, luo konfigurointitiedosto, joka määrittelee glob-mallit:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Sisällyttämismallit
**/*.css
**/*.js

# Poissulkemismallit (alkaa !)
!**/*.min.css
!**/*.min.js
```

## Mukautetut minifikaajat {#custom-minifiers}

Liitin tukee mukautettuja minifikaajia Java'n palveluntarjoajaliittymän (SPI) kautta, jolloin voit lisätä tukea lisätiedostotyypeille tai vaihtoehtoisille minifiointikirjastoille.

### Mukautetun minifikaajan luominen {#creating-a-custom-minifier}

Toteuta `AssetMinifier`-rajapinta luodaksesi oman minifikaajan. Seuraava esimerkki näyttää JSON-minifikaajan, joka käyttää Gsonia valkoisten tilojen poistamiseen:

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
      logger.warning("Väärä muotoiltu JSON tiedostossa " + sourceFile + ", ohitetaan: " + e.getMessage());
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
    // Ohita konfiguraatiotiedostot ja jo minifioidut tiedostot
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
    // Konfiguraatioasetukset, jos tarpeen
  }
}
```

### Minifikaajasi rekisteröinti {#registering-your-minifier}

Luo palveluntarjoajakonfiguraatiotiedosto:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Mukautetun minifikaajan käyttäminen {#using-your-custom-minifier}

Pakkaa minifikaajasi erilliseen JAR-tiedostoon ja lisää se liittimen riippuvuudeksi:

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
    <!-- Vakiominifikaajat (valinnainen) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Yhteiset ongelmat {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ei rekisteröityjä minifikaajia</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Ei rekisteröityjä minifikaajia SPI:llä. Minifiointi ohitetaan.
      [WARN] Varmista, että ph-css ja/tai closure-compiler ovat luettelossa.
      ```

      Lisää minifikaajamoduuli riippuvuudet liittimen konfiguraatioon. CSS:lle lisää `webforj-minify-phcss-css`. JavaScriptille lisää `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Tiedostoja ei käsitelty</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Jos liitin ilmoittaa `Käsitelty 0 tiedostoa`, varmista, että:

      1. Merkintäprosessorin konfigurointi on määritetty `maven-compiler-plugin`-tiedostoon, jossa `webforj-minify-foundation` on `annotationProcessorPaths`-osiossa
      2. webforJ-resurssimerkinnät ovat olemassa lähdekoodissasi
      3. `target/classes/META-INF/webforj-resources.json` on olemassa kääntämisen jälkeen
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
      [WARN] Tiedostoa ei löydy: /path/to/static/css/app.css (viitattiin 'ws://css/app.css')
      ```

      Varmista, että tiedosto on olemassa oikeassa polussa `src/main/resources/static` ja että URL-protokolla vastaa hakemistorakennetta.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Minifikausvirheet</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Virhe minifioinnissa tiedostossa /path/to/app.css: jäsentelyvirhe rivillä 42
      ```

      Liitin varoittaa, mutta jatkaa ilman, että rakennus epäonnistuu. Alkuperäinen sisältö säilyy, kun minifiointi epäonnistuu. Korjaa syntaksivirheet validoimalla CSS tai JavaScript linterillä.
    </div>
  </AccordionDetails>
</Accordion>
<br />
