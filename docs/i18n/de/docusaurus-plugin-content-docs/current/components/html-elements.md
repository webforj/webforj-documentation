---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ liefert eine Reihe von Komponenten, die direkt mit Standard-HTML-Elementen übereinstimmen und Ihnen eine typisierte Java-API für die häufigsten Bausteine von Webseiten bieten. Für eine umfassende Referenz zu jedem zugrunde liegenden HTML-Element siehe die [MDN HTML-Elementreferenz](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Verfügbare Komponenten {#available-components}

Die folgenden Komponenten sind verfügbar und entsprechen ihren jeweiligen HTML-Elementen:

| webforJ Klasse | HTML-Element | Beschreibung | Kinder |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Erstellt einen Hyperlink zu einer URL, E-Mail-Adresse oder Position auf der Seite. | ✔️ |
| `Article` | `<article>` | Repräsentiert eine in sich geschlossene Komposition wie einen Blogbeitrag, Nachrichtenartikel oder Foreneintrag. | ✔️ |
| `Aside` | `<aside>` | Repräsentiert Inhalte, die indirekt mit dem Hauptinhalt in Beziehung stehen, typischerweise als Sidebar dargestellt. | ✔️ |
| `Break` | `<hr>` | Repräsentiert einen thematischen Bruch zwischen Absatz-Elementen, dargestellt als horizontale Linie. | ❌ |
| `Div` | `<div>` | Ein generischer Blockcontainer zur Gruppierung und Styling von Inhalten. | ✔️ |
| `Emphasis` | `<em>` | Markiert Text mit Stressbetonung, wodurch die Bedeutung eines Satzes verändert wird. | ✔️ |
| `Fieldset` | `<fieldset>` | Gruppiert verwandte Formularelemente und deren Bezeichnungen. | ✔️ |
| `Footer` | `<footer>` | Repräsentiert den Fußbereich des nächstgelegenen Abschnitts, typischerweise mit Autoren- oder Navigationsinformationen. | ✔️ |
| `FormattedText` | `<pre>` | Zeigt vorformatierter Text an, wobei Leerzeichen und Zeilenumbrüche genau so erhalten bleiben, wie sie geschrieben sind. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Repräsentieren sechs Ebenen von Abschnittsüberschriften, wobei `H1` die wichtigste und `H6` die am wenigsten wichtige ist. | ✔️ |
| `Header` | `<header>` | Repräsentiert ein einführendes Element, typischerweise mit einer Überschrift, einem Logo oder Navigationslinks. | ✔️ |
| `Iframe` | `<iframe>` | Betten eine andere HTML-Seite als verschachtelten Browsing-Kontext innerhalb der aktuellen Seite ein. | ❌ |
| `Img` | `<img>` | Betten ein Bild in das Dokument ein. | ❌ |
| `Legend` | `<legend>` | Liefert eine Beschriftung für den Inhalt eines `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Repräsentiert einen einzelnen Artikel in einer `OrderedList` oder `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Repräsentiert den dominanten Inhalt des Dokumentenkörpers, einzigartig für die Seite. | ✔️ |
| `NativeButton` | `<button>` | Ein natives interaktives Schaltflächenelement, das Aktionen auslösen oder Formulare absenden kann. | ✔️ |
| `Nav` | `<nav>` | Repräsentiert einen Abschnitt der Seite, der Navigationslinks enthält. | ✔️ |
| `OrderedList` | `<ol>` | Repräsentiert eine nummerierte, geordnete Liste von Artikeln. | ✔️ |
| `Paragraph` | `<p>` | Repräsentiert einen Absatz von Text. | ✔️ |
| `Section` | `<section>` | Repräsentiert einen generischen eigenständigen Abschnitt eines Dokuments, typischerweise mit einer Überschrift. | ✔️ |
| `Span` | `<span>` | Ein generischer Inline-Container für Text und andere Inline-Inhalte. | ✔️ |
| `Strong` | `<strong>` | Gibt Inhalte von großer Bedeutung an, die typischerweise fett dargestellt werden. | ✔️ |
| `UnorderedList` | `<ul>` | Repräsentiert eine Aufzählungsliste von Artikeln. | ✔️ |

## Arbeiten mit Kindern {#working-with-children}

Komponenten, die in der **Kinder**-Spalte mit ✔️ markiert sind, unterstützen das Hinzufügen, Entfernen und Zugreifen auf Kindkomponenten. Diese Methoden werden durch die [`Element`](../building-ui/element#component-interaction) Klasse bereitgestellt.

Für das Erstellen beliebiger HTML-Elemente über die hier aufgelisteten hinaus oder zum Einbetten benutzerdefinierter Webkomponenten siehe die [`Element`](../building-ui/element) Dokumentation.
