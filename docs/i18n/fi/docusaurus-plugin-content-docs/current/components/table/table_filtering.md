---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
`Table`-komponentti mahdollistaa suodattamisen, jolla voidaan rajata näytettävää dataa tiettyjen kriteerien mukaan. Suodattaminen voidaan toteuttaa määrittelemällä suodatuskriteeri käyttämällä `setFilter(Predicate<T> filter)` -metodia, joka on saatavilla taulukkoon liitetystä `Repository`:sta.

Seuraavassa esimerkissä käytetään käyttäjän määrittelemää kriteeriä hakukentästä ja `setBaseFilter()` -metodia suodattimen soveltamiseksi `CollectionRepository`:lle `MusicRecord`-otsikoiden perusteella. Kun `commit()`-metodia kutsutaan, taulukko päivitetään suodatetulla datalla.

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
`setBaseFilter()`-metodi kuuluu `CollectionRepository`-luokkaan, ei `Table`-komponenttiin.
:::
