---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 00d93e4eb2ef6afad342bdbc64324e3e
---
Aloita tämä opas luomalla sijainti projektillesi, jossa voit hallita luokkiasi ja resurssejasi. Seuraavissa osioissa kuvataan eri tapoja, joilla voit luoda webforJ-projektisi tälle oppaalle.

## Lähdekoodin käyttö {#using-source-code}

Helpoin tapa seurata tätä opasta on viitata sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projektin rakenne {#project-structure}

Projektissa on kuusi alihakemistoa, yksi jokaista oppaan vaihetta varten, ja jokaisessa on toimiva sovellus. Seuraamalla mukana voit nähdä, kuinka sovellus etenee perustasolta täysin toimivaksi asiakashallintajärjestelmäksi.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

## startforJ:n käyttö {#using-startforj}

Jos haluat mieluummin luoda uuden projektin, voit käyttää [startforJ:tä](https://docs.webforj.com/startforj) luodaksesi minimaalisen aloitusprojektin. Katso [Aloitus](/docs/introduction/getting-started) saadaksesi tarkempaa tietoa startforJ:n käytöstä.

:::note Vaaditut asetukset
- Valitse **webforJ-version** alasvetovalikosta webforJ versio **26.00 tai uudempi**.
- Valitse **Flavor** alasvetovalikosta **webforJ + Spring Boot**.
:::

## Komentoriviltä käyttö {#using-command-line}

Voit myös luoda uuden projektin seuraavalla komennolla:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Komentorivi">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Konfiguraatiot {#configurations}

Edellä mainitut tavat uuden projektin luomiseen käyttävät webforJ [archetypeja](/docs/building-ui/archetypes/overview), jotka automaattisesti lisäävät tarvittavat konfiguraatiot projektiisi, kuten Spring [riippuvuudet](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) POM-tiedostoon ja seuraavat ominaisuudet `src/main/resources/application.properties`-tiedostoon:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Voidaksesi nähdä sovelluksen toiminnassa edetessäsi oppaan läpi:

1. Siirry haluamasi vaiheen hakemistoon. Tämä pitäisi olla kyseisen vaiheen ylin hakemisto, jossa on `pom.xml`.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.
