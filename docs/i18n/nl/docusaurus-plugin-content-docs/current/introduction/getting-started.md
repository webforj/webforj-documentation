---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
Dit artikel beschrijft de stappen om een nieuwe webforJ-app te maken met behulp van webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes bieden vooraf geconfigureerde projectstructuren en startercode, zodat je snel een project kunt opzetten en aan de slag kunt.

Om een nieuwe webforJ-app vanuit een archetype te maken, kun je [startforJ](#using-startforj) of de [opdrachtregel](#using-the-command-line) gebruiken.

:::tip Vereisten
Voordat je begint, bekijk de noodzakelijke [vereisten](./prerequisites) voor het instellen en gebruiken van webforJ.
:::


## Gebruik maken van startforJ {#using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj), dat een minimaal starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je meteen kunt beginnen met bouwen.

### Aanpassen met startforJ {#customizing-with-startforj}

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App Naam, Groep ID, Artifact ID)
- webforJ-versie en Java-versie
- Thema Kleur en Pictogram
- Archetype
- Smaak

Er zijn twee smaakopties om uit te kiezen, waarbij "webforJ Only" de standaard is:
  - **webforJ Only**: Standaard webforJ-app
  - **webforJ + Spring Boot**: webforJ-app met ondersteuning voor Spring Boot

:::tip Beschikbare Archetypes
webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).
:::

Met deze informatie zal startforJ een basisproject creëren op basis van jouw gekozen archetype met jouw gekozen aanpassingen. Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks op GitHub te publiceren.

Zodra je je project hebt gedownload, open je de projectmap in je IDE.

## Gebruik maken van de opdrachtregel {#using-the-command-line}


Als je de voorkeur geeft aan de opdrachtregel, kun je een project rechtstreeks genereren met behulp van het Maven-archetype:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
