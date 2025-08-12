---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4e709dc1db793a4ae1ed6f944375b512
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

webforJ est livré avec un système de conception complet nommé **DWC**. Ce n'est pas seulement un thème, c'est un système structuré et extensible qui gouverne le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer rapidement et en toute confiance des interfaces cohérentes et alignées sur la marque.

Au cœur de DWC, se trouve un ensemble de variables CSS soigneusement conçues (tokens de conception) qui couvrent les éléments visuels clés tels que les couleurs, la typographie, les bordures et l'espacement. Ces tokens servent de blocs de construction fondamentaux pour tous les styles de composants et permettent une personnalisation globale avec un minimum d'effort.

Pour soutenir un style plus avancé, webforJ utilise les CSS Shadow Parts, permettant de styliser sélectivement les internes des composants sans rompre l'encapsulation. Cela donne aux équipes un contrôle précis sur l'apparence des composants, même à travers des applications plus grandes.

DWC comprend également une palette de couleurs personnalisable et par défaut un thème visuel clair et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

## Figma Design Kit {#figma-design-kit}

La [bibliothèque Figma DWC](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) est la ressource de conception officielle pour créer des applications web modernes de niveau entreprise. Elle comprend un ensemble complet de composants, de styles typographiques et de tokens de couleurs qui s'alignent sur le système de conception DWC. Avec cette bibliothèque, les designers et développeurs peuvent créer des interfaces visuellement cohérentes et conviviales qui équilibrent fonctionnalité et expérience utilisateur raffinée.

<img src="/img/dwc.png" alt="Kit de conception Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Topics {#topics}

<DocCardList className="topics-section" />
