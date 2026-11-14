---
title: Aplicaciones MCP
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: c654b2ac1c5344132602148ba19216b3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# Aplicaciones MCP <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

Las aplicaciones MCP permiten que una aplicación de IA compatible con [MCP](https://modelcontextprotocol.io/), también llamada host, abra una vista de webforJ con enrutamiento dentro de su conversación. La vista sigue formando parte de la aplicación Java, por lo que utiliza los mismos componentes, servicios, enrutamiento y estado que en un navegador.

La persona y la IA pueden trabajar con la misma interfaz de usuario en vivo. La IA puede proporcionar datos de entrada al abrir la vista, invocar acciones que la modifiquen y recibir contexto de las opciones que elija la persona en la interfaz. La persona puede seguir utilizando directamente los componentes de webforJ renderizados.

Spring Boot con Spring AI es la forma principal de publicar una aplicación MCP. La integración detecta las rutas marcadas y las añade al servidor MCP de Spring AI. Empieza con la [configuración de Spring Boot](./spring) y luego [prueba la conexión](./testing) con la vista publicada mínima. Las aplicaciones que no usan Spring Boot pueden recurrir a la [configuración estándar de servlet](./without-spring).

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[La compatibilidad con hosts varía]

MCP Apps es una extensión en evolución de la especificación MCP, por lo que cada host adopta sus revisiones y políticas de seguridad a su propio ritmo. La aplicación declara los orígenes desde los que se carga y a los que se conecta su vista, y los hosts que los permiten la muestran. Los hosts también pueden aplicar políticas más estrictas. La herramienta de apertura siempre devuelve su contenido de texto y la ruta sigue estando disponible como una página normal del navegador. Comprueba cada host al que quieras dirigirte siguiendo los pasos de [Pruebas](./testing).
:::

## Temas {#topics}

<DocCardList className="topics-section" />
