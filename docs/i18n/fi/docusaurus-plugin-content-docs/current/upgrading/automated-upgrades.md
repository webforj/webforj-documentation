---
sidebar_position: 2
title: Automated Upgrades
sidebar_class_name: new-content
_i18n_hash: 1681300490f592540c6d96fbdbfd8ee4
---
# Using OpenRewrite {#using-openrewrite}

OpenRewrite on avointa lähdekoodin refaktorointiin suunnattu projekti, joka mahdollistaa tehokkaan ja automaattisen siirtymisen webforJ-versioiden välillä projekteissa, jotka käyttävät vanhentuneita tai poistettuja API:ita.

## When to use OpenRewrite? {#when-to-use-openrewrite}

Voit käyttää OpenRewriteä päivittäessäsi päästäksesi eroon vanhentuneista API:ista, ei vain suurissa julkaisuissa. Tämä auttaa sinua siirtymään pois uusista vanhentuneista metodeista ennen kuin ne poistetaan myöhemmässä versiossa.

## Setup {#setup}

Päivittääksesi projektisi OpenRewriteä käyttäen, lisää OpenRewrite Maven -laajennus `pom.xml`-tiedostoon, ja käytä [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) riippuvuuteena:

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

Korvaa `TARGET_VERSION` niillä webforJ-versiolla, johon päivität, ja `RECIPE_NAME` yhdellä artikkeleiden [Available recipes](#available-recipes) osiosta.

## Previewing changes (optional) {#previewing-changes}

Jos haluat ennakoida, mitä muutoksia OpenRewrite tekee webforJ-reseptillä, voit suorittaa seuraavan komennon, joka luo diffin `target/rewrite/rewrite.patch` ilman tiedostojen muokkaamista. Tarkista patch nähdäksesi tarkalleen, mitä resepti muuttaa.

```bash
mvn rewrite:dryRun
```

## Running OpenRewrite {#running-openrewrite}

Kun olet valmis soveltamaan muutoksia OpenRewritellä, suorita seuraava komento:

```bash
mvn rewrite:run
```

Resepti hoitaa suurimman osan päivityksestä automaattisesti päivittämällä riippuvuudet, nimeämällä metodit uudelleen, muuttamalla tyyppejä ja säätämällä palautustyyppejä. Niille harvoille tapauksille, joissa 1:1 korvausta ei ole, se lisää `TODO`-kommentteja koodiisi, joissa on tarkat ohjeet. Etsi projektistasi näitä `TODO`:ita löytääksesi mitä on vielä tehtävä.

## Clean up {#clean-up}

Kun olet suorittanut OpenRewriteä webforJ-reseptillä ja ratkaissut kaikki `TODO`-kommentit, poista laajennus `pom.xml`-tiedostostasi.

## Available recipes {#available-recipes}

| Version | Standard webforJ Projects | Spring Boot Projects |
| ------- | ------- | ------- |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
