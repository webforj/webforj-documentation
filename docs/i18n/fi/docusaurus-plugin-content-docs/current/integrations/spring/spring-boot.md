---
title: Spring Boot
sidebar_position: 10
_i18n_hash: c70504d9cf2cae7a877a0deb0d5420c6
---
Spring Boot on suosittu vaihtoehto Java-sovellusten rakentamiseen, ja se tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Käyttäessäsi Spring Bootia webforJ:n kanssa, voit injektoida palveluja, varastoja ja muita Springin hallinnoimia componenteja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin avulla.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona, jossa on upotettu Tomcat-palvelin sen sijaan, että käyttäisit WAR-tiedostoa ulkoiselle sovelluspalvelimelle. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja on linjassa pilvisovelluskäytännöiden kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontextin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käytä startforJ:ta {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimialkuperäisen projektin valitsemansa webforJ-archetypen perusteella. Tämä alkuperäinen projekti sisältää kaikki vaadittavat riippuvuudet, konfigurointitiedostot ja ennalta tehdyn asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:llä](https://docs.webforj.com/startforj), voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (sovelluksen nimi, ryhmä ID, artefakti ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Flavor** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootia varten.
Voit valita, lataatko projektisi ZIP-tiedostona vai julkaisetko sen suoraan GitHubiin.

### Vaihtoehto 2: Käytä komentoriviä {#option-2-using-the-command-line}

Jos haluat mieluummin käyttää komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttämällä virallisia webforJ-archetypeja:

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

`flavor`-parametri kertoo archetypelle, että sen tulee luoda Spring Boot -projekti sen sijaan, että luotaisiin tavallinen webforJ-projekti.

Tämä luo täydellisen Spring Boot -projektin, jossa on:
- Spring Boot -pää- POM-konfigurointi
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfigurointitiedostot både Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektin konfigurointia. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osa, jos luot uutta projektia alusta alkaen.
:::

### Vaihe 1: Päivitä Maven-konfigurointi {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Muuta pakkaustyyppi WAR:sta JAR:ksi:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot pää-POM:ksi:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR:lle spesifiset konfiguraatiot, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`:ään liittyvä konfigurointi

Jos sinulla on jo pää-POM, sinun on vietävä Spring Bootin materiaalit (BOM) sen sijaan:

```xml title="pom.xml"
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.5.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Vaihe 2: Lisää Spring-riippuvuudet {#step-2-add-spring-dependencies}

Lisää webforJ Spring Boot -aloitus riippuvuus riippuvuuksiisi. Pidä olemassa oleva webforJ-riippuvuutesi:

```xml title="pom.xml"
<dependencies>
    <!-- Olemassa oleva webforJ-riippuvuus -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Lisää Spring Boot -aloitus -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-spring-boot-starter</artifactId>
        <version>${webforj.version}</version>
    </dependency>

    <!-- Lisää devtools -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-spring-devtools</artifactId>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
    </dependency>
</dependencies>
```

:::tip[webforJ DevTools automaattiseen selaimen päivitykseen]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selaimen päivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfigurointitietoja varten.
:::

### Vaihe 3: Päivitä build-liitännäiset {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännäisellä. Poista kaikki olemassa oleva Jetty-konfigurointi ja lisää:

```xml title="pom.xml"
<build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludeDevtools>true</excludeDevtools>
        </configuration>
      </plugin>
    </plugins>
</build>
```

### Vaihe 4: Muunna sovelluksesi luokka {#step-4-convert-your-app-class}

Muunna pää `App`-luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämenetelmä:

```java title="Application.java"
package com.example;

import com.webforj.App;
import com.webforj.annotation.Routify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify(packages = "com.example.views")
public class Application extends App {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    // Pidä olemassa oleva run() -menetelmä, jos sinulla on sellainen
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva alkuunpanokoodisi
    }
}
```

`@SpringBootApplication`-annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttiskannauksen. `@Routify`-annotaatio pysyy samana, ja se jatkaa näkymäpakettiesi reittien skannaamista.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Täysin määritelty luokan nimi sovelluksen sisäänkäynnille
webforj.entry = org.example.Application

# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelin konfigurointi
server.port=8080
server.shutdown=immediate

# webforJ DevTools konfigurointi
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun olet konfiguroinut, suorita sovelluksesi komennolla:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy oletuksena upotetulla Tomcat-palvelimella portissa 8080. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat täsmälleen kuten ennen, mutta nyt voit injektoida Springin komponentteja ja käyttää Springin ominaisuuksia.

## Konfigurointi

Käytä `application.properties`-tiedostoa `src/main/resources` konfiguroidaksesi sovelluksesi. 
 Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoa webforJ:n konfigurointiominaisuuksista.

Seuraavat webforJ:n `application.properties` -asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|----------|------|-------------|--------|
| **`webforj.servletMapping`** | Merkkijono | URL-mapping mallinne webforJ-servletille. | `/*` |
| **`webforj.excludeUrls`** | Lista | URL-mallineet, joita ei pitäisi käsitellä webforJ:n toimesta, kun ne on kartoitettu juureen. Kun webforJ on kartoitettu juurikontekstiin (`/*`), näitä URL-mallineita ei käsitellä webforJ:ssa ja ne voidaan käsitellä Spring MVC -kontrollereiden avulla. Tämä mahdollistaa REST-päätepisteiden ja muiden Spring MVC -karttojen olemassaolon yhdessä webforJ-reittien kanssa. | `[]` |

### Konfigurointierot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfigurointinäkökohtia muuttuvat:

| Näkökohta | Tavallinen webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` kanssa |
| **Portin konfigurointi** | Liitännäiskonfiguraatiossa | `server.port` ominaisuuksissa |
