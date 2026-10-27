---
title: Bestaande Projecten
sidebar_position: 3
description: >-
  Move an existing webforJ project from the webforJ install plugin to BBjShip
  with one OpenRewrite run, and what the migration changes in the build and the
  configuration.
_i18n_hash: 36b02a0227a79d8b8aa9929d930c3e75
---
Een project dat met de verouderde webforJ install plugin implementeert, wordt overgezet naar BBjShip met één OpenRewrite-run. De run past de `pom.xml` en de `webforj.conf` van het project aan. Java-code, stylesheets en bronnen blijven onveranderd.

## Migreren met OpenRewrite {#migrate-with-openrewrite}

Voeg de OpenRewrite Maven-plugin toe aan je `pom.xml`, met het webforJ 26.03 recept:

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

Voer de migratie uit en bekijk wat er is veranderd:

```bash
mvn rewrite:run
git diff
```

Het gemigreerde project implementeert met BBjShip. `mvn install` implementeert de app en `mvn` start een [ontwikkelingsrun](/docs/configuration/deploy-reload/bbjservices). Verwijder de OpenRewrite-plugin daarna uit je `pom.xml`. [Geautomatiseerde upgrades](/docs/upgrading/automated-upgrades#previewing-changes) laat zien hoe je de wijzigingen kunt bekijken voordat je de run uitvoert.

## Wat de migratie wijzigt {#what-the-migration-changes}

### De build plugin {#the-build-plugin}

De install plugin wordt de [webforJ build plugin](/docs/configuration/build-plugin) met `<extensions>true</extensions>`, zodat `mvn install` zonder uitvoering implementeert. De instellingen verplaatsen naar het `<bbj>` blok:

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

| Install plugin | BBjShip |
|---|---|
| `<deployurl>` | `<bbj><deployment><url>`, met het pad `/webforj-bbjship` |
| `<publishname>` | `<bbj><publishName>` |
| `<username>`, `<password>`, `<token>` | `<bbj><admin>` |
| `<classname>` | `webforj.entry` in `webforj.conf` |
| `<debug>` | `webforj.debug` in `webforj.conf` |

[Implementeren](/docs/configuration/bbjship/deploying#settings) somt elke instelling van het `<bbj>` blok op.

### De webforJ engine {#the-webforj-engine}

BBjServices biedt de webforJ engine, dus je app mag niet zijn eigen versie meedragen. De `webforj` afhankelijkheid sluit al `webforj-engine` uit. De migratie verklaart het opnieuw in de `provided` scope zodat je IDE er nog steeds tegen kan compileren:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-engine</artifactId>
  <version>${webforj.version}</version>
  <scope>provided</scope>
</dependency>
```

### Het configuratiebestand {#webforjconf}

De entry klasse en de debug-vlag verplaatsen van de install plugin naar `webforj.conf`, naast de instellingen waarmee een BBjShip-app draait:

```hocon title="webforj.conf"
webforj.entry = "com.example.Application"
webforj.debug = true
webforj.assetsSource = packaged
webforj.devtools.livereload.enabled = true
webforj.devtools.craftforj.enabled = true
```

`webforj.assetsSource = packaged` levert de `ws://` statische bestanden en de `icons://` iconen van de app. Zonder deze instelling kan een app die `icons://` gebruikt niet opstarten in BBjServices. Sleutels die het bestand al instelt blijven onveranderd.

### Ontwikkelingsruns {#development-runs}

De migratie voegt HotswapAgent toe aan de configuratie van de build plugin:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

Het stelt ook `compile webforj:watch` in als het standaard doel van de build, zodat `mvn` de app draait in de BBjServices van je eigen machine:

```xml title="pom.xml"
<defaultGoal>compile webforj:watch</defaultGoal>
```

Zie [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Herimplementatie en Live Reload.

## Zonder de install plugin {#without-the-install-plugin}

Een project dat nooit de install plugin heeft gebruikt, krijgt dezelfde configuratie handmatig: de [build plugin](#the-build-plugin) met een `<bbj>` blok, de `webforj-engine` [uitsluiting en `provided` verklaring](#the-webforj-engine), en [`webforj.conf`](#webforjconf) met `webforj.assetsSource = packaged`. Het project gebruikt `jar` packaging, en de `webforj` afhankelijkheid sluit `webforj-engine` uit:

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
