---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 09a13bb6da32b3c4c0e77d4e44c1acb4
---
# webforJ build-plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

De webforJ build-plugin voert de bouwtijdtaken van webforJ uit als onderdeel van je Maven of Gradle build. Je voegt het één keer toe, en het bindt zijn doelen aan de fasen die je al uitvoert, zonder dat er een apart frontend-project nodig is om synchroon te houden. Het stuurt de [frontend bundler](/docs/managing-resources/bundler/overview), compileert de frontend, voert de frontend-tests uit, biedt de ontwikkelingswatch aan en voegt een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) toe aan de app die het start.

## Toevoegen van de plugin {#adding-the-plugin}

Een webforJ-project dat is gemaakt vanuit een [archetype](/docs/introduction/getting-started) heeft de plugin al. Om het aan een bestaand project toe te voegen:

<Tabs>
<TabItem value="maven" label="Maven">

De plugin declareren met `<extensions>true</extensions>` bindt zijn doelen aan de build zonder dat er uitvoeringsblokken hoeven te worden geschreven:

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

Drie doelen zijn gebonden aan fasen die je al uitvoert, zodat een normale `mvn package` of `./gradlew build` een app produceert met de frontend erin gecompileerd, en de testfase voert de frontend-tests uit naast de Java-tests. De watch is degene die je handmatig tijdens de ontwikkeling start:

| Maven-doel | Gradle-taak | Draait | Wat het doet |
|------------|-------------|--------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, vóór elke jar en war | Compileert de frontend voor de verpakte app |
| `test` | `webforjTest` | met de testfase | Voert de frontend-tests uit |
| `clean` | `webforjCleanFrontend` | met de schoonmaakfase | Verwijdert de gegenereerde frontend |
| `watch` | `webforjWatch` | handmatig, naast de app | Herbouwen bij wijzigingen tijdens de ontwikkeling |
| `push-keys` | `webforjPushKeys` | handmatig, eenmaal per implementatie | Genereert het sleutelpair voor [pushmeldingen](/docs/advanced/push-notifications) en drukt de configuratielijnen af |

Start de watch als het doel vóór degene die de app draait, bijvoorbeeld `mvn compile webforj:watch spring-boot:run`. Een archetype-project stelt dit in als het standaarddoel, zodat `mvn` alleen alles start. Het herlaadgewen gedrag wordt behandeld in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Sla de frontend-tests over samen met de Java-tests, `-DskipTests` of `-Dmaven.test.skip` met Maven en `-PskipTests` met Gradle.

## Opties {#options}

Stel opties in als Maven `<configuration>`-elementen, of als Gradle `webforj { }` extensiewaarden. Elke Maven-optie behalve `plugins` en `hotswap` accepteert ook een `-D`-eigenschap op de opdrachtregel. De twee buildtools spiegelen elkaar:

| Maven-element | Maven-eigenschap | Gradle | Standaard | Doel |
|---------------|------------------|--------|-----------|------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | beheerd | Bevestig de Bun-versie voor reproduceerbare builds |
| `bunPath` | `webforj.bundler.path` | `bunPath` | downloaden | Gebruik een bestaande Bun-binaire in plaats van te downloaden |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Waar beheerde Bun-binaries worden opgeslagen |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Waar de frontend-invoerlijnen zich bevinden |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Waar de plugin zijn gegenereerde buildbestanden schrijft |
| `plugins` | — | `plugins` | — | Zet een [extensie](/docs/managing-resources/bundler/extensions/overview) aan of uit op id, zoals `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakketvoorkeuren om over te slaan tijdens de annotatiescan |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Laad de hele frontend bij het starten van de app in plaats van per weergave, zie [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Extra argumenten die aan de frontend-testloper worden doorgegeven |
| `hotswap` | — | `hotswap` | — | Bevestig een klasse-updatetool aan de app die de build start, zie [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Bijvoorbeeld, om de Bun-versie vast te leggen en Tailwind in te schakelen:

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
