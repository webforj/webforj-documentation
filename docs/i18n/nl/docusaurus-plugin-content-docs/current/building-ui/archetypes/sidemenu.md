---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: c5fb775f5867b54eb53b0e1e63b90e20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu archetype
<!-- vale on -->

Voor projecten die een gestructureerd navigatiesysteem nodig hebben, is het `sidemenu` archetype een geweldige startplaats. Dit archetype bevat een zijmenu en een inhoudsgebied, en is ontworpen om je te helpen apps te creëren met een duidelijke en intuïtieve navigatiestructuur, waardoor het voor gebruikers gemakkelijker wordt om verschillende delen van je app te vinden en toegang te krijgen.

:::tip Gebruik startforJ
Voor meer controle over aanpassing en configuratie, kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te maken - selecteer gewoon het `SideMenu` archetype bij het kiezen van configuratie-opties.
:::

## Using the `sidemenu` archetype {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## Running the app {#running-the-app}

Voordat je je app uitvoert, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan. 
Navigeren naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ-app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
