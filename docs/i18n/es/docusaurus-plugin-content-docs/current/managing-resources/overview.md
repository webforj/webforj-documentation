---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

Las aplicaciones dependen de varios tipos de recursos, como JavaScript, CSS e imágenes. Este documento proporciona una exploración técnica integral de los mecanismos de manejo de recursos de webforJ, cubriendo anotaciones declarativas, métodos de API programáticos y la utilización de protocolos personalizados.

webforJ adopta un enfoque modular para la gestión de recursos, ofreciendo múltiples mecanismos para abordar diferentes necesidades de la aplicación:

- **Agrupador de Frontend**: Trae paquetes de npm, frameworks de componentes y lenguajes de hojas de estilo a la aplicación a través de una entrada compilada. Este es el camino predeterminado para los activos de frontend y hace todo lo que hacen las anotaciones.
- **Anotaciones Declarativas**: Incorpora recursos de JavaScript y CSS a nivel de componentes o de la aplicación, sin necesidad de un paso de construcción.
- **Inyección Dinámica Basada en API**: Inyecta recursos en tiempo de ejecución para habilitar comportamientos dinámicos de la aplicación.
- **Protocolos Personalizados**: Proporciona metodologías estandarizadas para el acceso a recursos.
- **Transmisión de Archivos y Descargas Controladas**: Facilita la recuperación y transmisión gestionadas de archivos de recursos.

## Temas {#topics}

<DocCardList className="topics-section" />
