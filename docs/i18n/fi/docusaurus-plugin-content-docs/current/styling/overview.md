---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Style webforJ apps with the DWC design system using CSS custom properties,
  palettes, shadow parts, and the Figma kit.
_i18n_hash: bacf450dadef59e4496e78d465a1e44d
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

webforJ toimittaa kattavan suunnittelujärjestelmän nimeltä **DWC**. Se on enemmän kuin pelkkä teema, se on rakenteellinen, laajennettavissa oleva järjestelmä, joka säätelee sovelluksesi visuaalista kieltä. DWC on rakennettu auttamaan kehittäjiä ja suunnittelijoita luomaan johdonmukaisia, brändille linjassa olevia käyttöliittymiä nopeasti ja itseluottamuksella.

DWC:n ytimessä on joukko huolellisesti suunniteltuja CSS-muuttujia (suunnittelutunnuksia), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunat ja välistykset. Nämä tunnukset toimivat kaikkiin komponenttityyleihin perustuvina rakennuspalikoina ja mahdollistavat globaalin mukautuksen minimaalisen vaivannäön avulla.

Tukeakseen edistyneempää tyylittelyä webforJ käyttää CSS Shadow Parts -ominaisuutta, joka mahdollistaa komponenttien sisäosien valikoivan tyylittelyn ilman kapseloinnin rikkoutumista. Tämä antaa tiimeille tarkkaa hallintaa siitä, miltä komponentit näyttävät, jopa suuremmissa sovelluksissa.

DWC:hen kuuluu myös mukautettava väriasteikko, ja se oletusarvoisesti käyttää puhdasta, vaaleaa visuaalista teemaa, mutta jokainen osa-alue voidaan mukauttaa brändisi tai tuotetyylisi mukaan.

<AISkillTip skill="webforj-styling-apps" />

## Figma-suunnittelupaketti {#figma-design-kit}

[DWC-suunnittelupaketti](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) on virallinen Figma-resurssi webforJ-sovellusten suunnitteluun. Se kattaa jokaisen DWC-komponentin teemoineen, laajuuksineen ja tiloineen, sekä suunnittelujärjestelmän väripaletit, typografian, välistykset ja varjotunnukset Figma-muuttujina ja tyyleinä sekä vaaleassa että tummassa tilassa. Suunnittelijat ja kehittäjät voivat käyttää pakettia visuaalisesti johdonmukaisten, käyttäjäystävällisten käyttöliittymien rakentamiseen, joilla on ennakoitavaa komponenttikäyttäytymistä, tarkkoja välistyksiä ja saavutettavaa värieroa.

<iframe
  title="DWC Design Kit"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Aiheet {#topics}

<DocCardList className="topics-section" />
