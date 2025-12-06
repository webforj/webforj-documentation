---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 23d00e916452c8a8f037d2e666b2300c
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, die afhankelijkheidsinjectie, auto-configuratie en een embedded servermodel biedt. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een ingebedde Tomcat-server in plaats van een WAR-bestand naar een externe app-server te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en komt overeen met cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: gebruik de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), die een minimaal starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er direct op kunt beginnen met bouwen.

Wanneer je een app met [startforJ](https://docs.webforj.com/startforj) maakt, kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App-naam, Groep-ID, Artifact-ID)  
- webforJ-versie en Java-versie
- Thema kleur en pictogram
- Archetype
- **Flavor** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject creëren op basis van je gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het direct naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer een Spring Boot webforJ-project rechtstreeks met behulp van de officiële webforJ-archetypes:

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

Dit creëert een compleet Spring Boot-project met:
- Spring Boot-ouder POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd-app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie aan te passen. Dit proces omvat het bijwerken van je Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het omzetten van je hoofd-app-klasse.

:::info[Alleen voor bestaande projecten]
Sla deze sectie over als je een nieuw project vanaf nul maakt.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Breng de volgende wijzigingen aan in je POM-bestand:

1. Verander de verpakking van WAR naar JAR:
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
   - `webapp`-directoryverwijzingen
   - `web.xml`-gerelateerde configuratie

Als je al een ouder POM hebt, moet je in plaats daarvan de Spring Boot Bill of Materials (BOM) importeren:

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

Voeg de webforJ Spring Boot-starter toe aan je afhankelijkheden. Houd je bestaande webforJ-afhankelijkheid:

```xml title="pom.xml"
<dependencies>
    <!-- Je bestaande webforJ-afhankelijkheid -->
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
De afhankelijkheid `webforj-spring-devtools` breidt Spring DevTools uit met automatische browserverversing. Wanneer je wijzigingen opslaat in je IDE, ververs de browser automatisch zonder handmatige interventie. Zie de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) gids voor configuratiedetails.
:::

### Stap 3: Update build-plugins {#step-3-update-build-plugins}

Vervang de Jetty-plugin door de Spring Boot Maven-plugin. Verwijder bestaande Jetty-configuratie en voeg toe:

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

Transformeer je hoofd `App`-klasse in een Spring Boot-app door de benodigde Spring-annotaties en een main-methode toe te voegen:

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

De annotatie `@SpringBootApplication` schakelt de auto-configuratie en component-scanning van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je view-pakketten scannen voor routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` in `src/main/resources` aan:

```Ini title="application.properties"
# Volledig gekwalificeerde klasse naam van het toegangspunt van de applicatie
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

Zodra geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start met een ingebedde Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en -routes werken precies zoals voorheen, maar nu kun je Spring-beans injecteren en gebruikmaken van Spring-functies.

## Configuratie

Gebruik het bestand `application.properties` in `src/main/resources` om je app te configureren. Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-instellingen.

De volgende webforJ-instellingen in `application.properties` zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-mapping patroon voor de webforJ servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden behandeld wanneer ze naar de root zijn gemapped. Wanneer webforJ naar de rootcontext (`/*`) is gemapped, worden deze URL-patronen uitgesloten van de behandeling door webforJ en kunnen ze door Spring MVC-controllers worden behandeld. Dit stelt REST-eindpunten en andere Spring MVC-mappings in staat om naast webforJ-routes te bestaan. | `[]` |

### Configuratieverschillen {#configuration-differences}

Wanneer je overstapt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebedde Tomcat |
| **Uitvoeringscommando** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofconfiguratie** | Alleen `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in properties |
