---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 23d00e916452c8a8f037d2e666b2300c
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseksi, tarjoten riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluita, tietovarastoja ja muita Springin hallitsemia objekteja suoraan käyttöliittymäkomponentteihisi konstruktori-injektion kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että asentaisit WAR-tiedoston ulkoiseen sovelluspalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja on linjassa pilvipohjaisten käyttöönoton käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontextin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven komentoriviä.

### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki vaaditut riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektimetatiedot (Sovelluksen nimi, Ryhmätunnus, Artefaktitunnus)  
- webforJ-versio ja Java-versio
- Teemaväri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon perusteella startforJ luo perusprojektin valitusta archetypestä, joka on konfiguroitu Spring Bootia varten. Voit valita lataavasi projektisi ZIP-tiedostona tai julkaisevan sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos suosittelet komentoriviä, voit luoda Spring Boot webforJ -projektin suoraan virallisten webforJ-archetyppien avulla:

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

`flavor`-parametri kertoo archetypelle, että se luo Spring Boot -projektin standardin webforJ-projektin sijaan.

Tämä luo täydellisen Spring Boot -projektin, johon kuuluu:
- Spring Boot -vanhempi POM-konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka `@SpringBootApplication` ja `@Routify`-annotaatioilla
- Esimerkkinäkymät
- Konfigurointitiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkasi muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osa, jos luot uuden projektin tyhjältä pohjalta.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Vaihda pakkaus WAR:sta JAR:iin:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhemmaksi POM:ksi:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR-erityinen konfiguraatio, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvät konfiguraatiot

Jos sinulla on jo vanhempi POM, sinun on tuoda Spring Bootin materiaalit (BOM) sen sijaan:

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

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiisi. Säilytä olemassa oleva webforJ-riippuvuus:

```xml title="pom.xml"
<dependencies>
    <!-- Olemassa oleva webforJ-riippuvuutesi -->
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

:::tip[webforJ DevTools automaattista selaimen päivitystä varten]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selaimen päivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfigurointitiedoista.
:::

### Vaihe 3: Päivitä rakentamisliitännäiset {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännäisellä. Poista kaikki olemassa oleva Jetty-konfiguraatio ja lisää:

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

### Vaihe 4: Muokkaa sovelllusluokkasi {#step-4-convert-your-app-class}

Muunna pää `App`-luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämetodi:

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
    
    // Säilytä olemassa oleva run() -metodisi, jos sinulla on sellainen
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva alustuskoodisi 
    }
}
```

`@SpringBootApplication`-annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttien etsinnän. `@Routify`-annotaatio pysyy samana, jatkaen näkymäpakettien reittien etsimistä.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Sovelluksen pääsovelluksen täydellinen puolueettoman nimen
webforj.entry = org.example.Application

# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelimen konfigurointi
server.port=8080
server.shutdown=immediate

# webforJ DevTools -konfigurointi
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun olet konfiguroinut, suorita sovelluksesi seuraavasti:

```bash
mvn spring-boot:run
```

Sovellus käynnistetään upotetulla Tomcat-palvelimella oletusarvoisesti portissa 8080. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat aivan kuten ennenkin, mutta nyt voit injektoida Springin objekteja ja käyttää Springin ominaisuuksia.

## Konfigurointi

Käytä `application.properties` -tiedostoa `src/main/resources` konfiguroidaksesi sovelluksesi. 
Katso [Property Configuration](/docs/configuration/properties) tietoja webforJ:n konfigurointiominaisuuksista.

Seuraavat webforJ `application.properties` asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|------------|--------|--------|-------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-mapperin malli webforJ-servlettia varten. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-kuviot, joita ei pitäisi käsitellä webforJ:n toimesta, kun se on kartoitettu juureen. Kun webforJ on kartoitettu juurikonfektiinaan (`/*`), nämä URL-kuviot jätetään pois webforJ:n käsittelystä ja niitä voivat käsitellä sen sijaan Spring MVC -ohjaimet. Tämä mahdollistaa REST-päätepisteiden ja muiden Spring MVC -kartoitusten olemassaolon yhdessä webforJ-reittien kanssa. | `[]` |

### Konfiguroinnin erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfigurointiaspektit muuttuvat:

| Aspekti | Standardi webforJ | Spring Boot webforJ |
|---------|-------------------|--------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettavan JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Porttikonfigurointi** | Liitännäiskonfiguraatiossa | `server.port` -konfiguraatiossa |
