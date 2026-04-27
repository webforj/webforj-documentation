---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 116777dbeb2e6707e2ef867f0dd6d78c
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, tarjoten riippuvuuksien injektointia, automaattista konfigurointia ja upotettua palvelinmallia. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluja, varastoja ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että julkaisisit WAR-tiedoston ulkoiselle sovelluspalvelimelle. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja vastaa pilvipohjaisia käyttöönotto käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiiksi tehdyyn asettelun, joten voit alkaa rakentaa sitä heti.

Kun luot sovelluksen [startforJ](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metadata (Sovelluksen nimi, Ryhmän ID, Artefakti ID)  
- webforJ:n versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootia varten. Voit valita lataamisen ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda suoraan Spring Boot webforJ -projektin virallisten webforJ-archetypejen avulla:

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

`flavor`-parametri kertoo archetypelle, että se luo Spring Boot -projektin sen sijaan, että se luo tavanomaista webforJ-projektia.

Tämä luo täydellisen Spring Boot -projektin, jossa on:
- Spring Boot -vanhempi POM-konfigurointi
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfiguraatiotiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektin konfiguraatiota. Tämä prosessi sisältää Mavenin konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Olemassa oleville projekteille vain]
Ohita tämä osio, jos luot uutta projektia alusta alkaen. Tämä opas olettaa **webforJ version 25.11 tai uudemman**.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

1. Vaihda pakkaus WAR:sta JAR:iin:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhemmaksi POM:iksi:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>4.0.5</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR-spesifiset konfiguraatiot, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemistoreferenssit
   - `web.xml`-liittyvä konfiguraatio

Jos sinulla on jo vanhempi POM, sinun on importoitava Spring Boot -materiaaliluettelo (BOM) sen sijaan:

```xml title="pom.xml"
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>4.0.5</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### Vaihe 2: Lisää Spring-riippuvuuksia {#step-2-add-spring-dependencies}

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiisi:

:::info[webforJ 25.11+ yksinkertaistus]
Aloittamalla **webforJ version 25.11**, `webforj-spring-boot-starter` sisältää kaikki ydindependencit automaattisesti. Sinun ei enää tarvitse erikseen lisätä `com.webforj:webforj` riippuvuutta.

Versioille **ennen 25.11** sinun on sisällytettävä molemmat riippuvuudet erikseen.
:::

**webforJ 25.11 ja uudemmat:**

```xml title="pom.xml"
<dependencies>
  <!-- Lisää Spring Boot -aloitus (sisältää webforJ automaattisesti) -->
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

**Versioille ennen 25.11:**

```xml title="pom.xml"
<dependencies>
  <!-- Lisää webforJ-riippuvuus erikseen -->
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
`webforj-spring-devtools` -riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti uudelleen ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opasta konfigurointitiedot.
:::

### Vaihe 3: Päivitä rakennuslisäosat {#step-3-update-build-plugins}

Korvaa Jetty-lisäosa Spring Boot Maven -lisäosalla. Poista kaikki olemassa olevat Jetty-konfiguraatiot ja lisää:

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
    
  // Säilytä olemassa oleva run() -menetelmäsi, jos sellainen on 
  @Override
  public void run() throws WebforjException {
    // Olemassa oleva alustuskoodeesi 
  }
}
```

`@SpringBootApplication` annotaatio mahdollistaa Springin automaattisen konfiguraation ja komponenttien skannauksen. `@Routify`-annotaatio pysyy samana ja jatkaa näkymäpakettiesi reittien skannaamista.

### Vaihe 5: Lisää Spring-konfiguraatio {#step-5-add-spring-configuration}

Luo `application.properties` tiedosto kansioon `src/main/resources`:

```Ini title="application.properties"
# Täysin määritelty luokan nimi sovelluksen pääpisteelle
webforj.entry = org.example.Application

# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelimen konfiguraatio
server.port=8080
server.shutdown=immediate

# webforJ DevTools -konfiguraatio
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun konfigurointi on valmis, suorita sovelluksesi käyttäen:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella oletusarvoisesti portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat täsmälleen kuten ennen, mutta nyt voit injektoida Spring- komponentteja ja käyttää Spring-ominaisuuksia.

## Konfiguraatio

Käytä `application.properties` -tiedostoa kansiossa `src/main/resources` sovelluksesi konfiguroimiseksi. 
Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoa webforJ:n konfiguraatio-ominaisuuksista.

Seuraavat webforJ `application.properties` asetukset ovat spesifisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletusarvo|
|------------|--------|--------|-----------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-mapping-malli webforJ servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita webforJ ei käsittele, kun se on mappattu juureen. Kun webforJ on mappattu juurikontekstiin (`/*`), nämä URL-mallit jätetään pois webforJ-käsittelystä ja ne voidaan käsitellä Spring MVC -ohjaimien toimesta. Tämä mahdollistaa REST-päätteiden ja muiden Spring MVC -mappien rinnakkaisen toiminnan webforJ-reittien kanssa. | `[]` |

### Konfiguraatioerot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfiguraatioasiat muuttuvat:

| Ominaisuus | Tavanomainen webforJ | Spring Boot webforJ |
|------------|---------------------|---------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääasiallinen konfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Portti-konfigurointi** | Lisäosan konfiguraatiossa | `server.port` ominaisuuksissa |
