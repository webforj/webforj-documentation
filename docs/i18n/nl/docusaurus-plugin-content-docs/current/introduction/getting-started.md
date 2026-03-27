---
title: Getting Started
sidebar_position: 2
_i18n_hash: e0271a7db26a5c4b3fdc29508119aade
---
Dit artikel beschrijft de stappen om een nieuwe webforJ-app te maken met behulp van webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes bieden vooraf geconfigureerde projectstructuren en startercode, zodat je snel een project kunt opzetten en aan de slag kunt.

Om een nieuwe webforJ-app vanuit een archetype te maken, kun je [startforJ](#using-startforj) of de [opdrachtregel](#using-the-command-line) gebruiken.

:::tip Vereisten
Voordat je begint, bekijk de noodzakelijke [vereisten](./prerequisites) voor het opzetten en gebruiken van webforJ.
:::

## Gebruik van startforJ {#using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj), waarmee een minimalistisch startersproject wordt gegenereerd op basis van een gekozen webforJ-archetype. Dit startersproject bevat alle noodzakelijke afhankelijkheden, configuratiebestanden en een vooraf gemaakte opmaak, zodat je meteen kunt beginnen met bouwen.

### Aanpassen met startforJ {#customizing-with-startforj}

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App-naam, Groep ID, Artifact ID)  
- webforJ-versie en Java-versie
- Themakleur en pictogram
- Archetype
- Smaak

Er zijn twee smaakopties om uit te kiezen, waarbij "webforJ Only" de standaard is:
  - **webforJ Only**: Standaard webforJ-app
  - **webforJ + Spring Boot**: webforJ-app met ondersteuning voor Spring Boot

:::tip Beschikbare Archetypes
webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).
:::

Met deze informatie zal startforJ een basisproject creëren vanuit je gekozen archetype met je gekozen aanpassingen.
Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

Zodra je je project hebt gedownload, open je de projectmap in je IDE.

## Gebruik van de opdrachtregel {#using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, kun je een project rechtstreeks genereren met behulp van de Maven-archetype:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
