---
sidebar_position: 1
title: Resurssien hallinta
description: >-
  Hallinnoi JavaScript-, CSS- ja muita resursseja webforJ-sovelluksissa
  deklaratiivisilla annotaatioilla, ajonaikaisilla injektiorajapinnoilla ja
  omilla URL-protokollilla.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_title_hash: a71e8aa6e3aa0dbb165119f7b68682a3
_i18n_description_hash: 1c0c8794c1ad64d279c287d021118800
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
