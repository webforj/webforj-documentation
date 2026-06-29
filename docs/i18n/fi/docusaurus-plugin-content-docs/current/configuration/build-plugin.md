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

webforJ build plugin suorittaa webforJ:n rakennusaikatyöt osana Maven- tai Gradle-rakennusta. Lisää se kerran, ja se sitoo tavoitteensa vaiheisiin, joita jo suoritat, ilman erillistä frontend-projektiä, jota pitää synkronoida. Se ohjaa [frontend bundleria](/docs/managing-resources/bundler/overview), kooten frontendin, suorittaen frontend-testit ja palvellen kehityshälytyksen.

## Adding the plugin {#adding-the-plugin}

webforJ-projekti, joka on luotu [archetypen](/docs/introduction/getting-started) perusteella, sisältää jo liitännäisen. Jotta voit lisätä sen olemassa olevaan projektiin:

<Tabs>
<TabItem value="maven" label="Maven">

Julkaise liitännäinen `<extensions>true</extensions>` sitomaan sen tavoitteet rakennukseen ilman suorituskäskyjä:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Lisää liitännäinen `buildscript` luokkakirjaston riippuvuutena ja sovella sitä:

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

## Goals {#goals}

Liitännäinen sitoo neljä tavoitetta, jokaisen vaiheen mukaan, jota jo suoritat, niin normaali `mvn package` tai `gradle build` tuottaa sovelluksen, jonka frontend on koottu sisään, ja `mvn test` suorittaa frontend-testit Java-testausten ohessa.

| Maven goal | Gradle task | Phase | What it does |
|------------|-------------|-------|--------------|
| `bundle` | `webforjBundle` | `prepare-package` | Kääntää frontendin tuotantoa varten |
| `test` | `webforjTest` | `test` | Suorittaa frontend-testit |
| `clean` | `webforjCleanFrontend` | `clean` | Poistaa luodun frontendin |
| `watch` | `webforjWatch` | käsin suoritettava | Uudelleenrakentaa muutoksen yhteydessä kehityksessä |

`watch` -tavoite on se, jota suoritat käsin kehityksen aikana sovelluksen ohella. Sen latauskäyttäytyminen on käsitelty [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Options {#options}

Aseta vaihtoehdot Mavenin `<configuration>` (tai `-D` ominaisuudet komentorivillä) ja Gradlen `webforj { }` laajennusarvoina. Kaksi rakennustyökalua peilaavat toisiaan.

| Option | Maven property | Gradle | Default | Purpose |
|--------|----------------|--------|---------|---------|
| Bun version | `webforj.bundler.version` | `bunVersion` | managed | Kiinnittää Bun-version toistettavia rakennuksia varten |
| Bun binary | `webforj.bundler.path` | `bunPath` | download | Käytää olemassa olevaa Bun-binaaria lataamisen sijaan |
| Cache directory | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Missä hallinnoidut Bun-binaarit tallennetaan |
| Source root | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Missä frontendin lähtöaineistot sijaitsevat |
| Work directory | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Missä liitännäinen kirjoittaa luodut rakennustiedostot |
| Extensions | `plugins` | `plugins` | — | Kytkee [laajennuksen](/docs/managing-resources/bundler/extensions/overview) päälle tai pois id:n mukaan, kuten `webforj-tailwind` |
| Exclude packages | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakettiesi etuliitteet, jotka jätetään väliin annotointiskannauksen aikana |
| Eager | `webforj.bundler.eager` | `eager` | `false` | Lataa koko frontend sovelluksen alussa sen sijaan, että lataisi näkymän mukaan, katso [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| Test arguments | `webforj.bundler.testArgs` | `testArgs` | — | Lisäargumentit, jotka välitetään frontend-testin suorittajalle |
| Skip tests | `skipTests`, `maven.test.skip` | — | `false` | Ohita frontend-testit |

Esimerkiksi, jos haluat kiinnittää Bun-version ja ottaa Tailwindin käyttöön:

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
