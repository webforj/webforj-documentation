---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 1704f647af5396bd4efd4fdbcc4da978
---
Aloita tämä opas valitsemalla projektillesi sijainti, jossa voit hallita luokkiasi ja resurssejasi. Seuraavissa osioissa kuvataan erilaisia tapoja luoda webforJ-projekti tätä opasta varten.

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

Projektissa on kuusi alikansiota, yksi jokaista opetusvaihetta varten, ja jokainen sisältää suoritettavan sovelluksen. Seuraamalla mukana näet, miten sovellus kehittyy perusasetuksista täysin toimivaksi asiakashallintajärjestelmäksi.

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

Jos haluat luoda uuden projektin, voit käyttää [startforJ:tä](https://docs.webforj.com/startforj) luodaksesi minimalisen aloitusprojektin. Katso [Aloittaminen](/docs/introduction/getting-started) saadaksesi tarkempia tietoja startforJ:n käytöstä.

:::note Pakolliset asetukset
- **webforJ version** alasvetovalikosta valitse webforJ versio **26.01 tai uudempi**.
- **Flavor** alasvetovalikosta valitse **webforJ + Spring Boot**.

## Kommennorivin käyttö {#using-command-line}

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
  <TabItem value="cmd" label="Komentokehote">
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

Kaksi mainittua tapaa luoda uusi projekti käyttää webforJ [archetypia](/docs/building-ui/archetypes/overview), joka automaattisesti lisää tarvittavat konfiguraatiot projektiisi. Tämä sisältää Spring [riippuvuudet](/docs/integrations/spring/spring-boot), webforJ Maven -lisäosan, joka rakentaa ja seuraa etupään lähteitä, sekä seuraavat ominaisuudet tiedostossa `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Nähdäksesi sovelluksen toiminnassa opastaessasi:

1. Siirry halutun vaiheen hakemistoon. Tämän tulisi olla sen vaiheen huipputason hakemisto, joka sisältää `pom.xml`.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

   Generoitu POM määrittää tämän oletuskomennon kääntämään sovelluksen, käynnistämään webforJ-etupään valvojan ja suorittamaan Spring Bootin.

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.
