---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ expédie un ensemble de composants qui correspondent directement aux éléments HTML standard, vous offrant une API Java typée pour les blocs de construction les plus courants des pages web. Pour une référence complète sur chaque élément HTML sous-jacent, consultez la [référence des éléments HTML MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Composants disponibles {#available-components}

Les composants suivants sont disponibles et correspondent à leurs éléments HTML respectifs :

| Classe webforJ | Élément HTML | Description | Enfants |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crée un lien hypertexte vers une URL, une adresse email ou un emplacement dans la page. | ✔️ |
| `Article` | `<article>` | Représente une composition autonome telle qu'un article de blog, un article de presse ou une entrée de forum. | ✔️ |
| `Aside` | `<aside>` | Représente un contenu qui est indirectement lié au contenu principal, généralement rendu comme une barre latérale. | ✔️ |
| `Break` | `<hr>` | Représente une rupture thématique entre des éléments de niveau paragraphe, rendu comme une règle horizontale. | ❌ |
| `Div` | `<div>` | Un conteneur générique de niveau bloc pour regrouper et styliser du contenu. | ✔️ |
| `Emphasis` | `<em>` | Marque un texte avec une emphase de stress, changeant le sens d'une phrase. | ✔️ |
| `Fieldset` | `<fieldset>` | Regroupe les contrôles de formulaire liés et leurs étiquettes. | ✔️ |
| `Footer` | `<footer>` | Représente le pied de page de son ancêtre de section le plus proche, contenant généralement des informations d'auteur ou de navigation. | ✔️ |
| `FormattedText` | `<pre>` | Affiche du texte préformaté, préservant les espaces et les sauts de ligne exactement tels qu'ils sont écrits. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Représentent six niveaux de titres de section, `H1` étant le plus important et `H6` le moins important. | ✔️ |
| `Header` | `<header>` | Représente du contenu introductif, contenant généralement un titre, un logo ou des liens de navigation. | ✔️ |
| `Iframe` | `<iframe>` | Intègre une autre page HTML comme un contexte de navigation imbriqué au sein de la page actuelle. | ❌ |
| `Img` | `<img>` | Intègre une image dans le document. | ❌ |
| `Legend` | `<legend>` | Fournit une légende pour le contenu d'un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Représente un élément individuel dans une `OrderedList` ou `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Représente le contenu dominant du corps du document, unique à la page. | ✔️ |
| `NativeButton` | `<button>` | Un élément bouton interactif natif qui peut déclencher des actions ou soumettre des formulaires. | ✔️ |
| `Nav` | `<nav>` | Représente une section de la page contenant des liens de navigation. | ✔️ |
| `OrderedList` | `<ol>` | Représente une liste numérotée et ordonnée d'éléments. | ✔️ |
| `Paragraph` | `<p>` | Représente un paragraphe de texte. | ✔️ |
| `Section` | `<section>` | Représente une section autonome générique d'un document, généralement avec un titre. | ✔️ |
| `Span` | `<span>` | Un conteneur en ligne générique pour du texte et d'autres contenus en ligne. | ✔️ |
| `Strong` | `<strong>` | Indique un contenu d'importance forte, généralement rendu en gras. | ✔️ |
| `UnorderedList` | `<ul>` | Représente une liste à puces et non ordonnée d'éléments. | ✔️ |

## Travailler avec les enfants {#working-with-children}

Les composants marqués avec ✔️ dans la colonne **Enfants** prennent en charge l'ajout, la suppression et l'accès aux composants enfants. Ces méthodes sont fournies par la classe [`Element`](../building-ui/element#component-interaction).

Pour créer des éléments HTML arbitraires au-delà de ceux énumérés ici, ou pour intégrer des composants web personnalisés, consultez la documentation de [`Element`](../building-ui/element).
