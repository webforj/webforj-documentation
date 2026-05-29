---
sidebar_position: 2
title: Automated Upgrades
sidebar_class_name: new-content
_i18n_hash: 1681300490f592540c6d96fbdbfd8ee4
---
# OpenRewrite gebruiken {#using-openrewrite}

OpenRewrite is een open source project dat is ontworpen voor geautomatiseerde refactoring van broncode, waardoor je efficiënt en automatisch kunt migreren tussen webforJ-versies voor projecten die verouderde of verwijderde API's gebruiken.

## Wanneer OpenRewrite gebruiken? {#when-to-use-openrewrite}

Je kunt OpenRewrite gebruiken bij het upgraden om afstand te nemen van verouderde API's, niet alleen bij grote releases. Dit helpt je om afstand te nemen van nieuw verouderde methoden, voordat ze in een latere versie worden verwijderd.

## Instellen {#setup}

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

Vervang `TARGET_VERSION` door de webforJ-versie waarnaar je upgrade en `RECIPE_NAME` door een van de recepten uit de [Beschikbare recepten](#available-recipes) sectie van dit artikel.

## Wijzigingen vooraf bekijken (optioneel) {#previewing-changes}

Als je liever wilt zien welke wijzigingen OpenRewrite zal aanbrengen met een webforJ-recept, genereert het uitvoeren van de volgende opdracht een diff in `target/rewrite/rewrite.patch` zonder enige bestanden te wijzigen. Bekijk de patch om precies te zien wat het recept zal veranderen.

```bash
mvn rewrite:dryRun
```

## OpenRewrite uitvoeren {#running-openrewrite}

Als je klaar bent om wijzigingen toe te passen met OpenRewrite, voer dan de volgende opdracht uit:

```bash
mvn rewrite:run
```

Het recept behandelt het merendeel van de upgrade automatisch door afhankelijkheden bij te werken, methoden te hernoemen, types te veranderen en retourtypes aan te passen. Voor de weinige gevallen waarin een 1:1 vervanging niet bestaat, voegt het `TODO`-opmerkingen in je code toe met specifieke instructies. Zoek in je project naar deze `TODO`s om te vinden wat er nog over is.

## Opruimen {#clean-up}

Verwijder na het uitvoeren van OpenRewrite met een webforJ-recept en het oplossen van eventuele `TODO`-opmerkingen de plugin uit je `pom.xml`.

## Beschikbare recepten {#available-recipes}

| Versie | Standaard webforJ-projecten | Spring Boot-projecten |
| ------- | ------- | ------- |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
