---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
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

Sovellukset luottavat erilaisiin resursseihin, kuten JavaScriptiin, CSS:ään ja kuviin. Tämä dokumentti tarjoaa kattavan teknisen tutkimuksen webforJ:n resurssinkäsittelymekanismeista, kattaen deklaratiiviset annotaatiot, ohjelmalliset API-menetelmät ja mukautettujen protokollien käytön.

webforJ omaksuu modulaari lähestymistavan resurssien hallintaan, tarjoten useita mekanismeja erilaisten sovellusten tarpeiden täyttämiseksi:

- **Frontend-bundler**: Tuo npm-paketit, komponenttipohjat ja tyylitiedostokielet sovellukseen koottujen sisäänkäyntien kautta. Tämä on oletustie frontend-varoille, ja se tekee kaiken, mitä annotaatiot tekevät.
- **Deklaratiiviset annotaatiot**: Upota JavaScript- ja CSS-resursseja komponenttien tai sovellustason tasolla ilman rakennusvaihetta.
- **API-pohjainen dynaaminen injektointi**: Injektoi resursseja ajon aikana, jotta mahdollistetaan dynaaminen sovelluskäyttäytyminen.
- **Mukautetut protokollat**: Tarjoa standardoituja menetelmiä resurssien käyttöön.
- **Tiedostovirtaukset ja hallitut lataukset**: Mahdollista hallittu tiedostojen hakeminen ja siirtäminen.

## Topics {#topics}

<DocCardList className="topics-section" />
