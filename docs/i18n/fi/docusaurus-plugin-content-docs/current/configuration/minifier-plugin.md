---
sidebar_position: 40
title: Minifier Plugin
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: a570df6cd0876073e88e7b38b6357b10
---
# Minifier-plugin <DocChip chip='since' label='25.11' />

webforJ Minifier Plugin automaattisesti [minifi.io](https://en.wikipedia.org/wiki/Minification_(programming)) ja optimoi CSS- ja JavaScript-resurssit rakennusprosessin aikana. Laajennus löytää varat, joita viitataan webforJ [varannotaatiot](/docs/managing-resources/importing-assets) kautta, ja minifioi ne rakennustulokseen, vähentäen tiedostokokoja ja parantaen latausaikoja ilman, että alkuperäisiä lähdetiedostoja muutetaan.

:::info Minifiointi on integroitu pakkaajaan
[frontend-pakkaaja](/docs/managing-resources/bundler/overview) minifioi CSS:ää ja JavaScript-tiedostoja osana tuotannon rakennusta, joten projekti, joka käyttää pakkaajaa, ei tarvitse tätä laajennusta. Laajennus on edelleen käytettävissä ja tuettu projekteille, jotka lataa varoja varannotaatioiden kautta ilman pakkaajaa. Olemassa olevat asetukset toimivat kuten ennenkin, eikä muutoksia tarvita.
:::

## Asetus {#setup}

Jos olet luonut projektisi käyttäen [startforJ](https://docs.webforj.com/startforj) tai webforJ [arkkitehtuuria](/docs/building-ui/archetypes/overview), minifier-laajennus on jo konfiguroitu ja se toimii automaattisesti, kun rakennat `prod`-profiililla käyttäen `mvn package -Pprod`.

Manuaalista asetusta varten minifier vaatii kaksi konfiguraatiota: annotaatioprosessorin, joka löytää varat käännön aikana, ja laajennuksen, joka suorittaa minifioinnin.

<Tabs>
<TabItem value="maven" label="Maven">

Lisää seuraava `pom.xml`-tiedostoosi:

```xml
<build>
  <plugins>
    <!-- Annotaatio Prosessori Konfiguraatio -->
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

Lisää seuraava `build.gradle`-tiedostoosi:

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
  // Annotaatio prosessori varojen löytämiseen käännön aikana
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier toteutukset
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify`-tehtävä suoritetaan automaattisesti ennen `jar` tai `war` tehtäviä. Voit myös suorittaa sen manuaalisesti komennolla `./gradlew minify`.

</TabItem>
</Tabs>

## Laajennuksen käyttö {#using-the-plugin}

Kun laajennus on konfiguroitu, se toimii automaattisesti. Käytä vain webforJ varannotaatioita koodissasi:

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

1. Löytää varat, joihin on viitattu annotaatioissa käännön aikana
2. Minifioi löydetyt CSS- ja JavaScript-tiedostot
3. Raportoi koon vähenemisen ja käsittelyajan

### URL-protokollan ratkaisu {#url-protocol-resolution}

Laajennus ymmärtää webforJ [URL-protokollat](/docs/managing-resources/assets-protocols) ja ratkaisee ne tiedostojärjestelmäpolkuiksi:

| Protokolla | Ratkaisee | Esimerkki |
|------------|-----------|-----------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Protokollattomat URL-osoitteet eivät ole tuettuja minifierissä ja ne ohitetaan.

## Sisäänrakennetut minifioijat {#built-in-minifiers}

webforJ sisältää kaksi tuotantovalmiita minifioijia CSS:lle ja JavaScriptille.

| Minifioija | Ominaisuudet | Ohittaa |
|------------|--------------|---------|
| CSS | Poistaa tyhjät välit, kommentit ja optimoi ominaisuusarvot | `.min.css` |
| JavaScript | Muuttujien uudelleennimeäminen, kuollut koodi eliminointi, syntaksin optimointi | `.min.js`, `.min.mjs` |

## Konfigurointivaihtoehdot {#configuration-options}

Laajennus tarjoaa vaihtoehtoja minifioinnin poistamiseen, JavaScriptin optimoinnin mukauttamiseen ja lisätiedostojen käsittelyyn.

### Minifioinnin poistaminen {#disabling-minification}

Saatat haluta kytkeä pois minifioinnin kehityksen aikana tai virheiden etsintätarkoituksiin.

<Tabs>
<TabItem value="maven" label="Maven">

**Komennon kautta:**
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

### JavaScript-minifioijan vaihtoehdot {#javascript-minifier-options}

JavaScript-minifioijalla on useita konfigurointivaihtoehtoja optimoinnin käyttäytymisen hallitsemiseksi.

:::note Gradle-tuki
Versiosta v25.12 alkaen Gradle-laajennus tukee JavaScript-minifioijan vaihtoehtojen siirtämistä.
:::

| Vaihtoehto | Oletusarvo | Kuvaus |
|------------|------------|--------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - poistaa vain tyhjät välit ja kommentit</li><li>`SIMPLE_OPTIMIZATIONS` - muuttujien uudelleennimeäminen ja kuolleiden koodien poistaminen</li><li>`ADVANCED_OPTIMIZATIONS` - aggressiivinen optimointi funktio/ominaisuuden uudelleennimeämisellä</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | Syötteen JavaScript-version: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` - `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | Tulo JavaScript-version: sama kuin `languageIn`, plus `NO_TRANSPILE` |
| `prettyPrint` | `false` | Aseta `true` säilyttääksesi muotoilun virheenkorjauksen vuoksi |

Määritä nämä vaihtoehdot konfiguraatio-osiossa:

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
    skip = false  // Aseta true ohittaaksesi minifioinnin
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

Minifioidaksesi tiedostoja, joita ei löydy annotaatioiden kautta, luo konfigurointitiedosto, joka määrittää glob-pohjat:

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Sisältökuviot
**/*.css
**/*.js

# Poissulkemiskuvioita (alkaa !-merkillä)
!**/*.min.css
!**/*.min.js
```

## Mukautetut minifioijat {#custom-minifiers}

Laajennus tukee mukautettuja minifioijia Java:n palveluntarjoajaliittymän (SPI) kautta, mikä mahdollistaa tukea lisätiedostotyypeiltä tai vaihtoehtoisilta minifiointikirjastoilta.

### Mukautetun minifioijan luominen {#creating-a-custom-minifier}

Toteuta `AssetMinifier` -rajapinta luodaksesi oman minifioijan. Seuraava esimerkki näyttää JSON-minifioijan, joka käyttää Gsonia tyhjien välisten poistamiseen:

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
      logger.warning("Viallinen JSON tiedostossa " + sourceFile + ", ohitetaan: " + e.getMessage());
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

### Minifioijasi rekisteröinti {#registering-your-minifier}

Luo palveluntarjoajan konfigurointitiedosto:

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### Mukautetun minifioijan käyttäminen {#using-your-custom-minifier}

Paketoit minifioijasi erillisenä JAR-tiedostona ja lisää se laajennuksen riippuvuudeksi:

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
    <!-- Vakio minifioijat (valinnainen) -->
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
    <p>Ei minifioijia rekisteröity</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Ei minifioijia rekisteröity SPI:n kautta. Ohitetaan minifiointi.
      [WARN] Varmista, että ph-css ja/tai closure-compiler ovat luokkareitissä.
      ```

      Lisää minifioijamoduuliriippuvuudet laajennuskonfiguraatioon. CSS:lle, lisää `webforj-minify-phcss-css`. JavaScriptille, lisää `webforj-minify-closure-js`.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Ei tiedostoja käsitelty</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Jos laajennus ilmoittaa `Käsitelty 0 tiedostoa`, varmista, että:

      1. Annotaatio prosessori on konfiguroitu `maven-compiler-plugin` -tiedostoon `webforj-minify-foundation` olevalle `annotationProcessorPaths`.
      2. webforJ varannotaatiot ovat olemassa lähdekoodissasi.
      3. `target/classes/META-INF/webforj-resources.json` on olemassa käännön jälkeen.
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
      [WARN] Tiedostoa ei löydy: /path/to/static/css/app.css (viitattu 'ws://css/app.css')
      ```

      Varmista, että tiedosto on olemassa oikeassa polussa `src/main/resources/static` ja että URL-protokolla vastaa hakemistorakennetta.
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
      [WARN] Virhe minifioitaessa tiedostoa /path/to/app.css: jäsentämisvirhe rivillä 42
      ```

      Laajennus varoittaa, mutta jatkaa ilman, että rakennus epäonnistuu. Alkuperäinen sisältö säilyy, kun minifiointi epäonnistuu. Korjataksesi syntaksivirheitä, varmista CSS tai JavaScript lintterillä.
    </div>
  </AccordionDetails>
</Accordion>
<br />
