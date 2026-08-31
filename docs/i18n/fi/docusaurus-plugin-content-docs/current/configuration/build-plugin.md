---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 09a13bb6da32b3c4c0e77d4e44c1acb4
---
# webforJ build plugin <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ build plugin suorittaa webforJ:n kokoamisajan työn osana Maven- tai Gradle-rakennustasi. Lisäät sen kerran, ja se sitoo tavoitteensa vaiheisiin, joita jo suoritat, ilman erillistä frontend-projektia, jota pitää synkronoida. Se ohjaa [frontend bundleria](/docs/managing-resources/bundler/overview), kooten frontendin, suorittaen frontend-testit, tarjoamalla kehityksen valvontaa ja liittämällä [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) sovellukseen, jonka se käynnistää.

## Adding the plugin {#adding-the-plugin}

webforJ-projekti, joka on luotu [archetypestä](/docs/introduction/getting-started), sisältää jo liitännäisen. Lisätäksesi sen olemassa olevaan projektiin:

<Tabs>
<TabItem value="maven" label="Maven">

Liittimen määrittäminen `<extensions>true</extensions>` -asetuksella sitoo sen tavoitteet rakentamiseen ilman suorituskosketuksia, jotka on kirjoitettava:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Lisää liitännäinen `buildscript`-luettelo- riippuvuutena ja käytä sitä:

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

## Goals and tasks {#goals-and-tasks}

Kolme tavoitetta sitoutuu vaiheisiin, joita jo suoritat, joten normaali `mvn package` tai `./gradlew build` tuottaa sovelluksen, jonka frontend on koottu sisään, ja testivaihe suorittaa frontend-testit Java-testien ohella. Valvonta on tämä, jonka aloitat käsin kehityksen aikana:

| Maven-päämäärä | Gradle-tehtävä | Suorittaa | Mitä se tekee |
|----------------|-----------------|-----------|---------------|
| `bundle` | `webforjBundle` | `prepare-package`, ennen jokaista jar- ja war-tiedostoa | Kootaan frontend pakatun sovelluksen varten |
| `test` | `webforjTest` | testivaiheen kanssa | Suorittaa frontend-testit |
| `clean` | `webforjCleanFrontend` | puhdistusvaiheen kanssa | Poistaa generoitu frontend |
| `watch` | `webforjWatch` | käsin, sovelluksen rinnalla | Uudelleenkootaan muutosten mukaan kehityksen aikana |
| `push-keys` | `webforjPushKeys` | käsin, kerran jokaisessa käyttöönotossa | Generoi avainten parin [push-ilmoituksia](/docs/advanced/push-notifications) varten ja tulostaa konfiguraatiorivit |

Aloita valvonta tavoitteena ennen sitä, joka suorittaa sovelluksen, esimerkiksi `mvn compile webforj:watch spring-boot:run`. Archetype-projekti asettaa tämän oletustavoitteeksi, joten `mvn` yksinään käynnistää kaiken. Sen uudelleenlatauskäyttäytyminen kattaa [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Ohita frontend-testit yhdessä Java-testien kanssa, käyttäen `-DskipTests` tai `-Dmaven.test.skip` Mavenin kanssa ja `-PskipTests` Gradlen kanssa.

## Options {#options}

Aseta vaihtoehtoja Mavenin `<configuration>` -elementteinä tai Gradlen `webforj { }` laajennusarvoina. Jokainen Maven-vaihtoehto, paitsi `plugins` ja `hotswap`, hyväksyy myös `-D` ominaisuuden komentorivillä. Kaksi rakennustyökalua heijastavat toisiaan:

| Maven-elementti | Maven-ominaisuus | Gradle | Oletus | Tarkoitus |
|------------------|------------------|--------|--------|----------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | hallittu | Varmista Bun-versio toistettavissa olevia kokoamisia varten |
| `bunPath` | `webforj.bundler.path` | `bunPath` | lataa | Käytä olemassa olevaa Bun-binaaria sen sijaan, että ladataan |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Missä hallitut Bun-binäärit välimuistissa |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Missä frontendin sisääntuloresurssit sijaitsevat |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Missä liitännäinen kirjoittaa generoituja rakennustiedostoja |
| `plugins` | — | `plugins` | — | Kytke [laajennus](/docs/managing-resources/bundler/extensions/overview) päälle tai pois id:n mukaan, kuten `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakettien etuliitteet, joita ohitetaan annotaatioskannauksessa |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Lataa koko frontend sovelluksen käynnistyessä sen sijaan, että per näkymä, katso [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Lisäargumentit, jotka siirretään frontend-testisuorittajalle |
| `hotswap` | — | `hotswap` | — | Liitä luokan päivitystyökalu sovellukseen, jonka rakennus käynnistää, katso [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Esimerkiksi, jos haluat varmistaa Bun-version ja aktivoida Tailwindin:

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
