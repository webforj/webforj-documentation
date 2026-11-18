---
title: Project instellen
sidebar_position: 1
description: >-
  Haal het webforJ-tutorialproject op via de broncode, startforJ of de
  opdrachtregel, ontdek hoe het is opgebouwd en geconfigureerd en voer de app
  van elke stap uit.
_i18n_title_hash: 2876afc7fdfa9650513dfc9a2ffb3a93
_i18n_description_hash: 1312c307fa70e0fe0e9d0e304f1a9f9c
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
Om met deze tutorial te beginnen, heb je een locatie voor je project nodig waar je je klassen en resources kunt beheren. In de volgende secties worden de verschillende manieren beschreven waarop je je webforJ-project voor deze tutorial kunt maken.

## De broncode gebruiken {#using-source-code}

De eenvoudigste manier om deze tutorial te volgen, is door de broncode te raadplegen. Je kunt het hele project downloaden of klonen vanaf GitHub:

<!-- vale off -->
- ZIP downloaden: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub-repository: kloon het project [rechtstreeks vanaf GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### Projectstructuur {#project-structure}

Het project heeft zes submappen, één voor elke stap van de tutorial. Elke map bevat een uitvoerbare app. Door mee te doen, zie je hoe de app zich ontwikkelt van een eenvoudige basisconfiguratie tot een volledig functioneel klantbeheersysteem.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

<!-- vale off -->
## startforJ gebruiken {#using-startforj}
<!-- vale on -->

Als je liever een nieuw project maakt, kun je [startforJ](https://docs.webforj.com/startforj) gebruiken om een minimaal startproject te genereren. Zie [Aan de slag](/docs/introduction/getting-started) voor meer informatie over het gebruik van startforJ.

:::note Vereiste instellingen
- Kies in de vervolgkeuzelijst **webforJ version** de webforJ-versie **26.01 of hoger**.
- Kies onder **Runs on** voor **Spring Boot**.

## De opdrachtregel gebruiken {#using-command-line}

Je kunt ook een nieuw project genereren met de volgende opdracht:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Configuratie {#configurations}

De twee genoemde manieren om een nieuw project te maken, gebruiken webforJ-[archetypen](/docs/building-ui/archetypes/overview), die automatisch de benodigde configuraties aan je project toevoegen. Dit omvat Spring-[afhankelijkheden](/docs/integrations/spring/spring-boot), de webforJ Maven-plug-in die frontendbronnen bouwt en in de gaten houdt, en de volgende eigenschappen in `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## De app uitvoeren {#running-the-app}

Voer de volgende stappen uit om de app te bekijken terwijl je de tutorial doorloopt:

1. Ga naar de map voor de gewenste stap. Dit is de hoofdmap voor die stap, met daarin de `pom.xml`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

   De gegenereerde POM configureert deze standaardopdracht om de app te compileren, de frontend-watcher van webforJ te starten en Spring Boot uit te voeren.

<!-- vale Google.WordList = NO -->
Wanneer je de app uitvoert, wordt automatisch een nieuw browservenster geopend op `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
