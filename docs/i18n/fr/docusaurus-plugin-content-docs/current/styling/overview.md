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

webforJ est livré avec un système de design complet nommé **DWC**. Ce n'est pas qu'un simple thème, c'est un système structuré et extensible qui régit le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer rapidement et avec confiance des interfaces cohérentes, alignées à la marque.

Au cœur de DWC se trouve un ensemble de variables CSS soigneusement conçues (jetons de design) qui couvrent des éléments visuels clés tels que les couleurs, la typographie, les bordures et les espacements. Ces jetons servent de blocs de construction fondamentaux pour tous les styles de composants et permettent une personnalisation mondiale avec un effort minimal.

Pour soutenir un style plus avancé, webforJ utilise les CSS Shadow Parts, permettant de styliser sélectivement les éléments internes des composants sans rompre l'encapsulation. Cela donne aux équipes un contrôle précis sur l'apparence des composants, même dans des applications plus grandes.

DWC comprend également une palette de couleurs personnalisable et par défaut un thème visuel épuré et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

<AISkillTip skill="webforj-styling-apps" />

## Kit de design Figma {#figma-design-kit}

La [bibliothèque Figma DWC](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) est la ressource de design officielle pour créer des applications web modernes de niveau entreprise. Elle comprend un ensemble complet de composants, de styles typographiques et de jetons de couleur qui s'alignent avec le système de design DWC. Les designers et les développeurs peuvent utiliser cette bibliothèque pour construire des interfaces utilisateur cohérentes sur le plan visuel, avec un comportement de composant prévisible, un espacement précis et un contraste de couleur accessible.

<img src="/img/dwc.png" alt="Kit de Design Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Capture d'écran du Kit de Design Figma](./path-to-your-screenshot.png) -->

## Thèmes {#topics}

<DocCardList className="topics-section" />
