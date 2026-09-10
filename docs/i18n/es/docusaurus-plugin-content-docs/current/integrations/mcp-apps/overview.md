---
title: MCP Apps
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: 27896fdcd80b0f7414e1e41f1087d848
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Aplicaciones MCP <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />

Las aplicaciones MCP permiten que una aplicación de IA capaz de MCP, también llamada anfitrión, abra una vista de webforJ enrutada dentro de su conversación. La vista sigue siendo parte de la aplicación Java, por lo que utiliza los mismos componentes, servicios, enrutamiento y estado que hace en un navegador.

La persona y la IA pueden trabajar con la misma interfaz de usuario en vivo. La IA puede proporcionar entrada cuando abre la vista, llamar a acciones que cambian la vista abierta y recibir contexto de las decisiones que la persona toma en la interfaz de usuario. La persona puede seguir utilizando los componentes de webforJ renderizados directamente.

Spring Boot con Spring AI es la forma principal de publicar una aplicación MCP. La integración descubre rutas marcadas y las agrega al servidor MCP de Spring AI. Comienza con la [configuración de Spring Boot](./spring), luego [prueba la conexión](./testing) con la vista publicada mínima. Las aplicaciones que no usan Spring Boot pueden usar la [configuración de servlet estándar](./without-spring) en su lugar.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[El soporte del anfitrión varía]

Las aplicaciones MCP son una extensión en evolución de la especificación MCP, por lo que los anfitriones adoptan sus revisiones y políticas de seguridad a su propio ritmo. La aplicación declara los orígenes de los que carga y conecta su vista, y un anfitrión que lo permita renderiza la vista. Los anfitriones también pueden aplicar políticas más estrictas. La herramienta de apertura siempre devuelve su contenido de texto, y la ruta permanece disponible como una página de navegador regular. Verifica cada anfitrión que apuntas con los pasos en [Pruebas](./testing).
:::

## Temas {#topics}

<DocCardList className="topics-section" />
