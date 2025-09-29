---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 46d6f92d638a214c8b5a0a8632ef0150
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, biedt afhankelijkheidsinjectie, auto-configuratie en een embedded servermodel. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot gebruikt met webforJ, draait je app als een uitvoerbare JAR met een embedded Tomcat-server in plaats van een WAR-bestand naar een externe app-server te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routing van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: het gebruik van de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), dat een minimale startproject genereert op basis van een gekozen webforJ-archetype. Dit startproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er meteen aan kunt beginnen bouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App Naam, Groep ID, Artifact ID)  
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- **Smaak** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject creëren op basis van je gekozen archetype, geconfigureerd voor Spring Boot.
Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer je een Spring Boot webforJ-project rechtstreeks met de officiële webforJ-archetypes:

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

De parameter `flavor` vertelt het archetype om een Spring Boot-project te genereren in plaats van een standaard webforJ-project.

Dit creëert een compleet Spring Boot-project met:
- Spring Boot ouder POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofdapp-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie te wijzigen. Dit proces houdt in dat je je Maven-configuratie bijwerkt, Spring-afhankelijkheden toevoegt en je hoofdapp-klasse converteert.

:::info[Voor bestaande projecten alleen]
Sla deze sectie over als je een nieuw project vanaf nul maakt.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Breng de volgende wijzigingen aan in je POM-bestand:

1. Wijzig de packaging van WAR naar JAR:
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
   - `webapp` directory-referenties
   - `web.xml` gerelateerde configuratie

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

Voeg de webforJ Spring Boot starter toe aan je afhankelijkheden. Houd je bestaande webforJ-afhankelijkheid:

```xml title="pom.xml"
<dependencies>
    <!-- Je bestaande webforJ-afhankelijkheid -->
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

:::tip[webforJ DevTools voor automatische herlaad van de browser]
De `webforj-spring-devtools` afhankelijkheid breidt Spring DevTools uit met automatische herlaad van de browser. Wanneer je wijzigingen opslaat in je IDE, herlaadt de browser automatisch zonder handmatige tussenkomst. Zie de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) gids voor configuratiedetails.
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

Transformeer je hoofd `App`-klasse in een Spring Boot-app door de nodige Spring-anotaties en een hoofdmethoden toe te voegen:

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
    
    // Behoud je bestaande run() methode als je die hebt
    @Override
    public void run() throws WebforjException {
      // Je bestaande initialisatiecode 
    }
}
```

De annotatie `@SpringBootApplication` stelt de auto-configuratie en component-scanning van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je view-pakketten scannen voor routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# Volledig gekwalificeerde klasse naam van de toepassing ingangspunt
webforj.entry = org.example.Application

# App Naam
spring.application.name=Hallo Wereld Spring

# Server configuratie
server.port=8080
server.shutdown=immediate

# webforJ DevTools configuratie
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Zodra je het hebt geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start met een embedded Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en -routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en Spring-functies gebruiken.

## Configuratie

Gebruik het `application.properties`-bestand in `src/main/resources` om je app te configureren. 
Bekijk [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-eigenschappen.

De volgende webforJ `application.properties`-instellingen zijn specifiek voor het Spring-framework:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servletMapping`** | String | URL-mapping patroon voor de webforJ-servlet. | `/*` |
| **`webforj.excludeUrls`** | Lijst | URL-patronen die niet door webforJ moeten worden afgehandeld wanneer ze aan de root zijn gekoppeld. Wanneer webforJ is gekoppeld aan de rootcontext (`/*`), zullen deze URL-patronen worden uitgesloten van de verwerking door webforJ en kunnen worden afgehandeld door Spring MVC-controllers. Dit maakt het mogelijk dat REST-eindpunten en andere Spring MVC-mappingen coëxisteren met webforJ-routes. | `[]` |

### Configuratieverschillen {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-------------------|---------------------|
| **Packaging** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Embedded Tomcat |
| **Uitvoeringscommando** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | `webforj.conf` alleen | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
