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

webforJ se envía con un sistema de diseño completo llamado **DWC**. No es solo un tema, es un sistema estructurado y extensible que gobierna el lenguaje visual de tu aplicación. DWC está diseñado para ayudar a desarrolladores y diseñadores a crear interfaces consistentes y alineadas con la marca de manera rápida y segura.

En su núcleo, DWC proporciona un conjunto de variables CSS cuidadosamente diseñadas (tokens de diseño) que cubren elementos visuales clave como colores, tipografía, bordes y espaciado. Estos tokens sirven como bloques de construcción fundamentales para todos los estilos de los componentes y permiten la personalización global con un esfuerzo mínimo.

Para soportar estilos más avanzados, webforJ aprovecha las CSS Shadow Parts, permitiendo que los internos de los componentes se estilicen selectivamente sin romper la encapsulación. Esto otorga a los equipos un control detallado sobre cómo aparecen los componentes, incluso en aplicaciones más grandes.

DWC también incluye una paleta de colores personalizable y, por defecto, presenta un tema visual limpio y claro, pero cada aspecto se puede adaptar al estilo de tu marca o producto.

## Figma Design Kit {#figma-design-kit}

La [biblioteca DWC Figma](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) es el recurso de diseño oficial para crear aplicaciones web modernas y de nivel empresarial. Incluye un conjunto completo de componentes, estilos tipográficos y tokens de color que se alinean con el sistema de diseño DWC. Con esta biblioteca, diseñadores y desarrolladores pueden construir interfaces visualmente consistentes y amigables para el usuario que equilibran la funcionalidad con una experiencia de usuario refinada.

<img src="/img/dwc.png" alt="Kit de Diseño Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Topics {#topics}

<DocCardList className="topics-section" />
