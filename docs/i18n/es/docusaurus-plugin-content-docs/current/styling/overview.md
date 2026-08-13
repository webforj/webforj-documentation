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

webforJ se envía con un sistema de diseño completo llamado **DWC**. Es más que un tema, es un sistema estructurado y extensible que rige el lenguaje visual de tu aplicación. DWC está construido para ayudar a desarrolladores y diseñadores a crear interfaces coherentes y alineadas con la marca de manera rápida y confiable.

En su núcleo, DWC proporciona un conjunto de variables CSS cuidadosamente diseñadas (tokens de diseño) que cubren elementos visuales clave como colores, tipografía, bordes y espaciado. Estos tokens sirven como bloques de construcción fundamentales para todos los estilos de los componentes y permiten una personalización global con un esfuerzo mínimo.

Para soportar un estilo más avanzado, webforJ aprovecha las Partes Sombra de CSS, lo que permite estilizar selectivamente los internos de los componentes sin romper la encapsulación. Esto otorga a los equipos un control detallado sobre cómo aparecen los componentes, incluso a través de aplicaciones más grandes.

DWC también incluye una paleta de colores personalizable y por defecto muestra un tema visual limpio y ligero, pero cada aspecto puede adaptarse al estilo de tu marca o producto.

<AISkillTip skill="webforj-styling-apps" />

## Kit de diseño de Figma {#figma-design-kit}

La [biblioteca DWC de Figma](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) es el recurso de diseño oficial para crear aplicaciones web modernas de grado empresarial. Incluye un conjunto completo de componentes, estilos de tipografía y tokens de color que se alinean con el sistema de diseño DWC. Diseñadores y desarrolladores pueden usar esta biblioteca para construir interfaces visualmente coherentes, amigables para el usuario, con un comportamiento de componente predecible, espaciado preciso y contraste de color accesible.

<img src="/img/dwc.png" alt="Kit de Diseño de Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Temas {#topics}

<DocCardList className="topics-section" />
