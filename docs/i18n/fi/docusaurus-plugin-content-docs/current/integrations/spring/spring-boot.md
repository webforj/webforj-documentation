---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot on suosittu valinta Java-sovellusten rakentamisessa, tarjoten riippuvuuksien injektointia, automaattista konfigurointia ja upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluita, varastoja ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona, jossa on upotettu Tomcat-palvelin, sen sijaan, että julkaisisit WAR-tiedoston ulkoiseen sovelluspalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja vastaa pilviperusteisia käyttöönotto käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontextin kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen Spring Bootilla: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

### Vaihtoehto 1: Käyttäen startforJ:tä {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimin aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiin ulkoasun, joten voit aloittaa sen rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä-ID, Artefakti-ID)
- webforJ:n versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Toimii** - Valitse **Spring Boot** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootille. Voit valita ladataksesi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttäen komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda Spring Boot webforJ -projektin suoraan käyttämällä virallisia webforJ-archetypeja:

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

`flavor`-parametri ilmoittaa archetypelle, että se luo Spring Boot -projektin sen sijaan, että se luo standardin webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, johon kuuluu:
- Spring Boot -vanhempi POM-konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfiguraatiotiedostot sekä Springille että webforJ:lle

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Archetype-projekti asettaa oletus Maven-tavoitteen, joten `mvn` ilman argumentteja kääntää sovelluksen, käynnistää [frontend watch](/docs/configuration/deploy-reload/frontend-watch) ja suorittaa sovelluksen:

```bash
mvn
```

Sovellus käynnistyy oletuksena upotetulla Tomcat-palvelimella portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat täsmälleen kuten ennenkin, mutta nyt voit injektoida Springin komponentteja ja käyttää Springin ominaisuuksia.

## Konfigurointi {#configuration}

Käytä `application.properties` -tiedostoa `src/main/resources` -hakemistossa konfiguroidaksesi sovellustasi. Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoa webforJ:n konfiguraatiopropertiesistä.

Seuraavat webforJ:n `application.properties` -asetukset liittyvät erityisesti Springiin:

| Ominaisuus | Tyyppi | Kuvaus | Oletus |
|------------|--------|--------|--------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-mapping-malli webforJ-servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita ei pitäisi käsitellä webforJ:n kautta, kun se on kartoitettu juureen. Kun webforJ on kartoitettu juurikontekstiin (`/*`), nämä URL-mallit jätetään pois webforJ:n käsittelystä, ja ne voidaan käsitellä Spring MVC -kontrollereiden kautta. Tämä mahdollistaa REST-päätteiden ja muiden Spring MVC -kartoitusten yhteiseloa webforJ-reittien kanssa. | `[]` |

### Konfiguraation erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfiguraatioasiat muuttuvat:

| Ominaisuus | Standard webforJ | Spring Boot webforJ |
|------------|------------------|---------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettavan JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pään konfiguraatio** | Vain `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Portin konfigurointi** | Lisäosakonfiguraatiossa | `server.port` konfiguraatiossa |
