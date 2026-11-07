---
title: Aan de slag
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 84b3ad107540f450350f754730522e0e
---
Deze handleiding maakt een webforJ-app die met BBjServices wordt uitgevoerd, laat de app tijdens de ontwikkeling op je lokale BBj draaien en implementeert de app met BBjShip.

:::tip Vereisten
Je hebt een Maven-project nodig en een BBj-installatie met dezelfde versie als webforJ, bijvoorbeeld BBj 26.03 voor webforJ 26.03, met BBjServices actief. BBjShip ondersteunt Gradle niet. Bekijk de [vereisten](/docs/introduction/prerequisites) voor de overige tools.
:::

## Een BBj-app maken {#create-a-bbj-app}

Je kunt op twee manieren een nieuwe webforJ-app maken die met BBjServices wordt uitgevoerd: met de grafische tool startforJ of via de Maven-opdrachtregel.

<!-- vale off -->
### Optie 1: startforJ gebruiken {#option-1-using-startforj}
<!-- vale on -->

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende gegevens op te geven:

- Basisgegevens van het project (appnaam, groeps-ID, artefact-ID)
- webforJ-versie en Java-versie
- themakleur en pictogram
- Archetype
- **Wordt uitgevoerd op** - Selecteer **BBjServices** om een project te maken dat met BBjServices wordt uitgevoerd

### Optie 2: de opdrachtregel gebruiken {#option-2-using-the-command-line}

Als je liever de opdrachtregel gebruikt, genereer je het project met de officiële webforJ-archetypen:

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

Met de parameter `flavor` genereert het archetype een project dat met BBjServices wordt uitgevoerd.

Dit maakt een project met:
- De `webforj`-afhankelijkheid zonder de webforJ-engine, die door BBjServices wordt geleverd
- De webforJ Maven-plug-in, ingesteld om te implementeren naar BBjShip op je lokale BBj, met de artefact-ID als appnaam
- HotswapAgent voor ontwikkelruns
- Voorbeeldweergaven
- webforJ-configuratiebestanden voor ontwikkeling en productie

## De app uitvoeren {#run-the-app}

Een archetypeproject stelt het standaard Maven-doel in. Voer daarom `mvn` zonder argumenten uit om de app te compileren en een [ontwikkelrun](/docs/configuration/deploy-reload/bbjservices) te starten in je lokale BBjServices:

```bash
mvn
```

De eerste run maakt je lokale BBj klaar. BBjShip wordt geïnstalleerd en HotswapAgent wordt aan BBjServices toegevoegd, waarna BBjServices één keer opnieuw wordt gestart. De build toont vervolgens het adres van je app:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Klassen die je compileert, zijn beschikbaar in de draaiende app zonder dat deze opnieuw hoeft te worden gestart. Stop de build met <kbd>Ctrl</kbd>+<kbd>C</kbd> om de app te stoppen.

## De app implementeren {#deploy-the-app}

Voer het volgende uit om de verpakte app te implementeren:

```bash
mvn install
```

De app wordt vervolgens gepubliceerd op `http://localhost:8888/webapp/my-app` totdat je deze opnieuw implementeert of verwijdert. Zie [Implementeren](/docs/configuration/bbjship/deploying) voor het implementeren naar een andere BBj-server, bijvoorbeeld vanuit een CI-pipeline.
