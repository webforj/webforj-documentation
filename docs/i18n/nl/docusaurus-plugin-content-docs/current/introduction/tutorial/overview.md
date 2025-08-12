---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
Deze tutorial is ontworpen om je stap voor stap door het proces van het maken van de app te leiden. Deze app, ontworpen om klantinformatie te beheren, demonstreert hoe je webforJ kunt gebruiken om een functionele en gebruiksvriendelijke interface te bouwen met functies voor het bekijken, toevoegen en bewerken van klantgegevens. Elke sectie bouwt voort op de vorige, maar voel je vrij om verder te springen waar nodig.

Elke stap in de tutorial resulteert in een programma dat compileert naar een WAR-bestand, dat op elke Java-webappserver kan worden ingezet. Voor deze tutorial zal de Maven Jetty-plugin worden gebruikt om de app lokaal te implementeren. Deze lichte opzet zorgt ervoor dat de app snel kan draaien en dat wijzigingen in realtime zichtbaar zijn tijdens de ontwikkeling.

## Functies van de tutorial-app {#tutorial-app-features}

 - Werken met gegevens in een tabel.
 - Gebruik van de [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) en assetbeheer.
 - [Routing](../../routing/overview) en [navigatie](../../routing/route-navigation)
 - [Gegevensbinding](../../data-binding/overview) en [validatie](../../data-binding/validation/overview)

## Vereisten {#prerequisites}

Om het meeste uit deze tutorial te halen, wordt aangenomen dat je een basisbegrip van Java-programmering hebt en vertrouwd bent met tools zoals Maven. Als je nieuw bent met webforJ, maak je geen zorgen - de basisprincipes van het framework worden onderweg behandeld.

De volgende tools/hulpmiddelen dienen aanwezig te zijn op je ontwikkelmachine

<!-- vale off -->
- Java 17 of hoger
- Maven
- Een Java IDE
- Een webbrowser
- Git (aanbevolen maar niet verplicht)
<!-- vale on -->

:::tip webforJ Vereisten
Bekijk [dit artikel](../prerequisites) voor een meer gedetailleerd overzicht van de vereiste tools.
:::

## Secties {#sections}

De tutorial is verdeeld in de volgende secties. Volg het in volgorde voor een uitgebreide walkthrough, of sla over voor specifieke informatie.

:::tip Projectopzet
Voor degenen die verder willen springen naar specifieke onderwerpen, wordt aanbevolen om eerst de sectie Projectopzet te lezen voordat je verdergaat.
:::

<DocCardList className="topics-section" />
