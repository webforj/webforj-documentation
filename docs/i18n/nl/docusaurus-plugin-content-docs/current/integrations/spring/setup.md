---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, die afhankelijkheidsinjectie, automatische configuratie en een ingebouwd servermodel biedt. Wanneer je Spring Boot gebruikt met webforJ, kun je diensten, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot gebruikt met webforJ, draait je app als een uitvoerbare JAR met een ingebouwde Tomcat-server in plaats van een WAR-bestand naar een externe app-server te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en komt overeen met cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de applicatiecontext van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: met de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), die een minimale starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakt lay-out, zodat je er meteen mee aan de slag kunt.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App Naam, Groep ID, Artefact ID)  
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- **Smaak** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject maken van je gekozen archetype dat is geconfigureerd voor Spring Boot. Je kunt ervoor kiezen je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer dan een Spring Boot webforJ-project rechtstreeks met behulp van de officiële webforJ-archetypen:

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

De parameter `flavor` geeft aan dat de archetype een Spring Boot-project moet genereren in plaats van een standaard webforJ-project.

Dit creëert een compleet Spring Boot-project met:
- Spring Boot parent POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeld weergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie te wijzigen. Dit proces houdt in dat je je Maven-configuratie bijwerkt, Spring-afhankelijkheden toevoegt en je hoofd app-klasse converteert.

:::info[Alleen voor bestaande projecten]
Sla deze sectie over als je een nieuw project vanaf nul aanmaakt.
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
       <version>3.5.3</version>
       <relativePath/>
   </parent>
   ```

3. Verwijder alle WAR-specifieke configuratie zoals:
   - `maven-war-plugin`
   - Verwijzingen naar de `webapp`-directory
   - Configuratie gerelateerd aan `web.xml`

Als je al een parent POM hebt, moet je in plaats daarvan de Spring Boot Bill of Materials (BOM) importeren:

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
De afhankelijkheid `webforj-spring-devtools` breidt Spring DevTools uit met automatische browserverversing. Wanneer je wijzigingen opslaat in je IDE, wordt de browser automatisch herladen zonder handmatige tussenkomst. Zie de [Spring DevTools](spring-devtools) gids voor configuratiedetails.
:::

### Stap 3: Werk de build-plugins bij {#step-3-update-build-plugins}

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

Transformeer je hoofd `App`-klasse in een Spring Boot-app door de nodige Spring-annotaties en een hoofdmethode toe te voegen:

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
      // Jouw bestaande initialisatiecode 
    }
}
```

De annotatie `@SpringBootApplication` stelt de automatische configuratie en component scanning van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je weergavepakketten scannen op routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` aan in `src/main/resources`:

```Ini title="application.properties"
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

Je bestaande `webforj.conf`-bestand blijft werken. Wijs het aan je hoofdklasse:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Zodra alles is geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start met een ingebouwde Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en gebruik maken van Spring-functies.

## Configuratieverschillen {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, verandert verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebouwde Tomcat |
| **Uitvoeringscommando** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In plugin-configuratie | `server.port` in properties |
