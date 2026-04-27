---
title: Spring Boot
sidebar_position: 10
_i18n_hash: 116777dbeb2e6707e2ef867f0dd6d78c
---
Spring Boot is een populaire keuze voor het bouwen van Java-apps, en biedt dependency injection, auto-configuratie en een embedded servermodel. Bij het gebruik van Spring Boot met webforJ, kunt u services, repositories en andere door Spring beheerde beans direct in uw UI-componenten injecteren via constructorinjectie.

Wanneer u Spring Boot gebruikt met webforJ, draait uw app als een uitvoerbare JAR met een embedded Tomcat-server in plaats van een WAR-bestand naar een externe app-server te deployen. Dit verpakkingsmodel vereenvoudigt de implementatie en sluit aan bij cloud-native implementatiepraktijken. Het componentmodel en de routing van webforJ werken samen met de app-context van Spring voor het beheren van afhankelijkheden en configuratie.

## Maak een Spring Boot-app {#create-a-spring-boot-app}

U heeft twee opties om een nieuwe webforJ-app met Spring Boot te maken: de grafische startforJ-tool gebruiken of de Maven-opdrachtregel.

### Optie 1: Gebruik startforJ {#option-1-using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj), die een minimaal starter-project genereert op basis van een gekozen webforJ-archetype. Dit starter-project bevat alle vereiste afhankelijkheden, configuratiebestanden en een voorgeconfigureerde lay-out, zodat u er meteen mee aan de slag kunt.

Wanneer u een app maakt met [startforJ](https://docs.webforj.com/startforj), kunt u het aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App Naam, Groep ID, Artefact ID)  
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- **Smaak** - Selecteer **webforJ Spring** om een Spring Boot-project te maken

Met deze informatie zal startforJ een basisproject creëren op basis van uw gekozen archetype dat is geconfigureerd voor Spring Boot.
U kunt ervoor kiezen om uw project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

### Optie 2: Gebruik de opdrachtregel {#option-2-using-the-command-line}

Als u de voorkeur geeft aan de opdrachtregel, genereert u rechtstreeks een Spring Boot webforJ-project met de officiële webforJ-archetypes:

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
- Spring Boot parent POM-configuratie
- webforJ Spring Boot starter afhankelijkheid
- Hoofd app-klasse met `@SpringBootApplication` en `@Routify`
- Voorbeeldweergaven
- Configuratiebestanden voor zowel Spring als webforJ

## Voeg Spring Boot toe aan bestaande projecten {#add-spring-boot-to-existing-projects}

Als u een bestaande webforJ-app heeft, kunt u Spring Boot toevoegen door uw projectconfiguratie te wijzigen. Dit proces omvat het bijwerken van uw Maven-configuratie, het toevoegen van Spring-afhankelijkheden en het converteren van uw hoofdsapp-klasse.

:::info[Voor bestaande projecten alleen]
Sla deze sectie over als u een nieuw project vanaf nul aanmaakt. Deze gids gaat uit van **webforJ versie 25.11 of later**.
:::

### Stap 1: Update Maven-configuratie {#step-1-update-maven-configuration}

Maak de volgende wijzigingen in uw POM-bestand:

1. Wijzig de verpakking van WAR naar JAR:
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
   - `web.xml` gerelateerde configuratie

Als u al een parent POM heeft, moet u in plaats daarvan de Spring Boot Bill of Materials (BOM) importeren:

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

Voeg de webforJ Spring Boot starter toe aan uw afhankelijkheden:

:::info[webforJ 25.11+ vereenvoudiging]
Vanaf **webforJ versie 25.11** omvat de `webforj-spring-boot-starter` alle kern webforJ-afhankelijkheden transitief. U hoeft de `com.webforj:webforj` afhankelijkheid niet langer expliciet toe te voegen.

Voor versies **voor 25.11** moet u beide afhankelijkheden apart opnemen.
:::

**Voor webforJ 25.11 en later:**

```xml title="pom.xml"
<dependencies>
  <!-- Voeg Spring Boot starter toe (omvat webforJ transitief) -->
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

**Voor versies voor 25.11:**

```xml title="pom.xml"
<dependencies>
  <!-- Voeg expliciet webforJ afhankelijkheid toe -->
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
De afhankelijkheid `webforj-spring-devtools` breidt Spring DevTools uit met automatische browservernieuwing. Wanneer u wijzigingen in uw IDE opslaat, wordt de browser automatisch opnieuw geladen zonder handmatige tussenkomst. Zie de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools) gids voor configuratiedetails.
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

