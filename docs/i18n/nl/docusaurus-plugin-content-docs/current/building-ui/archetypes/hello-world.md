---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# HelloWorld archetype
<!-- vale on -->

Dit archetype creëert een eenvoudige hello world-app om de basis van het bouwen van een UI met webforJ te demonstreren. Deze sjabloon is geweldig voor beginners om snel aan de slag te gaan. Het biedt een duidelijk voorbeeld van hoe je een basis webforJ-app opzet en uitvoert, wat het een uitstekende startpunt maakt voor nieuwe ontwikkelaars.

:::tip Starten vanaf nul
Dit archetype creëert een minimalistische app met een paar componenten en enige opmaak. Voor ontwikkelaars die een project met minimale scaffolding willen creëren, zie het [`blank` archetype](./blank).
:::

:::tip Gebruik startforJ
Voor meer controle over aanpassing en configuratie kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te maken - kies gewoon het `HelloWorld` archetype bij het kiezen van configuratie-opties.
:::

## Gebruik van het `hello-world` archetype {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## De app uitvoeren {#running-the-app}

Voordat je je app uitvoert, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan. 
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor een standaard webforJ-app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
