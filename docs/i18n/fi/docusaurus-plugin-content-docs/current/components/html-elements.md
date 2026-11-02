---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ toimittaa joukon komponentteja, jotka vastaavat suoraan standardeja HTML-elementtejä, tarjoten sinulle tyypitetyn Java API:n yleisimpiin verkkosivujen rakennuspalikoihin. Kattavan viittauksen jokaiselle perus HTML-elementille löydät [MDN HTML element reference](https://developer.mozilla.org/en-US/docs/Web/HTML/Element) -sivustolta.

## Saatavilla olevat komponentit {#available-components}

Seuraavat komponentit ovat saatavilla ja vastaavat niiden vastaavia HTML-elementtejä:

| webforJ Luokka | HTML Elementti | Kuvaus | Lapset |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Luo hyperlinkin URL-osoitteeseen, sähköpostiosoitteeseen tai sisäiseen sijaintiin. | ✔️ |
| `Article` | `<article>` | Edustaa itseään sisältävää sisältöä, kuten blogikirjoitusta, uutisartikkelia tai keskusteluaihetta. | ✔️ |
| `Aside` | `<aside>` | Edustaa sisältöä, joka on epäsuorasti liittynyt pääsisältöön, yleensä näkyvissä sivupalkkina. | ✔️ |
| `Break` | `<hr>` | Edustaa temaattista eroa kappaletasoisessa sisällössä, näkyvissä vaakasuorana viivana. | ❌ |
| `Div` | `<div>` | Yleinen lohkotason säilö sisällön ryhmittelyyn ja tyylittelyyn. | ✔️ |
| `Emphasis` | `<em>` | Merkitsee tekstiä korostetulla painotuksella, muuttaen lauseen merkitystä. | ✔️ |
| `Fieldset` | `<fieldset>` | Ryhmittelee liittyvät lomakeohjaimet ja niiden etiketit. | ✔️ |
| `Footer` | `<footer>` | Edustaa lähimmän osiointiansaajan alatunnistetta, joka sisältää yleensä tekijänoikeusinformaatiota tai navigointitietoja. | ✔️ |
| `FormattedText` | `<pre>` | Näyttää esimuotoiltua tekstiä, säilyttäen tyhjät merkit ja rivinvaihdot täsmälleen kirjoitettuina. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Edustaa kuutta tasoa osioniittia, jossa `H1` on tärkein ja `H6` vähiten tärkeä. | ✔️ |
| `Header` | `<header>` | Edustaa johdannais sisältöä, joka yleensä sisältää otsikon, logon tai navigointilinkit. | ✔️ |
| `Iframe` | `<iframe>` | Upottaa toisen HTML-sivun sisäiseksi selausympäristöksi nykyiselle sivulle. | ❌ |
| `Img` | `<img>` | Upottaa kuvan asiakirjaan. | ❌ |
| `Legend` | `<legend>` | Antaa kuvauksen `Fieldset`-sisällölle. | ❌ |
| `ListEntry` | `<li>` | Edustaa yksittäistä elementtiä `OrderedList`- tai `UnorderedList`-listassa. | ✔️ |
| `Main` | `<main>` | Edustaa asiakirjan rungon hallitsevaa sisältöä, joka on ainutlaatuinen sivulle. | ✔️ |
| `NativeButton` | `<button>` | Natiivi interaktiivinen painikekomponentti, joka voi laukaista toimintoja tai lähettää lomakkeita. | ✔️ |
| `Nav` | `<nav>` | Edustaa sivun osaa, joka sisältää navigointilinkkejä. | ✔️ |
| `OrderedList` | `<ol>` | Edustaa numeroitua, järjestettyä listaa. | ✔️ |
| `Paragraph` | `<p>` | Edustaa kappaletta tekstiä. | ✔️ |
| `Section` | `<section>` | Edustaa yleistä itsenäistä osiota asiakirjassa, yleensä otsikon kera. | ✔️ |
| `Span` | `<span>` | Yleinen rivinsisäinen säilö tekstille ja muulle rivinsisäiselle sisällölle. | ✔️ |
| `Strong` | `<strong>` | Ilmaisee vahvan merkityksen sisältöä, yleensä esitetään lihavoituna. | ✔️ |
| `UnorderedList` | `<ul>` | Edustaa lueteltua, järjestämätöntä listaa. | ✔️ |

## Työskentely lapsien kanssa {#working-with-children}

Jokainen HTML-komponentti laajentaa `HtmlComponent`-luokkaa. ✔️-merkillä varustetut komponentit **Lapset**-sarakkeessa laajentavat `HtmlComponentContainer`-luokkaa, joka lisää menetelmät lapsikomponenttien lisäämiseen, poistamiseen ja käyttöön. Lapsia ilman komponentteja, kuten `Break`, laajentavat suoraan `HtmlComponent`-luokkaa.

Jos haluat luoda satunnaisia HTML-elementtejä, jotka eivät ole tässä lueteltuja, tai upottaa mukautettuja verkkokomponentteja, katso [`Element`](../building-ui/element) -dokumentaatio.
