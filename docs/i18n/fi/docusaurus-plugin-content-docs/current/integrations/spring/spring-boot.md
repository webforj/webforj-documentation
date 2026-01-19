---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 620d9c2e8df79418d1e4902b970b42c8
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, ja se tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin sekä upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluita, varastoja ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäkomponentteihisi konstruktorinjektoinnin avulla.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että jouduit käyttämään WAR-tiedostoa ulkoisessa sovelluspalvelimessa. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja on linjassa pilviperustaisten käyttöönoton käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontextin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisesti lähtöprojektin valitun webforJ-archetypen perusteella. Tämä lähtöprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metadata (Sovelluksen nimi, Ryhmä-ID, Artefakti-ID)  
- webforJ-versio ja Java-versio
- Teema väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Käyttämällä näitä tietoja, startforJ luo perustason projektin valitsemastasi archetypesta, joka on konfiguroitu Spring Bootia varten.
Voit valita projektisi lataamisen ZIP-tiedostona tai julkaisemisen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttämällä virallisia webforJ-archetypeja:

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

Tämä luo täydellisen Spring Boot -projektin, johon sisältyy:
- Spring Bootin vanhempien POM-konfigurointi
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfigurointitiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfigurointia. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkasi muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osa, jos luot uuden projektin alusta alkaen. Tämä opas olettaa, että **webforJ versio 25.11 tai uudempi**.
:::

### Vaihe 1: Päivitä Maven-konfigurointi {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

1. Muuta pakkaus WAR:sta JAR:ksi:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhempana POM:ina:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR-spesifiset konfiguroinnit, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvät konfiguroinnit

Jos sinulla on jo vanhempi POM, sinun täytyy tuoda sen sijaan Spring Bootin materiaalit (BOM):

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

Lisää webforJ:n Spring Boot -aloitusriippuvuus riippuvuuksiisi:

:::info[webforJ 25.11+ yksinkertaistus]
Aloittaen **webforJ -version 25.11** `webforj-spring-boot-starter` sisältää kaikki ydinin webforJ -riippuvuudet transitiivisesti. Sinun ei enää tarvitse eksplisiittisesti lisätä `com.webforj:webforj` -riippuvuutta.

Versioissa **ennen 25.11** sinun on ilmoitettava molemmat riippuvuudet erikseen.
:::

**Versioille webforJ 25.11 ja myöhemmin:**

```xml title="pom.xml"
<dependencies>
    <!-- Lisää Spring Boot -aloitus (sisältää webforJ transitiivisesti) -->
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

**Versioissa ennen 25.11:**

```xml title="pom.xml"
<dependencies>
    <!-- Lisää eksplisiittisesti webforJ-riippuvuus -->
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
`webforj-spring-devtools` -riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfigurointitietoja varten.
:::

### Vaihe 3: Päivitä rakennusliittimet {#step-3-update-build-plugins}

Korvaa Jetty-liitin Spring Boot Maven -liittimellä. Poista olemassa olevat Jetty-konfiguraatiot ja lisää:

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

Muunna pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Springin annotaatiot ja päämenetelmä:

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
      // Olemassa oleva alustuskoodisi 
    }
}
```

`@SpringBootApplication` -annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttihaun. `@Routify`-annotaatio pysyy ennallaan, ja se jatkaa näkymäpakettiesi reittien hakua.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedosto `src/main/resources`:

```Ini title="application.properties"
# Täysin pätevän luokan nimi sovelluksen sisäänkäynnille
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

Kun olet konfiguroinut, suorita sovelluksesi käyttämällä:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella portissa 8080 oletusarvoisesti. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat täsmälleen kuten ennen, mutta nyt voit injektoida Springin komponentteja ja käyttää Springin ominaisuuksia.

## Konfigurointi

Käytä `application.properties` -tiedostoa `src/main/resources` konfiguroidaksesi sovellustasi. 
 Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoa webforJ:n konfigurointiominaisuuksista.

Seuraavat webforJ:n `application.properties` -asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|------------|--------|--------|-------|
| **`webforj.servlet-mapping`** | String | URL-reititysmalli webforJ-servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita ei tulisi käsitellä webforJ:n kun ne on kartoitettu juureen. Kun webforJ on kartoitettuna juurikontextiin (`/*`), nämä URL-mallit jätetään webforJ:n käsittelyn ulkopuolelle ja niitä voidaan käsitellä Spring MVC -kontrollereilla sen sijaan. Tämä sallii REST-pisteiden ja muiden Spring MVC -karttojen coexisting webforJ-reittien kanssa. | `[]` |

### Konfigurointieroja {#configuration-differences}

Kun vaihdat Spring Bootiin, useita konfigurointiaspekteja muuttuu:

| Aspekti | Standardi webforJ | Spring Boot webforJ |
|---------|------------------|---------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pään konfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profiili}.properties` |
| **Portin konfigurointi** | Liitinkanavien konfiguroinnissa | `server.port` ominaisuudessa |
