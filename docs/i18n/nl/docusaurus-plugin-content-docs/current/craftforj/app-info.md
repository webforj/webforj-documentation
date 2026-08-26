---
title: App-info
sidebar_position: 10
description: >-
  Read the versions, Java runtime, and project root of the app craftforJ is
  attached to.
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
App-info rapporten wat je app daadwerkelijk draait, wat niet altijd overeenkomt met wat je `pom.xml` zegt dat het zou moeten draaien. Naast de versies van webforJ en BBj Services, behandelt het de Java-runtime, het besturingssysteem, en waar de app op de schijf is geworteld.

![Het App Info-tabblad](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Twee van deze waarden beïnvloeden hoe craftforJ zich gedraagt:

- **De projectroot** is waar craftforJ zoekt naar je bronnen. [Schrijven naar de bron](/docs/craftforj/source-changes) kan niet werken wanneer dit verkeerd is, dus stel [`project-root`](/docs/craftforj/configuration#project-root) in als de gerapporteerde waarde niet overeenkomt met jouw project.
- **De Java-runtime** bepaalt hoe grondig de [Java-wijzigingen](/docs/craftforj/ai#it-writes-java) van de assistent worden gevalideerd, omdat volledige validatie een compiler nodig heeft.

:::tip Een probleem indienen
Neem alles op deze pagina op, samen met een log gedownload van de probleemoplossingsinstellingen van craftforJ.
:::
