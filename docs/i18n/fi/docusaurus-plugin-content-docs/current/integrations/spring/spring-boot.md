---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 2178bfe95edd9c5e1a1c95aef4184662
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, ja se tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Kun käytät Spring Bootia yhdessä webforJ:n kanssa, voit injektoida palveluita, tallennuskerroksia ja muita Springin hallinnoimia ytimiä suoraan käyttöliittymäkomponentteihisi rakentajakonstruktorin kautta.

Kun käytät Spring Bootia yhdessä webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että julkaisisit WAR-tiedoston ulkoiseen sovelluspalvelimeen. Tämä paketointimalli yksinkertaistaa käyttöä ja on linjassa pilvipohjaisten käyttöönotto käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovellusrajapinnan kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseksi Spring Bootilla: käyttää graafista startforJ-työkalua tai Maven komentoriviä.

### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfigurointitiedostot ja valmiin asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Group ID, Artifact ID)  
- webforJ:n versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Flavor** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perustason projektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootia varten.
Voit valita ladattavaksi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubissa.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, voit luoda Spring Boot -webforJ-projektin suoraan käyttämällä virallisia webforJ-archetypejä:

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

`flavor`-parametri kertoo archetypelle generoida Spring Boot -projekti tavallisen webforJ-projektin sijaan.

Tämä luo täydellisen Spring Boot -projektin, johon sisältyy:
- Spring Bootin vanhempi POM-konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka `@SpringBootApplication` ja `@Routify` -ilmoituksilla
- Esimerkkinäkymät
- Konfigurointitiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osa, jos luot uuden projektin tyhjältä pöydältä.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

1. Muuta paketointi WAR:sta JAR:ksi:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Määritä Spring Boot vanhempana POM:ina:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Poista kaikki WAR-tiedostoon liittyvät konfiguraatiot, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvä konfiguraatio

Jos sinulla on jo olemassa oleva vanhempi POM, sinun on importoitava Spring Bootin materiaalilista (BOM) sen sijaan:

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

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiisi. Pidä olemassa oleva webforJ-riippuvuutesi:

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
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain lataa automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfigurointitietoja varten.
:::

### Vaihe 3: Päivitä rakennusliitännät {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot Maven -liitännällä. Poista kaikki olemassa olevat Jetty-konfiguraatiot ja lisää:

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

### Vaihe 4: Muunna sovellusluokkaasi {#step-4-convert-your-app-class}

Muunna pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-ilmoitukset ja päämetodi:

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
    
    // Pidä olemassa oleva run()-metodisi, jos sinulla on sellainen
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva aloituskoodisi 
    }
}
```

`@SpringBootApplication` -ilmoitus mahdollistaa Springin automaattisen konfiguroinnin ja komponenttiskannauksen. `@Routify` -ilmoitus pysyy ennallaan ja jatkaa näkypakettiesi reittien skannaamista.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

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

Olemassa oleva `webforj.conf`-tiedostosi toimii edelleen. Ohjaa se pääluokkaasi:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun olet konfiguroinut, suorita sovelluksesi komennolla:

```bash
mvn spring-boot:run
```

Sovellus alkaa upotetun Tomcat-palvelimen kanssa portissa 8080 oletuksena. Olemassa olevat webforJ-näkymäsi ja reittisi toimivat täsmälleen kuten ennen, mutta nyt voit injektoida Spring-yksikköjä ja käyttää Springin ominaisuuksia.

## Konfiguraatio-erot {#configuration-differences}

Kun siirryt Spring Bootiin, useat konfiguraation osa-alueet muuttuvat:

| Osa-alue | Standardi webforJ | Spring Boot webforJ |
|----------|-------------------|---------------------|
| **Paketointi** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfiguraatio** | Vain `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Porttikongigurointi** | Liitännäiskonfiguraatiossa | `server.port` ominaisuuksissa |
