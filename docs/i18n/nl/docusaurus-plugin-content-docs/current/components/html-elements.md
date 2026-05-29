---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ verzendt een set componenten die rechtstreeks overeenkomen met standaard HTML-elementen, waardoor je een getypte Java API hebt voor de meest voorkomende bouwstenen van webpagina's. Voor een uitgebreide referentie over elk onderliggend HTML-element, zie de [MDN HTML-elementreferentie](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Beschikbare componenten

De volgende componenten zijn beschikbaar en komen overeen met hun respectieve HTML-elementen:

| webforJ Klasse | HTML Element | Beschrijving | Kinderen |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Maakt een hyperlink naar een URL, e-mailadres of locatie op de pagina. | ✔️ |
| `Article` | `<article>` | Vertegenwoordigt een zelfvoorzienende compositie zoals een blogpost, nieuwsartikel of foruminvoer. | ✔️ |
| `Aside` | `<aside>` | Vertegenwoordigt inhoud die indirect gerelateerd is aan de hoofdinhoud, meestal weergegeven als een zijbalk. | ✔️ |
| `Break` | `<hr>` | Vertegenwoordigt een thematische onderbreking tussen paragraaf-niveau elementen, weergegeven als een horizontale lijn. | ❌ |
| `Div` | `<div>` | Een algemene blokniveau-container voor het groeperen en stylen van inhoud. | ✔️ |
| `Emphasis` | `<em>` | Markeert tekst met stress benadrukking, waardoor de betekenis van een zin verandert. | ✔️ |
| `Fieldset` | `<fieldset>` | Groepeert gerelateerde formuliercontroles en hun labels. | ✔️ |
| `Footer` | `<footer>` | Vertegenwoordigt de voetnoot van zijn dichtstbijzijnde sectie-ouder, meestal met auteurschap of navigatie-informatie. | ✔️ |
| `FormattedText` | `<pre>` | Toont voorgeformatteerde tekst, waarbij witruimtes en regelafbrekingen exact worden behouden zoals geschreven. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Vertegenwoordigen zes niveaus van sectiekoppen, waarbij `H1` het meest en `H6` het minst belangrijk is. | ✔️ |
| `Header` | `<header>` | Vertegenwoordigt inleidende inhoud, meestal met een kop, logo of navigatielinks. | ✔️ |
| `Iframe` | `<iframe>` | Embeds een andere HTML-pagina als een genestelde browsecontext binnen de huidige pagina. | ❌ |
| `Img` | `<img>` | Embed een afbeelding in het document. | ❌ |
| `Legend` | `<legend>` | Biedt een bijschrift voor de inhoud van een `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Vertegenwoordigt een individueel item in een `OrderedList` of `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Vertegenwoordigt de dominante inhoud van de documentbody, uniek voor de pagina. | ✔️ |
| `NativeButton` | `<button>` | Een native interactieve knop die acties kan triggeren of formulieren kan indienen. | ✔️ |
| `Nav` | `<nav>` | Vertegenwoordigt een sectie van de pagina met navigatielinks. | ✔️ |
| `OrderedList` | `<ol>` | Vertegenwoordigt een genummerde, geordende lijst van items. | ✔️ |
| `Paragraph` | `<p>` | Vertegenwoordigt een alinea van tekst. | ✔️ |
| `Section` | `<section>` | Vertegenwoordigt een algemene zelfstandige sectie van een document, meestal met een kop. | ✔️ |
| `Span` | `<span>` | Een algemene inline-container voor tekst en andere inline-inhoud. | ✔️ |
| `Strong` | `<strong>` | Geeft inhoud van sterke belangrijkheid aan, meestal weergegeven in vet. | ✔️ |
| `UnorderedList` | `<ul>` | Vertegenwoordigt een niet-genummerde, ongesorteerde lijst van items. | ✔️ |

## Werken met kinderen

Componenten gemarkeerd met ✔️ in de **Kinderen** kolom ondersteunen het toevoegen, verwijderen en toegang krijgen tot kindcomponenten. Deze methoden worden aangeboden via de [`Element`](../building-ui/element#component-interaction) klasse.

Voor het maken van willekeurige HTML-elementen buiten die hier zijn opgesomd, of voor het insluiten van aangepaste webcomponenten, zie de [`Element`](../building-ui/element) documentatie.
