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

webforJ build -liitin suorittaa webforJ:n rakennusaikatyöt osana Maven- tai Gradle-rakennusta. Lisäät sen kerran, ja se sitoo tavoitteensa jo suoritettuihin vaiheisiin ilman erillistä frontend-projektia, jota pitää synkronoida. Se ohjaa [frontend bundleria](/docs/managing-resources/bundler/overview), kooten frontendin, suorittaen frontend-testit, palvellen kehitykselle tarkoitettua valvontaa ja liittäen [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) sen sovellukseen, jonka se käynnistää.

## Adding the plugin {#adding-the-plugin}

webforJ-projekti, joka on luotu [archetypestä](/docs/introduction/getting-started), sisältää jo liitännäisen. Lisätäksesi sen olemassa olevaan projektiin:

<Tabs>
<TabItem value="maven" label="Maven">

Ilmoittamalla liitännäisen `<extensions>true</extensions>` sitoo sen tavoitteet rakennukseen ilman suorituskukkoja, joita tarvitsisi kirjoittaa:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Lisää liitännäinen `buildscript`-luokan riippuvuutena ja sovella sitä:

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

Kolme tavoitetta sitoo vaiheisiin, joita jo suoritat, joten normaali `mvn package` tai `./gradlew build` tuottaa sovelluksen, jonka frontend on koottu sisään, ja testivaihe suorittaa frontend-testit Java-testien rinnalla. Valvonta on se, jonka käynnistät käsin kehityksen aikana:

| Maven goal | Gradle task | Runs | What it does |
|------------|-------------|------|--------------|
| `bundle` | `webforjBundle` | `prepare-package`, ennen jokaista jar- ja war-tiedostoa | Kootaan frontend pakattua sovellusta varten |
| `test` | `webforjTest` | testivaiheen aikana | Suorittaa frontend-testit |
| `clean` | `webforjCleanFrontend` | puhdistusvaiheen aikana | Poistaa tuotetun frontendin |
| `watch` | `webforjWatch` | käsin, sovelluksen rinnalla | Uudelleenrakentaa muutoksen yhteydessä kehityksen aikana |

Aloita valvonta ennen tavoitetta, joka käynnistää sovelluksen, esimerkiksi `mvn compile webforj:watch spring-boot:run`. Archetype-projekti asettaa tämän oletustavoitteeksi, joten `mvn` yksin käynnistää kaiken. Sen uudelleenlatauskäyttäytyminen käsitellään [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

Ohita frontend-testit yhdessä Java-testien kanssa, `-DskipTests` tai `-Dmaven.test.skip` Mavenin kanssa ja `-PskipTests` Gradlen kanssa.

## Options {#options}

Aseta vaihtoehdot Mavenin `<configuration>`-elementteinä tai Gradlen `webforj { }`-laajennusarvoina. Jokainen Maven-vaihtoehto, paitsi `plugins` ja `hotswap`, hyväksyy myös `-D`-ominaisuuden komentorivillä. Kaksi rakennustyökalua peilaavat toisiaan:

| Maven element | Maven property | Gradle | Default | Purpose |
|---------------|----------------|--------|---------|---------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | managed | Kiinnitä Bun-version yhteensopivia rakennuksia varten |
| `bunPath` | `webforj.bundler.path` | `bunPath` | download | Käytä olemassa olevaa Bun-binääriä sen sijaan, että ladataan |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Missä hallinnoidut Bun-binäärit välimuistissa |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Missä frontendin pääsourcedat sijaitsevat |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Missä liitännäinen kirjoittaa tuottamansa rakennustiedostot |
| `plugins` | — | `plugins` | — | Kytke [laajennus](/docs/managing-resources/bundler/extensions/overview) päälle tai pois päältä id:n mukaan, kuten `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakettien etuliitteet, joita ohitetaan annotaatiopolkujen aikana |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Lataa koko frontend sovelluksen alussa sen sijaan, että lataa sen näkymäkohtaisesti, katso [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Lisäargumentit, jotka annetaan frontend-testisuorittimelle |
| `hotswap` | — | `hotswap` | — | Liitä luokan päivitystyökalu sovellukseen, jonka rakennus käynnistää, katso [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Esimerkiksi, kiinnittääksesi Bun-version ja kytkeäksesi Tailwindin päälle:

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
