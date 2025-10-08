---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2bd69e8c9fad1e483d3c087f0e00e229
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Muchos equipos de Java ya utilizan [Spring Boot](https://spring.io/projects/spring-boot) para construir aplicaciones. La integración de Spring con webforJ ahora te permite agregar los componentes de UI de webforJ a las aplicaciones Spring existentes, o utilizar las características de Spring en nuevos proyectos de webforJ.

Tus servicios, repositorios y configuraciones de Spring funcionan como de costumbre. Tus componentes de webforJ pueden `@Autowired` cualquier bean de Spring. Los repositorios de [Spring Data](https://spring.io/projects/spring-data) se conectan directamente a las tablas de webforJ a través de `SpringDataRepository`. El desarrollo se vuelve más rápido con la actualización automática del navegador de [Spring DevTools y webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools).

La integración mantiene ambos frameworks haciendo lo que mejor saben hacer: Spring maneja las preocupaciones del backend mientras que webforJ se encarga de la UI.

## Temas {#topics}

<DocCardList className="topics-section" />
