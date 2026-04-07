---
title: Spring Boot
sidebar_position: 10
_i18n_hash: ea7e45ae4100f45754621a1e3b9d2980
---
Spring Boot on suosittu valinta Java-sovellusten rakentamiseen, joka tarjoaa riippuvuuksien injektoinnin, automaattisen konfiguroinnin ja upotetun palvelinmallin. Käyttämällä Spring Bootia webforJ:n kanssa voit injektoida palveluita, repositorioita ja muita Springin hallinnoimia komponentteja suoraan käyttöliittymäkomponentteihisi konstruktorin injektoinnin kautta.

Kun käytät Spring Bootia webforJ:n kanssa, sovelluksesi toimii suoritettavana JAR-tiedostona, jossa on upotettu Tomcat-palvelin sen sijaan, että pitäisi julkaista WAR-tiedosto ulkoiselle sovelluspalvelimelle. Tämä pakkausmalli yksinkertaistaa julkaisemista ja on linjassa pilviperusteisten käyttöönotto käytäntöjen kanssa. webforJ:n komponenttimalli ja reititys toimivat yhdessä Springin sovelluskontekstin kanssa riippuvuuksien ja konfiguraatioiden hallitsemiseksi.

## Luo Spring Boot -sovellus {#create-a-spring-boot-app}

Sinulla on kaksi vaihtoehtoa uuden webforJ-sovelluksen luomiseen Spring Bootin avulla: käyttää graafista startforJ-työkalua tai Maven-komentoriviä.

### Vaihtoehto 1: Käyttämällä startforJ:ta {#option-1-using-startforj}

