---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: 40e7755b35318ea88eb990c6b6dbd240
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

webforJ toimittaa kattavan suunnittelujärjestelmän nimeltä **DWC**. Se on enemmän kuin pelkkä teema, se on rakenteellinen, laajennettavissa oleva järjestelmä, joka säätelee sovelluksesi visuaalista kieltä. DWC on rakennettu auttamaan kehittäjiä ja suunnittelijoita luomaan johdonmukaisia, brändin mukaisia käyttöliittymiä nopeasti ja vahvasti.

Perusperiaatteeltaan DWC tarjoaa joukon tarkasti suunniteltuja CSS-muuttujia (suunnittelutokeneita), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunat ja välistykset. Nämä tokenit toimivat kaikkien komponenttityylien perustavanlaatuisina rakennuspalikoina ja mahdollistavat globaaliin mukauttamiseen minimaalista vaivannäköä.

Edistyneemmän tyylittelyn tukemiseksi webforJ hyödyntää CSS Shadow Parts -tekniikkaa, joka mahdollistaa komponenttien sisäisten osien valikoivan tyylittelyn ilman kapseloinnin murtumista. Tämä antaa tiimeille tarkkaa hallintaa siitä, miltä komponentit näyttävät, jopa suurissa sovelluksissa.

DWC sisältää myös räätälöitävän väriasteikon ja oletuksena puhtaan, vaalean visuaalisen teeman, mutta jokainen osa voidaan mukauttaa brändisi tai tuotteen tyyliin.

<AISkillTip skill="webforj-styling-apps" />

## Figma-suunnittelupaketti {#figma-design-kit}

[DWC Figma -kirjasto](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) on virallinen suunnitteluresurssi modernien, yritysluokan verkkosovellusten luomiseen. Se sisältää kattavan joukon komponentteja, typografiastylejä ja väritokeneita, jotka vastaavat DWC-suunnittelujärjestelmää. Suunnittelijat ja kehittäjät voivat käyttää tätä kirjastoa visuaalisesti johdonmukaisten, käyttäjäystävällisten käyttöliittymien rakentamiseen, joissa komponenttien käyttäytyminen on ennustettavaa, välistykset tarkkoja ja värikontrasti saavutettavaa.

<img src="/img/dwc.png" alt="Figma-suunnittelupaketti" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Aiheita {#topics}

<DocCardList className="topics-section" />
