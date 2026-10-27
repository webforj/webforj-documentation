---
title: Aan de slag
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
Deze gids maakt een webforJ-app die draait met BBjServices, draait op jouw lokale BBj terwijl je ontwikkelt, en implementeert deze met BBjShip.

:::tip Vereisten
Je hebt een Maven-project en een BBj-installatie van dezelfde versie als webforJ nodig, bijvoorbeeld BBj 26.03 voor webforJ 26.03, met BBjServices draaiende. BBjShip ondersteunt geen Gradle. Bekijk de [vereisten](/docs/introduction/prerequisites) voor de rest van de tools.
:::

## Maak een BBj-app {#create-a-bbj-app}

Je hebt twee opties om een nieuwe webforJ-app te maken die draait met BBjServices: met de grafische tool startforJ of de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: met startforJ {#option-1-using-startforj}
<!-- vale on -->

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App Naam, Groep ID, Artifact ID)
- webforJ versie en Java versie
- Themakleur en pictogram
- Archetype
- **Draait op** - Selecteer **BBjServices** om een project te creëren dat draait met BBjServices

### Optie 2: met de opdrachtregel {#option-2-using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, genereer dan het project met de officiële webforJ-archetypes:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

Met de parameter `flavor` genereert het archetype een project dat draait met BBjServices.

Dit creëert een project met:
- De `webforj` afhankelijkheid zonder de webforJ-engine, die door BBjServices wordt geleverd
- De webforJ Maven-plugin, ingesteld om te implementeren naar BBjShip op jouw lokale BBj onder de artifact ID als de app naam
- HotswapAgent voor ontwikkelingsruns
- Voorbeeldweergaven
- webforJ-configuratiebestanden voor ontwikkeling en productie

## Voer de app uit {#run-the-app}

Een archetype-project stelt zijn standaard Maven-doel in, dus `mvn` zonder argumenten compileert de app en start een [ontwikkelingsrun](/docs/configuration/deploy-reload/bbjservices) in jouw lokale BBjServices:

```bash
mvn
```

De eerste run bereidt jouw lokale BBj voor. Het installeert BBjShip en voegt HotswapAgent toe aan BBjServices, die BBjServices eenmaal herstart. De build drukt vervolgens het adres van jouw app af:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press Ctrl+C to stop the app and the watch
```

Klassen die je compileert bereiken de draaiende app zonder een herstart. Stop de build met `Ctrl+C` om de app te stoppen.

## Implementeer de app {#deploy-the-app}

Om de verpakte app te implementeren, voer je uit:

```bash
mvn install
```

De app wordt vervolgens gepubliceerd op `http://localhost:8888/webapp/my-app` totdat je deze opnieuw implementeert of verwijdert. Zie [Implementeren](/docs/configuration/bbjship/deploying) om naar een andere BBj-server te implementeren, bijvoorbeeld vanuit een CI-pipeline.
