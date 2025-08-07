---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: a384d1b849730a301f5bc1d0a20e9c41
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Reittihierarkia

Reitit on organisoitu hierarkkiseen puu- rakenteeseen, joka mahdollistaa kehittäjien määritellä asetteluja, hallita näkymiä ja renderöidä komponentteja dynaamisesti eri osissa sovellusta.

Keskeiset käsitteet, joihin kohtaat rakentamalla webforJ-reititettävää sovellusta, sisältävät:

- **Reittihierarkia**: Järjestää reitit vanhempi-lapsi -rakenteisiin modulaarista käyttöliittymän kehittämistä varten.
- **Reittityypit**: Reitit jaotellaan joko **Näkymäreitteihin** tai **Asettelu-reitteihin**, joista kummallakin on oma tarkoituksensa.
- **Nidottuja reittejä**: Reittejä voidaan sisällyttää toisiinsa, jolloin vanhempikomponentit voivat renderöidä lapsikomponentteja määritellyissä lähteissä.
- **Lähteet**: Komponentit, joihin lapsinäkymät injektoidaan dynaamisesti vanhempien asetteluihin.
- **Asettelu**: Erityiset reitit, jotka kietovat lapsikomponentit ilman, että ne vaikuttavat URL-osoitteeseen, tarjoten yhteisiä käyttöliittymän elementtejä, kuten otsikoita, alatunnisteita tai sivupalkkeja.

## Aihealueet {#topics}

<DocCardList className="topics-section" />
