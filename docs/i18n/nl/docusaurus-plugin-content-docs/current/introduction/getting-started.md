---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5a051bf7c5a9494b21ba5df3629c35b4
---
Dit artikel beschrijft de stappen om een nieuwe webforJ-app te maken met behulp van webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes bieden vooraf geconfigureerde projectstructuren en startcode, zodat je snel een project kunt opzetten en uitvoeren. 
Om een nieuwe webforJ-app vanuit een archetype te maken, kun je [startforJ](#using-startforj) of de [opdrachtregel](#using-the-command-line) gebruiken.

:::tip Voorwaarden
Voordat je begint, bekijk je de noodzakelijke [voorwaarden](./prerequisites) voor het opzetten en gebruiken van webforJ.
:::


## Gebruik van startforJ {#using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj), dat een minimaal startproject genereert op basis van een gekozen webforJ-archetype. Dit startproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er meteen mee aan de slag kunt.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Aanpassen met startforJ {#customizing-with-startforj}

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basis projectmetadata (App-naam, Groep-ID, Artifact-ID)  
- webforJ-versie en Java-versie
- Thema kleur en pictogram
- Archetype
- Smaak

Er zijn twee smaakopties om uit te kiezen, waarbij "webforJ Only" de standaard is:
  - **webforJ Only**: Standaard webforJ-app
  - **webforJ + Spring Boot**: webforJ-app met ondersteuning voor Spring Boot

:::tip Beschikbare Archetypes
webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).
:::

Met deze informatie zal startforJ een basisproject creëren op basis van jouw gekozen archetype met jouw gekozen aanpassingen. 
Je kunt kiezen om je project als een ZIP-bestand te downloaden of het direct naar GitHub te publiceren.

Zodra je je project hebt gedownload, open je de projectmap in je IDE.

## Gebruik van de opdrachtregel {#using-the-command-line}


Als je de voorkeur geeft aan het gebruik van de opdrachtregel, kun je een project direct genereren met behulp van de Maven-archetype:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
