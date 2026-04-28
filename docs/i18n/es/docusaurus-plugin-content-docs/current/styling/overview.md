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

webforJ se envía con un sistema de diseño completo llamado **DWC**. Es más que solo un tema, es un sistema estructurado y extensible que rige el lenguaje visual de tu aplicación. DWC está diseñado para ayudar a desarrolladores y diseñadores a crear interfaces consistentes y alineadas con la marca de manera rápida y segura.

En su núcleo, DWC proporciona un conjunto de variables CSS cuidadosamente diseñadas (tokens de diseño) que cubren elementos visuales clave como colores, tipografía, bordes y espaciado. Estos tokens sirven como los bloques de construcción fundamentales para todos los estilos de componentes y permiten una personalización global con un esfuerzo mínimo.

Para soportar estilos más avanzados, webforJ aprovecha las CSS Shadow Parts, lo que permite que los elementos internos de los componentes sean estilizados selectivamente sin romper la encapsulación. Esto brinda a los equipos un control detallado sobre cómo aparecen los componentes, incluso en aplicaciones más grandes.

DWC también incluye una paleta de colores personalizable y por defecto tiene un tema visual limpio y claro, pero cada aspecto puede adaptarse al estilo de tu marca o producto.

## Temas {#topics}

<DocCardList className="topics-section" />
