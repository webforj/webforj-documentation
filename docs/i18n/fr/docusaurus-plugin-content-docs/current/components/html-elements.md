---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ propose un ensemble de composants qui correspondent directement aux éléments HTML standards, vous donnant une API Java typée pour les blocs de construction les plus courants des pages web. Pour une référence complète sur chaque élément HTML sous-jacent, consultez la [référence des éléments HTML MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Composants disponibles

Les composants suivants sont disponibles et correspondent à leurs éléments HTML respectifs :

| Classe webforJ | Élément HTML | Description | Enfants |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crée un hyperlien vers une URL, une adresse e-mail ou un emplacement dans la page. | ✔️ |
| `Article` | `<article>` | Représente une composition autonome telle qu'un article de blog, un article d'actualité ou une entrée de forum. | ✔️ |
| `Aside` | `<aside>` | Représente un contenu qui est indirectement lié au contenu principal, généralement rendu sous forme de barre latérale. | ✔️ |
| `Break` | `<hr>` | Représente une rupture thématique entre des éléments de niveau paragraphe, rendue sous forme de règle horizontale. | ❌ |
| `Div` | `<div>` | Un conteneur générique de niveau bloc pour regrouper et styliser le contenu. | ✔️ |
| `Emphasis` | `<em>` | Marque le texte avec une emphase, changeant le sens d'une phrase. | ✔️ |
| `Fieldset` | `<fieldset>` | Regroupe des contrôles de formulaire connexes et leurs étiquettes. | ✔️ |
| `Footer` | `<footer>` | Représente le pied de page de son ancêtre de section le plus proche, contenant généralement des informations sur l'auteur ou de navigation. | ✔️ |
| `FormattedText` | `<pre>` | Affiche du texte préformaté, préservant les espaces et les sauts de ligne exactement comme écrit. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Représentent six niveaux de titres de section, le `H1` étant le plus important et le `H6` le moins important. | ✔️ |
| `Header` | `<header>` | Représente un contenu introductif, contenant généralement un titre, un logo ou des liens de navigation. | ✔️ |
| `Iframe` | `<iframe>` | Intègre une autre page HTML comme un contexte de navigation imbriqué au sein de la page actuelle. | ❌ |
| `Img` | `<img>` | Intègre une image dans le document. | ❌ |
| `Legend` | `<legend>` | Fournit une légende pour le contenu d'un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Représente un élément individuel dans un `OrderedList` ou un `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Représente le contenu dominant du corps du document, unique à la page. | ✔️ |
| `NativeButton` | `<button>` | Un élément de bouton interactif natif qui peut déclencher des actions ou soumettre des formulaires. | ✔️ |
| `Nav` | `<nav>` | Représente une section de la page contenant des liens de navigation. | ✔️ |
| `OrderedList` | `<ol>` | Représente une liste numérotée et ordonnée d'éléments. | ✔️ |
| `Paragraph` | `<p>` | Représente un paragraphe de texte. | ✔️ |
| `Section` | `<section>` | Représente une section autonome générique d'un document, généralement avec un titre. | ✔️ |
| `Span` | `<span>` | Un conteneur en ligne générique pour le texte et d'autres contenus en ligne. | ✔️ |
| `Strong` | `<strong>` | Indique un contenu d'importance forte, généralement rendu en gras. | ✔️ |
| `UnorderedList` | `<ul>` | Représente une liste de puces d'éléments non ordonnée. | ✔️ |

## Travailler avec des enfants

Les composants marqués avec ✔️ dans la colonne **Enfants** prennent en charge l'ajout, la suppression et l'accès aux composants enfants. Ces méthodes sont fournies par la classe [`Element`](../building-ui/element#component-interaction).

Pour créer des éléments HTML arbitraires au-delà de ceux répertoriés ici, ou pour intégrer des composants web personnalisés, consultez la documentation [`Element`](../building-ui/element).
