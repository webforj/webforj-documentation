---
sidebar_position: 1
title: Automatische upgrades
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# OpenRewrite gebruiken {#using-openrewrite}

OpenRewrite is een open source project dat is ontworpen voor geautomatiseerde refactoring van source code, waarmee je efficiënt en automatisch kunt migreren tussen webforJ-versies voor projecten die verouderde of verwijderde API's gebruiken.

## Wanneer OpenRewrite gebruiken? {#when-to-use-openrewrite}

Je kunt OpenRewrite gebruiken bij het upgraden om afstand te nemen van verouderde API's, niet alleen bij grote releases. Dit helpt je om afstand te nemen van nieuw verouderde methoden, voordat ze in een latere versie worden verwijderd.

## Setup {#setup}

Om je project te upgraden met OpenRewrite, voeg je de OpenRewrite Maven-plugin toe aan je `pom.xml`, met [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) als afhankelijkheid:

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

Vervang `TARGET_VERSION` door de webforJ-versie waarnaar je upgraden wilt en `RECIPE_NAME` door een van de recepten uit de sectie [Beschikbare recepten](#available-recipes) van dit artikel.

## Wijzigingen previewen (optioneel) {#previewing-changes}

Als je liever wilt previewen welke wijzigingen OpenRewrite zal aanbrengen met een webforJ-recept, genereert het uitvoeren van de volgende opdracht een diff in `target/rewrite/rewrite.patch` zonder enige bestanden te wijzigen. Bekijk de patch om precies te zien wat het recept zal veranderen.

```bash
mvn rewrite:dryRun
```

## OpenRewrite uitvoeren {#running-openrewrite}

Wanneer je klaar bent om wijzigingen toe te passen met OpenRewrite, voer je de volgende opdracht uit:

```bash
mvn rewrite:run
```

Het recept behandelt de meeste upgrades automatisch door afhankelijkheden bij te werken, methoden te hernoemen, types te veranderen en retourtypes aan te passen. Voor de weinige gevallen waarin een 1:1 vervanging niet bestaat, voegt het `TODO`-commentaren in je code toe met specifieke instructies. Zoek je project naar deze `TODO`s om te ontdekken wat er nog overblijft.

## Opruimen {#clean-up}

Na het uitvoeren van OpenRewrite met een webforJ-recept en het oplossen van eventuele `TODO`-commentaren, verwijder je de plugin uit je `pom.xml`.

## Beschikbare recepten {#available-recipes}

| Versie | Standaard webforJ-projecten | Spring Boot-projecten |
| ------- | ------- | ------- |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
