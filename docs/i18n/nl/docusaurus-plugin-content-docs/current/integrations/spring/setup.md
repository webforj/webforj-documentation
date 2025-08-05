---
title: Spring Boot Setup
sidebar_position: 10
_i18n_hash: c988b8fe49245d60c0e5f18c830595e3
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, met afhankelijkheidsinjectie, auto-configuratie en een embedded server model. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans direct in je UI-componenten injecteren via constructor injectie.

Wanneer je Spring Boot gebruikt met webforJ, draait je app als een uitvoerbare JAR met een embedded Tomcat-server in plaats van een WAR-bestand te implementeren op een externe app-server. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routing van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: het grafische startforJ-hulpmiddel of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), dat een minimalistisch starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte indeling, zodat je er direct mee kunt beginnen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je het aanpassen door de volgende informatie op te geven:

- Basis projectmetadata (App Naam, Groep ID, Artifact ID)  
- webforJ-versie en Java-versie
- Themakleur en pictogram
- Archetype
- **Smaak** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie genereert startforJ een basisproject gebaseerd op jouw gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het direct naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer dan rechtstreeks een Spring Boot webforJ-project met de officiële webforJ-archetypes:

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
- Spring Boot-parent POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie aan te passen. Dit proces omvat het bijwerken van je Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het converteren van je hoofdapp-klasse.

:::info[Alleen voor bestaande projecten]
Sla deze sectie over als je een nieuw project vanaf nul maakt.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Maak de volgende wijzigingen in je POM-bestand:

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

3. Verwijder eventuele WAR-specifieke configuratie zoals:
   - `maven-war-plugin`
   - Verwijzingen naar de `webapp`-directory
   - Configuratie met betrekking tot `web.xml`

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

### Stap 2: Voeg Spring afhankelijkheden toe {#step-2-add-spring-dependencies}

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

:::tip[webforJ DevTools voor automatische browservernieuwing]
De afhankelijkheid `webforj-spring-devtools` breidt Spring DevTools uit met automatische browservernieuwing. Wanneer je wijzigingen opslaat in je IDE, vernieuwt de browser automatisch zonder handmatige tussenkomst. Zie de [Spring DevTools](spring-devtools) gids voor configuratiegegevens.
:::

### Stap 3: Update build-plugins {#step-3-update-build-plugins}

Verander de Jetty-plugin met de Spring Boot Maven-plugin. Verwijder eventuele bestaande Jetty-configuratie en voeg toe:

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

Transformeer je hoofdklasse `App` in een Spring Boot-app door de nodige Spring-annotaties en een main-methode toe te voegen:

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
      // Jouw bestaande initialisatiecode 
    }
}
```

De annotatie `@SpringBootApplication` stelt de auto-configuratie en component scanning van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je view-pakketten scannen voor routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` in `src/main/resources`:

```Ini title="application.properties"
# App Naam
spring.application.name=Hallo Wereld Spring

# Serverconfiguratie
server.port=8080
server.shutdown=immediate

# webforJ DevTools configuratie
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

Je bestaande `webforj.conf`-bestand blijft werken. Wijs het naar je hoofdklasse:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Eenmaal geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start standaard met een embedded Tomcat-server op poort 8080. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en gebruikmaken van Spring-functies.

## Configuratiediversen {#configuration-differences}

Wanneer je overstapt naar Spring Boot, veranderen verschillende configuraspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Embedded Tomcat |
| **Uitvoeringsopdracht** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfig** | Alleen `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfig** | In plugin-configuratie | `server.port` in properties |
