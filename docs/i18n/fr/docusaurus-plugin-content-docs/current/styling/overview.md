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

webforJ est livré avec un système de design complet nommé **DWC**. Ce n'est pas seulement un thème, c'est un système structuré et extensible qui régit le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer des interfaces cohérentes et alignées sur la marque rapidement et en toute confiance.

Au cœur de DWC se trouve un ensemble de variables CSS soigneusement conçues (tokens de design) qui couvrent les éléments visuels clés tels que les couleurs, la typographie, les bordures et l'espacement. Ces tokens servent de blocs de construction fondamentaux pour tous les styles de composants et permettent une personnalisation globale avec un minimum d'effort.

Pour supporter un style plus avancé, webforJ utilise les CSS Shadow Parts, permettant de styliser sélectivement les éléments internes des composants sans rompre l'encapsulation. Cela donne aux équipes un contrôle précis sur l'apparence des composants, même à travers des applications plus larges.

DWC comprend également une palette de couleurs personnalisable et passe par défaut à un thème visuel clair et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

<AISkillTip skill="webforj-styling-apps" />

## Kit de design Figma {#figma-design-kit}

Le [Kit de Design DWC](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) est la ressource officielle Figma pour la conception d'applications webforJ. Il couvre chaque composant DWC avec ses thèmes, ses étendues et ses états, ainsi que les palettes de couleurs, la typographie, l'espacement et les tokens d'ombre du système de design en tant que variables et styles Figma en mode clair et sombre. Les designers et développeurs peuvent utiliser le kit pour créer des interfaces visuellement cohérentes, conviviales avec un comportement des composants prévisible, un espacement précis et un contraste de couleur accessible.

<iframe
  title="DWC Design Kit"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Sujets {#topics}

<DocCardList className="topics-section" />
