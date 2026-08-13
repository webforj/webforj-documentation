---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 7cb4ddbb9aea86ff6f501296b42c5bbf
---
# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

De webforJ build plugin voert het werk van webforJ tijdens de bouwtijd uit als onderdeel van uw Maven- of Gradle-build. U voegt het eenmaal toe en het bindt zijn doelen aan de fasen die u al uitvoert, zonder een apart frontendproject dat in sync moet blijven. Het stuurt de [frontend bundler](/docs/managing-resources/bundler/overview), compileert de frontend, voert de frontend-tests uit, bedient de ontwikkelingstoezicht en hecht een [hotswap tool](/docs/configuration/deploy-reload/hotswap) aan de app die het start.

## Het toevoegen van de plugin {#adding-the-plugin}

Een webforJ-project dat is gemaakt vanuit een [archetype](/docs/introduction/getting-started) heeft de plugin al. Om het aan een bestaand project toe te voegen:

<Tabs>
<TabItem value="maven" label="Maven">

De plugin verklaren met `<extensions>true</extensions>` bindt zijn doelen aan de build zonder uitvoeringsblokken te schrijven:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Voeg de plugin toe via een `buildscript` classpath-afhankelijkheid en pas deze toe:

```groovy title="build.gradle"
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-gradle-plugin:${webforjVersion}"
  }
}

apply plugin: 'com.webforj'
```

</TabItem>
</Tabs>

## Doelen en taken {#goals-and-tasks}

Drie doelen zijn gebonden aan fasen die u al uitvoert, zodat een normale `mvn package` of `./gradlew build` een app produceert met de frontend ingebed, en de testfase voert de frontend-tests uit naast de Java-tests. De kijkfunctie is degene die u handmatig start tijdens de ontwikkeling:

| Maven doel | Gradle taak | Voert uit | Wat het doet |
|------------|-------------|-----------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, voor elke jar en war | Compileert de frontend voor de verpakte app |
| `test` | `webforjTest` | met de testfase | Voert de frontend-tests uit |
| `clean` | `webforjCleanFrontend` | met de schoonmaakfase | Verwijdert de gegenereerde frontend |
| `watch` | `webforjWatch` | handmatig, parallel aan de app | Bouwt opnieuw bij wijziging tijdens de ontwikkeling |

Start de watch als het doel vóór degene die de app uitvoert, bijvoorbeeld `mvn compile webforj:watch spring-boot:run`. Een archetype-project stelt dit in als het standaarddoel, zodat `mvn` alleen alles start. Het herlaadgedrag wordt behandeld in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Sla de frontend-tests over samen met de Java-tests, `-DskipTests` of `-Dmaven.test.skip` met Maven en `-PskipTests` met Gradle.

## Opties {#options}

Stel opties in als Maven `<configuration>` elementen, of als Gradle `webforj { }` extensiewaarden. Elke Maven-optie, behalve `plugins` en `hotswap`, accepteert ook een `-D` eigenschap op de commandoregel. De twee buildtools spiegelen elkaar:

| Maven element | Maven eigenschap | Gradle | Standaard | Doel |
|---------------|------------------|--------|-----------|------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | beheerd | Pin de Bun-versie voor reproduceerbare builds |
| `bunPath` | `webforj.bundler.path` | `bunPath` | downloaden | Gebruik een bestaande Bun-binaire in plaats van te downloaden |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Waar beheerde Bun-binaries worden gecached |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Waar de frontend invoerbronnen zich bevinden |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Waar de plugin zijn gegenereerde bouwbestanden schrijft |
| `plugins` | — | `plugins` | — | Zet een [extensie](/docs/managing-resources/bundler/extensions/overview) aan of uit op id, zoals `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakketvoorvoegsels die moeten worden overgeslagen tijdens de annotatiescan |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Laad de hele frontend bij de start van de app in plaats van per weergave, zie [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Extra argumenten die naar de frontend test runner worden doorgegeven |
| `hotswap` | — | `hotswap` | — | Hecht een klasupdate-tool aan de app die de build start, zie [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Bijvoorbeeld, om de Bun-versie vast te pinnen en Tailwind in te schakelen:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <bunVersion>1.3.0</bunVersion>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  bunVersion = '1.3.0'
  plugins.put('webforj-tailwind', 'true')
}
```

</TabItem>
</Tabs>
