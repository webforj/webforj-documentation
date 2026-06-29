---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

Sovellukset perustuvat erilaisiin resurssityyppeihin, kuten JavaScriptiin, CSS:ään ja kuviin. Tämä asiakirja tarjoaa kattavan teknisen tutkimuksen webforJ:n resurssinhallintamekanismeista, kattaen deklaratiiviset annotaatiot, ohjelmalliset API-menetelmät ja mukautetun protokollan käytön.

webforJ omaksuu modulaarisen lähestymistavan resurssinhallintaan, tarjoten useita mekanismeja eri sovellustarpeiden käsittelemiseksi:

- **Frontend-bundler**: Tuo npm-paketteja, komponenttikehyksiä ja tyylisivukieliä sovellukseen kootun syötteen kautta. Tämä on oletustapa frontend-resursseille, ja se tekee kaiken, mitä annotaatiot tekevät.
- **Deklaratiiviset annotaatiot**: Upota JavaScript- ja CSS-resurssit komponenttien tai sovellustason tasolla ilman rakennusvaihetta.
- **API-pohjainen dynaaminen injektio**: Injektoi resursseja ajonaikaisesti dynaamisen sovelluskäyttäytymisen mahdollistamiseksi.
- **Mukautetut protokollat**: Tarjoa standardisoituja menetelmiä resurssien käyttöön.
- **Tiedostojen virran hallinta ja hallitut lataukset**: Mahdollistaa resurssitiedostojen hallitun hankinnan ja siirron.

## Topics {#topics}

<DocCardList className="topics-section" />
