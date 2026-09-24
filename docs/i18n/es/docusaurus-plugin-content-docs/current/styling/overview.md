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

webforJ se envía con un sistema de diseño integral llamado **DWC**. Es más que solo un tema, es un sistema estructurado y extensible que gobierna el lenguaje visual de tu aplicación. DWC está construido para ayudar a desarrolladores y diseñadores a crear interfaces consistentes y alineadas con la marca de manera rápida y confiada.

En su núcleo, DWC proporciona un conjunto de variables CSS cuidadosamente diseñadas (tokens de diseño) que cubren elementos visuales clave como colores, tipografía, bordes y espaciado. Estos tokens sirven como los bloques de construcción fundamentales para todos los estilos de componentes y permiten una personalización global con un esfuerzo mínimo.

Para soportar un estilo más avanzado, webforJ utiliza CSS Shadow Parts, permitiendo que los internals de los componentes sean estilizados selectivamente sin romper la encapsulación. Esto proporciona a los equipos un control detallado sobre cómo aparecen los componentes, incluso en aplicaciones más grandes.

DWC también incluye una paleta de colores personalizable y por defecto tiene un tema visual limpio y claro, pero cada aspecto puede ser adaptado al estilo de tu marca o producto.

<AISkillTip skill="webforj-styling-apps" />

## Kit de diseño de Figma {#figma-design-kit}

El [Kit de Diseño DWC](https://www.figma.com/community/file/1682060886525639971/dwc-design-kit) es el recurso oficial de Figma para diseñar aplicaciones webforJ. Cubre cada componente de DWC con sus temas, expansiones y estados, junto con las paletas de colores, tipografía, espaciado y tokens de sombra del sistema de diseño como variables y estilos de Figma en modo claro y oscuro. Los diseñadores y desarrolladores pueden usar el kit para construir interfaces visualmente consistentes, amigables para el usuario, con un comportamiento de componente predecible, espaciado preciso y contraste de color accesible.

<iframe
  title="Kit de Diseño DWC"
  src="https://embed.figma.com/design/xZVIDRnF7FJ3Dibb5At2lU/DWC-Design-Kit?node-id=6707-254&embed-host=webforj-docs"
  style={{width: '100%', aspectRatio: '16 / 10', borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}}
  loading="lazy"
  allowFullScreen
/>

## Temas {#topics}

<DocCardList className="topics-section" />
