---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: bd6e6de9bb8396f7926e01ac2f34cfc3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tabs archetype

Het `tabs` startproject genereert een app met een eenvoudige tabbladeninterface. Ideaal voor projecten die meerdere weergaven of secties vereisen die via tabbladen toegankelijk zijn, biedt dit archetype een schone en georganiseerde manier om verschillende delen van je app te beheren, waardoor het gemakkelijk is om tussen verschillende secties te navigeren zonder de gebruikersinterface te rommelen.

:::tip Gebruik startforJ
Voor meer controle over aanpassing en configuratie kun je [startforJ](https://docs.webforj.com/startforj/) gebruiken om je project te maken - selecteer gewoon het `Tabs` archetype bij het kiezen van configuratieopties.
:::

## Het `tabs` archetype gebruiken {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## De app uitvoeren {#running-the-app}

Voordat je je app uitvoert, installeer de [vereisten](../../introduction/prerequisites) als je dat nog niet hebt gedaan.
Navigeer vervolgens naar de hoofdmap van het project en voer de volgende opdracht uit:

```bash
# voor standaard webforJ-app
mvn jetty:run

# voor webforJ + Spring Boot
mvn spring-boot:run
```

Zodra de server draait, open je je browser en ga je naar [http://localhost:8080](http://localhost:8080) om de app te bekijken.
