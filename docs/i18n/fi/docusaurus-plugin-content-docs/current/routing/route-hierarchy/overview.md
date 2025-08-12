---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Reittihierarkia

Reitit on järjestetty hierarkkiseen puurakenteeseen, joka mahdollistaa kehittäjille käyttöliittymien määrittämisen, näkymien hallinnan ja komponenttien dynaamisen renderöinnin eri osissa sovellusta.

Keskeiset käsitteet, joihin törmäät rakentaessasi webforJ-reititettävää sovellusta, sisältävät:

- **Reittihierarkia**: Järjestää reitit vanhempi-lapsi-rakenteisiin modulaarista käyttöliittymäkehitystä varten.
- **Reittityypit**: Reitit jaotellaan joko **Näkymäreitteihin** tai **Layout-reitteihin**, joista kummallakin on erilainen tarkoitus.
- **Sisäkkäiset reitit**: Reittejä voidaan sisällyttää toisiinsa, jolloin vanhempikomponentit voivat renderöidä lapsikomponentteja määrätyissä ulostuloissa.
- **Ulostulot**: Komponentit, joihin lapsinäkymät injektoidaan dynaamisesti vanhempien asetteluihin.
- **Asettelut**: Erityiset reitit, jotka ympäröivät lapsikomponentteja ilman, että ne vaikuttavat URL-osoitteeseen, tarjoten yhteisiä käyttöliittymäelementtejä, kuten otsikoita, alatunnisteita tai sivupalkkeja.

## Aiheita {#topics}

<DocCardList className="topics-section" />
