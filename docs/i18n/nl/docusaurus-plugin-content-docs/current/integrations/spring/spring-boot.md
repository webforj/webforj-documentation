---
title: Spring Boot
sidebar_position: 10
_i18n_hash: a482c057615b165ea1c9ff9270f34def
---
Spring Boot is een populaire keuze voor het bouwen van Java-applicaties, en biedt afhankelijkheidsinjectie, automatische configuratie en een ingebouwd servermodel. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere Spring-beheerde beans rechtstreeks injecteren in je UI-componenten via constructorinjectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een ingebouwde Tomcat-server in plaats van een WAR-bestand naar een externe app-server te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties om een nieuwe webforJ-app met Spring Boot te maken: gebruik de grafische startforJ-tool of de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: Gebruik startforJ {#option-1-using-startforj}
<!-- vale on -->

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), dat een minimale startproject genereert op basis van een gekozen webforJ-archetype. Dit startproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er meteen op kunt beginnen bouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je het aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App Naam, Groep ID, Artifact ID)  
- webforJ-versie en Java-versie
- Thema kleur en pictogram
- Archetype
- **Smaak** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject maken op basis van jouw gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

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

De parameter `flavor` geeft aan dat het archetype een Spring Boot-project moet genereren in plaats van een standaard webforJ-project.

Dit creëert een volledig Spring Boot-project met:
- Spring Boot ouder POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie te wijzigen. Dit proces houdt in dat je je Maven-configuratie bijwerkt, Spring-afhankelijkheden toevoegt en je hoofdapp-klasse converteert.

:::info[Voor bestaande projecten alleen]
Sla deze sectie over als je een nieuw project vanaf nul maakt.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Maak de volgende wijzigingen in je POM-bestand:

1. Wijzig de verpakking van WAR naar JAR:
   ```xml title="pom.xml"
   <packaging>jar</packaging>
   ```

2. Stel Spring Boot in als de ouder POM:
   ```xml title="pom.xml"
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Verwijder alle WAR-specifieke configuratie, zoals:
   - `maven-war-plugin`
   - Verwijzingen naar de `webapp`-directory
   - Configuratie met betrekking tot `web.xml`

Als je al een ouder POM hebt, moet je in plaats daarvan de Bill of Materials (BOM) van Spring Boot importeren:

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

Voeg de webforJ Spring Boot starter toe aan je afhankelijkheden. Houd je bestaande webforJ-afhankelijkheid:

```xml title="pom.xml"
<dependencies>
    <!-- Jouw bestaande webforJ-afhankelijkheid -->
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

### Stap 4: Converteer je app-klasse {#step-4-convert-your-app-class}

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
    
    // Houd je bestaande run() methode als je er een hebt
    @Override
    public void run() throws WebforjException {
      // Je bestaande initialisatiecode 
    }
}
```

De annotatie `@SpringBootApplication` schakelt de automatische configuratie en componentscan van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je weergavepakketten scannen voor routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Volledig gekwalificeerde klassenaam van het applicatie-invoerpunt
webforj.entry = org.example.Application

# App Naam
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

Zodra je app is geconfigureerd, voer deze uit met:

```bash
mvn spring-boot:run
```

De app start met een ingebouwde Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring-beans injecteren en gebruikmaken van Spring-functies.

## Configuratie

Gebruik het `application.properties`-bestand in `src/main/resources` om je app te configureren. 
Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-eigenschappen.

De volgende webforJ `application.properties` instellingen zijn specifiek voor het Spring-framework:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servletMapping`** | String | URL-mappingpatroon voor de webforJ-servlet. | `/*` |
| **`webforj.excludeUrls`** | Lijst | URL-patronen die niet door webforJ moeten worden behandeld wanneer deze zijn gekoppeld aan de root. Wanneer webforJ is gekoppeld aan de rootcontext (`/*`), worden deze URL-patronen uitgesloten van webforJ-behandeling en kunnen ze in plaats daarvan door Spring MVC-controllers worden behandeld. Dit staat REST-eindpunten en andere Spring MVC-mapping toe om samen te bestaan met webforJ-routes. | `[]` |

### Configuratiewe-verschillen {#configuration-differences}

Wanneer je overstapt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebouwde Tomcat |
| **Opdracht voor uitvoeren** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
