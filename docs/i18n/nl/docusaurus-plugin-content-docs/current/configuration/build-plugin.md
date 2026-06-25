---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 0c02e741918864a34c35227387259b40
---
# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

De webforJ build plugin voert het bouwtijdwerk van webforJ uit als onderdeel van je Maven- of Gradle-build. Je voegt het eenmaal toe en het koppelt zijn doelen aan de fasen die je al uitvoert, zonder een apart frontendproject dat je in sync moet houden. Het stuurt de [frontend bundler](/docs/managing-resources/bundler/overview), compileert de frontend, voert de frontend-tests uit en serveert de ontwikkelingswatch.

## Toevoegen van de plugin {#adding-the-plugin}

Een webforJ-project dat is gemaakt van een [archetype](/docs/introduction/getting-started) heeft de plugin al. Om het toe te voegen aan een bestaand project:

<Tabs>
<TabItem value="maven" label="Maven">

De plugin deklareren met `<extensions>true</extensions>` koppelt zijn doelen aan de build zonder uitvoeringsblokken te hoeven schrijven:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Voeg de plugin toe via een `buildscript` classpath afhankelijkheid en pas deze toe:

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

## Doelen {#goals}

De plugin koppelt vier doelen, elk aan een fase die je al uitvoert, zodat een normale `mvn package` of `gradle build` een app produceert met de frontend die is gecompileerd, en `mvn test` voert de frontend-tests uit naast de Java-tests.

| Maven doel | Gradle taak | Fase | Wat het doet |
|------------|-------------|-------|--------------|
| `bundle` | `webforjBundle` | `prepare-package` | Compiles de frontend voor productie |
| `test` | `webforjTest` | `test` | Voert de frontend-tests uit |
| `clean` | `webforjCleanFrontend` | `clean` | Verwijdert de gegenereerde frontend |
| `watch` | `webforjWatch` | handmatig uitgevoerd | Herbouwen bij wijziging tijdens ontwikkeling |

Het doel `watch` is degene die je handmatig uitvoert tijdens de ontwikkeling, naast de app. Het herlaadgebruik is behandeld in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Opties {#options}

Stel opties in als Maven `<configuration>` (of `-D` eigenschappen op de opdrachtregel), en als Gradle `webforj { }` extensiewaarden. De twee buildtools spiegelen elkaar.

| Optie | Maven eigenschap | Gradle | Standaard | Doel |
|--------|----------------|--------|---------|---------|
| Bun versie | `webforj.bundler.version` | `bunVersion` | beheerd | Pin de Bun-versie voor reproduceerbare builds |
| Bun-binaire | `webforj.bundler.path` | `bunPath` | downloaden | Gebruik een bestaande Bun-binaire in plaats van te downloaden |
| Cache-directory | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Waar beheerde Bun-binaries worden gecached |
| Source root | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Waar de frontend ingrondbronnen zich bevinden |
| Werkdirectory | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Waar de plugin zijn gegenereerde buildbestanden schrijft |
| Extensies | `plugins` | `plugins` | — | Zet een [extensie](/docs/managing-resources/bundler/extensions/overview) aan of uit met id, zoals `webforj-tailwind` |
| Uitsluiten pakketten | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakketprefixen om over te slaan tijdens de annotatiescan |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Laad de hele frontend bij de app-start in plaats van per weergave, zie [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Testargumenten | `webforj.bundler.testArgs` | `testArgs` | — | Extra argumenten die aan de frontend-testrunner worden doorgegeven |
| Sla tests over | `skipTests`, `maven.test.skip` | — | `false` | Sla de frontend-tests over |

Bijvoorbeeld, om de Bun-versie vast te pinnen en Tailwind in te schakelen:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <version>1.3.0</version>
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
