---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: abb693dec702e4a253cf4e1228fb2d7e
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

webforJ propose un système de design complet nommé **DWC**. Ce n'est pas seulement un thème, c'est un système structuré et extensible qui régit le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer des interfaces cohérentes et alignées sur la marque rapidement et en toute confiance.

Au cœur de DWC se trouve un ensemble de variables CSS soigneusement conçues (tokens de design) qui couvrent des éléments visuels clés tels que les couleurs, la typographie, les bordures et l'espacement. Ces tokens servent de blocs de construction fondamentaux pour tous les styles de composant et permettent une personnalisation globale avec un effort minimal.

Pour soutenir un style plus avancé, webforJ utilise les CSS Shadow Parts, permettant aux internes des composants d'être stylisés de manière sélective sans briser l'encapsulation. Cela donne aux équipes un contrôle précis sur l'apparence des composants, même dans des applications plus grandes.

DWC comprend également une palette de couleurs personnalisable et par défaut un thème visuel clair et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

<AISkillTip skill="webforj-styling-apps" />

## Kit de design Figma {#figma-design-kit}

La [bibliothèque Figma DWC](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) est la ressource officielle de design pour créer des applications web modernes de niveau entreprise. Elle comprend un ensemble complet de composants, de styles de typographie et de tokens de couleur qui s'alignent sur le système de design DWC. Les designers et les développeurs peuvent utiliser cette bibliothèque pour construire des interfaces visuellement cohérentes, conviviales, avec un comportement de composant prévisible, un espacement précis et un contraste de couleur accessible.

<img src="/img/dwc.png" alt="Kit de design Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Thèmes {#topics}

<DocCardList className="topics-section" />
