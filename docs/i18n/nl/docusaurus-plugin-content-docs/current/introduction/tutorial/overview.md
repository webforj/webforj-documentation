---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
Dit tutorial is bedoeld om je stap voor stap door het proces van het maken van de app te begeleiden. Deze app, die is ontworpen om klantinformatie te beheren, demonstreert hoe je webforJ kunt gebruiken om een functionele en gebruiksvriendelijke interface te bouwen met functies voor het bekijken, toevoegen en bewerken van klantgegevens. Elke sectie bouwt voort op de vorige, maar voel je vrij om verder te springen indien nodig.

Elke stap in de tutorial resulteert in een programma dat compileert tot een WAR-bestand, dat kan worden gedeployed naar elke Java-webappserver. Voor deze tutorial zal de Maven Jetty-plugin worden gebruikt om de app lokaal te deployen. Deze lichte opzet zorgt ervoor dat de app snel kan draaien en dat wijzigingen in real-time zichtbaar zijn tijdens de ontwikkeling.

## Functies van de tutorial-app {#tutorial-app-features}

 - Werken met gegevens in een tabel.
 - Gebruik van de [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) en assetbeheer.
 - [Routing](../../routing/overview) en [navigatie](../../routing/route-navigation)
 - [Gegevensbindingen](../../data-binding/overview) en [validatie](../../data-binding/validation/overview)

## Vereisten {#prerequisites}

Om het maximale uit deze tutorial te halen, wordt verondersteld dat je een basisbegrip van Java-programmering hebt en vertrouwd bent met tools zoals Maven. Als je nieuw bent met webforJ, maak je geen zorgen - de basisprincipes van het framework worden onderweg behandeld.

De volgende tools/hulpmiddelen moeten aanwezig zijn op je ontwikkelmachine

<!-- vale off -->
- Java 17 of hoger
- Maven
- Een Java IDE
- Een webbrowser
- Git (aanbevolen maar niet vereist)
<!-- vale on -->

:::tip webforJ Vereisten
Zie [dit artikel](../prerequisites) voor een meer gedetailleerd overzicht van de vereiste tools.
:::

## Secties {#sections}

De tutorial is opgedeeld in de volgende secties. Volg ze sequentieel voor een uitgebreide walkthrough, of sla over voor specifieke informatie.

:::tip Projectsetup
Voor degenen die verder willen springen naar specifieke onderwerpen, is het aan te raden eerst de sectie Projectsetup te lezen voordat je verdergaat. 
:::

<DocCardList className="topics-section" />
