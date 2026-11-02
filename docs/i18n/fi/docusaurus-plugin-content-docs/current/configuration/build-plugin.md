---
title: webforJ Build Plugin
sidebar_position: 5
sidebar_class_name: updated-content
description: >-
  Add the webforJ Maven or Gradle plugin to your build, the goals it binds to
  each phase, and the options it accepts.
_i18n_hash: 70642cf4584ff6eb218a11d53cd33a89
---
# webforJ build -laajennus <DocChip chip='since' label='26.01' /> {#webforj-build-plugin}

webforJ build -laajennus suorittaa webforJ:n rakennusaikatyöt osana Maven- tai Gradle-rakennusta. Lisäät sen kerran, ja se sitoo tavoitteensa vaiheisiin, joita jo suoritat, ilman erillistä frontend-projektia, jota pitää synkronoida. Se ohjaa [frontend-kokoajaa](/docs/managing-resources/bundler/overview), kooten frontendin, suorittaen frontend-testit, palvellen kehitysharsoja ja liittämällä [hotswap-työkalun](/docs/configuration/deploy-reload/hotswap) sovellukseen, jonka se käynnistää.

## Liittäminen laajennus {#adding-the-plugin}

webforJ-projektilla, joka on luotu [archetypen](/docs/introduction/getting-started) avulla, on jo laajennus. Lisää se olemassa olevaan projektiin:

<Tabs>
<TabItem value="maven" label="Maven">

Ilmoittamalla laajennuksen `<extensions>true</extensions>` sitoo sen tavoitteet rakennukseen ilman erillisiä suorituslohkot kirjoitettavaksi:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

Lisää laajennus `buildscript`-luokan riippuvuuden kautta ja käytä sitä:

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

## Tavoitteet ja tehtävät {#goals-and-tasks}

Kolme tavoitetta sitoo vaiheisiin, joita jo suoritat, joten normaali `mvn package` tai `./gradlew build` tuottaa sovelluksen, jonka frontend on koottu sisään, ja testivaihe suorittaa frontend-testit rinnakkain Java-testien kanssa. Tarkkailu on se, jonka aloitat käsin kehityksen aikana:

| Maven-tavoite | Gradle-tehtävä | Suorittaa | Mitä se tekee |
|---------------|-----------------|-----------|---------------|
| `bundle` | `webforjBundle` | `prepare-package`, ennen jokaista jar- ja war-tiedostoa | Kootaan frontend pakattua sovellusta varten |
| `test` | `webforjTest` | testivaiheessa | Suorittaa frontend-testit |
| `clean` | `webforjCleanFrontend` | puhdistusvaiheessa | Poistaa luodun frontendi |
| `watch` | `webforjWatch` | käsin, sovelluksen rinnalla | Uudelleenkootaan muutoksista kehityksen aikana |
| `push-keys` | `webforjPushKeys` | käsin, kerran per käyttöönotto | Luo avainparin [push-ilmoituksille](/docs/advanced/push-notifications) ja tulostaa konfiguraatiolinjat |
| `bbj-setup` | Vain Maven | käsin, BBj-koneella | Asentaa [BBjShip](/docs/configuration/bbjship/installing#through-the-maven-plugin) paikalliseen BBj:hen ja aktivoi sen käyttöönotto-päätteen |
| `bbj-package` | Vain Maven | pakettivaiheessa, `<bbj>`-lohkon kanssa | Kirjoittaa [BBj-käyttöönottoarkiston](/docs/configuration/bbjship/deploying#package-without-deploying) |
| `bbj-deploy` | Vain Maven | asennusvaiheessa, `<bbj>`-lohkon kanssa | [Ota sovellus käyttöön](/docs/configuration/bbjship/deploying#deploy) BBjServices-palveluissa |
| `bbj-undeploy` | Vain Maven | käsin | [Poistaa sovelluksen](/docs/configuration/bbjship/deploying#undeploy) BBjServices-palveluista |

Aloita tarkkailu tavoitteena ennen sitä, joka suorittaa sovellusta, esimerkiksi `mvn compile webforj:watch spring-boot:run`. Archetype-projekti asettaa tämän oletustavoitteeksi, joten `mvn` yksinään käynnistää kaiken. Sen uudelleenlatauskäyttäytyminen käsitellään [Frontend-watch](/docs/configuration/deploy-reload/frontend-watch).

Ohita frontend-testit yhdessä Java-testien kanssa, käytä `-DskipTests` tai `-Dmaven.test.skip` Mavenissa ja `-PskipTests` Gradle:ssa.

## Asetukset {#options}

Määritä asetukset Mavenin `<configuration>`-elementteinä tai Gradlen `webforj { }`-laajennusarvoina. Jokainen Maven-asetus, lukuun ottamatta `plugins` ja `hotswap`, hyväksyy myös `-D`-ominaisuuden komentorivillä. Kaksi rakennustyökalua peilaavat toisiaan:

| Maven-elementti | Maven-ominaisuus | Gradle | Oletus | Tarkoitus |
|----------------|-------------------|--------|--------|----------|
| `bunVersion` | `webforj.bundler.version` | `bunVersion` | hallittu | Jatka Bun-version kiinnittämistä toistettavissa rakennuksissa |
| `bunPath` | `webforj.bundler.path` | `bunPath` | lataa | Käytä olemassa olevaa Bun-binaaria sen sijaan, että lataa |
| `cacheDir` | `webforj.bundler.cacheDir` | `cacheDir` | `${user.home}/.webforj/bun` | Missä hallitut Bun-binäärit välimuistitetaan |
| `sourceRoot` | `webforj.bundler.sourceRoot` | `sourceRoot` | `src/main/frontend` | Missä frontendin pääsyötteet sijaitsevat |
| `workDir` | `webforj.bundler.workDir` | `workDir` | `target/bundle` | Missä laajennus kirjoittaa luodut rakennustiedostonsa |
| `plugins` | — | `plugins` | — | Ota [laajennus](/docs/managing-resources/bundler/extensions/overview) käyttöön tai pois päältä id:n mukaan, kuten `webforj-tailwind` |
| `excludePackages` | `webforj.bundler.excludePackages` | `excludePackages` | — | Pakettien etuliitteet, joita ohitetaan annotaatioskannauksessa |
| `eager` | `webforj.bundler.eager` | `eager` | `false` | Lataa koko frontend sovelluksen käynnistyessä sen sijaan, että lataa näkymäkohtaisesti, katso [Eager bundle](/docs/managing-resources/bundler/build-and-tests#eager-bundle) |
| `testArgs` | `webforj.bundler.testArgs` | `testArgs` | — | Lisäargumentit, jotka siirretään frontend-testausajurille |
| `hotswap` | — | `hotswap` | — | Liitä luokkapäivitystyökalu sovellukseen, jonka rakennus käynnistää, katso [Hotswap](/docs/configuration/deploy-reload/hotswap) |

Esimerkiksi, kiinnittääksesi Bun-version ja käynnistääksesi Tailwind:

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
