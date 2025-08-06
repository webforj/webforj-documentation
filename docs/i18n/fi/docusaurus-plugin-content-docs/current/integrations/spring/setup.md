---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: c988b8fe49245d60c0e5f18c830595e3
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, ja se tarjoaa riippuvuuksien injektoinnin, automaattikonfiguroinnin ja upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluja, tallennustiloja ja muita Spring-hallittuja hippuja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona, jossa on upotettu Tomcat-palvelin sen sijaan, että käyttäisit WAR-tiedostoa ulkoisessa sovellpalvelimessa. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja on linjassa pilviperusteisten käyttöönotto käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

### Vaihtoehto 1: Käyttäen startforJ {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimiaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja esirakennetun asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä ID, Artifact ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Flavor** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon perusteella startforJ luo perusprojektin valitusta archetypestä, joka on konfiguroitu Spring Bootia varten.
Voit valita lataavasi projektisi ZIP-tiedostona tai julkaiseman sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttäen komentoriviä {#option-2-using-the-command-line}

Jos suosit komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttämällä virallisia webforJ-archetypeja:

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

`flavor`-parametri kertoo archetypelle, että sen tulisi luoda Spring Boot -projekti sen sijaan, että se luo tavallisen webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, johon sisältyy:
- Spring Boot -vanhempa POM -konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkikatselut
- Konfiguraatiotiedostot niin Springille kuin webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muuttamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkasi muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osio, jos luot uuden projektin alusta alkaen.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Muuta pakkaus WAR:sta JAR:ksi:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhempa POM:iksi:
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
   - `webapp`-hakemistoviittaukset
   - `web.xml`-liittyvät konfiguraatiot

Jos sinulla on jo vanhempi POM, sinun on kuitenkin tuontava Spring Boot Bill of Materials (BOM):

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

Lisää webforJ Spring Boot -aloitus riippuvuus riippuvuuksiisi. Pidä olemassa oleva webforJ-riippuvuus:

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

:::tip[webforJ DevTools automaattiseen selaimen päivittämiseen]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selaimen päivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain latautuu automaattisesti ilman manuaalista puuttumista. Katso [Spring DevTools](spring-devtools)-opas konfigurointitietoja varten.
:::

### Vaihe 3: Päivitä rakennusliitännät {#step-3-update-build-plugins}

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

### Vaihe 4: Muunna sovellusluokkasi {#step-4-convert-your-app-class}

Muuta pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämenetelmä:

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

`@SpringBootApplication` -annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttiskannauksen. `@Routify`-annotaatio pysyy ennallaan ja jatkaa näkymäpakettiesi reittien skannausta.

### Vaihe 5: Lisää Spring-konfiguraatio {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelimen konfigurointi
server.port=8080
server.shutdown=immediate

# webforJ DevTools -konfiguraatio
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Olemassa oleva `webforj.conf` -tiedostosi toimii edelleen. Suuntaa se pääluokkaasi:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun konfiguraatio on valmis, suorita sovelluksesi käyttäen:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella oletusarvoisesti portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat tarkalleen kuten ennen, mutta nyt voit injektoida Spring-hippuja ja käyttää Spring-ominaisuuksia.

## Konfiguraation erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfiguraation osa-alueet muuttuvat:

| Osa-alue | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfiguraatio** | Vain `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Portin konfigurointi** | Liitännäiskonfiguraatiossa | `server.port` konfiguraatioissa |
