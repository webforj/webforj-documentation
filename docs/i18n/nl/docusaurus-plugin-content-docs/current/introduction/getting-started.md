---
title: Getting Started
sidebar_position: 2
_i18n_hash: 32a742a43fe6dd9e983eaf428e04e06d
---
Dit artikel beschrijft de stappen om een nieuwe webforJ-app te maken met behulp van webforJ [archetypes](../building-ui/archetypes/overview.md). Archetypes bieden vooraf geconfigureerde projectstructuren en startercode, zodat je snel een project kunt opzetten en draaien.  
Om een nieuwe webforJ-app vanuit een archetype te maken, kun je [startforJ](#using-startforj) of de [opdrachtregel](#using-the-command-line) gebruiken.

:::tip Vereisten  
Voordat je begint, bekijk de nodige [vereisten](./prerequisites) voor het opzetten en gebruiken van webforJ.  
:::


## Gebruik maken van startforJ {#using-startforj}

De eenvoudigste manier om een nieuwe webforJ-app te maken, is met [startforJ](https://docs.webforj.com/startforj), dat een minimaal starterproject genereert op basis van een gekozen webforJ-archetype. Dit starterproject bevat alle vereiste afhankelijkheden, configuratiebestanden en een vooraf gemaakte lay-out, zodat je er meteen aan kunt beginnen.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Aanpassen met startforJ {#customizing-with-startforj}

Wanneer je een app maakt met [startforJ](https://docs.webforj.com/startforj), kun je deze aanpassen door de volgende informatie te verstrekken:

- Basisprojectmetadata (App-naam, Groep-ID, Artefact-ID)  
- webforJ-versie en Java-versie  
- Thema kleur en pictogram  
- Archetype  
- Smaak  

Er zijn twee smaakopties om uit te kiezen, waarbij "webforJ Only" de standaard is:
  - **webforJ Only**: Standaard webforJ-app  
  - **webforJ + Spring Boot**: webforJ-app met ondersteuning voor Spring Boot  

:::caution Ondersteuning voor Spring Boot  
De Spring Boot-smaak is alleen beschikbaar in webforJ versie 25.02 en hoger. Als je deze optie kiest, zorg er dan voor dat je een compatibele versie selecteert.  
:::

:::tip Beschikbare Archetypes  
webforJ wordt geleverd met verschillende vooraf gedefinieerde archetypes om je te helpen snel aan de slag te gaan. Voor een complete lijst van beschikbare archetypes, zie de [archetypes catalogus](../building-ui/archetypes/overview).  
:::

Met deze informatie zal startforJ een basisproject maken vanuit je gekozen archetype met je gekozen aanpassingen.  
Je kunt ervoor kiezen om je project als een ZIP-bestand te downloaden of het direct naar GitHub te publiceren.

Zodra je je project hebt gedownload, open je de projectmap in je IDE en ga je verder met [de app uitvoeren](#running-the-app).

## Gebruik maken van de opdrachtregel {#using-the-command-line}

Als je de voorkeur geeft aan de opdrachtregel, kun je een project rechtstreeks genereren met behulp van het Maven-archetype:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## De app uitvoeren {#running-the-app}

Voordat je je app uitvoert, installeer je de [vereisten](./prerequisites.md) als je dit nog niet hebt gedaan.  
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforj-app
mvn jetty:run

# voor webforj + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.

:::info Licentie en watermerk  
Voor informatie over het watermerk dat aanwezig is in ongecertificeerde projecten, zie [Licentie en Watermerk](../configuration/licensing-and-watermark).  
:::
