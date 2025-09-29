---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 46d6f92d638a214c8b5a0a8632ef0150
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, tarjoten riippuvuuden injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Kun käytät Spring Bootia webforJ:n kanssa, voit injektoida palveluja, repositorioita ja muita Spring-hallittuja komponentteja suoraan käyttöliittymäkomponentteihisi konstruktori-injektion kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona upotetulla Tomcat-palvelimella sen sijaan, että se julkaistaisiin WAR-tiedostona ulkoiseen sovelluspalvelimeen. Tämä pakkausmalli yksinkertaistaa käyttöönottoa ja on linjassa pilvinatiivien käyttöönotto käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguraation hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen Spring Bootin kanssa: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

<!-- vale off -->
### Vaihtoehto 1: Käyttämällä startforJ:tä {#option-1-using-startforj}
<!-- vale on -->

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki vaaditut riippuvuudet, konfiguraatiotiedostot ja valmiiksi tehdyn asettelun, joten voit aloittaa sen perusteella rakentamisen heti.

Kun luot sovelluksen [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metatiedot (Sovelluksen nimi, Ryhmä ID, Artefakti ID)  
- webforJ versio ja Java versio
- Teeman väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon perusteella startforJ luo perusprojektin valitsemastasi archetypestä, joka on konfiguroitu Spring Bootia varten. Voit valita, lataatko projektisi ZIP-tiedostona vai julkaisetko sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat mieluummin käyttää komentoriviä, luo Spring Boot webforJ -projekti suoraan käyttämällä virallisia webforJ arkkityyppejä:

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

`flavor`-parametri ohjaa arkkityyppiä luomaan Spring Boot -projektin sen sijaan, että se luo tavallisen webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, johon kuuluu:
- Spring Boot -vanhempi POM -konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pääsovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfiguraatiotiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokan muuntamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osio, jos luot uutta projektia alusta alkaen.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

1. Muuta pakkaustyyppi WAR:sta JAR:ksi:
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

3. Poista kaikki WAR-spesifiset konfiguraatiot, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-aiheiset konfiguraatiot

Jos sinulla on jo vanhempi POM, sinun on tuontava Spring Bootin Bill of Materials (BOM) sen sijaan:

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

:::tip[webforJ DevTools automaattiseen selainpäivitykseen]
`webforj-spring-devtools`-riippuvuus laajentaa Spring DevToolsia automaattisella selainpäivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain lataa automaattisesti ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -opas konfiguraatiotietoja varten.
:::

### Vaihe 3: Päivitä build-lisäosat {#step-3-update-build-plugins}

Korvaa Jetty-lisäosa Spring Boot Maven -lisäosalla. Poista kaikki olemassa oleva Jetty-konfiguraatio ja lisää:

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

Muuta pääsovellusluokkasi `App` Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-annotaatiot ja päämetodi:

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
    
    // Pidä olemassa oleva run() -metodisi, jos sellainen on
    @Override
    public void run() throws WebforjException {
      // Olemassa oleva alustuskoodisi 
    }
}
```

`@SpringBootApplication`-annotaatio mahdollistaa Springin automaattiset konfiguroinnit ja komponenttiskannauksen. `@Routify`-annotaatio pysyy ennallaan ja jatkaa näkymäpakettiesi reittien skannaamista.

### Vaihe 5: Lisää Spring-konfiguraatio {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Täysin pätevä luokan nimi sovelluksen sisäänkäynnille
webforj.entry = org.example.Application

# Sovelluksen nimi
spring.application.name=Hello World Spring

# Palvelin konfiguraatio
server.port=8080
server.shutdown=immediate

# webforJ DevTools -konfiguraatio
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Suorita Spring Boot -sovellus {#run-the-spring-boot-app}

Kun se on konfiguroitu, suorita sovelluksesi käyttämällä:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy upotetulla Tomcat-palvelimella portissa 8080 oletusarvoisesti. Olemassa olevat webforJ-näkymät ja reitit toimivat tarkalleen kuten ennen, mutta nyt voit injektoida Spring-komponentteja ja käyttää Spring-ominaisuuksia.

## Konfigurointi

Käytä `application.properties` tiedostoa `src/main/resources` konfiguroidaksesi sovelluksesi. 
 Katso [Property Configuration](/docs/configuration/properties) saadaksesi tietoja webforJ-konfiguraatioparametreista.

Seuraavat webforJ `application.properties` asetukset ovat erityisiä Spring-kehykselle:

| Ominaisuus | Tyyppi | Kuvaus | Oletus |
|----------|------|-------------|--------|
| **`webforj.servletMapping`** | Merkkijono | URL-kartoituskuvio webforJ-sovellukselle. | `/*` |
| **`webforj.excludeUrls`** | Lista | URL-kuviot, joita ei pitäisi käsitellä webforJ:llä juurella. Kun webforJ on kartoitettu juurikontekstiin (`/*`), nämä URL-kuviot jätetään webforJ-käsittelyn ulkopuolelle ja ne voi käsitellä Spring MVC -ohjaimet sen sijaan. Tämä sallii REST-päätepisteiden ja muiden Spring MVC -kartoitusten rinnakkaisen toiminnan webforJ-reittien kanssa. | `[]` |

### Konfiguraation erot {#configuration-differences}

Kun vaihdat Spring Bootiin, useat konfigurointiasiat muuttuvat:

| Ominaisuus | Standard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettavan JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorita komento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfiguraatio** | `webforj.conf` vain | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit muodossa `application-{profile}.properties` |
| **Porttikonnfiguraatio** | Lisäosakonfiguraatiossa | `server.port` ominaisuuksissa |
