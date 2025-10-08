---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
_i18n_hash: 008eef50f8ab27ec3f8a455fb5649f41
---
`Table`-komponentti mahdollistaa suodattamisen toteuttamisen, jotta voidaan kaventaa näytettävää dataa tiettyjen kriteerien mukaan. Suodattaminen voidaan saavuttaa määrittelemällä suodatuskriteerit käyttäen `setFilter(Predicate<T> filter)` -metodia, joka on tarjottu taulukkoon liittyvälle `Repository`:lle.

Seuraavassa esimerkissä käytetään käyttäjän määrittelemiä kriteerejä hakukentästä ja `setBaseFilter()` -metodia suodattimen soveltamiseen `CollectionRepository`:lle `MusicRecord`:ien otsikoiden perusteella. Kun `commit()`-metodia aktivoidaan, taulukko päivitetään suodatetulla datalla.

:::note
`setBaseFilter()` -metodi kuuluu `CollectionRepository` -luokalle, ei `Table` -komponentille.
:::
