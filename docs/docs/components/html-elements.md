---
sidebar_position: 155
title: HTML Element Components
---
<!-- vale Google.EmDash = NO -->
webforJ ships a set of components that map directly to standard HTML elements, giving you a typed Java API for the most common building blocks of web pages. For a comprehensive reference on each underlying HTML element, see the [MDN HTML element reference](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Available components

The following components are available and map to their corresponding HTML elements:

| webforJ Class | HTML Element | Description | Children |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Creates a hyperlink to a URL, email address, or in-page location. | ✔️ |
| `Article` | `<article>` | Represents a self-contained composition such as a blog post, news article, or forum entry. | ✔️ |
| `Aside` | `<aside>` | Represents content that's indirectly related to the main content, typically rendered as a sidebar. | ✔️ |
| `Break` | `<hr>` | Represents a thematic break between paragraph-level elements, rendered as a horizontal rule. | ❌ |
| `Div` | `<div>` | A generic block-level container for grouping and styling content. | ✔️ |
| `Emphasis` | `<em>` | Marks text with stress emphasis, changing the meaning of a sentence. | ✔️ |
| `Fieldset` | `<fieldset>` | Groups related form controls and their labels. | ✔️ |
| `Footer` | `<footer>` | Represents the footer of its nearest sectioning ancestor, typically containing authorship or navigation information. | ✔️ |
| `FormattedText` | `<pre>` | Displays preformatted text, preserving whitespace and line breaks exactly as written. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Represent six levels of section headings, with `H1` being the most and `H6` the least important. | ✔️ |
| `Header` | `<header>` | Represents introductory content, typically containing a heading, logo, or navigation links. | ✔️ |
| `Iframe` | `<iframe>` | Embeds another HTML page as a nested browsing context within the current page. | ❌ |
| `Img` | `<img>` | Embeds an image into the document. | ❌ |
| `Legend` | `<legend>` | Provides a caption for the content of a `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Represents an individual item in an `OrderedList` or `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Represents the dominant content of the document body, unique to the page. | ✔️ |
| `NativeButton` | `<button>` | A native interactive button element that can trigger actions or submit forms. | ✔️ |
| `Nav` | `<nav>` | Represents a section of the page containing navigation links. | ✔️ |
| `OrderedList` | `<ol>` | Represents a numbered, ordered list of items. | ✔️ |
| `Paragraph` | `<p>` | Represents a paragraph of text. | ✔️ |
| `Section` | `<section>` | Represents a generic standalone section of a document, typically with a heading. | ✔️ |
| `Span` | `<span>` | A generic inline container for text and other inline content. | ✔️ |
| `Strong` | `<strong>` | Indicates content of strong importance, typically rendered in bold. | ✔️ |
| `UnorderedList` | `<ul>` | Represents a bulleted, unordered list of items. | ✔️ |

## Working with children

Components marked with ✔️ in the **Children** column support adding, removing, and accessing child components. These methods are provided through the [`Element`](../building-ui/element#component-interaction) class.

For creating arbitrary HTML elements beyond those listed here, or for embedding custom web components, see the [`Element`](../building-ui/element) documentation.
