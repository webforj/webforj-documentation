---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 04ddb356576ffb59456111d5b45fd4da
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

webforJ est livré avec un système de design complet nommé **DWC**. Ce n'est pas seulement un thème, c'est un système structuré et extensible qui régit le langage visuel de votre application. DWC est conçu pour aider les développeurs et les designers à créer rapidement et avec confiance des interfaces cohérentes et alignées sur la marque.

Au cœur de DWC se trouve un ensemble de variables CSS soigneusement conçues (tokens de design) qui couvrent les éléments visuels clés tels que les couleurs, la typographie, les bordures et les espacements. Ces tokens servent de blocs de construction fondamentaux pour tous les styles de composants et permettent une personnalisation globale avec un effort minimal.

Pour prendre en charge des stylisations plus avancées, webforJ utilise les CSS Shadow Parts, permettant de styliser sélectivement l'intérieur des composants sans rompre l'encapsulation. Cela donne aux équipes un contrôle précis sur l'apparence des composants, même dans des applications plus grandes.

DWC comprend également une palette de couleurs personnalisable et par défaut un thème visuel clair et léger, mais chaque aspect peut être adapté au style de votre marque ou produit.

## Topics {#topics}

<DocCardList className="topics-section" />
