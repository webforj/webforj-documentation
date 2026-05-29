---
sidebar_position: 3
title: HTML Element Components
_i18n_hash: 9b4589a970d529d60503af3b502742ac
---
Vaikka webforJ:n `Element`-luokka sallii käyttäjien luoda HTML-elementtejä sovelluksissaan, webforJ:n ydinkomponenttien mukana on tarjoiltu joukko standardeja HTML-elementtejä käyttömukavuuden vuoksi.

Seuraavia komponentteja voidaan käyttää, ja ne vastaavat vastaavia HTML-elementtejä:

|webforJ-luokka|HTML-elementti|Voi lisätä komponentteja?|
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

Nämä komponentit on toteutettu tarjoamaan API vuorovaikutukseen eri ominaisuuksien, attribuuttien ja toiminnallisuuksien kanssa, joita näiltä elementeiltä odotetaan.

Menetelmiä on myös tarjolla komponenttien lisäämiseen, poistamiseen ja käyttöön, kuten on kuvattu tämän [`Element`](../elements.md#component-interaction) -luokan osassa.
