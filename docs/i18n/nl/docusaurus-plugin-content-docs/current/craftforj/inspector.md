---
title: Inspecteren van Componenten
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 93bf0d91de1f942373082ccebd0bab6d
---
De Inspector toont de componentboom die jouw Java-code heeft opgebouwd. Een `Composite` verschijnt als de klasse die je hebt geschreven, met de kinderen die je eraan hebt gegeven in de volgorde waarin webforJ ze heeft, zodat de structuur in craftforJ overeenkomt met de structuur in je bron.

![De componentboom met een geselecteerde en gemarkeerde component in de draaiende app](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Een component selecteren {#selecting-a-component}

Om een component van de pagina te selecteren, druk je op <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> en klik je erop. craftforJ selecteert de bijbehorende knoop in de boom. Het hoveren over een knoop in de boom doet het omgekeerde en markeert die component op de pagina, zodat je in beide richtingen tussen het scherm en de boom kunt bewegen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Om de boom te doorzoeken, druk je op <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Een term tussen schuine streepjes plaatst het als een reguliere expressie. Rechtsklikken op een knoop opent de beschikbare acties voor die knoop. Je kunt de bron openen of het aan de [assistent](/docs/craftforj/ai) geven.

## Eigenschappen lezen en wijzigen {#reading-and-changing-properties}

Het selecteren van een component vult de zijbalk met zijn eigenschappen, gegroepeerd op basis van wat ze beïnvloeden. Welke eigenschappen een component biedt, hangt af van de component, en sommige daarvan zijn alleen-lezen. Eigenschappen die niet goed leesbaar zijn als platte tekst, krijgen in plaats daarvan een editor die is afgestemd op hun waarde. Het wijzigen van een waarde heeft onmiddellijk effect in de draaiende app.

:::info Live aanpassingen wijzigen je bestanden niet
Een eigenschapswijziging verandert de app voor je en verder niets. Het in je bron krijgen, is een aparte stap die je opzettelijk uitvoert, zoals beschreven in [Wijzigingen naar de bron schrijven](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## De bron van een component bekijken {#viewing-the-source-of-a-component}

Je kunt elke component terugvolgen naar de Java die deze heeft gebouwd. Standaard opent de bron in craftforJ als alleen-lezen, gepositioneerd op de regel die de component heeft aangemaakt. Je kunt craftforJ configureren om het in je editor te openen, op dezelfde regel. Wanneer een component niet terug te traceren is naar een regel, meldt craftforJ dat in plaats van een lege viewer te openen.

![De bronviewer gepositioneerd op de regel die de geselecteerde component heeft aangemaakt](/img/craftforj/inspector/source-viewer.png#rounded-border)
