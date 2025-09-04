---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Leeg archetype

De `blank` archetype is een fundamenteel beginproject voor webforJ-toepassingen. Deze sjabloon biedt een schone lei waarop je je app vanaf nul kunt bouwen. Het is ideaal voor ontwikkelaars die volledige controle willen over de structuur en componenten van hun app zonder enige vooraf gedefinieerde beperkingen.

:::tip Gebruik maken van startforJ
Voor meer controle over aanpassing en configuratie kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te maken - selecteer gewoon de `Blank` archetype wanneer je configuratieopties kiest.
:::

## Gebruik van de `blank` archetype {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## De app draaien {#running-the-app}

Voordat je je app draait, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan.
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ-app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
