---
sidebar_position: 1000
title: Sanasto
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  webforJ-dokumentaatiossa käytettyjen termien määritelmät, kuten DOM, Shadow
  DOM sekä muut verkkopalustan ja sovelluskehyksen käsitteet.
_i18n_title_hash: 31d109d162629f8d0cfc8ce8316c0567
_i18n_description_hash: a6875fc0c7c07fa420f5f6c4dd606cf9
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

DOM (Document Object Model) on verkkodokumenttien ohjelmointirajapinta. Se esittää sivun rakenteen objektipuuna, jossa kukin solmu vastaa HTML-elementtiä. JavaScript ja verkkokehykset käyttävät DOMia verkkosivujen sisällön, rakenteen ja tyylien dynaamiseen käsittelyyn.

## Shadow DOM {#shadow-dom}

Shadow DOM on verkkostandardi, joka mahdollistaa DOMin (Document Object Model) ja CSS:n kapseloinnin tiettyyn elementtiin, jota kutsutaan varjopuuksi. Tämä eristetty DOM ja CSS ovat erillään päädokumentin DOMista, mikä luo komponentille rajatun alueen. Shadow DOM auttaa luomaan itsenäisiä ja uudelleenkäytettäviä verkkokomponentteja, joita voi lisätä verkkosivulle huolehtimatta ristiriidoista sivun muiden tyylien ja komentosarjojen kanssa.

Se tuo myös käyttöön ominaisuuksia, kuten varjo-osat ja paikat, joiden avulla kehittäjät voivat tarjota tiettyjä varjopuun osia pääsivun mukautettaviksi. Näin komponentille voidaan välittää sisältöä ja sen ulkoasua voidaan mukauttaa joustavasti kapseloinnin säilyessä.

## Rajatut komponentit {#scoped-components}

Komponentteja, joiden tyylit on eristetty komponentin ulkopuolisten tyylien kanssa syntyvien ristiriitojen estämiseksi. Eristys toteutetaan lisäämällä komponentin tyyleihin yksilölliset data-attribuutit sen sijaan, että käytettäisiin natiivia Shadow DOMia.
