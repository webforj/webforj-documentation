---
sidebar_position: 1
title: Automated Upgrades
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
sidebar_class_name: new-content
_i18n_hash: 7f4b51fb3daf9ba91f0e755758d8ec52
---
# Using OpenRewrite {#using-openrewrite}

OpenRewrite on avoin lähdekoodirefaktoroinnin projekti, joka mahdollistaa tehokkaan ja automaattisen siirtymisen webforJ-versioiden välillä projekteille, jotka käyttävät vanhentuneita tai poistettuja API:ita.

## When to use OpenRewrite? {#when-to-use-openrewrite}

Voit käyttää OpenRewriteä päivittäessäsi siirtyäksesi vanhentuneista API:ista, ei vain suurissa julkaisussa. Tämä auttaa sinua siirtymään uusista vanhentuneista metodeista ennen kuin ne poistetaan myöhemmässä versiossa.

## Setup {#setup}

Päivittääksesi projektisi OpenRewrite:llä, lisää OpenRewrite Maven -laajennus `pom.xml`-tiedostoon, jossa on [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) riippuvuutena:

```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>RECIPE_NAME</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>TARGET_VERSION</version>
    </dependency>
  </dependencies>
</plugin>
```

Korvata `TARGET_VERSION` webforJ-versiolla, johon päivität, ja `RECIPE_NAME` yhdellä resepteistä [Available recipes](#available-recipes) tämän artikkelin osiossa.

## Previewing changes (optional) {#previewing-changes}

Jos haluat ennakoida, mitä muutoksia OpenRewrite tekee webforJ-reseptillä, seuraavan komennon suorittaminen luo eron `target/rewrite/rewrite.patch` ilman, että se muuttaa mitään tiedostoja. Tarkista korjaus nähdäksesi tarkalleen, mitä resepti muuttaa.

```bash
mvn rewrite:dryRun
```

## Running OpenRewrite {#running-openrewrite}

Kun olet valmis soveltamaan muutoksia OpenRewrite:llä, suorita seuraava komento:

```bash
mvn rewrite:run
```

Resepti hoitaa suurimman osan päivityksistä automaattisesti päivittämällä riippuvuuksia, nimeämällä metodeja uudelleen, muuttamalla tyyppejä ja säätämällä palautustyyppejä. Harvinaisissa tapauksissa, joissa 1:1 korvausta ei ole, se lisää `TODO`-kommentteja koodiisi erityisillä ohjeilla. Etsi projektistasi nämä `TODO`-kommentit selvittääksesi, mitä on jäljellä.

## Clean up {#clean-up}

Kun olet suorittanut OpenRewrite:n webforJ-reseptillä ja ratkaissut mahdolliset `TODO`-kommentit, poista laajennus `pom.xml`:stäsi.

## Available recipes {#available-recipes}

| Version | Standard webforJ Projects | Spring Boot Projects |
| ------- | ------- | ------- |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
