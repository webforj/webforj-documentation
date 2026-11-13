---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen. Se tarjoaa riippuvuuksien injektoinnin, automaattisen määrityksen ja sulautetun palvelimen mallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluita, tietovarastoja ja muita Springin hallinnoimia beaneja suoraan käyttöliittymäkomponentteihisi konstruktorin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi suoritetaan suoritettavana JAR-tiedostona, johon sisältyy sulautettu Tomcat-palvelin. Ulkoiselle sovelluspalvelimelle ei siis tarvitse ottaa käyttöön WAR-tiedostoa. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja sopii yhteen pilvinatiivisten käyttöönottokäytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat Springin sovelluskontekstin rinnalla riippuvuuksien ja määritysten hallinnassa.

## Spring Boot -sovelluksen luominen {#create-a-spring-boot-app}

Voit luoda uuden Spring Bootia käyttävän webforJ-sovelluksen kahdella tavalla: graafisella startforJ-työkalulla tai Mavenin komentoriviltä.

<!-- vale off -->
### Vaihtoehto 1: startforJ-työkalun käyttäminen {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on käyttää [startforJ-työkalua](https://docs.webforj.com/startforj), joka luo valittuun webforJ-arkkityyppiin perustuvan minimaalisen aloitusprojektin. Aloitusprojekti sisältää kaikki tarvittavat riippuvuudet ja määritystiedostot sekä valmiin asettelun, joten voit aloittaa sen pohjalta kehittämisen heti.

Kun luot sovelluksen [startforJ-työkalulla](https://docs.webforj.com/startforj), voit mukauttaa sitä antamalla seuraavat tiedot:

- Projektin perustiedot (sovelluksen nimi, ryhmätunnus ja artefaktitunnus)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Arkkityyppi
- **Suoritusympäristö** – valitse **Spring Boot**, jos haluat luoda Spring Boot -projektin

Näiden tietojen perusteella startforJ luo valitsemaasi arkkityyppiin perustuvan Spring Bootille määritetyn perusprojektin.
Voit ladata projektin ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: komentorivin käyttäminen {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, luo Spring Bootia käyttävä webforJ-projekti suoraan virallisilla webforJ-arkkityypeillä:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

`flavor`-parametri määrittää, että arkkityypillä luodaan tavallisen webforJ-projektin sijaan Spring Boot -projekti.

Tämä luo täydellisen Spring Boot -projektin, joka sisältää seuraavat:

- Spring Boot -emo-POM-määritys
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa ovat `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Sekä Springin että webforJ:n määritystiedostot

## Spring Boot -sovelluksen suorittaminen {#run-the-spring-boot-app}

Arkkityyppiprojekti määrittää Mavenin oletustavoitteen, joten argumentiton `mvn` kääntää sovelluksen, käynnistää [frontend watch -toiminnon](/docs/configuration/deploy-reload/frontend-watch) ja suorittaa sovelluksen:

```bash
mvn
```

Sovellus käynnistyy oletusarvoisesti portissa 8080 toimivalla sulautetulla Tomcat-palvelimella. Nykyiset webforJ-näkymäsi ja -reittisi toimivat aivan kuten ennenkin, mutta nyt voit injektoida Spring-beaneja ja käyttää Springin ominaisuuksia.

## Määritys {#configuration}

Määritä sovellus käyttämällä `src/main/resources`-hakemiston `application.properties`-tiedostoa.
Katso webforJ:n määritysominaisuuksia koskevat tiedot kohdasta [Ominaisuuksien määritys](/docs/configuration/properties).

Seuraavat webforJ:n `application.properties`-asetukset ovat Spring-kohtaisia:

| Ominaisuus | Tyyppi | Kuvaus | Oletusarvo|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | Merkkijono | webforJ-servletin URL-osoitteen täsmäytysmalli. | `/*` |
| **`webforj.exclude-urls`** | Luettelo | URL-mallit, joita webforJ:n ei tule käsitellä, kun se on yhdistetty juureen. Kun webforJ on yhdistetty juurikontekstiin (`/*`), nämä URL-mallit ohitetaan webforJ:n käsittelyssä, ja Spring MVC -ohjaimet voivat käsitellä ne. Näin REST-päätepisteet ja muut Spring MVC -määritykset voivat toimia yhdessä webforJ-reittien kanssa. | `[]` |

### Määritysten erot {#configuration-differences}

Kun siirryt käyttämään Spring Bootia, useat määritykset muuttuvat:

| Ominaisuus | Tavallinen webforJ | Spring Bootia käyttävä webforJ |
|--------|-----------------|-------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR-tiedosto |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Sulautettu Tomcat |
| **Suorituskomento** | `mvn` | `mvn` |
| **Päämääritystiedosto** | Vain `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit ja `application-{profile}.properties` |
| **Portin määritys** | Liitännäisen määrityksessä | Ominaisuuksissa kohdassa `server.port` |

:::info Suorita komennolla `mvn`, älä komennolla `mvn spring-boot:run`
`mvn spring-boot:run` käynnistää vain sovelluksen. Se ei käynnistä [frontend watch -toimintoa](/docs/configuration/deploy-reload/frontend-watch), joten `src/main/frontend`-hakemiston lähdekoodia ei käännetä eikä rakenneta uudelleen sovelluksen suorituksen aikana, jos projektissasi on kyseinen hakemisto. Pelkkä `mvn` suorittaa projektin oletustavoitteen, joka käynnistää ensin tarkkailun ja sitten sovelluksen.
:::
