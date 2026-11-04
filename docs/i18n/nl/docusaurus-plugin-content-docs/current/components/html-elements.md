---
sidebar_position: 52
title: HTML Componenten
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ biedt een set componenten die rechtstreeks overeenkomen met standaard HTML-elementen, waardoor je een getypeerde Java API krijgt voor de meest voorkomende bouwstenen van webpagina's. Voor een uitgebreide referentie van elk onderliggend HTML-element, zie de [MDN HTML-elementreferentie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Beschikbare componenten {#available-components}

De volgende componenten zijn beschikbaar en komen overeen met hun bijbehorende HTML-elementen:

| webforJ Class | HTML Element | Beschrijving | Kinderen |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Creëert een hyperlink naar een URL, e-mailadres of locatie binnen de pagina. | ✔️ |
| `Article` | `<article>` | Vertegenwoordigt een zelf-contained compositie zoals een blogpost, nieuwsartikel of forumitem. | ✔️ |
| `Aside` | `<aside>` | Vertegenwoordigt content die indirect gerelateerd is aan de hoofdinhoud, meestal weergegeven als een zijbalk. | ✔️ |
| `Break` | `<hr>` | Vertegenwoordigt een thematische scheiding tussen paragraafniveau-elementen, weergegeven als een horizontale lijn. | ❌ |
| `Div` | `<div>` | Een generieke blokniveau-container voor het groeperen en stylen van inhoud. | ✔️ |
| `Emphasis` | `<em>` | Markeert tekst met nadruk, waardoor de betekenis van een zin verandert. | ✔️ |
| `Fieldset` | `<fieldset>` | Groepeert gerelateerde formulierelementen en hun labels. | ✔️ |
| `Footer` | `<footer>` | Vertegenwoordigt de voettekst van zijn dichtstbijzijnde sectie-voorouder, meestal met auteurs- of navigatie-informatie. | ✔️ |
| `FormattedText` | `<pre>` | Weergeeft opgemaakte tekst, waarbij witruimtes en regelafbrekingen precies worden behouden zoals geschreven. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Vertegenwoordigt zes niveaus van sectiekoppen, waarbij `H1` de belangrijkste is en `H6` de minst belangrijke. | ✔️ |
| `Header` | `<header>` | Vertegenwoordigt inleidende inhoud, meestal met een kop, logo of navigatielinks. | ✔️ |
| `Iframe` | `<iframe>` | Embed een andere HTML-pagina als een genest browsen context binnen de huidige pagina. | ❌ |
| `Img` | `<img>` | Embed een afbeelding in het document. | ❌ |
| `Legend` | `<legend>` | Biedt een bijschrift voor de inhoud van een `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Vertegenwoordigt een individueel item in een `OrderedList` of `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Vertegenwoordigt de dominante inhoud van de documentinhoud, uniek voor de pagina. | ✔️ |
| `NativeButton` | `<button>` | Een native interactieve knop die acties kan triggeren of formulieren kan indienen. | ✔️ |
| `Nav` | `<nav>` | Vertegenwoordigt een sectie van de pagina met navigatielinks. | ✔️ |
| `OrderedList` | `<ol>` | Vertegenwoordigt een genummerde lijst van items. | ✔️ |
| `Paragraph` | `<p>` | Vertegenwoordigt een alinea tekst. | ✔️ |
| `Section` | `<section>` | Vertegenwoordigt een generieke zelfstandige sectie van een document, meestal met een kop. | ✔️ |
| `Span` | `<span>` | Een generieke inline-container voor tekst en andere inline-inhoud. | ✔️ |
| `Strong` | `<strong>` | Geeft inhoud van sterke belangrijkheid aan, meestal vet weergegeven. | ✔️ |
| `UnorderedList` | `<ul>` | Vertegenwoordigt een opsommingstekens-lijst van items. | ✔️ |

## Werken met kinderen {#working-with-children}

Elke HTML-component breidt `HtmlComponent` uit. Componenten gemarkeerd met ✔️ in de **Kinderen**-kolom breiden `HtmlComponentContainer` uit, wat de methoden toevoegt voor het toevoegen, verwijderen en benaderen van kindcomponenten. Componenten zonder kinderen, zoals `Break`, breiden `HtmlComponent` rechtstreeks uit.

Voor het creëren van willekeurige HTML-elementen buiten die hier vermeld staan, of voor het insluiten van aangepaste webcomponenten, zie de [`Element`](../building-ui/element) documentatie.
