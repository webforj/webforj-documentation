---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
description: >-
  Organize webforJ routes into parent-child trees with view routes, layout
  routes, outlets, and nested components.
_i18n_hash: 4bfc9c9d46d57c866c67a2baaf2e3c3a
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Reittihierarkia

Reitit on järjestetty hierarkkiseen puurakenteeseen, joka mahdollistaa kehittäjien määritellä ulkoasuja, hallita näkymiä ja dynaamisesti renderoida komponentteja sovelluksen eri osiin.

Keskeiset käsitteet, joihin törmäät rakentaessasi webforJ-reititettävää sovellusta, sisältävät:

- **Reittihierarkia**: Järjestää reitit vanhempi-lapsi-rakenteisiin modulaarista käyttöliittymää kehittäessä.
- **Reittityypit**: Reitit luokitellaan **Näkymäreiteiksi** tai **Ulkoasureiteiksi**, joilla kummallakin on oma tarkoituksensa.
- **Sisäkkäiset reitit**: Reitit voidaan asettaa toistensa sisään, jolloin vanhempikomponentit voivat renderöidä lapsikomponentteja määritellyissä ulosotoissa.
- **Ulosotot**: Komponentit, joihin lapsinäkymät injektoidaan dynaamisesti vanhempien ulkoasuissa.
- **Ulkoasut**: Erityiset reitit, jotka ympäröivät lapsikomponentteja ilman, että ne vaikuttavat URL-osoitteeseen, tarjoten jaettuja käyttöliittymäelementtejä, kuten otsikoita, alatunnisteita tai sivupalkkeja.

## Aiheet {#topics}

<DocCardList className="topics-section" />
