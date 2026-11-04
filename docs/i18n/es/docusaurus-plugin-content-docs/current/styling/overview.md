---
title: Estilos
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

webforJ incluye un completo sistema de diseño llamado **DWC**. Es mucho más que un tema: es un sistema estructurado y extensible que define el lenguaje visual de tu aplicación. DWC está diseñado para ayudar a desarrolladores y diseñadores a crear interfaces coherentes y alineadas con la marca de forma rápida y segura.

En esencia, DWC proporciona un conjunto de variables CSS (tokens de diseño) cuidadosamente diseñadas que abarcan elementos visuales clave, como colores, tipografía, bordes y espaciado. Estos tokens son los componentes básicos de todos los estilos de los componentes y permiten personalizar el conjunto con un mínimo esfuerzo.

Para facilitar estilos más avanzados, webforJ usa CSS Shadow Parts, que permiten aplicar estilos selectivamente a los elementos internos de los componentes sin romper el encapsulamiento. Así, los equipos pueden controlar con precisión la apariencia de los componentes, incluso en aplicaciones de gran tamaño.

DWC también incluye una paleta de colores personalizable y, de forma predeterminada, un tema visual claro y sencillo, pero todos sus aspectos se pueden adaptar a la marca o al estilo de tu producto.

<AISkillTip skill="webforj-styling-apps" />

## Kit de diseño de Figma {#figma-design-kit}

El [DWC Design Kit](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) es el recurso oficial de Figma para diseñar aplicaciones webforJ. Incluye todos los componentes de DWC, con sus temas, variantes y estados, además de las paletas de colores, la tipografía, el espaciado y los tokens de sombra del sistema de diseño, disponibles como variables y estilos de Figma para los modos claro y oscuro. Los diseñadores y desarrolladores pueden usar el kit para crear interfaces coherentes desde el punto de vista visual y fáciles de usar, con un comportamiento predecible de los componentes, un espaciado preciso y un contraste de color accesible.

<a href="https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" target="_blank" rel="noopener noreferrer">
  <img
    src="https://cdn.webforj.com/webforj-documentation/figma/dwc-design-kit-cover.jpg"
    alt="DWC Design Kit en Figma Community"
    style={{width: '100%', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
    loading="lazy"
  />
</a>

## Temas {#topics}

<DocCardList className="topics-section" />
