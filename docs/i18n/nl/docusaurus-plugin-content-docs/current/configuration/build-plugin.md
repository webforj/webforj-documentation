---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

De webforJ build plugin voert het bouwproces van webforJ uit als onderdeel van je Maven- of Gradle-bouw. Je voegt het eenmaal toe, en het koppelt zijn doelen aan de fasen die je al uitvoert, zonder dat er een apart frontendproject moet worden gesynchroniseerd. Het stuurt de [frontend bundler](/docs/managing-resources/bundler/overview), compileert de frontend, voert de frontend-tests uit, serveert de ontwikkelingseisen, en voegt een [hotswap-tool](/docs/configuration/deploy-reload/hotswap) toe aan de app die het start.

## De plugin toevoegen {#adding-the-plugin}

Een webforJ-project dat is gemaakt van een [archetype](/docs/introduction/getting-started) heeft al de plugin. Om het aan een bestaand project toe te voegen:

<Tabs>
<TabItem value="maven" label="Maven">

De plugin declareren met `<extensions>true</extensions>` koppelt zijn doelen aan de bouw zonder dat er uitvoeringsblokken geschreven hoeven te worden:

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

Drie doelen zijn gekoppeld aan de fasen die je al uitvoert, zodat een normale `mvn package` of `./gradlew build` een app produceert met de frontend ingebouwd, en de testfase de frontend-tests naast de Java-tests uitvoert. De watch is degene die je handmatig start tijdens de ontwikkeling:

| Maven doel | Gradle taak | Draait | Wat het doet |
|------------|-------------|--------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, vóór elke jar en war | Compileert de frontend voor de verpakte app |
| `test` | `webforjTest` | met de testfase | Voert de frontend-tests uit |
| `clean` | `webforjCleanFrontend` | met de schoonmaakfase | Verwijdert de gegenereerde frontend |
| `watch` | `webforjWatch` | handmatig, naast de app | Bouwt opnieuw bij wijzigingen tijdens de ontwikkeling |
| `push-keys` | `webforjPushKeys` | handmatig, eenmaal per implementatie | Genereert het sleutel paar voor [pushmeldingen](/docs/advanced/push-notifications) en print de configuratielijnen |
| `bbj-setup` | Alleen Maven | handmatig, op de BBj-machine | Installeert [BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin) in de lokale BBj en activeert het implementatie-eindpunt |
| `bbj-package` | Alleen Maven | met de pakketfase, met een `<bbj>`-blok | Schrijft het [BBj-implementatiearchief](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy` | Alleen Maven | met de installatiefase, met een `<bbj>`-blok | [Implementeert de app](/docs/configuration/bbjship/deploying#deploy) naar BBjServices |
| `bbj-undeploy` | Alleen Maven | handmatig | [Verwijdert de app](/docs/configuration/bbjship/deploying#undeploy) uit BBjServices |

Start de watch als het doel vóór degene die de app uitvoert, `mvn compile webforj:watch spring-boot:run` bijvoorbeeld. Een archetype-project stelt dit in als het standaarddoel, zodat `mvn` alleen alles start. Het herlaadgedrag wordt behandeld in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Sla de frontend-tests samen met de Java-tests over, `-DskipTests` of `-Dmaven.test.skip` met Maven en `-PskipTests` met Gradle.

## Opties {#options}

Stel opties in als Maven `<configuration>` elementen of als Gradle `webforj { }` extensiewaarden. Elke Maven-optie behalve `plugins` en `hotswap` accepteert ook een `-D`-eigenschap op de opdrachtregel. De twee buildtools weerspiegelen elkaar:

| Maven-element | Maven-eigenschap | Gradle | Standaard | Doel |
|---------------|------------------|--------|-----------|------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | beheerd | Stel de Bun-versie in voor reproduceerbare builds |
| `bunPath` | `webforj.bundler.path` | `bunPath` | downloaden | Gebruik een bestaande Bun-binaire in plaats van te downloaden |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Waar beheerde Bun-binaries worden gecached |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Waar de frontend entry sources zich bevinden |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Waar de plugin zijn gegenereerde bouwbestanden schrijft |
| `plugins` | — | `plugins` | — | Zet een [extensie](/docs/managing-resources/bundler/extensions/overview) aan of uit op id, zoals `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakketvoorkeurs om over te slaan tijdens de annotatiescan |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Laad de hele frontend bij app-start in plaats van per weergave, zie [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Extra argumenten die aan de frontend-testloper worden doorgegeven |
| `hotswap` | — | `hotswap` | — | Hecht een class update tool aan de app die de build start, zie [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Bijvoorbeeld, om de Bun-versie vast te zetten en Tailwind in te schakelen:

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
