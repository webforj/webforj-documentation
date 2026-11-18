---
sidebar_position: 1
title: Gestión de recursos
description: >-
  Gestiona JavaScript, CSS y otros recursos en aplicaciones webforJ mediante
  anotaciones declarativas, API de inyección en tiempo de ejecución y protocolos
  URL personalizados.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_title_hash: a71e8aa6e3aa0dbb165119f7b68682a3
_i18n_description_hash: 1c0c8794c1ad64d279c287d021118800
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
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

Las aplicaciones dependen de varios tipos de recursos, como JavaScript, CSS e imágenes. Este documento proporciona una exploración técnica completa de los mecanismos de manejo de recursos de webforJ, cubriendo anotaciones declarativas, métodos de API programáticos y la utilización de protocolos personalizados.

webforJ adopta un enfoque modular para la gestión de recursos, ofreciendo múltiples mecanismos para satisfacer diferentes necesidades de la aplicación:

- **Agrupador de frontend**: Lleva paquetes de npm, frameworks de componentes y lenguajes de hojas de estilo a la aplicación a través de una entrada compilada. Este es el camino predeterminado para los activos de frontend, y hace todo lo que hacen las anotaciones.
- **Anotaciones Declarativas**: Incrusta recursos de JavaScript y CSS a nivel de componentes o de aplicación, sin ningún paso de construcción.
- **Inyección Dinámica Basada en API**: Inyecta recursos en tiempo de ejecución para habilitar el comportamiento dinámico de la aplicación.
- **Protocolos Personalizados**: Proporciona metodologías estandarizadas para el acceso a recursos.
- **Transmisión de Archivos y Descargas Controladas**: Facilita la recuperación y transmisión gestionada de archivos de recursos.

## Temas {#topics}

<DocCardList className="topics-section" />
