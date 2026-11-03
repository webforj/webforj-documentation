---
title: Mise en forme
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

webforJ est livré avec un système de conception complet nommé **DWC**. Bien plus qu’un simple thème, il s’agit d’un système structuré et extensible qui définit le langage visuel de votre application. DWC aide les développeurs et les concepteurs à créer rapidement et en toute confiance des interfaces cohérentes, fidèles à leur marque.

Au cœur de DWC se trouve un ensemble de variables CSS (jetons de conception) soigneusement conçues, qui couvrent des éléments visuels clés comme les couleurs, la typographie, les bordures et l’espacement. Ces jetons constituent les éléments de base de tous les styles des composants et permettent une personnalisation globale avec un minimum d’effort.

Pour permettre un style plus avancé, webforJ utilise les CSS Shadow Parts, qui permettent de styliser certains éléments internes des composants sans compromettre l’encapsulation. Les équipes disposent ainsi d’un contrôle précis sur l’apparence des composants, même dans les applications de grande envergure.

DWC comprend également une palette de couleurs personnalisable et adopte par défaut un thème visuel clair et épuré, mais chaque aspect peut être adapté à l’identité de votre marque ou au style de votre produit.

<AISkillTip skill="webforj-styling-apps" />

## Kit de conception Figma {#figma-design-kit}

Le [kit de conception DWC](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) est la ressource Figma officielle pour concevoir des applications webforJ. Il couvre chaque composant DWC, avec ses thèmes, ses tailles et ses états, ainsi que les palettes de couleurs, la typographie, l’espacement et les jetons d’ombre du système de conception, sous forme de variables et de styles Figma en modes clair et sombre. Les concepteurs et les développeurs peuvent utiliser ce kit pour créer des interfaces cohérentes et conviviales, avec un comportement prévisible des composants, un espacement précis et un contraste des couleurs accessible.

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="Kit de conception DWC sur Figma Community"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## Sujets {#topics}

<DocCardList className="topics-section" />
