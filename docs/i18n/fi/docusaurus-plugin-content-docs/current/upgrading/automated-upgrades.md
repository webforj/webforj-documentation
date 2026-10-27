---
sidebar_position: 1
title: Automaattiset päivitykset
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# OpenRewrite käyttö {#using-openrewrite}

OpenRewrite on avoimen lähdekoodin projekti, joka on suunniteltu automaattiseen lähdekoodin refaktorointiin, jolloin voit tehokkaasti ja automaattisesti siirtää webforJ-versioiden välillä projekteissa, jotka käyttävät poistettuja tai vanhentuneita API:ita.

## Milloin käyttää OpenRewritea? {#when-to-use-openrewrite}

Voit käyttää OpenRewritea päivittääksesi poistumista vanhentuneista API:ista, ei vain suurten julkaisujen yhteydessä. Tämä auttaa sinua siirtymään uusiin vanhentuneisiin menetelmiin ennen niiden poistamista myöhemmissä versioissa.

## Asennus {#setup}

Päivittääksesi projektisi OpenRewriteilla, lisää OpenRewrite Maven -lisäosa `pom.xml`-tiedostoon, missä [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) on riippuvuus:

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

Korvaa `TARGET_VERSION` sillä webforJ-versiolla, johon päivität, ja `RECIPE_NAME` jollain resepteistä [Saatavilla olevat reseptit](#available-recipes) -osion kohdalla.

## Muutosten esikatselu (valinnainen) {#previewing-changes}

Jos haluat esikatsella, mitä muutoksia OpenRewrite tekee webforJ-reseptillä, voit suorittaa seuraavan komennon, joka luo diffin `target/rewrite/rewrite.patch` ilman tiedostojen muuttamista. Käy läpi patch-tiedosto nähdäksesi tarkasti, mitä resepti muuttaa.

```bash
mvn rewrite:dryRun
```

## OpenRewrite-noudon suorittaminen {#running-openrewrite}

Kun olet valmis tekemään muutoksia OpenRewritella, suorita seuraava komento:

```bash
mvn rewrite:run
```

Resepti hoitaa suurimman osan päivityksestä automaattisesti päivittämällä riippuvuuksia, nimeämällä menetelmiä uudelleen, muuntamalla tyyppejä ja säätämällä palautustyyppejä. Niissä harvoissa tapauksissa, joissa 1:1 korvausta ei ole, se lisää koodisi `TODO`-kommentteja tarkkoine ohjeineen. Etsi projektistasi nämä `TODO`:t nähdäksesi, mitä on vielä jäljellä.

## Siivous {#clean-up}

Kun olet suorittanut OpenRewritea webforJ-reseptillä ja ratkaissut kaikki `TODO`-kommentit, poista lisäosa `pom.xml`-tiedostostasi.

## Saatavilla olevat reseptit {#available-recipes}

| Versio | Standard webforJ Projectit | Spring Boot Projektit |
| ------- | ------- | ------- |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
