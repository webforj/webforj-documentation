---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: edf3c4087bb9491b2be06b67e32bb27e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Muchas equipos de Java ya utilizan [Spring Boot](https://spring.io/projects/spring-boot) para construir aplicaciones. La integración de Spring con webforJ ahora te permite agregar los componentes de UI de webforJ a aplicaciones Spring existentes, o usar las características de Spring en nuevos proyectos de webforJ.

Tus servicios, repositorios y configuraciones de Spring funcionan como de costumbre. Tus componentes de webforJ pueden `@Autowired` cualquier bean de Spring. Los repositorios de [Spring Data](https://spring.io/projects/spring-data) se conectan directamente a las tablas de webforJ a través de `SpringDataRepository`. El desarrollo se vuelve más rápido con la actualización automática del navegador desde Spring DevTools y webforJ LiveReload.

La integración mantiene ambos frameworks haciendo lo que mejor saben hacer: Spring se encarga de las preocupaciones del backend mientras que webforJ maneja la UI.

## Temas {#topics}

<DocCardList className="topics-section" />
