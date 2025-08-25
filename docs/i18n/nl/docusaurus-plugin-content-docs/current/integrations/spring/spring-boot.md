---
title: Spring Boot
sidebar_position: 10
_i18n_hash: de620ee956248e4cea0e14dec25225a8
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, waarbij afhankelijkheidsinjectie, automatische configuratie en een ingebed serversysteem worden geboden. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans direct in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een ingebedde Tomcat-server in plaats van dat je een WAR-bestand naar een externe app-server deployt. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties om een nieuwe webforJ-app met Spring Boot te maken: het gebruik van de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), waarmee een minimale starterproject wordt gegenereerd op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er direct op kunt voortbouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App Naam, Groep ID, Artifact ID)  
- webforJ versie en Java versie
- Thema Kleur en Pictogram
- Archetype
- **Flavor** - Kies **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie creëert startforJ een basisproject van je gekozen archetype dat is geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het direct naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer je een Spring Boot webforJ-project direct met de officiële webforJ-archetypes:

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

De `flavor` parameter vertelt het archetype een Spring Boot-project te genereren in plaats van een standaard webforJ-project.

Dit maakt een volledig Spring Boot-project aan met:
- Spring Boot parent POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofdapp-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als je een bestaande webforJ-app hebt, kun je Spring Boot toevoegen door je projectconfiguratie te wijzigen. Dit proces omvat het bijwerken van je Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het converteren van je hoofdapp-klasse.

:::info[Alleen voor bestaande projecten]
Sla deze sectie over als je een nieuw project vanaf nul maakt.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Maak de volgende wijzigingen in je POM-bestand:

1. Wijzig de verpakking van WAR naar JAR:
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

3. Verwijder alle WAR-specifieke configuraties, zoals:
   - `maven-war-plugin`
   - Verwijzingen naar de `webapp`-directory
   - `web.xml` gerelateerde configuratie

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
    <!-- Je bestaande webforJ afhankelijkheid -->
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
De `webforj-spring-devtools` afhankelijkheid breidt Spring DevTools uit met automatische browservernieuwing. Wanneer je wijzigingen in je IDE opslaat, wordt de browser automatisch opnieuw geladen zonder handmatige tussenkomst. Zie de [Spring DevTools](spring-devtools) gids voor configuratiedetails.
:::

### Stap 3: Werk buildplugins bij {#step-3-update-build-plugins}

Vervang de Jetty-plugin door de Spring Boot Maven-plugin. Verwijder eventuele bestaande Jetty-configuraties en voeg toe:

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

Transformeer je hoofdklasse `App` in een Spring Boot-app door de nodige Spring-annotaties en een hoofdmethode toe te voegen:

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

De annotatie `@SpringBootApplication` schakelt de automatische configuratie en component scanning van Spring in. De annotatie `@Routify` blijft hetzelfde en blijft je kijkpakketten scannen op routes.

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

Je bestaande `webforj.conf`-bestand blijft werken. Wijzig het naar je hoofdklasse:

```Ini title="webforj.conf"
webforj.entry = org.example.Application
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Zodra je alles hebt geconfigureerd, voer je je app uit met:

```bash
mvn spring-boot:run
```

De app start met een ingebedde Tomcat-server op poort 8080 standaard. Je bestaande webforJ-weergaven en -routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en gebruik maken van Spring-functies.

## Configuratieverschillen {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-----------------|-------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebedde Tomcat |
| **Uitvoeringsopdracht** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `webforj.conf` + `application.properties` |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in properties |
