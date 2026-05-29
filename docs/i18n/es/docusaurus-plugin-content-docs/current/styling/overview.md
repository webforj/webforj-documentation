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

webforJ se envía con un sistema de diseño integral llamado **DWC**. Es más que solo un tema, es un sistema estructurado y extensible que gobierna el lenguaje visual de tu aplicación. DWC está diseñado para ayudar a desarrolladores y diseñadores a crear interfaces consistentes y alineadas con la marca de forma rápida y segura.

En su núcleo, DWC proporciona un conjunto de variables CSS cuidadosamente diseñadas (tokens de diseño) que cubren elementos visuales clave como colores, tipografía, bordes y espaciado. Estos tokens sirven como bloques de construcción fundamentales para todos los estilos de componentes y permiten personalización global con un esfuerzo mínimo.

Para soportar estilos más avanzados, webforJ aprovecha las CSS Shadow Parts, permitiendo que los internos de los componentes sean estilizados selectivamente sin romper la encapsulación. Esto otorga a los equipos un control detallado sobre cómo aparecen los componentes, incluso en aplicaciones más grandes.

DWC también incluye una paleta de colores personalizable y por defecto un tema visual limpio y claro, pero cada aspecto se puede adaptar al estilo de tu marca o producto.

<AISkillTip skill="webforj-styling-apps" />

## Kit de diseño de Figma {#figma-design-kit}

La [biblioteca de Figma DWC](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) es el recurso de diseño oficial para crear aplicaciones web modernas de calidad empresarial. Incluye un conjunto completo de componentes, estilos de tipografía y tokens de color que se alinean con el sistema de diseño DWC. Los diseñadores y desarrolladores pueden utilizar esta biblioteca para construir interfaces visualmente consistentes, amigables para el usuario, con un comportamiento de componente predecible, espaciado preciso y contraste de color accesible.

<img src="/img/dwc.png" alt="Kit de Diseño de Figma" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Temas {#topics}

<DocCardList className="topics-section" />
