---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Combine webforJ UI components with Spring Boot for dependency injection,
  Spring Data repositories, custom scopes, and live reload.
_i18n_hash: 7af3520db108b976dda9856890c61979
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Muchas equipos de Java ya utilizan [Spring Boot](https://spring.io/projects/spring-boot) para construir aplicaciones. La integración de Spring con webforJ ahora te permite agregar los componentes de UI de webforJ a aplicaciones Spring existentes o usar las funciones de Spring en nuevos proyectos de webforJ.

Tus servicios, repositorios y configuraciones de Spring funcionan como de costumbre. Tus componentes de webforJ pueden `@Autowired` cualquier bean de Spring. Los repositorios de [Spring Data](https://spring.io/projects/spring-data) se conectan directamente a las tablas de webforJ a través de `SpringDataRepository`. El desarrollo se acelera con [live reload](/docs/configuration/deploy-reload/spring-devtools), que actualiza el navegador a medida que cambias el código.

La integración mantiene a ambos frameworks haciendo lo que mejor saben hacer: Spring maneja las preocupaciones del backend mientras que webforJ maneja la interfaz de usuario.

## Temas {#topics}

<DocCardList className="topics-section" />
