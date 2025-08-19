---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, sillä se tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluja, tietovarastoja ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäkomponentteihisi konstruktori-injektion avulla.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että se julkaistaisiin WAR-tiedostona ulkoiselle sovelluspalvelimelle. Tämä pakkausmalli yksinkertaistaa julkaisemista ja vastaa pilviedellytyksiin. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa luodaksesi uusi webforJ-sovellus Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven komentoriviä.

### Vaihtoehto 1: Käyttämällä startforJ:tä {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka generoi minimaalisen aloitusprojektin valitsemansa webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiiksi tehdyn asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) kanssa, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä ID, Artefakti ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Flavor** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypesta, joka on konfiguroitu Spring Bootia varten.
Voit valita ladataksesi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttäen virallisia webforJ-archetypeja:

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

`flavor`-parametri kertoo archetypelle, että se luo Spring Boot -projektin sen sijaan, että se luo tavallisen webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, jossa on:
- Spring Boot -vanhemman POM-konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovelluksen luokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfigurointitiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muuttamalla projektin konfigurointia. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osio, jos luot uuden projektin tyhjältä pöydältä.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Muuta pakkaus WAR:sta JAR:ksi:
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

3. Poista kaikki WAR-spesifiset konfiguroinnit, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvät konfiguroinnit

Jos sinulla on jo vanhempana POM, sinun täytyy tuoda Spring Bootin materiaalivirasto (BOM) sen sijaan:

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
    <!-- Olemassa oleva webforJ-riippuvuus -->
    <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj</artifactId>
        <version>${webforj.version}</version>
    </dependency>
    
    <!-- Lisää Spring Boot -aloitusriippuvuus -->
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
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain lataa automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](spring-devtools) -opas konfigurointitiedoista.
:::

### Vaihe 3: Päivitä rakennusliitännät {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännällä. Poista kaikki olemassa oleva Jetty-konfigurointi ja lisää:

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
      // Olemassa oleva aloituskoodisi 
    }
}
```

`@SpringBootApplication`-annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttien skannauksen. `@Routify`-annotaatio pysyy ennallaan ja jatkaa reittien etsimistä näkymäpaketeistasi.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedosto `src/main/resources` -hakemistoon:

```Ini title="application.properties"
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

Olemassa oleva `webforj.conf` -tiedostosi toimii edelleen. Suuntaa se pääluokkaasi:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun konfigurointi on valmis, suorita sovelluksesi seuraavasti:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella oletusarvoisesti portissa 8080. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat täsmälleen kuten ennenkin, mutta nyt voit injektoida Spring-komponentteja ja käyttää Spring-ominaisuuksia.

## Konfigurointierot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfigurointiasiat muuttuvat:

| Näkökohta | Standard webforJ | Spring Boot webforJ |
|-----------|------------------|---------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | `webforj.conf` vain | `webforj.conf` + `application.properties` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` kanssa |
| **Porttikonfigurointi** | Liitännän konfiguroinnissa | `server.port` -ominaisuus tiedostossa |
