---
title: Stijlen
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

webforJ wordt geleverd met een uitgebreid ontwerpsysteem genaamd **DWC**. Het is meer dan alleen een thema: het is een gestructureerd, uitbreidbaar systeem dat de visuele taal van je app bepaalt. DWC is ontwikkeld om ontwikkelaars en ontwerpers te helpen snel en vol vertrouwen consistente interfaces te maken die aansluiten bij hun merk.

DWC biedt als basis een reeks zorgvuldig ontworpen CSS-variabelen (ontwerptokens) voor belangrijke visuele elementen, zoals kleuren, typografie, randen en tussenruimten. Deze tokens vormen de bouwstenen voor alle componentstijlen en maken het mogelijk om met minimale inspanning globale aanpassingen te doen.

Voor geavanceerdere stijlaanpassingen gebruikt webforJ CSS Shadow Parts, waarmee onderdelen binnen componenten gericht kunnen worden gestileerd zonder de inkapseling te doorbreken. Zo hebben teams nauwkeurige controle over de weergave van componenten, zelfs in grotere applicaties.

DWC bevat ook een aanpasbaar kleurenpalet en gebruikt standaard een helder, licht thema. Elk aspect kan echter worden aangepast aan de stijl van je merk of product.

<AISkillTip skill="webforj-styling-apps" />

## Figma-ontwerpset {#figma-design-kit}

De [DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) is de officiële Figma-bron voor het ontwerpen van webforJ-apps. De kit bevat alle DWC-componenten met hun thema's, afmetingen en statussen, evenals de kleurenpaletten, typografie, tussenruimten en schaduwtokens van het ontwerpsysteem als Figma-variabelen en -stijlen in zowel de lichte als de donkere modus. Ontwerpers en ontwikkelaars kunnen de kit gebruiken om visueel consistente, gebruiksvriendelijke interfaces te maken met voorspelbaar componentgedrag, nauwkeurige tussenruimten en toegankelijke kleurcontrasten.

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="DWC Design Kit in Figma Community"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