### Stap 4: Converteer uw app-klasse {#step-4-convert-your-app-class}

Transformeer uw hoofd `App`-klasse in een Spring Boot-app door de noodzakelijke Spring-annotaties en een main-methode toe te voegen:

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
    
  // Behoud uw bestaande run() methode als u die heeft
  @Override
  public void run() throws WebforjException {
    // Uw bestaande initiatiecode 
  }
}
```

De annotatie `@SpringBootApplication` maakt de auto-configuratie en component scanning van Spring mogelijk. De annotatie `@Routify` blijft hetzelfde en blijft uw weergavepakketten scannen naar routes.

### Stap 5: Voeg Spring-configuratie toe {#step-5-add-spring-configuration}

Maak `application.properties` aan in `src/main/resources`:

```Ini title="application.properties"
# Volledige gekwalificeerde classnaam van het ingangspunt van de applicatie
webforj.entry = org.example.Application

# App Naam
spring.application.name=Hello World Spring

# Server configuratie
server.port=8080
server.shutdown=immediate

# webforJ DevTools configuratie
spring.devtools.livereload.enabled=false
webforj.devtools.livereload.enabled=true
webforj.devtools.livereload.static-resources-enabled=true
```

## Voer de Spring Boot-app uit {#run-the-spring-boot-app}

Eenmaal geconfigureerd, voert u uw app uit met:

```bash
mvn spring-boot:run
```

De app start met een embedded Tomcat-server op poort 8080, standaard. Uw bestaande webforJ-weergaven en routes werken precies zoals voorheen, maar nu kunt u Spring beans injecteren en gebruikmaken van Spring-functies.

## Configuratie

Gebruik het `application.properties`-bestand in `src/main/resources` om uw app te configureren. 
 Zie [Property Configuration](/docs/configuration/properties) voor informatie over webforJ-configuratie-eigenschappen.

De volgende webforJ `application.properties` instellingen zijn specifiek voor Spring:

| Eigenschap | Type | Beschrijving | Standaard|
|------------|------|--------------|----------|
| **`webforj.servlet-mapping`** | String | URL-mapping patroon voor de webforJ-servlet. | `/*` |
| **`webforj.exclude-urls`** | Lijst | URL-patronen die niet door webforJ moeten worden afgehandeld wanneer ze naar de root zijn gemapt. Wanneer webforJ naar de rootcontext (`/*`) is gemapt, worden deze URL-patronen uitgesloten van webforJ-afhandeling en kunnen ze door Spring MVC-controllers worden afgehandeld. Dit maakt het mogelijk om REST-eindpunten en andere Spring MVC-mappings naast webforJ-routes te laten bestaan. | `[]` |

### Verschillen in configuratie {#configuration-differences}

Wanneer u overschakelt naar Spring Boot, veranderen verschillende configuratieaspecten:

| Aspect | Standaard webforJ | Spring Boot webforJ |
|--------|-------------------|---------------------|
| **Verpakking** | WAR-bestand | Uitvoerbare JAR |
| **Server** | Extern (Jetty, Tomcat) | Embedded Tomcat |
| **Uitvoeringsopdracht** | `mvn jetty:run` | `mvn spring-boot:run` |
| **Hoofdconfiguratie** | `webforj.conf` alleen | `application.properties` + `webforj.conf`  |
| **Profielen** | `webforj-dev.conf`, `webforj-prod.conf` | Spring-profielen met `application-{profiel}.properties` |
| **Poortconfiguratie** | In pluginconfiguratie | `server.port` in eigenschappen |
