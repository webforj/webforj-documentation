---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ levert een set componenten die direct overeenkomen met standaard HTML-elementen, waarmee je een getypte Java API krijgt voor de meest voorkomende bouwstenen van webpagina's. Voor een uitgebreide referentie over elk onderliggend HTML-element, zie de [MDN HTML-elementreferentie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Beschikbare componenten {#available-components}

De volgende componenten zijn beschikbaar en komen overeen met hun bijbehorende HTML-elementen:

| webforJ Klasse | HTML Element | Beschrijving | Kinderen |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Creëert een hyperlink naar een URL, e-mailadres of locatie op de pagina. | ✔️ |
| `Article` | `<article>` | Vertegenwoordigt een zelf-contained compositie zoals een blogpost, nieuwsartikel of forumvermelding. | ✔️ |
| `Aside` | `<aside>` | Vertegenwoordigt inhoud die indirect verband houdt met de hoofdinhoud, meestal weergegeven als een zijbalk. | ✔️ |
| `Break` | `<hr>` | Vertegenwoordigt een thematische onderbreking tussen alinea-niveau elementen, weergegeven als een horizontale lijn. | ❌ |
| `Div` | `<div>` | Een generieke container op blokniveau voor het groeperen en styliseren van inhoud. | ✔️ |
| `Emphasis` | `<em>` | Markeert tekst met nadruk, wat de betekenis van een zin verandert. | ✔️ |
| `Fieldset` | `<fieldset>` | Groepeert gerelateerde formulierbesturingselementen en hun labels. | ✔️ |
| `Footer` | `<footer>` | Vertegenwoordigt de voetnoot van de dichtstbijzijnde sectie-voorouder, meestal met auteursinformatie of navigatie-informatie. | ✔️ |
| `FormattedText` | `<pre>` | Toont vooraf opgemaakte tekst, waarbij witruimtes en regeleinden exact worden behouden zoals geschreven. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Vertegenwoordigen zes niveaus van sectiekoppen, waarbij `H1` het belangrijkste en `H6` het minst belangrijke is. | ✔️ |
| `Header` | `<header>` | Vertegenwoordigt inleidende inhoud, meestal met een kop, logo of navigatielinks. | ✔️ |
| `Iframe` | `<iframe>` | Inbedden van een andere HTML-pagina als een geneste browsecontext binnen de huidige pagina. | ❌ |
| `Img` | `<img>` | Voegt een afbeelding in het document in. | ❌ |
| `Legend` | `<legend>` | Biedt een onderschrift voor de inhoud van een `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Vertegenwoordigt een enkel item in een `OrderedList` of `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Vertegenwoordigt de dominante inhoud van de documentbody, uniek voor de pagina. | ✔️ |
| `NativeButton` | `<button>` | Een native interactieve knop die acties kan triggeren of formulieren kan indienen. | ✔️ |
| `Nav` | `<nav>` | Vertegenwoordigt een sectie van de pagina met navigatielinks. | ✔️ |
| `OrderedList` | `<ol>` | Vertegenwoordigt een genummerde, gesorteerde lijst van items. | ✔️ |
| `Paragraph` | `<p>` | Vertegenwoordigt een alinea tekst. | ✔️ |
| `Section` | `<section>` | Vertegenwoordigt een generieke op zichzelf staande sectie van een document, meestal met een kop. | ✔️ |
| `Span` | `<span>` | Een generieke inline-container voor tekst en andere inline-inhoud. | ✔️ |
| `Strong` | `<strong>` | Geeft inhoud van sterke belangrijkheid aan, meestal vet weergegeven. | ✔️ |
| `UnorderedList` | `<ul>` | Vertegenwoordigt een opsomming, ongeordende lijst van items. | ✔️ |

## Werken met kinderen {#working-with-children}

Componenten gemarkeerd met ✔️ in de **Kinderen** kolom ondersteunen het toevoegen, verwijderen en benaderen van kindcomponenten. Deze methoden worden geleverd via de [`Element`](../building-ui/element#component-interaction) klasse.

Voor het maken van willekeurige HTML-elementen buiten die hier zijn vermeld, of voor het insluiten van aangepaste webcomponenten, zie de [`Element`](../building-ui/element) documentatie.
