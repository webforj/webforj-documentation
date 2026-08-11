---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 72ee1120081fa9f4d4fed86c13741d5b
---
Aloita tämä opas valitsemalla sijainti projektille, jossa voit hallita luokkia ja resursseja. Seuraavissa osioissa kuvataan erilaisia tapoja luoda webforJ-projekti tätä opasta varten.

## Käyttämällä lähdekoodia {#using-source-code}

Helpoin tapa seurata tätä opasta on viitata sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div> -->

### Projektin rakenne {#project-structure}

Projektissa on kuusi alikansiota, yksi kutakin opastusta varten, ja jokainen sisältää toimivan sovelluksen. Seuraamalla voit nähdä, kuinka sovellus etenee perusasetuksesta täysin toimivaksi asiakashallintajärjestelmäksi.

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

<!-- vale off -->
## Käyttämällä startforJ {#using-startforj}
<!-- vale on -->

Jos haluat mieluummin luoda uuden projektin, voit käyttää [startforJ:ta](https://docs.webforj.com/startforj) luodaksesi minimaalisen aloitusprojektin. Katso [Aloitus](/docs/introduction/getting-started) saadaksesi yksityiskohtaisempaa tietoa startforJ:n käytöstä.

:::note Vaaditut asetukset
- **webforJ-version** pudotusvalikosta valitse webforJ versio **26.01 tai uudempi**.
- **Maku** pudotusvalikosta valitse **webforJ + Spring Boot**.

## Käyttämällä komentoriviä {#using-command-line}

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

## Kokoonpanot {#configurations}

Kaksi mainittua tapaa uuden projektin luomiseksi käyttävät webforJ [archetyyppejä](/docs/building-ui/archetypes/overview), jotka automaattisesti lisäävät tarvittavat kokoonpanot projektiisi. Tämä sisältää Spring [riippuvuudet](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies), webforJ Maven -lisäosan, joka rakentaa ja seuraa frontend-lähteitä, sekä seuraavat asetukset tiedostossa `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Näyttääksesi sovelluksen toiminnassa edetessäsi ohjeessa:

1. Siirry sen vaiheen hakemistoon, jonka haluat. Tämä tulisi olla korkean tason hakemisto kyseiselle vaiheelle, joka sisältää `pom.xml`.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

   Luotu POM määrittää tämän oletuskomennon kokoamaan sovelluksen, käynnistämään webforJ frontend -seuraajan ja suorittamaan Spring Bootin.

<!-- vale Google.WordList = NO -->
Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
