---
title: Spring Boot
sidebar_position: 10
_i18n_hash: ea7e45ae4100f45754621a1e3b9d2980
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps en biedt afhankelijkheidsinjectie, auto-configuratie en een embedded servermodel. Wanneer je Spring Boot met webforJ gebruikt, kun je diensten, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een embedded Tomcat-server in plaats van het implementeren van een WAR-bestand naar een externe app-server. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken naast de applicatiecontext van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties om een nieuwe webforJ-app met Spring Boot te maken: met de grafische startforJ-tool of de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: Gebruik startforJ {#option-1-using-startforj}
<!-- vale on -->

De eenvoudigste manier om een nieuwe webforJ-app te maken is via [startforJ](https://docs.webforj.com/startforj), die een minimaal startproject genereert op basis van een gekozen webforJ-archetype. Dit startproject omvat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er direct mee aan de slag kunt.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App-naam, Groep-ID, Artifact-ID)  
- webforJ-versie en Java-versie
- Thema kleur en pictogram
- Archetype
- **Flavor** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject creëren vanuit je gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer dan een Spring Boot webforJ-project rechtstreeks met behulp van de officiële webforJ-archetypes:

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

De `flavor`-parameter geeft aan dat de archetype een Spring Boot-project moet genereren in plaats van een standaard webforJ-project.

Dit creëert een compleet Spring Boot-project met:
- Spring Boot-parent POM-configuratie
- webforJ Spring Boot-starter afhankelijkheid
- Hoofdapp-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie aan te passen. Dit proces omvat het bijwerken van je Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het omzetten van je hoofdapp-klasse.

:::info[Alleen voor bestaande projecten]
Sla deze sectie over als je een nieuw project vanaf nul maakt. Deze gids gaat uit van **webforJ-versie 25.11 of later**.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Maak de volgende wijzigingen in je POM-bestand:

1. Verander de packaging van WAR naar JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Stel Spring Boot in als de parent POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Verwijder alle WAR-specifieke configuratie zoals:
   - `maven-war-plugin`
   - `webapp`-directoryreferenties
   - `web.xml` gerelateerde configuratie

Als je al een parent POM hebt, moet je de Spring Boot Bill of Materials (BOM) in plaats daarvan importeren:

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

### Stap 2: Voeg Spring-afhankelijkheden toe {#step-2-add-spring-dependencies}

Voeg de webforJ Spring Boot-starter toe aan je afhankelijkheden:

:::info[webforJ 25.11+ vereenvoudiging]
Sinds **webforJ-versie 25.11** omvat de `webforj-spring-boot-starter` alle kern webforJ-afhankelijkheden transitief. Je hoeft de `com.webforj:webforj` afhankelijkheid niet meer expliciet toe te voegen.

Voor versies **voor 25.11** moet je beide afhankelijkheden afzonderlijk opnemen.
:::

**Voor webforJ 25.11 en later:**

```xml title="pom.xml"
<dependencies>
  <!-- Voeg de Spring Boot-starter toe (inclusief webforJ transitief) -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Voeg devtools toe -->
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

**Voor versies vóór 25.11:**

```xml title="pom.xml"
<dependencies>
  <!-- Voeg expliciet de webforJ-afhankelijkheid toe -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
  </dependency>
    
  <!-- Voeg de Spring Boot-starter toe -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-spring-boot-starter</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Voeg devtools toe -->
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

:::tip[webforJ DevTools voor automatische browserverversing]
De `webforj-spring-devtools` afhankelijkheid breidt Spring DevTools uit met automatische browserverversing. Wanneer je wijzigingen opslaat in je IDE, wordt de browser automatisch herladen zonder handmatige tussenkomst. Zie de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) gids voor configuratiedetails.
:::

### Stap 3: Update build-plugins {#step-3-update-build-plugins}

Vervang de Jetty-plugin door de Spring Boot Maven-plugin. Verwijder alle bestaande Jetty-configuratie en voeg toe:

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

### Stap 4: Zet je app-klasse om {#step-4-convert-your-app-class}

Transformeer je hoofd `App`-klasse in een Spring Boot-app door de nodige Spring-annotaties en een main-methode toe te voegen:

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
    
  // Behoud je bestaande run() methode als je er een hebt
  @Override
  public void run() throws WebforjException {
    // Je bestaande initialisatiecode 
  }
}
```

De `@SpringBootApplication` annotatie activeert de auto-configuratie en component scanning van Spring. De `@Routify` annotatie blijft hetzelfde en blijft je view-pakketten scannen voor routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Volledig gekwalificeerde classnaam van het applicatie-invoerpunt
webforj.entry = org.example.Application

# App naam
spring.application.name=Hallo wereld Spring

# Serverconfiguratie
server.port=8080
server.shutdown=immediate

# webforJ DevTools-configuratie
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Eenmaal geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start met een embedded Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en gebruikmaken van Spring-functies.

## Configuratie

Gebruik het `application.properties`-bestand in `src/main/resources` om je app te configureren. Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ configuratie-eigenschappen.

De volgende webforJ `application.properties`-instellingen zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|---------------|----------|
| **`webforj.servlet-mapping`** | String | URL-mappingpatroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden afgehandeld wanneer ze naar de root zijn gemapped. Wanneer webforJ naar de rootcontext is gemapped (`/*`), zullen deze URL-patronen worden uitgesloten van de afhandeling door webforJ en kunnen in plaats daarvan door Spring MVC-controllers worden afgehandeld. Dit maakt het mogelijk om REST-eindpunten en andere Spring MVC-mappings naast webforJ-routes te laten bestaan. | `[]` |

### Configuratieverschillen {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|--------------------|----------------------|
| **Packaging** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Embedded Tomcat |
| **Uitvoeringsopdracht** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | `webforj.conf` alleen | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in properties |
