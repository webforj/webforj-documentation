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

webforJ wordt geleverd met een uitgebreid ontwerp systeem genaamd **DWC**. Het is meer dan alleen een thema, het is een gestructureerd, uitbreidbaar systeem dat de visuele taal van je app beheert. DWC is gebouwd om ontwikkelaars en ontwerpers te helpen bij het snel en met vertrouwen creëren van consistente, merk-gebaseerde interfaces.

In de kern biedt DWC een set zorgvuldig ontworpen CSS-variabelen (ontwerp tokens) die de belangrijkste visuele elementen dekken zoals kleuren, typografie, randafwerkingen en ruimte. Deze tokens dienen als de fundamentele bouwstenen voor alle component stijlen en maken wereldwijde aanpassing met minimale inspanning mogelijk.

Om meer geavanceerde styling te ondersteunen, gebruikt webforJ CSS Shadow Parts, waardoor interne componenten selectief gestyled kunnen worden zonder de encapsulatie te doorbreken. Dit geeft teams fijne controle over hoe componenten verschijnen, zelfs in grotere applicaties.

DWC bevat ook een aanpasbare kleurpalet en standaard een schoon, licht visueel thema, maar elk aspect kan worden aangepast aan de stijl van jouw merk of product.

<AISkillTip skill="webforj-styling-apps" />

## Figma ontwerpkit {#figma-design-kit}

De [DWC Ontwerpkit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) is de officiële Figma-bron voor het ontwerpen van webforJ-apps. Het dekt elke DWC-component met zijn thema's, extensies en staten, samen met de kleurpaletten, typografie, spatiëring en schaduwtokens van het ontwerpsysteem als Figma-variabelen en stijlen in zowel de lichte als donkere modus. Ontwerpers en ontwikkelaars kunnen de kit gebruiken om visueel consistente, gebruiksvriendelijke interfaces te bouwen met voorspelbaar componentgedrag, precieze spatiëring en toegankelijke kleurcontrasten.

<iframe
  title="DWC Ontwerpkit"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
