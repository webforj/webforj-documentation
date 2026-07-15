---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 4512bc42001e5f96301c60758cb0ca81
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, en biedt afhankelijkheidsinjectie, automatische configuratie en een ingebed servermodel. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot gebruikt met webforJ, draait je app als een uitvoerbare JAR met een ingebedde Tomcat-server in plaats van een WAR-bestand naar een externe applicatieserver te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de appcontext van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: het gebruik van de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), waarmee een minimaal startersproject wordt gegenereerd op basis van een gekozen webforJ-archetype. Dit startersproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je direct verder kunt bouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je het aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App-naam, Groep ID, Artifact ID)
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- **Flavor** - Selecteer **webforJ Spring** om een Spring Boot-project te creëren

Met deze informatie zal startforJ een basisproject creëren uit jouw gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer dan een Spring Boot webforJ-project rechtstreeks met de officiële webforJ-archetypes:

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

De parameter `flavor` geeft aan dat het archetype een Spring Boot-project moet genereren in plaats van een standaard webforJ-project.

Dit creëert een volledig Spring Boot-project met:
- Spring Boot parent POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie te wijzigen. Dit proces omvat het bijwerken van je Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het converteren van je hoofdapp-klasse.

:::info[Alleen voor bestaande projecten]
Sla dit gedeelte over als je een nieuw project vanaf nul maakt. Deze gids gaat ervan uit dat **webforJ versie 25.11 of later** is.
:::

### Stap 1: Werk de Maven-configuratie bij {#step-1-update-maven-configuration}

Breng de volgende wijzigingen aan in je POM-bestand:

1. Verander de verpakking van WAR naar JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Stel Spring Boot in als de parent POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>4.0.5</version>
       <relativePath/>
   </parent>
   ```

3. Verwijder alle WAR-specifieke configuratie zoals:
   - `maven-war-plugin`
   - Verwijzingen naar de `webapp`-directory
   - Configuratie met betrekking tot `web.xml`

Als je al een parent POM hebt, moet je in plaats daarvan de Spring Boot Bill of Materials (BOM) importeren:

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

### Stap 2: Voeg Spring-afhankelijkheden toe {#step-2-add-spring-dependencies}

Voeg de webforJ Spring Boot starter toe aan je afhankelijkheden:

:::info[webforJ 25.11+ vereenvoudiging]
Vanaf **webforJ versie 25.11** omvat de `webforj-spring-boot-starter` alle kern webforJ-afhankelijkheden transitief. Je hoeft de `com.webforj:webforj`-afhankelijkheid niet langer expliciet toe te voegen.

Voor versies **vóór 25.11** moet je beide afhankelijkheden afzonderlijk opnemen.
:::

**Voor webforJ 25.11 en later:**

```xml title="pom.xml"
<dependencies>
  <!-- Voeg Spring Boot starter toe (bevat webforJ transitief) -->
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
  <!-- Expliciet webforJ-afhankelijkheid toevoegen -->
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
  </dependency>

  <!-- Voeg Spring Boot starter toe -->
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
De afhankelijkheid `webforj-spring-devtools` breidt Spring DevTools uit met automatische browserverversing. Wanneer je wijzigingen opslaat in je IDE, wordt de browser automatisch opnieuw geladen zonder handmatige tussenkomst. Zie de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) gids voor configuratiedetails.
:::

### Stap 3: Update build-plugins {#step-3-update-build-plugins}

Vervang de Jetty-plugin door de Spring Boot Maven-plugin. Verwijder alle bestaande Jetty-configuraties en voeg toe:

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

### Stap 4: Converteer je app-klasse {#step-4-convert-your-app-class}

Transformeer je hoofdklasse `App` in een Spring Boot-app door de noodzakelijke Spring-annotaties en een hoofdmethode toe te voegen:

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

  // Houd je bestaande run() methode als je die hebt
  @Override
  public void run() throws WebforjException {
    // Je bestaande initialisatiecode
  }
}
```

De annotatie `@SpringBootApplication` ingeschakeld automatische configuratie van Spring en component-scanning. De annotatie `@Routify` blijft hetzelfde en blijft je weergavepakketten scannen naar routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` aan in `src/main/resources`:

```Ini title="application.properties"
# Volledig gekwalificeerde klassenaam van het toepassingsinstappunt
webforj.entry = org.example.Application

# App-naam
spring.application.name=Hallo Wereld Spring

# Serverconfiguratie
server.port=8080
server.shutdown=immediate

# webforJ DevTools-configuratie
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Eenmaal geconfigureerd, start je je app met:

```bash
mvn spring-boot:run
```

De app start met een ingebedde Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring-beans injecteren en Spring-functies gebruiken.

## Configuratie {#configuration}

Gebruik het `application.properties`-bestand in `src/main/resources` om je app te configureren. Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-eigenschappen.

De volgende webforJ `application.properties`-instellingen zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-mapping patroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden afgehandeld wanneer deze aan de root zijn gekoppeld. Wanneer webforJ aan de rootcontext is gekoppeld (`/*`), worden deze URL-patronen uitgesloten van de afhandeling door webforJ en kan dit in plaats daarvan door Spring MVC-controllers worden afgehandeld. Dit stelt REST-eindpunten en andere Spring MVC-koppelingen in staat om samen te bestaan met webforJ-routes. | `[]` |

### Configuratiedifferentiëlen {#configuration-differences}

Wanneer je overstapt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebedde Tomcat |
| **Uitvoeringscommando** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | `webforj.conf` alleen | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