Yksinkertaisin tapa luoda uusi webforJ-sovellus on [startforJ](https://docs.webforj.com/startforj), joka luo minimaalisen aloitusprojektin valitun webforJ-archetypen perusteella. Tämä aloitusprojekti sisältää kaikki tarvittavat riippuvuudet, konfiguraatiotiedostot ja valmiiksi tehdyn asettelun, joten voit aloittaa sen päällä heti.

Kun luot sovellusta [startforJ:n](https://docs.webforj.com/startforj) avulla, voit mukauttaa sitä antamalla seuraavat tiedot:

- Perusprojektin metadata (Sovelluksen nimi, Ryhmän ID, Artefaktin ID)  
- webforJ-versio ja Java-versio
- Teeman väri ja kuvake
- Archetype
- **Maku** - Valitse **webforJ Spring** luodaksesi Spring Boot -projektin

Tämän tiedon avulla startforJ luo perusprojektin valitsemastasi archetypesta, joka on konfiguroitu Spring Bootia varten. Voit valita ladata projektisi ZIP-tiedostona tai julkaista sen suoraan GitHubiin.

### Vaihtoehto 2: Käyttämällä komentoriviä {#option-2-using-the-command-line}

Jos haluat käyttää komentoriviä, luo Spring Boot -webforJ-projekti suoraan käyttäen virallisia webforJ-archetypeja:

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

`flavor`-parametri kertoo archeypelle, että se luo Spring Boot -projektin sen sijaan, että se luo tavallisen webforJ-projektin.

Tämä luo täydellisen Spring Boot -projektin, johon kuuluu:
- Spring Boot -vanhempi POM -konfiguraatio
- webforJ Spring Boot -aloitusriippuvuus
- Pään sovellusluokka, jossa on `@SpringBootApplication` ja `@Routify`
- Esimerkkinäkymät
- Konfiguraatiotiedostot sekä Springille että webforJ:lle

## Lisää Spring Boot olemassa oleviin projekteihin {#add-spring-boot-to-existing-projects}

Jos sinulla on olemassa oleva webforJ-sovellus, voit lisätä Spring Bootin muokkaamalla projektisi konfiguraatiota. Tämä prosessi sisältää Maven-konfiguraation päivittämisen, Spring-riippuvuuksien lisäämisen ja pääsovellusluokkasi muuttamisen.

:::info[Vain olemassa oleville projekteille]
Ohita tämä osio, jos luot uuden projektin tyhjältä pohjalta. Tämä opas olettaa **webforJ version 25.11 tai uudempi**.
:::

### Vaihe 1: Päivitä Maven-konfiguraatio {#step-1-update-maven-configuration}

Tee seuraavat muutokset POM-tiedostoon:

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

3. Poista kaikki WAR:iin liittyvä konfiguraatio, kuten:
   - `maven-war-plugin`
   - `webapp`-hakemiston viittaukset
   - `web.xml`-liittyvät konfiguraatiot

Jos sinulla on jo vanhempi POM, sinun on tuotava Spring Bootin materiaalivelka (BOM) sen sijaan:

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

Lisää webforJ Spring Boot -aloitusriippuvuus riippuvuuksiesi joukkoon:

:::info[webforJ 25.11+ yksinkertaistaminen]
Alkaen **webforJ version 25.11**, `webforj-spring-boot-starter` sisältää kaikki ydin webforJ -riippuvuudet välillisesti. Sinun ei tarvitse enää erikseen lisätä `com.webforj:webforj` -riippuvuutta.

Versioissa **ennen 25.11** sinun on sisällytettävä molemmat riippuvuudet erikseen.
:::

**Versioille webforJ 25.11 ja uudempi:**

```xml title="pom.xml"
<dependencies>
  <!-- Lisää Spring Boot -aloitus (sisältää webforJ:n välillisesti) -->
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
  <!-- Lisää erikseen webforJ-riippuvuus -->
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
`webforj-spring-devtools` -riippuvuus laajentaa Spring DevToolsia automaattisella selaimen päivityksellä. Kun tallennat muutoksia IDE:ssäsi, selain lataa automaattisesti uudelleen ilman manuaalista väliintuloa. Katso [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) -oppaita konfiguraatiosuosituksista.
:::

### Vaihe 3: Päivitä rakennusliitännäiset {#step-3-update-build-plugins}

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

### Vaihe 4: Muuta sovellusluokkasi {#step-4-convert-your-app-class}

Muunna pää `App` -luokkasi Spring Boot -sovellukseksi lisäämällä tarvittavat Spring-anotaatiot ja päämetodi:

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
    
  // Säilytä olemassa oleva run()-metodisi, jos sinulla on sellainen
  @Override
  public void run() throws WebforjException {
    // Olemassa oleva initialisointikoodi 
  }
}
```

`@SpringBootApplication` -anotaatiolla aktivoidaan Springin automaattinen konfigurointi ja komponenttisakannus. `@Routify` -anotaatiota käytetään edelleen samalla tavalla, ja se skannaa näkymäpakettisi reittejä varten.

### Vaihe 5: Lisää Spring-konfigurointi {#step-5-add-spring-configuration}

Luo `application.properties` tiedostoon `src/main/resources`:

```Ini title="application.properties"
# Täydellinen luokan nimi sovelluskäynnistysluokasta
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

Kun konfigurointi on valmis, suorita sovelluksesi käyttäen:

```bash
mvn spring-boot:run
```

Sovellus käynnistyy oletuksena upotetulla Tomcat-palvelimella portissa 8080. Olemassa olevat webforJ-näkymät ja reitit toimivat tarkalleen kuten ennenkin, mutta nyt voit injektoida Springin komponentteja ja käyttää Springin toimintoja.

## Konfigurointi

Käytä `application.properties` tiedostoa `src/main/resources` sovelluksesi konfiguroimiseen. 
Katso [Ominaisuuden konfigurointi](/docs/configuration/properties) tiedot webforJ:n konfigurointiasetuksista.

Seuraavat webforJ:n `application.properties` -asetukset ovat erityisiä Springille:

| Ominaisuus | Tyyppi | Kuvaus | Oletusarvo|
|------------|--------|--------|-----------|
| **`webforj.servlet-mapping`** | Merkkijono | URL-kartoitusmalli webforJ-servletille. | `/*` |
| **`webforj.exclude-urls`** | Lista | URL-mallit, joita ei pitäisi käsitellä webforJ:ssa, kun ne on kartoitettu juurelle. Kun webforJ on kartoitettu juurikontekstiin (`/*`), nämä URL-mallit jätetään pois webforJ:n käsittelystä ja ne voivat olla Spring MVC -ohjaimien käsiteltävissä. Tämä sallii REST-päätteet ja muut Spring MVC -kartoitukset olla rinnakkain webforJ-reittien kanssa. | `[]` |

### Konfiguraation erot {#configuration-differences}

Kun siirryt Spring Bootiin, useat konfiguraatioasiat muuttuvat:

| Ominaisuus | Tavanomainen webforJ | Spring Boot webforJ |
|------------|---------------------|----------------------|
| **Pakkaus** | WAR-tiedosto | Suoritettava JAR |
| **Palvelin** | Ulkoinen (Jetty, Tomcat) | Upotettu Tomcat |
| **Suorituskomento** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Pääkonfigurointi** | Vain `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profiilit** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profiilit `application-{profiili}.properties` |
| **Porttikonfigurointi** | Liitännäisen konfiguraatiossa | `server.port` ominaisuuksissa |
