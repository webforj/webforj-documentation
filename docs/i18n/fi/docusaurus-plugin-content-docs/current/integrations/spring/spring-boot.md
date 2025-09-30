---
title: Spring Boot
sidebar_position: 10
_i18n_hash: a482c057615b165ea1c9ff9270f34def
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, tarjoten riippuvuuden injektoinnin, automaattisen konfiguroinnin ja sisäänrakennetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluita, varastoja ja muita Springin hallitsemia papereita suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona sisäänrakennetun Tomcat-palvelimen kanssa sen sijaan, että käyttäisit WAR-tiedostoa ulkoiseen sovelluspalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja vastaa pilvikohtaisia käyttöönotto käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa luoda uusi webforJ-sovellus Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käyttämällä startforJ:tä {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiiksi tehdyn asettelun, jolloin voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit räätälöidä sitä antamalla seuraavat tiedot:

- Perusprojektin metadata (Sovelluksen nimi, Ryhmä ID, Artefakti ID)  
- webforJ:n versio ja Java-versio
- Teeman väri ja ikoni
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootia varten. Voit valita ladattavaksi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

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

`flavor`-parametri kertoo archetypelle, että se luo Spring Boot -projektin sen sijaan, että luotaisiin tavallinen webforJ-projekti.

Tämä luo täydellisen Spring Boot -projektin, joka sisältää:
- Spring Boot -vanhempi POM -konfiguraation
- webforJ Spring Boot -aloitusriippuvuuden
- Pääsovellusluokan, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymiä
- Konfigurointitiedostoja sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkasi muuntamisen.

:::info[Voimassa vain olemassa oleville projekteille]
Ohita tämä osa, jos luot uutta projektia alusta alkaen.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

1. Muuta pakkausmuotoa WAR:ista JAR:iin:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhempana POM:na:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR-spesifiset konfiguraatiot, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvä konfiguraatio

Jos sinulla on jo vanhempi POM, sinun täytyy tuoda Spring Bootin materiaaliluettelon (BOM) sijaan:

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

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiisi. Pidä olemassa oleva webforJ-riippuvuus:

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

:::tip[webforJ DevTools automaattiseen selainpäivitykseen]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfigurointitiedoista.
:::

### Vaihe 3: Päivitä rakennusliitännäiset {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännäisellä. Poista kaikki olemassa olevat Jetty-konfiguraatiot ja lisää:

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

### Vaihe 4: Muunna sovellusluokkasi {#step-4-convert-your-app-class}

Muunna pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-anotaatiot ja päämenetelmä:

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
    
    // Pidä olemassa oleva run() -menetelmäsi, jos sinulla on sellainen
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva alustus koodisi 
    }
}
```

`@SpringBootApplication` -anotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttien skannaamisen. `@Routify`-anotaatio pysyy samana jatkaen reittien skannausta näkymäpaketeissasi.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Sovelluksen aloituspisteen täysin määritelty luokan nimi
webforj.entry = org.example.Application

# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelimen konfigurointi
server.port=8080
server.shutdown=immediate

# webforJ DevTools konfigurointi
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun kaikki on konfiguroitu, käynnistä sovelluksesi käyttämällä:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy sisäänrakennetulla Tomcat-palvelimella oletusarvoisesti portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat tarkalleen kuten ennenkin, mutta nyt voit injektoida Spring-papereita ja käyttää Spring-ominaisuuksia.

## Konfigurointi

Käytä `application.properties` -tiedostoa kansiossa `src/main/resources` sovelluksesi konfiguroimiseen. 
Katso [Ominaisuuden konfigurointi](/docs/configuration/properties) webforJ:n konfigurointiominaisuuksista.

Seuraavat webforJ `application.properties` -asetukset ovat erityisiä Spring-kehykseen:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|------------|--------|--------|-------|
| **`webforj.servletMapping`** | Merkkijono | URL-osoitteen mappausmalli webforJ-palvelimelle. | `/*` |
| **`webforj.excludeUrls`** | Lista | URL-mallit, joita ei pitäisi käsitellä webforJ:llä, kun ne on mappattu juureen. Kun webforJ on mappattu juurisivustoon (`/*`), nämä URL-mallit jätetään pois webforJ:n käsittelystä ja voivat olla Spring MVC -kontrollereiden käsiteltävissä. Tämä sallii REST-pisteiden ja muiden Spring MVC -mappausten esiintymisen yhdessä webforJ-reittien kanssa. | `[]` |

### Konfigurointierot {#configuration-differences}

Kun siirryt Spring Bootiin, useat konfigurointiasiat muuttuvat:

| Näkökohta | Tavallinen webforJ | Spring Boot webforJ |
|-----------|------------------|---------------------|
| **Pakkaus**  | WAR-tiedosto | Suoritettava JAR |
| **Palvelin**  | Ulkoinen (Jetty, Tomcat) | Sisäänrakennettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | vain `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit muodossa `application-{profiili}.properties` |
| **Porttikokoonpano** | Liitännäisen konfiguraatiossa | `server.port` ominaisuuksissa |
