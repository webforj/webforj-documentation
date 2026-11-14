---
title: Projektin käyttöönotto
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
Aloittaaksesi tämän opetusohjelman tarvitset projektillesi sijainnin, jossa voit hallita luokkiasi ja resurssejasi. Seuraavissa osioissa kuvataan eri tavat luoda webforJ-projekti tätä opetusohjelmaa varten.

## Lähdekoodin käyttäminen {#using-source-code}

Helpoin tapa seurata tätä opetusohjelmaa on tutustua sen lähdekoodiin. Voit ladata koko projektin tai kloonata sen GitHubista:

<!-- vale off -->
- Lataa ZIP-tiedosto: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-arkisto: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### Projektin rakenne {#project-structure}

Projektissa on kuusi alihakemistoa, yksi kutakin opetusohjelman vaihetta varten. Jokaisessa on suoritettava sovellus. Kun seuraat vaiheita, näet, miten sovellus kehittyy perusasetuksista täysin toimivaksi asiakashallintajärjestelmäksi.

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
## startforJ:n käyttäminen {#using-startforj}
<!-- vale on -->

Jos haluat mieluummin luoda uuden projektin, voit luoda minimaalisen aloitusprojektin [startforJ:n](https://docs.webforj.com/startforj) avulla. Katso lisätietoja startforJ:n käytöstä kohdasta [Aloittaminen](/docs/introduction/getting-started).

:::note Vaaditut asetukset
- Valitse **webforJ version** -pudotusvalikosta webforJ-versioksi **26.01 tai uudempi**.
- Valitse kohdasta **Runs on** vaihtoehto **Spring Boot**.

## Komentorivin käyttäminen {#using-command-line}

Voit luoda uuden projektin myös seuraavalla komennolla:

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
  <TabItem value="cmd" label="Command Prompt">
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

## Asetukset {#configurations}

Molemmissa mainituissa tavoissa luoda uusi projekti käytetään webforJ:n [arkkityyppejä](/docs/building-ui/archetypes/overview), jotka lisäävät projektiin tarvittavat asetukset automaattisesti. Näihin kuuluvat Springin [riippuvuudet](/docs/integrations/spring/spring-boot), webforJ:n Maven-laajennus, joka kääntää käyttöliittymän lähdekoodit ja valvoo niiden muutoksia, sekä seuraavat ominaisuudet tiedostossa `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Sovelluksen suorittaminen {#running-the-app}

Voit nähdä sovelluksen toiminnassa opetusohjelman edetessä seuraavasti:

1. Siirry haluamasi vaiheen hakemistoon. Sen pitäisi olla kyseisen vaiheen ylimmän tason hakemisto, joka sisältää tiedoston `pom.xml`.

2. Suorita Spring Boot -sovellus paikallisesti seuraavalla Maven-komennolla:
    ```bash
    mvn
    ```

   Luotu POM määrittää tämän oletuskomennon kääntämään sovelluksen, käynnistämään webforJ:n käyttöliittymän lähdekoodien valvojan ja suorittamaan Spring Bootin.

<!-- vale Google.WordList = NO -->
Sovelluksen suorittaminen avaa automaattisesti uuden selainikkunan osoitteessa `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
