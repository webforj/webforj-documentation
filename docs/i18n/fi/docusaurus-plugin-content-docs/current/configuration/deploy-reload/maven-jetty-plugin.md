---
title: Jetty
sidebar_position: 40
description: >-
  Käynnistä webforJ-sovellus upotetulla Jetty-palvelimella Maven Jetty -lisäosan
  avulla ja hyödynnä kehityksen aikana live reloadia ja hotswapia.
_i18n_title_hash: 95dd2e7e150f2e8a04c35a78cb15b3e5
_i18n_description_hash: 0090f7699180a26ef9808777682744c1
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
Maven Jetty -liitännäinen suorittaa sovelluksen liitetyssä Jetty-palvelimessa suoraan projektista. Arkkitehtuuriprojekti asettaa `compile webforj:watch jetty:run` oletus Maven -tavoitteekseen, joten `mvn` ilman argumentteja kääntää sovelluksen, käynnistää [frontend watch](/docs/configuration/deploy-reload/frontend-watch) ja palvelee sovellusta Jettyllä.

## Vaatimukset {#requirements}

Jetty-projekti ilmoittaa kehitystyökalut itse, kehityskäytöissä käytetyssä profiilissa:

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

Versio tulee webforJ:n materiaaliluettelosta (BOM). Profiili pitää riippuvuuden pakatusta war-tiedostosta eristyksissä. Projekti, joka on luotu [arkkitehtuurista](/docs/introduction/getting-started), sisältää tämän profiilin.

## Live-latauksen käyttöönottaminen {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Avaimet ovat samoja, joita Spring Boot -sovellus asettaa `application.properties`-tiedostossa, lueteltuna [asetuksissa](/docs/configuration/deploy-reload/overview#settings).

## Luokka muutokset {#class-changes}

Kun [hotswap-työkalu](/docs/configuration/deploy-reload/hotswap) on määritetty, työkalu soveltaa luokkamuutokset eikä Jetty julkaise mitään. Kaksi Jetty-ominaisuutta tukevat tätä, ja arkkitehtuuriprojekti asettaa molemmat:

- `scan` on `0`, joka sammuu Jettyn tiedostoskannauksen.
- `deployMode` jää asettamatta. Hotswap vaatii forkattu tilan, ja liitännäinen valitsee sen. Käännä, joka asettaa `deployMode`:n toiseen arvoon, käynnistyy ilman työkalua ja kirjaa sen.

Ilman hotswap-työkalua aseta `scan` aikaväli sekunneissa, ja Jetty julkaisee sovelluksen, kun käännetyt luokat tai resurssit muuttuvat:

| Ominaisuus | Kuvaus | Oletus |
|------------|--------|--------|
| `scan`     | Sekuntien väli käännetyille tulosteille, asetettuna `jetty.scan`-ominaisuutena. `0` sammuttaa skannaamisen. Pidemmät välin vähentävät kuormitusta ja viivästyttävät julkaisemista. | `1` |

## Käyttöhuomiot {#usage-considerations}

- **Muisti ja CPU**: alhaiset `scan`-arvot lisäävät resurssien kulutusta suurilla projekteilla. Pidemmät välin vähentävät sitä ja viivästyttävät julkaisemista.
- **Vain kehitykseen**: Jetty-liitännäinen ei ole tarkoitettu tuotantokäyttöön.
- **Istunnot**: julkaisu voi poistaa käyttäjäistunnot. [Hotswap-työkalu](/docs/configuration/deploy-reload/hotswap) soveltaa muutoksia ilman julkaisemista, ja istunto säilyy.
