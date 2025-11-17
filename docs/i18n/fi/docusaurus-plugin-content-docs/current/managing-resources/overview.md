---
sidebar_position: 1
title: Resurssien Hallinta
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: a18b5fd490eca0891f470c7ccdb44e94
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Sovellukset riippuvat erilaisista resurssityypeistä, kuten JavaScriptistä, CSS:stä ja kuvista. Tämä asiakirja tarjoaa kattavan teknisen tutkimuksen webforJ:n resurssinhallintamekanismeista, kattaen deklaratiiviset annotaatiot, ohjelmalliset API-menetelmät ja mukautetun protokollan käytön.

webforJ omaksuu moduulipohjaisen lähestymistavan resurssien hallintaan, tarjoten useita mekanismeja erilaisten sovellustarpeiden ratkaisemiseen:

- **Deklaratiiviset annotaatiot**: Upota JavaScript- ja CSS-resursseja komponenttitasolla tai sovellustasolla.
- **API-pohjainen dynaaminen injektio**: Injektoi resursseja ajonaikana dynaamisen sovelluskäyttäytymisen mahdollistamiseksi.
- **Mukautetut protokollat**: Tarjoa standardoituja menetelmiä resurssien käyttöön.
- **Tiedostovirtauksen ja hallittujen latausten mahdollistaminen**: Helpota resurssitiedostojen hallittua noutoa ja siirtoa.

## Topics {#topics}

<DocCardList className="topics-section" />
