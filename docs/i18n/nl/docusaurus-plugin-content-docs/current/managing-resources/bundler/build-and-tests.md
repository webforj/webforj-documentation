---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

De bundler wordt uitgevoerd als doelen van de [webforJ build plugin](/docs/configuration/build-plugin). Voeg de plugin eenmaal toe, zoals daar weergegeven, en een normale `mvn package` of `gradle build` produceert een app met de frontend erin gecompileerd, terwijl `mvn test` de frontend tests samen met de Java tests uitvoert. Deze pagina behandelt wat de bundler doet tijdens die stappen.

## De ontwikkeling watch {#the-development-watch}

De `watch` stap is degene die je met de hand voert tijdens de ontwikkeling, naast de app. Het compileert de frontend eenmaal, bouwt vervolgens opnieuw bij elke wijziging en verfrist de browser.

```bash
mvn compile webforj:watch spring-boot:run
```

Een webforJ-project stelt dit in als zijn standaarddoel, dus `mvn` zonder argumenten start de watch en de app samen. Het herlaadgedrag dat het aanstuurt, een stylesheetwijziging die op zijn plaats wordt toegepast tegen een scriptwijziging die de weergave opnieuw laadt, wordt behandeld in [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Frontend tests {#frontend-tests}

De `test` stap voert de Bun test runner uit over `src/main/frontend` tijdens de testfase, zodat `mvn test` de frontend tests uitvoert samen met de Java tests. Wanneer de bronroot geen testbestanden bevat, wordt de stap overgeslagen en faalt een mislukte frontend test de build, zodat een gebroken frontend een release op dezelfde manier stopt als een gebroken Java test. Voor het schrijven van die tests, zie [Frontend testing](/docs/testing/frontend-testing).

## Een compiler afstemmen {#tuning-a-compiler}

Een compiler leest zijn instellingen vanuit `src/main/frontend/bun.config.ts`, gekoppeld aan de extensie-id, zodat een instelling de juiste compiler bereikt zonder vlag op de build. Zie [SCSS en Sass](/docs/managing-resources/bundler/extensions/scss) voor een uitgewerkt voorbeeld dat de SCSS-compiler een laadpad geeft.

## Het productie pakket {#the-production-bundle}

De `bundle` stap wordt uitgevoerd tijdens `prepare-package`, zodat het verpakken van een app de frontend compileert voor productie. Een productie-build verschilt van de ontwikkeling één op twee manieren die belangrijk zijn zodra een app is gedeployed.

- **Gehashede bestandsnamen.** Elk uitvoerbestand heeft een hash van zijn inhoud in zijn naam. Een browser kan dan een bestand lange tijd cachen, omdat een wijziging in de inhoud een nieuwe naam produceert, en de nieuwe naam dwingt een nieuwe fetch af. Caching blijft veilig zonder een handmatige versie bump.
- **Geminaliseerde uitvoer.** Spaties en dode code worden verwijderd, zodat de bestanden die een browser downloadt zo klein mogelijk zijn, zoals de compilatie kan maken.

Een ontwikkeling build slaat beide over. Het houdt stabiele namen en leesbare uitvoer, zodat de watch één bestand ter plaatse kan verwisselen en jij kunt lezen wat er laadt terwijl je debugt.

Omdat minificatie onderdeel van deze stap is, heeft een project dat de bundler gebruikt niets anders nodig om geminaliseerde CSS en JavaScript te verzenden. Voor een app die middelen laadt via de [asset annotaties](/docs/managing-resources/importing-assets) zonder de bundler, de [minifier plugin](/docs/configuration/minifier-plugin) dekt in plaats daarvan die productie minificatie.

## Eager bundle {#eager-bundle}

Standaard laadt elke weergave alleen de frontend die het gebruikt, wanneer een component van die klasse wordt gemaakt, zodat een weergave niets betaalt dat het niet rendert.

Eager-modus laadt de hele frontend bij het opstarten van de app als één enkele bundel, in plaats van per weergave. Zet het aan met de `eager` optie:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

Eager is standaard uit, en het per weergave model past bij de meeste apps. Gebruik het wanneer je de hele frontend vanaf het begin in positie wilt hebben in plaats van geladen wanneer weergaven worden gerenderd.
