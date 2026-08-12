---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 8664ccf60a8cd3a84330aabbc75c3a3b
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, die afhankelijkheidsinjectie, automatische configuratie en een ingebouwd servermodel biedt. Wanneer je Spring Boot met webforJ gebruikt, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructor-injectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een ingebouwde Tomcat-server in plaats van een WAR-bestand naar een externe app-server te implementeren. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Create a Spring Boot app {#create-a-spring-boot-app}

Je hebt twee opties om een nieuwe webforJ-app met Spring Boot te maken: de grafische startforJ-tool gebruiken of de Maven-opdrachtregel.

<!-- vale off -->
### Option 1: Using startforJ {#option-1-using-startforj}
<!-- vale on -->

De eenvoudigste manier om een nieuwe webforJ-app te maken, is [startforJ](https://docs.webforj.com/startforj), dat een minimale starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er direct op kunt bouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App Naam, Groep ID, Artifact ID)
- webforJ-versie en Java-versie
- Thema kleur en pictogram
- Archetype
- **Flavor** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject creëren van het gekozen archetype dat is geconfigureerd voor Spring Boot. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Option 2: Using the command line {#option-2-using-the-command-line}

Als je de voorkeur geeft aan het gebruik van de opdrachtregel, genereer je een Spring Boot webforJ-project rechtstreeks met behulp van de officiële webforJ-archetypes:

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
- Spring Boot-ouder POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofdapp klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Run the Spring Boot app {#run-the-spring-boot-app}

Een archetype-project stelt zijn standaard Maven-doel in, zodat `mvn` zonder argumenten de app compileert, de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) start en de app uitvoert:

```bash
mvn
```

De app start met een ingebouwde Tomcat-server op poort 8080 als standaard. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kun je Spring beans injecteren en Spring-functies gebruiken.

## Configuration {#configuration}

Gebruik het `application.properties`-bestand in `src/main/resources` om je app te configureren. Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-eigenschappen.

De volgende webforJ `application.properties`-instellingen zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard |
|------------|------|--------------|-----------|
| **`webforj.servlet-mapping`** | String | URL-mappingpatroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden behandeld wanneer ze aan de root zijn gekoppeld. Wanneer webforJ aan de rootcontext (`/*`) is gekoppeld, worden deze URL-patronen uitgesloten van de behandeling door webforJ en kunnen ze door Spring MVC-controllers in plaats daarvan worden behandeld. Dit stelt REST-eindpunten en andere Spring MVC-mappings in staat om samen met webforJ-routes te bestaan. | `[]` |

### Configuration differences {#configuration-differences}

Wanneer je overschakelt naar Spring Boot, verandert een aantal configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebouwde Tomcat |
| **Uitvoeringsopdracht** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `application.properties` + `webforj.conf` |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
