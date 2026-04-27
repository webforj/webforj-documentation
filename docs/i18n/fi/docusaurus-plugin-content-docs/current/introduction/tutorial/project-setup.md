---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 02dbd05d1fdaba50c25155904013471b
---
Aloittaaksesi tämän oppaan, tarvitset paikan projektiisi, jossa voit hallita luokkia ja resursseja. Seuraavissa osioissa kuvataan eri tapoja luoda webforJ-projekti tälle oppaalle.

## Koodin käyttäminen {#using-source-code}

Helpoin tapa seurata tätä oppaata on viitata sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projektin rakenne {#project-structure}

Projektissa on kuusi alikansiota, yksi jokaiselle oppaan vaiheelle, ja jokaisessa on suoritettava sovellus. Seuraamalla mukana voit nähdä, kuinka sovellus etenee perustason asetuksesta täysin toimivaksi asiakashallintajärjestelmäksi.

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

## Käyttämällä startforJ {#using-startforj}

Jos haluat mieluummin luoda uuden projektin, voit käyttää [startforJ](https://docs.webforj.com/startforj) -työkalua luodaksesi minimaalisen aloitusprojektin. Katso [Aloitus](/docs/introduction/getting-started) saadaksesi tarkempaa tietoa startforJ:n käytöstä.

:::note Vaatimustasetukset
- Valitse **webforJ version** -pudotusvalikosta webforJ versio **26.00 tai uudempi**.
- Valitse **Flavor** -pudotusvalikosta **webforJ + Spring Boot**. 
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
  <TabItem value="cmd" label="Komentokehotte">
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

Kaksi mainittua tapaa luoda uusi projekti käyttää webforJ [archetyyppien](/docs/building-ui/archetypes/overview) avulla, jotka automaattisesti lisäävät tarvittavat konfiguraatiot projektiisi, kuten Spring [riippuvuudet](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) POM-tiedostoon ja seuraavat ominaisuudet tiedostoon `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Näet sovelluksen toiminnassa edetessäsi oppaan läpi:

1. Siirry halutun vaiheen hakemistoon. Tämän tulisi olla kyseisen vaiheen ylin hakemisto, jossa on `pom.xml`.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.
