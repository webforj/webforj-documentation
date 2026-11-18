---
sidebar_position: 1
title: Reittihierarkia
hide_giscus_comments: true
description: >-
  Järjestä webforJ-reitit hierarkkisiksi emo–lapsipuiksi näkymäreittien,
  asettelureittien, outletien ja sisäkkäisten komponenttien avulla.
_i18n_title_hash: 9efa0a285cc1eb30cf7a63d79dc97170
_i18n_description_hash: 7382223dd1bfaa572d36732205d62653
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
