---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
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

Voor projecten die een gestructureerd navigatiesysteem nodig hebben, is het `sidemenu` archetype een geweldige startplaats. Dit archetype bevat een zijmenu en een inhoudsgebied, en is ontworpen om je te helpen apps te maken met een duidelijke en intuïtieve navigatiestructuur, waardoor het voor gebruikers gemakkelijker wordt om verschillende delen van je app te vinden en te benaderen.

:::tip Gebruik startforJ
Voor meer controle over aanpassing en configuratie, kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te maken - selecteer gewoon het `SideMenu` archetype bij het kiezen van configuratieopties.
:::

## Gebruik van het `sidemenu` archetype {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## De app draaien {#running-the-app}

Voordat je je app draait, installeer je de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan. 
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
