---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 855d2d04609e239338e5a18ebe1b02c4
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, omdat het afhankelijkheidsinjectie, automatische configuratie en een embedded servermodel biedt. Wanneer je Spring Boot gebruikt met webforJ, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot gebruikt met webforJ, draait je app als een uitvoerbare JAR met een embedded Tomcat-server in plaats van een WAR-bestand te implementeren op een externe app-server. Dit verpakkingsmodel vereenvoudigt de implementatie en komt overeen met cloud-native implementatiepraktijken. Het componentmodel en routingsysteem van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

Je hebt twee opties voor het maken van een nieuwe webforJ-app met Spring Boot: het gebruik van de grafische startforJ-tool of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is [startforJ](https://docs.webforj.com/startforj), die een minimaal startersproject genereert op basis van een gekozen webforJ-archetype. Dit startersproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakt indeling, zodat je er direct op kunt bouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App Naam, Groep ID, Artifact ID)
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- **Draait op** - Selecteer **Spring Boot** om een Spring Boot-project te maken

Met deze informatie creëert startforJ een basisproject van je gekozen archetype, geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

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

Dit creëert een compleet Spring Boot-project met:
- Spring Boot hoofd POM-configuratie
- webforJ Spring Boot starterafhankelijkheid
- Hoofdapp-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Een archetypeproject stelt zijn standaard Maven-doel in, zodat `mvn` zonder argumenten de app compileert, de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) start en de app uitvoert:

```bash
mvn
```

De app start standaard met een embedded Tomcat-server op poort 8080. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring-beans injecteren en gebruik maken van Spring-functies.

## Configuratie {#configuration}

Gebruik het bestand `application.properties` in `src/main/resources` om je app te configureren. Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratieparameters.

De volgende webforJ `application.properties` instellingen zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-mappingpatroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden behandeld wanneer ze aan de root zijn gemapt. Wanneer webforJ aan de rootcontext (`/*`) is gemapt, worden deze URL-patronen uitgesloten van behandeling door webforJ en kunnen ze in plaats daarvan door Spring MVC-controllers worden behandeld. Dit maakt het mogelijk voor REST-eindpunten en andere Spring MVC-mapping om samen te bestaan met webforJ-routes. | `[]` |

### Configuratieverschillen {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Embedded Tomcat |
| **Uitvoercommando** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
