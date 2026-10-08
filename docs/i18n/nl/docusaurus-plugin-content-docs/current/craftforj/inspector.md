---
title: Componenten inspecteren
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
De Inspector toont de componentenboom die jouw Java-code heeft opgebouwd. Een `Composite` verschijnt als de klasse die je hebt geschreven, met de kinderen die je eraan hebt gegeven in de volgorde waarin webforJ ze vasthoudt, zodat de structuur in craftforJ overeenkomt met de structuur in jouw bron.

![De componentenboom met een geselecteerde en gemarkeerde component in de draaiende app](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Een component selecteren {#selecting-a-component}

Om een component van de pagina te selecteren, druk je op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> en klik je erop. craftforJ selecteert de bijbehorende knoop in de boom. Als je met de muis over een knoop in de boom gaat, gebeurt het omgekeerde en wordt die component op de pagina gemarkeerd, zodat je tussen het scherm en de boom in beide richtingen kunt bewegen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

Om in de boom te zoeken, druk je op <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Een term omhullen met schuinen streepjes behandelt het als een reguliere expressie. Rechtsklikken op een knoop opent de beschikbare acties daarvoor. Je kunt de bron openen of het aan de [assistent](/docs/craftforj/ai) overhandigen.

## Eigenschappen lezen en wijzigen {#reading-and-changing-properties}

Een geselecteerde component vult de zijbalk met zijn eigenschappen, gegroepeerd op basis van wat ze beïnvloeden. Welke eigenschappen een component biedt, hangt af van de component en sommige daarvan zijn alleen-lezen. Eigenschappen die niet goed als platte tekst leesbaar zijn, krijgen in plaats daarvan een editor die geschikt is voor hun waarde. Het wijzigen van een waarde heeft onmiddellijk effect in de draaiende app.

:::info Live bewerkingen wijzigen je bestanden niet
Een wijziging aan een eigenschap verandert de app voor je en niets anders. Het in je bron krijgen is een aparte stap die je op een bewuste manier uitvoert, zoals beschreven in [Wijzigingen aan de bron schrijven](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## De bron van een component bekijken {#viewing-the-source-of-a-component}

Je kunt elke component terugvinden naar de Java die deze heeft opgebouwd. Standaard wordt de bron in craftforJ geopend als alleen-lezen, gepositioneerd op de regel die de component heeft gemaakt. Je kunt craftforJ configureren om deze in jouw editor te openen in plaats van op dezelfde regel. Wanneer een component niet kan worden teruggeleid naar een regel, meldt craftforJ dat in plaats van een lege viewer te openen.

![De bronviewer gepositioneerd op de regel die de geselecteerde component heeft gemaakt](/img/craftforj/inspector/source-viewer.png#rounded-border)
