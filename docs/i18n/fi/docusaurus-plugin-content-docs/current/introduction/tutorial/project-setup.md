---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: f9a028daac660f61634ad84d00cb0130
---
Aloita tämä opas luomalla sijainti projektille, jossa voit hallita luokkiasi ja resurssejasi. Seuraavat osiot kuvaavat erilaisia ​​tapoja luoda webforJ-projekti tätä opasta varten.

## Lähdekoodin käyttäminen {#using-source-code}

Helpoin tapa seurata tätä opasta on viitata sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-tietovarasto: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projektirakenne {#project-structure}

Projektissa on viisi alihakemistoa, yksi jokaiselle oppaan vaiheelle, ja jokainen sisältää suoritettavan sovelluksen. Seuraamalla voit nähdä, miten sovellus etenee perusasetuksesta täysin toimivaksi asiakashallintajärjestelmäksi.

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
└───5-validating-and-binding-data
```

## startforJ:n käyttäminen {#using-startforj}

Jos haluat luoda uuden projektin, voit käyttää [startforJ](https://docs.webforj.com/startforj) -työkalua luodaksesi minimaalisen aloitusprojektin. Katso [Aloitus](/docs/introduction/getting-started) saadaksesi tarkempia tietoja startforJ:n käytöstä.

:::note Vaaditut asetukset
- **webforJ-version** pudotusvalikossa valitse webforJ versio **25.10 tai uudempi**.
- **Flavor**-pudotusvalikossa valitse **webforJ + Spring Boot**.
:::

## Komentorivin käyttäminen {#using-command-line}

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

## Konfiguraatiot

Kaksi mainittua tapaa uuden projektin luomiseen käyttävät webforJ [archetypeja](/docs/building-ui/archetypes/overview), jotka lisäävät automaattisesti tarvittavat asetukset projektiisi, kuten Spring [riippuvuudet](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) POM-tiedostoon ja seuraavat ominaisuudet `src/main/resources/application.properties`-tiedostoon:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Näet sovelluksen toiminnassa edetessäsi oppaassa:

1. Siirry halutun vaiheen hakemistoon. Tämän tulisi olla kyseisen vaiheen ylin hakemisto, joka sisältää `pom.xml`-tiedoston.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen käynnistäminen avaa automaattisesti uuden selainikkunan osoitteessa `http://localhost:8080`.
