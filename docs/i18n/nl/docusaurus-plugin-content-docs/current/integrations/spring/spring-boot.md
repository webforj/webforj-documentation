---
title: Spring Boot
sidebar_position: 10
description: >-
  Generate a Spring Boot webforJ project with startforJ or Maven archetypes, or
  convert an existing WAR project to an embedded Tomcat JAR.
_i18n_hash: 98535c8b91fd041be0dcb32840ef9302
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps en biedt dependency injection, automatische configuratie en een model met een ingebouwde server. Wanneer je Spring Boot met webforJ gebruikt, kun je services, repositories en andere door Spring beheerde beans rechtstreeks in je UI-componenten injecteren via constructorinjectie.

Wanneer je Spring Boot met webforJ gebruikt, draait je app als een uitvoerbare JAR met een ingebouwde Tomcat-server, in plaats van als WAR-bestand te worden geïmplementeerd op een externe appserver. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routering van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Een Spring Boot-app maken {#create-a-spring-boot-app}

Je hebt twee opties om een nieuwe webforJ-app met Spring Boot te maken: met de grafische tool startforJ of via de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: startforJ gebruiken {#option-1-using-startforj}
<!-- vale on -->

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj). Deze tool genereert een minimaal startproject op basis van een gekozen webforJ-archetype. Dit startproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een kant-en-klare lay-out, zodat je er meteen op kunt voortbouwen.

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende gegevens op te geven:

- Basisgegevens van het project (App Name, Group ID, Artifact ID)
- webforJ-versie en Java-versie
- Themakleur en pictogram
- Archetype
- **Runs on** - Selecteer **Spring Boot** om een Spring Boot-project te maken

Op basis van deze gegevens maakt startforJ een basisproject op basis van het gekozen archetype, geconfigureerd voor Spring Boot.
Je kunt ervoor kiezen om je project als ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: De opdrachtregel gebruiken {#option-2-using-the-command-line}

Als je liever de opdrachtregel gebruikt, genereer dan rechtstreeks een Spring Boot-webforJ-project met de officiële webforJ-archetypes:

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

De parameter `flavor` geeft het archetype de opdracht om een Spring Boot-project te genereren in plaats van een standaard webforJ-project.

Dit maakt een volledig Spring Boot-project aan met:
- Spring Boot-parent-POM-configuratie
- De webforJ Spring Boot-starterafhankelijkheid
- Een hoofdappklasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## De Spring Boot-app uitvoeren {#run-the-spring-boot-app}

Een archetypeproject stelt zijn standaard Maven-doel in. Daarom compileert `mvn` zonder argumenten de app, start het de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) en voert het de app uit:

```bash
mvn
```

De app start standaard met een ingebouwde Tomcat-server op poort 8080. Je bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar je kunt nu Spring-beans injecteren en Spring-functies gebruiken.

## Configuratie {#configuration}

Gebruik het bestand `application.properties` in `src/main/resources` om je app te configureren.
Zie [Eigenschappen configureren](/docs/configuration/properties) voor informatie over configuratie-eigenschappen van webforJ.

De volgende webforJ-instellingen in `application.properties` zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|----------|------|-------------|--------|
| **`webforj.servlet-mapping`** | String | URL-toewijzingspatroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | List | URL-patronen die niet door webforJ moeten worden afgehandeld wanneer webforJ aan de root is toegewezen. Wanneer webforJ aan de rootcontext (`/*`) is toegewezen, worden deze URL-patronen uitgesloten van afhandeling door webforJ en kunnen ze in plaats daarvan door Spring MVC-controllers worden afgehandeld. Hierdoor kunnen REST-eindpunten en andere Spring MVC-toewijzingen naast webforJ-routes bestaan. | `[]` |

### Verschillen in configuratie {#configuration-differences}

Wanneer je overstapt op Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot-webforJ |
|--------|-----------------|-------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Ingebouwde Tomcat |
| **Uitvoeropdracht** | `mvn` | `mvn` |
| **Hoofdconfiguratie** | Alleen `webforj.conf` | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profile}.properties` |
| **Poortconfiguratie** | In de pluginconfiguratie | `server.port` in properties |

:::info Uitvoeren met `mvn`, niet met `mvn spring-boot:run`
Met `mvn spring-boot:run` wordt alleen de app gestart. De opdracht start de [frontend watch](/docs/configuration/deploy-reload/frontend-watch) niet, waardoor bronnen onder `src/main/frontend` (als je project die bevat) niet worden gecompileerd of opnieuw opgebouwd terwijl de app draait. Met `mvn` wordt het standaarddoel van het project uitgevoerd. Dit start de watch en daarna de app.
:::
