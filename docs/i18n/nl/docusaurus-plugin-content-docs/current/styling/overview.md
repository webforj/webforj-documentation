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

webforJ levert een uitgebreid ontwerpsysteem genaamd **DWC**. Het is meer dan alleen een thema, het is een gestructureerd, uitbreidbaar systeem dat de visuele taal van je app bepaalt. DWC is ontworpen om ontwikkelaars en ontwerpers te helpen bij het snel en met vertrouwen creëren van consistente, merk-georiënteerde interfaces.

In de kern biedt DWC een set zorgvuldig ontworpen CSS-variabelen (ontwerptokens) die belangrijke visuele elementen zoals kleuren, typografie, randen en afstanden dekken. Deze tokens dienen als de fundamentele bouwstenen voor alle componentstijlen en maken wereldwijde aanpassing met minimale inspanning mogelijk.

Om geavanceerdere styling te ondersteunen, maakt webforJ gebruik van CSS Shadow Parts, waardoor de interne delen van componenten selectief kunnen worden gestyled zonder de encapsulatie te doorbreken. Dit geeft teams gedetailleerde controle over hoe componenten eruitzien, zelfs in grotere applicaties.

DWC bevat ook een aanpasbare kleurenpalet en standaard een schone, lichte visuele thema, maar elk aspect kan worden aangepast aan jouw merk of productstijl.

<AISkillTip skill="webforj-styling-apps" />

## Figma ontwerpkit {#figma-design-kit}

De [DWC Figma-bibliotheek](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) is de officiële ontwerpmiddleware voor het creëren van moderne, enterprise-grade webapplicaties. Het omvat een uitgebreide set componenten, typografiestijlen en kleurentokens die in lijn zijn met het DWC-ontwerpsysteem. Ontwerpers en ontwikkelaars kunnen deze bibliotheek gebruiken om visueel consistente, gebruiksvriendelijke interfaces te bouwen met voorspelbaar componentgedrag, precieze afstanden en toegankelijke kleurcontrasten.

<img src="/img/dwc.png" alt="Figma Ontwerpkit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
