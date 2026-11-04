---
title: Tyylittely
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 18caef78e838347a9b6376fb635573d9
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

webforJ sisältää kattavan DWC-nimisen suunnittelujärjestelmän. Se on paljon enemmän kuin pelkkä teema: se on jäsennelty ja laajennettava järjestelmä, joka määrittää sovelluksesi visuaalisen ilmeen. DWC auttaa kehittäjiä ja suunnittelijoita luomaan yhtenäisiä ja brändin mukaisia käyttöliittymiä nopeasti ja luottavaisin mielin.

DWC tarjoaa perustana huolellisesti suunniteltuja CSS-muuttujia (suunnittelutunnisteita), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunukset ja välit. Nämä tunnisteet toimivat kaikkien komponenttityylien perusrakennuspalikoina ja mahdollistavat koko järjestelmän mukauttamisen vähällä vaivalla.

Edistyneempien tyylien tueksi webforJ käyttää CSS Shadow Parts -ominaisuuksia, joiden avulla komponenttien sisäisiä osia voi tyylitellä valikoivasti kapselointia rikkomatta. Näin tiimit voivat hallita komponenttien ulkoasua yksityiskohtaisesti myös suurissa sovelluksissa.

DWC sisältää myös mukautettavan väripaletin, ja sen oletusteema on selkeä ja vaalea. Kaikkia sen ominaisuuksia voi kuitenkin mukauttaa brändiisi tai tuotteesi tyyliin sopiviksi.

<AISkillTip skill="webforj-styling-apps" />

## Figman suunnittelupaketti {#figma-design-kit}

[DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) on virallinen Figma-resurssi webforJ-sovellusten suunnitteluun. Se kattaa kaikki DWC-komponentit teemoineen, kokoineen ja tiloineen sekä suunnittelujärjestelmän väripaletit, typografian, välit ja varjotunnisteet Figma-muuttujina ja -tyyleinä sekä vaaleassa että tummassa tilassa. Suunnittelijat ja kehittäjät voivat paketin avulla luoda visuaalisesti yhtenäisiä ja käyttäjäystävällisiä käyttöliittymiä, joiden komponenttien toiminta on ennakoitavaa, välit täsmällisiä ja värikontrasti saavutettava.

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="DWC Design Kit Figman yhteisössä"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## Aiheet {#topics}

<DocCardList className="topics-section" />
