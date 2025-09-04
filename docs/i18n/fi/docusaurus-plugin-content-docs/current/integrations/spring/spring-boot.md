---
title: Spring Boot
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, ja se tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Käyttäessäsi Spring Bootia webforJ:n kanssa voit injektoida palveluja, tietovarastoja ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäsovelluksiisi rakentajainjektion kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetulla Tomcat-palvelimella sen sijaan, että se julkaistaan WAR-tiedostona ulkoiseen sovellpalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoprosessia ja vastaa pilviperusteisia käyttöönoton käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguroinnin hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: graafisen startforJ-työkalun käyttäminen tai Maven-komentorivin käyttö.

### Vaihtoehto 1: Käyttäen startforJ:tä {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-mallin perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovellusta [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä-ID, Artefakti-ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja ikoni
- Malli
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi mallista, joka on konfiguroitu Spring Bootia varten.
Voit valita ladataksesi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttäen komentoriviä {#option-2-using-the-command-line}

Jos mieluummin käytät komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttämällä virallisia webforJ-malleja:

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

`flavor`-parametri kertoo mallille, että se luo Spring Boot -projektin sen sijaan, että se luo standardin webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, johon kuuluu:
- Spring Boot -vanhemman POM-konfigurointi
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfigurointitiedostot sekä springiä että webforJ:tä varten

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektin konfigurointia. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osio, jos luot uuden projektin alusta alkaen.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Muuta pakkaus muotoon WAR JAR:iksi:
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

3. Poista kaikki WAR:iin liittyvä konfigurointi, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemistoviittaukset
   - `web.xml`-liittyvä konfigurointi

Jos sinulla on jo vanhempi POM, sinun on tuontaa Spring Bootin materiaalien lista (BOM) sen sijaan:

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

Lisää webforJ Spring Boot -aloitus riippuvuudeksi. Pidä olemassa oleva webforJ-riippuvuus:

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

:::tip[webforJ DevTools automaattista selainpäivitystä varten]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattiseen selainpäivitykseen. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti uudelleen ilman manuaalista väliintuloa. Katso [Spring DevTools](spring-devtools) -opas konfigurointitiedoista.
:::

### Vaihe 3: Päivitä build-liitännäiset {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännäisellä. Poista olemassa oleva Jetty-konfigurointi ja lisää:

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

Muuta pää `App`-luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämetodi:

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
    
    // Pidä olemassa oleva run()-metodi, jos sellainen on
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva alustus koodi 
    }
}
```

`@SpringBootApplication`-annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttien etsinnän. `@Routify`-annotaatio pysyy samana, ja se etsii näkymäpakettejasi reittejä varten.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedosto `src/main/resources`-hakemistoon:

```Ini title="application.properties"
# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelimen konfiguraatio
server.port=8080
server.shutdown=immediate

# webforJ DevTools konfigurointi
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Olemassa oleva `webforj.conf` -tiedosto toimii yhä. Kohdista se pääluokkaasi:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun konfigurointi on valmis, suorita sovelluksesi käyttäen:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella portissa 8080 oletusarvoisesti. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat täsmälleen kuten ennenkin, mutta nyt voit injektoida Spring-komponentteja ja käyttää Spring-ominaisuuksia.

## Konfigurointierot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfigurointiasiat muuttuvat:

| Ominaisuus | Standard webforJ | Spring Boot webforJ |
|------------|------------------|---------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | vain `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` avulla |
| **Portin konfigurointi** | Liitännäiskonfiguraatiossa | `server.port` ominaisuusasetuksissa |
