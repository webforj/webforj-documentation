---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ liefert eine Reihe von Komponenten, die direkt den standardmäßigen HTML-Elementen zugeordnet sind und Ihnen eine typisierte Java-API für die gängigsten Bausteine von Webseiten bieten. Für eine umfassende Referenz zu jedem zugrunde liegenden HTML-Element siehe die [MDN HTML-Elementreferenz](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Verfügbare Komponenten

Die folgenden Komponenten sind verfügbar und entsprechen ihren jeweiligen HTML-Elementen:

| webforJ Klasse | HTML-Element | Beschreibung | Kinder |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Erstellt einen Hyperlink zu einer URL, E-Mail-Adresse oder einer Position auf der Seite. | ✔️ |
| `Article` | `<article>` | Stellt eine selbstenthaltende Komposition wie einen Blogbeitrag, Nachrichtenartikel oder Foreneintrag dar. | ✔️ |
| `Aside` | `<aside>` | Stellt Inhalte dar, die indirekt mit dem Hauptinhalt verbunden sind, typischerweise als Sidebar gerendert. | ✔️ |
| `Break` | `<hr>` | Stellt eine thematische Trennung zwischen Absätzen dar, die als horizontale Linie gerendert wird. | ❌ |
| `Div` | `<div>` | Ein generischer Blockcontainer zum Gruppieren und Stylen von Inhalten. | ✔️ |
| `Emphasis` | `<em>` | Markiert Text mit Betonung, was die Bedeutung eines Satzes verändert. | ✔️ |
| `Fieldset` | `<fieldset>` | Gruppiert verwandte Formularsteuerelemente und deren Beschriftungen. | ✔️ |
| `Footer` | `<footer>` | Stellt den Fußbereich des nächsten Abschnitts dar, typischerweise mit Autor- oder Navigationsinformationen. | ✔️ |
| `FormattedText` | `<pre>` | Stellt vorformatierten Text dar, der Whitespace und Zeilenumbrüche genau so beibehält, wie er geschrieben wurde. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Stellen sechs Ebenen von Abschnittsüberschriften dar, wobei `H1` die wichtigste und `H6` die am wenigsten wichtige ist. | ✔️ |
| `Header` | `<header>` | Stellt einführende Inhalte dar, typischerweise mit einer Überschrift, einem Logo oder Navigationslinks. | ✔️ |
| `Iframe` | `<iframe>` | Bindet eine andere HTML-Seite als verschachtelten Browsing-Kontext innerhalb der aktuellen Seite ein. | ❌ |
| `Img` | `<img>` | Bindet ein Bild in das Dokument ein. | ❌ |
| `Legend` | `<legend>` | Liefert eine Beschriftung für den Inhalt eines `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Stellt einen einzelnen Punkt in einer `OrderedList` oder `UnorderedList` dar. | ✔️ |
| `Main` | `<main>` | Stellt den dominierenden Inhalt des Dokumentkörpers dar, der einzigartig für die Seite ist. | ✔️ |
| `NativeButton` | `<button>` | Ein natives interaktives Schaltflächenelement, das Aktionen auslösen oder Formulare absenden kann. | ✔️ |
| `Nav` | `<nav>` | Stellt einen Abschnitt der Seite dar, der Navigationslinks enthält. | ✔️ |
| `OrderedList` | `<ol>` | Stellt eine nummerierte, geordnete Liste von Elementen dar. | ✔️ |
| `Paragraph` | `<p>` | Stellt einen Absatz von Text dar. | ✔️ |
| `Section` | `<section>` | Stellt einen generischen eigenständigen Abschnitt eines Dokuments dar, typischerweise mit einer Überschrift. | ✔️ |
| `Span` | `<span>` | Ein generischer Inline-Container für Text und andere Inline-Inhalte. | ✔️ |
| `Strong` | `<strong>` | Kennzeichnet Inhalte von großer Bedeutung, die typischerweise fett dargestellt werden. | ✔️ |
| `UnorderedList` | `<ul>` | Stellt eine Aufzählungsliste von Elementen dar. | ✔️ |

## Arbeiten mit Kindern

Komponenten, die in der Spalte **Kinder** mit ✔️ markiert sind, unterstützen das Hinzufügen, Entfernen und Zugreifen auf untergeordnete Komponenten. Diese Methoden werden durch die [`Element`](../building-ui/element#component-interaction) Klasse bereitgestellt.

Für die Erstellung beliebiger HTML-Elemente, die über die hier aufgelisteten hinausgehen, oder für das Einbetten benutzerdefinierter Webkomponenten siehe die [`Element`](../building-ui/element) Dokumentation.
