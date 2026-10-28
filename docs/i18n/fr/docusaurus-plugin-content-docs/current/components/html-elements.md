---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ expédie un ensemble de composants qui se mappent directement aux éléments HTML standards, vous offrant une API Java typée pour les blocs de construction les plus courants des pages web. Pour une référence complète sur chaque élément HTML sous-jacent, consultez la [référence des éléments HTML de MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Composants disponibles {#available-components}

Les composants suivants sont disponibles et se mappent à leurs éléments HTML correspondants :

| Classe webforJ | Élément HTML | Description | Enfants |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crée un hyperlien vers une URL, une adresse e-mail ou un emplacement dans la page. | ✔️ |
| `Article` | `<article>` | Représente une composition autonome, comme un article de blog, une actualité ou une entrée de forum. | ✔️ |
| `Aside` | `<aside>` | Représente un contenu qui est indirectement lié au contenu principal, généralement rendu comme une barre latérale. | ✔️ |
| `Break` | `<hr>` | Représente une rupture thématique entre des éléments de niveau paragraphe, rendu comme une règle horizontale. | ❌ |
| `Div` | `<div>` | Un conteneur générique de niveau bloc pour regrouper et styliser du contenu. | ✔️ |
| `Emphasis` | `<em>` | Marque un texte avec une emphase d'accentuation, changeant le sens d'une phrase. | ✔️ |
| `Fieldset` | `<fieldset>` | Regroupe les contrôles de formulaire associés et leurs étiquettes. | ✔️ |
| `Footer` | `<footer>` | Représente le pied de la section ancêtre la plus proche, contenant généralement des informations sur l'auteur ou de navigation. | ✔️ |
| `FormattedText` | `<pre>` | Affiche du texte préformaté, préservant les espaces et les retours à la ligne exactement comme écrit. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Représentent six niveaux de titres de section, avec `H1` étant le plus important et `H6` le moins important. | ✔️ |
| `Header` | `<header>` | Représente un contenu introductif, contenant généralement un titre, un logo ou des liens de navigation. | ✔️ |
| `Iframe` | `<iframe>` | Intègre une autre page HTML comme un contexte de navigation imbriqué au sein de la page actuelle. | ❌ |
| `Img` | `<img>` | Intègre une image dans le document. | ❌ |
| `Legend` | `<legend>` | Fournit une légende pour le contenu d'un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Représente un élément individuel dans une `OrderedList` ou `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Représente le contenu dominant du corps du document, unique à la page. | ✔️ |
| `NativeButton` | `<button>` | Un élément de bouton interactif natif qui peut déclencher des actions ou soumettre des formulaires. | ✔️ |
| `Nav` | `<nav>` | Représente une section de la page contenant des liens de navigation. | ✔️ |
| `OrderedList` | `<ol>` | Représente une liste ordonnée de numéros d'items. | ✔️ |
| `Paragraph` | `<p>` | Représente un paragraphe de texte. | ✔️ |
| `Section` | `<section>` | Représente une section autonome générique d'un document, généralement avec un titre. | ✔️ |
| `Span` | `<span>` | Un conteneur en ligne générique pour du texte et d'autres contenus en ligne. | ✔️ |
| `Strong` | `<strong>` | Indique un contenu d'importance forte, généralement rendu en gras. | ✔️ |
| `UnorderedList` | `<ul>` | Représente une liste à puces non ordonnée d'items. | ✔️ |

## Travailler avec les enfants {#working-with-children}

Chaque composant HTML étend `HtmlComponent`. Les composants marqués avec ✔️ dans la colonne **Enfants** étendent `HtmlComponentContainer`, qui ajoute les méthodes pour ajouter, supprimer et accéder aux composants enfants. Les composants sans enfants, tels que `Break`, étendent directement `HtmlComponent`.

Pour créer des éléments HTML arbitraires au-delà de ceux énumérés ici, ou pour intégrer des composants web personnalisés, consultez la documentation sur l'[`Element`](../building-ui/element).
