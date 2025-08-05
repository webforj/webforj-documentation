---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: e1da494f783aca68616cd374b92e700c
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

Dit archetype creëert een eenvoudige hello world-app om de basisprincipes van het bouwen van een UI met webforJ te demonstreren. Deze sjabloon is geweldig voor beginners om snel aan de slag te gaan. Het biedt een rechttoe rechtaan voorbeeld van hoe je een basis webforJ-app kunt opzetten en uitvoeren, wat het een uitstekende startpunt maakt voor nieuwe ontwikkelaars.

:::tip Beginnen vanaf nul
Dit archetype creëert een minimalistische app met een paar componenten en wat styling. Voor ontwikkelaars die een project met minimale scaffolding willen creëren, zie het [`blank` archetype](./blank).
:::

:::tip Gebruik van startforJ
Voor meer controle over aanpassing en configuratie kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te creëren - selecteer gewoon het `HelloWorld` archetype bij het kiezen van configuratieopties.
:::

## Using the `hello-world` archetype {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Running the app {#running-the-app}

Voordat je je app uitvoert, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet gedaan hebt. 
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ-app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je browser en ga naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
