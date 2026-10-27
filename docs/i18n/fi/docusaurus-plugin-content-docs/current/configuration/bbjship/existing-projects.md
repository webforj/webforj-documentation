---
title: Olemassa olevat projektit
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
Projekt, joka asennetaan vanhentuneen webforJ asennusliitännäisen avulla, siirtyy BBjShipiin yhdellä OpenRewrite-suorituksella. Suoritus muuttaa projektin `pom.xml`- ja `webforj.conf`-tiedostoa. Java-koodi, tyylisivut ja resurssit pysyvät muuttumattomina.

## Siirrä OpenRewrite:llä {#migrate-with-openrewrite}

Lisää OpenRewrite Maven -liitännäinen `pom.xml`-tiedostoosi, käyttäen webforJ 26.03 reseptiä:

```xml title="pom.xml"
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>com.webforj.rewrite.v26.UpgradeWebforj_26_03</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>26.03</version>
    </dependency>
  </dependencies>
</plugin>
```

Suorita migraatio ja tarkista, mitä on muuttunut:

```bash
mvn rewrite:run
git diff
```

Siirretty projekti asennetaan BBjShipin avulla. `mvn install` asentaa sovelluksen, ja `mvn` käynnistää [kehityssuorituksen](/docs/configuration/deploy-reload/bbjservices). Poista OpenRewrite-liitännäinen `pom.xml`-tiedostostasi tämän jälkeen. [Automaattiset päivitykset](/docs/upgrading/automated-upgrades#previewing-changes) näyttää, kuinka voit esikatsella muutokset ennen suoritusta.

## Mitä migraatio muuttaa {#what-the-migration-changes}

### Asennusliitännäinen {#the-build-plugin}

Asennusliitännäinen muuttuu [webforJ rakennusliitännäiseksi](/docs/configuration/build-plugin) `<extensions>true</extensions>` -asetuksella, joten `mvn install` asentaa ilman suoritetta. Sen asetukset siirtyvät `<bbj>` lohkoon:

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
  <configuration>
    <bbj>
      <publishName>inventory</publishName>
      <deployment>
        <url>http://localhost:8888/webforj-bbjship</url>
      </deployment>
    </bbj>
  </configuration>
</plugin>
```

| Asennusliitännäinen | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, polulla `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` `webforj.conf`-tiedostossa |
| `<debug>` | `webforj.debug` `webforj.conf`-tiedostossa |

[Asentaminen](/docs/configuration/bbjship/deploying#settings) listaa kaikki `<bbj>`-lohkon asetukset.

### webforJ-moottori {#the-webforj-engine}

BBjServices tarjoaa webforJ-moottorin, joten sovelluksesi ei saa sisältää omaa. `webforj`-riippuvuus sulkee jo `webforj-engine`:n. Migraatio määrittelee sen jälleen `provided`-laajuudessa, jotta IDEsi voi edelleen kääntää sen:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### Konfigurointitiedosto {#webforjconf}

Entry-luokka ja debug-lippu siirtyvät asennusliitännäisestä `webforj.conf`-tiedostoon, rinnakkain asetusten kanssa, joita BBjShip-sovellus käyttää:

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` toimittaa `ws://` staattiset tiedostot ja `icons://` ikonit sovelluksesta. Ilman sitä sovellus, joka käyttää `icons://`, ei käynnisty BBjServicesissä. Tiedoston jo asettamat avaimet pysyvät muuttumattomina.

### Kehityssuoritukset {#development-runs}

Migraatio lisää HotswapAgentin rakennusliitännäisen konfiguraatioon:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Se asettaa myös `compile webforj:watch` oletustavoitteeksi rakennuksessa, jotta `mvn` suorittaa sovelluksen omassa koneesi BBjServicesissä:

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

Katso [BBjServices](/docs/configuration/deploy-reload/bbjservices) uudelleenlähetyksessä ja live reloadissa.

## Ilman asennusliitännäistä {#without-the-install-plugin}

Projekti, joka ei ole koskaan käyttänyt asennusliitännäistä, saa saman konfiguraation käsin: [rakennusliitännäisen](#the-build-plugin) `<bbj>`-lohkolle, `webforj-engine` [poissulun ja `provided` määrittelyn](#the-webforj-engine), ja [`webforj.conf`](#webforjconf) `webforj.assetsSource = packaged` kanssa. Projekti käyttää `jar`-pakkausta, ja `webforj`-riippuvuus sulkee `webforj-engine`:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj</artifactId>
  <version>${webforj.version}</version>
  <exclusions>
    <exclusion>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-engine</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
