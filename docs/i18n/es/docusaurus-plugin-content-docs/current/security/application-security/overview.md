---
sidebar_position: 1
title: Seguridad de la aplicación
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Cómo el modelo dirigido por servidor de webforJ protege tu aplicación, las
  amenazas de las que sigues siendo responsable y cómo mantener seguro un
  despliegue.
_i18n_title_hash: 2993e06f9e34b9da0ed41173d7d56a4a
_i18n_description_hash: 4dfd7c6db5603bf452b8b8617acd834f
_i18n_hash: f23f925b237729d4160089bb260cf329
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Ejecutar tu aplicación en Java en el servidor (ver [Cliente y servidor](/docs/architecture/client-server)) contiene toda clase de ataques web antes de que escribas una línea de código. Sin embargo, no elimina tus responsabilidades. Estas páginas explican dónde cae la línea, qué maneja webforJ por ti, dónde permaneces a cargo y cómo mantener un despliegue en producción restringido.

Para controlar quién puede alcanzar cada vista, consulta el resto de la sección de [Seguridad](/docs/security/overview) sobre autenticación y autorización a nivel de ruta.

## Temas {#topics}

<DocCardList className="topics-section" />
