---
sidebar_position: 3
title: HTML Element Components
_i18n_hash: 86c5e2ee07360ad502e8857eb68cebe2
---
Vaikka webforJ:n `Element`-luokka antaa käyttäjille mahdollisuuden luoda HTML-elementtejä sovelluksissaan, webforJ:n ydinkomponenttien mukana on mukana joukko vakio-HTML-elementtejä helppokäyttöisyyden vuoksi.

Seuraavia komponentteja voidaan käyttää, ja ne vastaavat vastaavia HTML-elementtejä:

|webforJ-luokka|HTML-elementti|Voiko lisätä komponentteja?|
|:--:|:--:|:--:|
|`Anchor`|`<a>`| ✔️ |
|`Article`|`<article>`| ✔️ |
|`Aside`|`<aside>`| ✔️ |
|`Break`|`<hr>`| ❌ |
|`Div`|`<div>`| ✔️ |
|`Emphasis`|`<em>`| ✔️ |
|`Fieldset`|`<fieldset>`| ✔️ |
|`Footer`|`<footer>`| ✔️ |
|`FormattedText`|`<pre>`| ✔️ |
|`H1 - H6`|`<h1> - <h6>`| ✔️ |
|`Header`|`<header>`| ✔️ |
|`Iframe`|`<iframe>`| ❌ |
|`Img`|`<img>`| ❌ |
|`Legend`|`<legend>`| ❌ |
|`ListEntry`|`<li>`| ✔️ |
|`Main`|`<main>`| ✔️ |
|`NativeButton`|`<button>`| ✔️ |
|`Nav`|`<nav>`| ✔️ |
|`OrderedList`|`<ol>`| ✔️ |
|`Paragraph`|`<p>`| ✔️ |
|`Section`|`<section>`| ✔️ |
|`Span`|`<span>`| ✔️ |
|`Strong`|`<strong>`| ✔️ |
|`UnorderedList`|`<ul>`| ✔️ |

Näitä komponentteja on toteutettu tarjoamaan API, jonka avulla voidaan käyttää erilaisia ominaisuuksia, attribuutteja ja toimintoja, joita näiltä elementeiltä odotetaan.

Menetelmiä on myös tarjottu komponenttien lisäämiseen, poistamiseen ja käyttämiseen, kuten on esitetty [`Element`](../elements.md#component-interaction) -luokan tässä osassa.
