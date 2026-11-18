---
sidebar_position: 35
title: Suodatus
slug: filtering
description: >-
  Rajaa Table-rivejä sidotun Repositoryn kautta käytettävällä Predicate-ehdolla
  ja päivitä tulokset commitin yhteydessä.
_i18n_title_hash: 9a588db8471730dbfebac65cd5467ad8
_i18n_description_hash: 16cf6eedec1585c3016bf61678214883
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
`Table`-komponentti mahdollistaa suodatuksen toteuttamisen, jotta voit rajoittaa näytettävää dataa tiettyjen kriteerien perusteella. Suodatus voidaan toteuttaa määrittelemällä suodatuskriteeri käyttäen `setFilter(Predicate<T> filter)` -menetelmää, joka on tarjottu taulukon kanssa liitetyssä `Repository`-luokassa.

Seuraavassa esimerkissä käytetään käyttäjän määrittelemää kriteeriä hakukentästä ja `setBaseFilter()` -menetelmää, jotta suodatin voidaan soveltaa `CollectionRepository`-luokkaan `MusicRecord`-otsikoiden perusteella. Kun `commit()`-menetelmää kutsutaan, taulukko päivittyy suodatetulla datalla.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableFilteringView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::note
`setBaseFilter()` -menetelmä kuuluu `CollectionRepository`-luokkaan, ei `Table`-komponenttiin.
:::
