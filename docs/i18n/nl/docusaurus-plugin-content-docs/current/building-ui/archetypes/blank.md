---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Leeg archetype

De `blank` archetype is een fundamenteel startproject voor webforJ-toepassingen. Deze sjabloon biedt een schone lei om je app vanaf nul te bouwen. Het is ideaal voor ontwikkelaars die volledige controle willen over de structuur en componenten van hun app zonder vooraf gedefinieerde beperkingen.

:::tip Gebruik maken van startforJ
Voor meer controle over aanpassing en configuratie, kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te creëren - kies gewoon de `Blank` archetype bij het selecteren van configuratie-opties.
:::

## Het gebruik van de `blank` archetype {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## De app uitvoeren {#running-the-app}

Voordat je je app uitvoert, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan.
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je browser en ga naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
