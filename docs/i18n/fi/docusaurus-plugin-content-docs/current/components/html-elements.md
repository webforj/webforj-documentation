---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ toimittaa joukon komponentteja, jotka vastaavat suoraan standardeja HTML-elementtejä, tarjoten tyyppitetyn Java-API:n yleisimpiin verkkosivujen rakennuspalikoihin. Katso kattava viite jokaiselle taustalla olevalle HTML-elementille [MDN HTML element reference](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Saatavilla olevat komponentit

Seuraavat komponentit ovat saatavilla ja ne vastaavat vastaavia HTML-elementtejä:

| webforJ-luokka | HTML-elementti | Kuvaus | Lapset |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Luo hyperlinkin URL-osoitteeseen, sähköpostiosoitteeseen tai sivun sisäiseen sijaintiin. | ✔️ |
| `Article` | `<article>` | Edustaa itsenäistä teosta, kuten blogikirjoitusta, uutisartikkelia tai foorumi-inputia. | ✔️ |
| `Aside` | `<aside>` | Edustaa sisältöä, joka on epäsuorasti liittynyt pääsisältöön, yleensä renderöity sivupalkkina. | ✔️ |
| `Break` | `<hr>` | Edustaa temaattista jakoa kappaletasolla olevien elementtien välillä, renderöitynä vaakaviivana. | ❌ |
| `Div` | `<div>` | Yleisluonteinen lohkotason säiliö sisällön ryhmittelyyn ja muotoiluun. | ✔️ |
| `Emphasis` | `<em>` | Merkitsee tekstin painotetulla painotuksella, muuttaen lauseen merkitystä. | ✔️ |
| `Fieldset` | `<fieldset>` | Ryhmittelee liittyvät lomakekomponentit ja niiden etiketti. | ✔️ |
| `Footer` | `<footer>` | Edustaa sen lähimmän osallisen juuren alatunnistetta, joka sisältää yleensä tekijätietoa tai navigointitietoa. | ✔️ |
| `FormattedText` | `<pre>` | Näyttää esimuotoiltua tekstiä säilyttäen valkoisen tilan ja rivinvaihdot täsmälleen niin kuin ne on kirjoitettu. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Edustavat kuutta eritasoista osion otsikkoa, joissa `H1` on tärkein ja `H6` vähiten tärkeä. | ✔️ |
| `Header` | `<header>` | Edustaa johdantoa, joka sisältää yleensä otsikon, logon tai navigointilinkkejä. | ✔️ |
| `Iframe` | `<iframe>` | Upottaa toisen HTML-sivun sisäiseksi selausyhteydeksi nykyiselle sivulle. | ❌ |
| `Img` | `<img>` | Upottaa kuvan asiakirjaan. | ❌ |
| `Legend` | `<legend>` | Tarjoaa kuvatekstin `Fieldset`:n sisällölle. | ❌ |
| `ListEntry` | `<li>` | Edustaa yksittäistä kohdetta `OrderedList`- tai `UnorderedList`:ssa. | ✔️ |
| `Main` | `<main>` | Edustaa asiakirjan rungon vallitsevaa sisältöä, joka on ainutlaatuinen sivulle. | ✔️ |
| `NativeButton` | `<button>` | Paikallinen interaktiivinen painiketyyppi, joka voi laukaista toimintoja tai lähettää lomakkeita. | ✔️ |
| `Nav` | `<nav>` | Edustaa sivun osaa, joka sisältää navigointilinkkejä. | ✔️ |
| `OrderedList` | `<ol>` | Edustaa numeroitua, järjestettyä luetteloa kohteista. | ✔️ |
| `Paragraph` | `<p>` | Edustaa tekstiparaafia. | ✔️ |
| `Section` | `<section>` | Edustaa yleistä itsenäistä osiota asiakirjassa, yleensä otsikon kanssa. | ✔️ |
| `Span` | `<span>` | Yleisluonteinen inline-säiliö tekstille ja muulle inline-sisällölle. | ✔️ |
| `Strong` | `<strong>` | Tarkoittaa vahvasti tärkeitä sisältöjä, yleensä renderöitynä lihavoituna. | ✔️ |
| `UnorderedList` | `<ul>` | Edustaa luetteloa kohteista, jossa on luetteloimattomia merkkejä. | ✔️ |

## Työskentely lapsien kanssa

✔️ merkittyjä komponentteja **Lapsia** sarakkeessa tukee lapsikomponenttien lisäämistä, poistamista ja käsittelyä. Nämä menetelmät tarjoavat [`Element`](../building-ui/element#component-interaction) -luokka.

Jotta voit luoda satunnaisia HTML-elementtejä tämän luettelon ulkopuolella, tai upottaa mukautettuja verkkokomponentteja, katso [`Element`](../building-ui/element) dokumentaatio.
