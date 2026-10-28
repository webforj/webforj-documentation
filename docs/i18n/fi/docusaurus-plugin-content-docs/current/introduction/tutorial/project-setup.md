---
title: Projektin asetukset
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 8c9cc05d141898612beff7f1c3f73fae
---
Aloittaaksesi tämän oppaan, tarvitset paikan projektiisi, jossa voit hallita luokkiasi ja resurssejasi. Seuraavat osat kuvaavat eri tapoja, joilla voit luoda webforJ-projektisi tälle oppaalle.

## Käyttäen lähdekoodia {#using-source-code}

Helpoin tapa seurata tätä opasta on viitata sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Projektirakenne {#project-structure}

Projektissa on kuusi alikansiota, yksi jokaiselle oppaan vaiheelle, ja jokaisessa on suoritettava sovellus. Seuraamalla mukana voit nähdä, miten sovellus etenee yksinkertaisesta asetuksesta täysin toimivaksi asiakashallintajärjestelmäksi.

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

## Käyttäen startforJ:ta {#using-startforj}

Jos haluat luoda uuden projektin, voit käyttää [startforJ:ta](https://docs.webforj.com/startforj) luomaan minimaalisen aloitusprojektin. Katso [Aloittaminen](/docs/introduction/getting-started) saadaksesi tarkempia tietoja startforJ:n käytöstä.

:::note Vaaditut asetukset
- Valitse **webforJ version** -pudotusvalikosta webforJ versio **26.01 tai uudempi**.
- Valitse **Runs on** -kohdasta **Spring Boot**.

## Käyttäen komentoriviä {#using-command-line}

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
  <TabItem value="cmd" label="Komentoikkuna">
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

Kaksi mainittua tapaa uuden projektin luomiseksi käyttää webforJ [archetypeja](/docs/building-ui/archetypes/overview), jotka lisäävät automaattisesti tarvittavat konfiguraatiot projektiisi. Tämä sisältää Spring [riippuvuudet](/docs/integrations/spring/spring-boot), webforJ Maven -liitännäisen, joka rakentaa ja valvoo frontend-lähteitä, sekä seuraavat ominaisuudet tiedostossa `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Nähdäksesi sovelluksen toiminnassa edetessäsi oppaan läpi:

1. Siirry halutun vaiheen hakemistoon. Tämä tulisi olla kyseisen vaiheen ylimmän tason hakemisto, jossa on `pom.xml`.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

   Luotu POM määrittää tämän oletuskomennon kokoamaan sovelluksen, käynnistämään webforJ frontend -valvojan ja suorittamaan Spring Bootin.

Sovelluksen käynnistäminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.
