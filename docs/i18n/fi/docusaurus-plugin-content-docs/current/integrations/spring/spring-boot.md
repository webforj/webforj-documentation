---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 4512bc42001e5f96301c60758cb0ca81
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, tarjoten riippuvuuden injektoimisen, automaattisen konfiguroinnin ja upotetun palvelinmallin. Käyttäessäsi Spring Bootia webforJ:n kanssa voit injektoida palveluja, varastoja ja muita Springin hallitsemia komponentteja suoraan käyttöliittymäosaasi konstruktori-injektion kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetun Tomcat-palvelimen kanssa sen sijaan, että julkaisisit WAR-tiedoston ulkoiseen sovelluspalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja vastaa pilvi-natiivin käyttöönoton käytäntöjä. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovellusympäristön kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

### Vaihtoehto 1: Käyttämällä startforJ {#option-1-using-startforj}

Helpoin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo vähäisen aloitusprojektin valitun webforJ-archetypen mukaan. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiiksi tehdyn asettelun, joten voit aloittaa rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmän ID, Artefaktin ID)
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perustason projektin valitusta archetypesta, joka on konfiguroitu Spring Bootia varten. Voit valita lataavasi projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, luo Spring Boot webforJ -projekti suoraan virallisilla webforJ-archetypeilla:

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

`flavor`-parametri kertoo archepyylle luoda Spring Boot -projekti sen sijaan, että luotaisiin normaali webforJ-projekti.

Tämä luo täydellisen Spring Boot -projektin, jossa on:
- Spring Boot -vanhemman POM-konfigurointi
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka `@SpringBootApplication` ja `@Routify` -annotaatioilla
- Esimerkki-näkymät
- Konfiguraatiotiedostot niin Springille kuin webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraatiosi päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkanne muuttamisen.

:::info[Olemassa oleville projekteille vain]
Ohita tämä osa, jos luot uutta projektia tyhjältä pöydältä. Tämä opas olettaa **webforJ-version 25.11 tai uudemman**.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoosi:

1. Muuta pakkaus muotoon JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Aseta Spring Boot vanhemmaksi POM:ksi:
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
   - `webapp` -kansioon viittaukset
   - `web.xml` -liittyvät konfiguraatiot

Jos sinulla on jo vanhempi POM, sinun on sen sijaan tuontava Spring Bootin materiaalit (BOM):

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

### Vaihe 2: Lisää Spring-riippuvuudet {#step-2-add-spring-dependencies}

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiisi:

:::info[webforJ 25.11+ yksinkertaistus]
Aloittamisesta **webforJ version 25.11**, `webforj-spring-boot-starter` sisältää kaikki ydinin webforJ-riippuvuudet välillisesti. Sinun ei enää tarvitse lisätä `com.webforj:webforj` -riippuvuutta erikseen.

Versioissa **ennen 25.11** sinun on sisällytettävä molemmat riippuvuudet erikseen.
:::

**webforJ 25.11 ja uudemmat:**

```xml title="pom.xml"
<dependencies>
  <!-- Lisää Spring Boot -aloitusriippuvuus (sisältää webforJ välillisesti) -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Lisää kehitystyökalut -->
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
  <!-- Lisää nimenomaisesti webforJ-riippuvuus -->
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

  <!-- Lisää kehitystyökalut -->
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

### Vaihe 3: Päivitä rakennusliitännäiset {#step-3-update-build-plugins}

Korvaa Jetty-liitännäinen Spring Boot -Maven-liitännäisellä. Poista kaikki olemassa olevat Jetty-konfiguraatiot ja lisää:

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

Muunna pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämetodi:

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
    // Olemassa oleva alustuskoodisi
  }
}
```

`@SpringBootApplication` -annotaatio mahdollistaa Springin automaattisen konfiguroinnin ja komponenttiskannauksen. `@Routify` -annotaatio pysyy samana, jatkaen näkymäpakettiesi reittien skannaamista.

### Vaihe 5: Lisää Spring-konfiguraatio {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Sovelluksen pääsijaintipisteen täysin määrätty luokan nimi
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

Kun konfiguraatio on valmis, suorita sovelluksesi käyttämällä:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy oletuksena upotetulla Tomcat-palvelimella portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat täsmälleen kuten ennenkin, mutta nyt voit injektoida Springin komponentteja ja käyttää Springin ominaisuuksia.

## Konfiguraatio {#configuration}

Käytä `application.properties` -tiedostoa `src/main/resources` konfiguroidaksesi sovelluksesi. Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoa webforJ:n konfiguraatiopropertiesistä.

Seuraavat webforJ:n `application.properties` -asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletus|
|------------|--------|--------|-------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-reitin malli webforJ-servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita ei tule käsitellä webforJ:n käsittelyyn juurelle. Kun webforJ on kytketty juuren kontekstiin (`/*`), nämä URL-mallit suljetaan pois webforJ-käsittelystä ja ne voidaan käsitellä sen sijaan Spring MVC -kontrollereilla. Tämä mahdollistaa REST-päätepisteiden ja muiden Spring MVC -reititysten rinnakkaisen käytön webforJ-reittien kanssa. | `[]` |

### Konfiguraation erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfiguraatio-asiat muuttuvat:

| Ominaisuus | Normaali webforJ | Spring Boot webforJ |
|------------|------------------|----------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | `webforj.conf` vain | `application.properties` + `webforj.conf` |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profile}.properties` |
| **Portin konfigurointi** | Liitännäiskonfiguraatiossa | `server.port` -asetuksessa |
