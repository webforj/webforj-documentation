---
title: Aan de Slag
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
Dit artikel beschrijft de stappen om een nieuwe webforJ-app te creëren met behulp van webforJ [archetypen](../building-ui/archetypes/overview.md). Archetypen bieden voorgeconfigureerde projectstructuren en startcode, zodat je snel een project kunt opzetten en draaien.  
Om een nieuwe webforJ-app vanuit een archetype te maken, kun je [startforJ](#using-startforj) of de [opdrachtregel](#using-the-command-line) gebruiken.

:::tip Vereisten  
Voordat je begint, bekijk de noodzakelijke [vereisten](./prerequisites) voor het opzetten en gebruiken van webforJ.  
:::

## Gebruikmaken van startforJ {#using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken is met [startforJ](https://docs.webforj.com/startforj), dat een minimaal startproject genereert op basis van een gekozen webforJ-archetype. Dit startproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakt lay-out, zodat je er meteen mee aan de slag kunt.

<div class="videos-container">  
  <video controls>  
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />  
  </video>  
</div>

### Aanpassen met startforJ {#customizing-with-startforj}

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App-naam, Groep-ID, Artifact-ID)  
- webforJ-versie en Java-versie  
- Thema kleur en icoon  
- Archetype  
- Smaak  

Er zijn twee smaakopties om uit te kiezen, waarbij "webforJ Only" de standaard is:  
  - **webforJ Only**: Standaard webforJ-app  
  - **webforJ + Spring Boot**: webforJ-app met ondersteuning voor Spring Boot  

:::tip Beschikbare Archetypen  
webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypen om je snel op weg te helpen. Voor een complete lijst van beschikbare archetypen, zie de [archetypencatalogus](../building-ui/archetypes/overview).  
:::

Met deze informatie zal startforJ een basisproject maken vanuit je gekozen archetype met je gekozen aanpassingen.  
Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het rechtstreeks naar GitHub te publiceren.

Zodra je je project hebt gedownload, open je de projectmap in je IDE en ga je verder met [de app uitvoeren](#running-the-app).

## Gebruikmaken van de opdrachtregel {#using-the-command-line}

Als je de voorkeur geeft aan het gebruik van de opdrachtregel, kun je een project rechtstreeks genereren met behulp van het Maven-archetype:

<ComponentArchetype  
project="hello-world"  
flavor="webforj"  
/>
