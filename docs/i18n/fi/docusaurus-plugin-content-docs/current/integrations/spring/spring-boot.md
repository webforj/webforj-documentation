---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 8664ccf60a8cd3a84330aabbc75c3a3b
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, se tarjoaa riippuvuuden injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Käyttäessäsi Spring Bootia webforJ:n kanssa voit injektoida palveluja, tietovarastoja ja muita Spring-hallittuja komponentteja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona, jossa on upotettu Tomcat-palvelin, sen sijaan että käyttäisit WAR-tiedostoa ulkoisessa sovelluspalvelimessa. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja vastaa pilviperusteisia käyttöönotto käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen Spring Bootilla: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja esivalmiin asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä-ID, Artefakti-ID)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon perusteella startforJ luo perusprojektin valitsemastasi archetypesta, joka on konfiguroitu Spring Bootia varten.
Voit valita lataavasi projektisi ZIP-tiedostona tai julkaisevan sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat mieluummin käyttää komentoriviä, voit luoda Spring Boot webforJ -projektin suoraan virallisten webforJ-archetypen avulla:

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

`flavor`-parametri kertoo archetypelle, että sen tulee luoda Spring Boot -projekti standardin webforJ-projektin sijaan.

Tämä luo täydellisen Spring Boot -projektin, jossa on:
- Spring Boot -vanhempi POM-konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka `@SpringBootApplication` ja `@Routify`-annotaatioilla
- Esimerkkinäkymiä
- Konfiguraatiotiedostot sekä Springille että webforJ:lle

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Archetype-projekti asettaa oletus Maven-tavoitteensa, joten `mvn` ilman argumentteja kääntää sovelluksen, käynnistää [frontend watch](/docs/configuration/deploy-reload/frontend-watch) ja suorittaa sovelluksen:

```bash
mvn
```

Sovellus käynnistyy oletusarvoisesti upotetulla Tomcat-palvelimella portissa 8080. Nykyiset webforJ-näkymäsi ja reitit toimivat aivan kuten ennen, mutta nyt voit injektoida Spring-komponentteja ja hyödyntää Springin ominaisuuksia.

## Konfiguraatio {#configuration}

Käytä `application.properties`-tiedostoa hakemistossa `src/main/resources` sovelluksesi konfiguroimiseksi.
 Katso [Property Configuration](/docs/configuration/properties) tietoa webforJ:n konfiguraatioparametreista.

Seuraavat webforJ:n `application.properties`-asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-mapping-malli webforJ servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita ei pitäisi käsitellä webforJ:llä, kun se on mapattu juureen. Kun webforJ on mapattu juurikontekstiin (`/*`), nämä URL-mallit jätetään webforJ:n käsittelyn ulkopuolelle ja niitä voivat käsitellä Spring MVC -kontrollerit. Tämä mahdollistaa REST-pisteiden ja muiden Spring MVC -mappien yhteensopivuuden webforJ-reittien kanssa. | `[]` |

### Konfiguraation erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfiguraation näkökohdat muuttuvat:

| Näkökohta | Standardi webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Pakkaaminen** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Portin konfigurointi** | Lisäosakonfiguraatiossa | `server.port` ominaisuuksissa |
