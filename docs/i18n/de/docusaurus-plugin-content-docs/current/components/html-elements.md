---
sidebar_position: 52
title: HTML-Komponenten
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ bietet eine Reihe von Komponenten an, die direkt auf Standard-HTML-Elemente abgebildet werden, sodass Ihnen eine typisierte Java-API für die gebräuchlichsten Bausteine von Webseiten zur Verfügung steht. Für ein umfassendes Nachschlagewerk zu jedem zugrunde liegenden HTML-Element siehe das [MDN HTML-Elemente-Nachschlagewerk](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Verfügbare Komponenten {#available-components}

Die folgenden Komponenten sind verfügbar und entsprechen ihren jeweiligen HTML-Elementen:

| webforJ Klasse | HTML-Element | Beschreibung | Kinder |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Erstellt einen Hyperlink zu einer URL, E-Mail-Adresse oder einer Position innerhalb der Seite. | ✔️ |
| `Article` | `<article>` | Stellt eine eigenständige Komposition dar, wie einen Blogbeitrag, einen Nachrichtenartikel oder einen Foreneintrag. | ✔️ |
| `Aside` | `<aside>` | Stellt Inhalte dar, die indirekt mit dem Hauptinhalt verbunden sind, typischerweise als Seitenleiste angezeigt. | ✔️ |
| `Break` | `<hr>` | Stellt einen thematischen Bruch zwischen Absatzebene-Elementen dar, der als horizontale Regel angezeigt wird. | ❌ |
| `Div` | `<div>` | Ein allgemeiner Container auf Blockebene zum Gruppieren und Gestalten von Inhalten. | ✔️ |
| `Emphasis` | `<em>` | Markiert Text mit Betonung, die die Bedeutung eines Satzes ändert. | ✔️ |
| `Fieldset` | `<fieldset>` | Gruppiert verwandte Formularelemente und deren Bezeichnungen. | ✔️ |
| `Footer` | `<footer>` | Stellt den Fußbereich des nächstgelegenen Abschnitts dar, der typischerweise Informationen zur Urheberschaft oder Navigation enthält. | ✔️ |
| `FormattedText` | `<pre>` | Zeigt formatierten Text an, wobei Leerzeichen und Zeilenumbrüche genau so beibehalten werden, wie sie geschrieben wurden. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Stellen sechs Ebenen von Abschnittsüberschriften dar, wobei `H1` die wichtigste und `H6` die am wenigsten wichtige ist. | ✔️ |
| `Header` | `<header>` | Stellt einführende Inhalte dar, typischerweise mit einer Überschrift, einem Logo oder Navigationslinks. | ✔️ |
| `Iframe` | `<iframe>` | Betten Sie eine andere HTML-Seite als eingebetteten Browsing-Kontext innerhalb der aktuellen Seite ein. | ❌ |
| `Img` | `<img>` | Betten Sie ein Bild in das Dokument ein. | ❌ |
| `Legend` | `<legend>` | Bietet eine Beschriftung für den Inhalt eines `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Stellt einen einzelnen Artikel in einer `OrderedList` oder `UnorderedList` dar. | ✔️ |
| `Main` | `<main>` | Stellt den dominierenden Inhalt des Dokumentenkörpers dar, der einzigartig für die Seite ist. | ✔️ |
| `NativeButton` | `<button>` | Ein natives interaktives Schaltflächenelement, das Aktionen auslösen oder Formulare absenden kann. | ✔️ |
| `Nav` | `<nav>` | Stellt einen Abschnitt der Seite dar, der Navigationslinks enthält. | ✔️ |
| `OrderedList` | `<ol>` | Stellt eine nummerierte, geordnete Liste von Artikeln dar. | ✔️ |
| `Paragraph` | `<p>` | Stellt einen Absatz von Text dar. | ✔️ |
| `Section` | `<section>` | Stellt einen allgemeinen eigenständigen Abschnitt eines Dokuments dar, typischerweise mit einer Überschrift. | ✔️ |
| `Span` | `<span>` | Ein allgemeiner Inline-Container für Text und andere Inline-Inhalte. | ✔️ |
| `Strong` | `<strong>` | Kennzeichnet Inhalte von großer Bedeutung, die typischerweise fett dargestellt werden. | ✔️ |
| `UnorderedList` | `<ul>` | Stellt eine Aufzählungsliste von Artikeln dar. | ✔️ |

## Arbeiten mit Kindern {#working-with-children}

Jede HTML-Komponente erweitert `HtmlComponent`. Komponenten, die mit ✔️ in der **Kinder**-Spalte gekennzeichnet sind, erweitern `HtmlComponentContainer`, was die Methoden zum Hinzufügen, Entfernen und Zugreifen auf untergeordnete Komponenten hinzufügt. Komponenten ohne Kinder, wie `Break`, erweitern direkt `HtmlComponent`.

Um beliebige HTML-Elemente über die hier aufgeführten hinaus zu erstellen oder benutzerdefinierte Webkomponenten einzubetten, siehe die [`Element`](../building-ui/element) Dokumentation.
