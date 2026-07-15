---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ toimittaa joukon komponentteja, jotka vastaavat suoraan standardeja HTML-elementtejä ja tarjoavat tyypitetyn Java-rajapinnan verkkosivujen yleisimpiin rakennuspalikoihin. Kattavan viittauksen kuhunkin perus-html-elementtiin löydät [MDN HTML-elementtiviittauksesta](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Saatavilla olevat komponentit {#available-components}

Seuraavat komponentit ovat saatavilla ja karttuvat vastaaviin HTML-elementteihin:

| webforJ-luokka | HTML-elementti | Kuvaus | Lapset |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Luo hyperlinkin URL-osoitteeseen, sähköpostiosoitteeseen tai sivun sisäiseen sijaintiin. | ✔️ |
| `Article` | `<article>` | Edustaa itseään sisältävää rakennetta, kuten blogikirjoitusta, uutisartikkelia tai foorumin viestiä. | ✔️ |
| `Aside` | `<aside>` | Edustaa sisältöä, joka on epäsuorasti liittynyt pääsisältöön, yleensä sivupalkkina. | ✔️ |
| `Break` | `<hr>` | Edustaa temaattista taukoa kappaletasolla, esitettynä vaakasuorana viivana. | ❌ |
| `Div` | `<div>` | Yleinen lohkotason säiliö sisällön ryhmittelyyn ja tyylittelyyn. | ✔️ |
| `Emphasis` | `<em>` | Merkitsee tekstin painotusta korostamalla, muuttaen lauseen merkitystä. | ✔️ |
| `Fieldset` | `<fieldset>` | Ryhmittelee yhteen liittyviä lomakeohjaimia ja niiden etikettejä. | ✔️ |
| `Footer` | `<footer>` | Edustaa lähimmän osioitaon esivanhemman alaosaa, joka sisältää yleensä tekijä- tai navigointitietoja. | ✔️ |
| `FormattedText` | `<pre>` | Näyttää muotoiltua tekstiä, säilyttäen valkoisen alueen ja rivinvaihdot tarkalleen sellaisina kuin ne on kirjoitettu. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Edustavat kuutta tasoa osion otsikoita, joissa `H1` on tärkein ja `H6` vähiten tärkeä. | ✔️ |
| `Header` | `<header>` | Edustaa johdantoa sisältöä, joka sisältää yleensä otsikon, logon tai navigointilinkkejä. | ✔️ |
| `Iframe` | `<iframe>` | Liittää toisen HTML-sivun upotettuna selauskontekstina nykyiseen sivuun. | ❌ |
| `Img` | `<img>` | Liittää kuvan asiakirjaan. | ❌ |
| `Legend` | `<legend>` | Tarjoaa kuvatekstin `Fieldset`-sisällölle. | ❌ |
| `ListEntry` | `<li>` | Edustaa yksittäistä kohtaa `OrderedList`- tai `UnorderedList`-kohdassa. | ✔️ |
| `Main` | `<main>` | Edustaa asiakirjan rungon hallitsevaa sisältöä, joka on ainutlaatuinen sivulle. | ✔️ |
| `NativeButton` | `<button>` | Natiivi interaktiivinen painike-elementti, joka voi laukaista toimintoja tai lähettää lomakkeita. | ✔️ |
| `Nav` | `<nav>` | Edustaa sivun osiota, joka sisältää navigointilinkkejä. | ✔️ |
| `OrderedList` | `<ol>` | Edustaa numeroitua, järjestettyä listaa esineistä. | ✔️ |
| `Paragraph` | `<p>` | Edustaa tekstikappaletta. | ✔️ |
| `Section` | `<section>` | Edustaa yleistä itsenäistä osiota asiakirjassa, yleensä otsikon kanssa. | ✔️ |
| `Span` | `<span>` | Yleinen rivissä oleva säiliö tekstille ja muulle rivissä olevalle sisällölle. | ✔️ |
| `Strong` | `<strong>` | Ilmaisee vahvasti merkityksellistä sisältöä, joka esitetään yleensä lihavoituna. | ✔️ |
| `UnorderedList` | `<ul>` | Edustaa lueteltua, järjestämätöntä listaa esineistä. | ✔️ |

## Työskentely lapsien kanssa {#working-with-children}

Komponentit, jotka on merkitty ✔️ **Lapset**-sarakkeessa, tukevat lapsikomponenttien lisäämistä, poistamista ja käyttöä. Nämä menetelmät tarjoavat [`Element`](../building-ui/element#component-interaction) -luokka.

Erityisten HTML-elementtien luomiseen näiden lisäksi, tai mukautettujen verkkokomponenttien upottamiseen, katso [`Element`](../building-ui/element) -dokumentaatio.
