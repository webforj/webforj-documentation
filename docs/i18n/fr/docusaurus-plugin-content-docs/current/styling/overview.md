---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: dfbf68fb580d6fb1622f513be8983739
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

webforJ est livré avec un système de design complet nommé **DWC**. Ce n'est pas seulement un thème, c'est un système structuré et extensible qui régit le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer des interfaces cohérentes, alignées sur la marque, rapidement et avec confiance.

Au cœur de DWC se trouve un ensemble de variables CSS soigneusement conçues (tokens de design) qui couvrent les éléments visuels clés tels que les couleurs, la typographie, les bordures et l'espacement. Ces tokens servent de blocs de construction fondamentaux pour tous les styles de composants et permettent une personnalisation globale avec un effort minimal.

Pour prendre en charge un style plus avancé, webforJ utilise les parties d'ombre CSS, permettant aux éléments internes des composants d'être stylisés de manière sélective sans rompre l'encapsulation. Cela donne aux équipes un contrôle granulaire sur l'apparence des composants, même à travers des applications plus grandes.

DWC comprend également une palette de couleurs personnalisable et par défaut un thème visuel clair et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

## Kit de Design Figma {#figma-design-kit}

La [bibliothèque Figma DWC](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) est la ressource de design officielle pour créer des applications web modernes de qualité entreprise. Elle comprend un ensemble complet de composants, de styles typographiques et de tokens de couleur qui s'alignent sur le système de design DWC. Avec cette bibliothèque, les designers et les développeurs peuvent construire des interfaces visuellement cohérentes et conviviales, équilibrant fonctionnalité et expérience utilisateur raffinée.

<img src="/img/dwc.png" alt="Kit de Design Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Thèmes {#topics}

<DocCardList className="topics-section" />
