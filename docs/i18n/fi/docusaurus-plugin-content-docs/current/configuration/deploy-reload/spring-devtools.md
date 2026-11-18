---
title: Automaattinen päivitys Spring Bootilla
sidebar_label: Spring Boot
sidebar_position: 30
description: >-
  Ota live reload käyttöön Spring Boot -webforJ-sovelluksessa webforJ:n
  build-pluginin tarjoamilla kehitystyökaluilla.
_i18n_title_hash: d5c637214c47ee7e474c357635a126ba
_i18n_sidebar_label_hash: d2b84a7bb8d211a07fd6ecbf4db9d426
_i18n_description_hash: d2db8310c2f562443c975e7b774dbe44
_i18n_hash: 2fa5b74377a864e82b67db98ee8c9c04
---
In a Spring Boot -sovelluksessa [webforJ build plugin](/docs/configuration/build-plugin) toimittaa kehitystyökalut kehitysajoihin. Projektissa ei ole näille riippuvuutta, eikä ne koskaan ole osa pakattua sovellusta.

## Vaatimukset {#requirements}

Alustavan riippuvuuden ja build pluginin. Projekti, joka on luotu [archetypestä](/docs/introduction/getting-started), sisältää molemmat.

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
```

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <extensions>true</extensions>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-spring-boot-starter'
}
```

[webforJ-pluginin](/docs/configuration/build-plugin#adding-the-plugin) oltava lisättynä buildiin.

</TabItem>
</Tabs>

## Live reloadin kytkeminen päälle {#turning-live-reload-on}

```Ini title="application.properties"
webforj.devtools.livereload.enabled=true
server.shutdown=immediate
```

Käynnistä sovellus tavallisesti, `mvn` Mavenin kanssa tai `./gradlew bootRun` Gradlen kanssa. Java-muutokset tulevat voimaan käännöksen jälkeen, tyylitiedostojen ja kuvien muutokset tapahtuvat paikan päällä, ja lähteet `src/main/frontend` uudelleenrakennetaan [frontend watch](/docs/configuration/deploy-reload/frontend-watch) -toimintojen kautta. Muut avaimet on lueteltu [asetuksissa](/docs/configuration/deploy-reload/overview#settings).

## Spring DevTools {#spring-devtools}

Spring DevTools on valinnainen, live reload toimii ilman sitä. Jos haluat käyttää sen uudelleenkäynnistysmalleja, lisää sen riippuvuus:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
}
```

</TabItem>
</Tabs>

Kun Spring DevTools on käytössä, käännetty muutos käynnistää Spring-kontekstit ja selain päivittää, kun uudelleenkäynnistys on valmis. Jos myös [hotswap-työkalu](/docs/configuration/deploy-reload/hotswap) on määritetty, työkalu soveltaa luokka päivityksiä ja uudelleenkäynnistys jää pois päältä.

## Tuotantorakennukset {#production-builds}

`mvn package` ja `./gradlew bootJar` tuottavat sovelluksen ilman kehitystyökaluja, ilman, että poissulkevia sääntöjä, profiileja tai ominaisuuksia tarvitaan. Ominaisuus `webforj.devtools.livereload.enabled` ei vaikuta pakatussa sovelluksessa.
