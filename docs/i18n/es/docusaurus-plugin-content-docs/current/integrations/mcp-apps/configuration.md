---
title: Configurar una app MCP
sidebar_position: 30
description: >-
  Configure the public app origin, allowed embedding clients, and external
  browser domains.
_i18n_hash: 6d6d861d57b9a398007bd9a792e9ec1f
---
Agregue la configuración de la aplicación MCP a `application.properties`, o a `webforj.conf` cuando use la configuración estándar de webforJ. Establezca la dirección donde el cliente puede acceder a la aplicación, luego agregue solo los orígenes del cliente y del navegador que requiere el despliegue.

## Establecer el origen de la aplicación {#app-origin}

`webforj.origin` es el origen público utilizado en el recurso de la aplicación, la política de seguridad de contenido y las URL de componentes de webforJ. Durante las pruebas locales, es la dirección de la aplicación:

```Ini
webforj.origin=http://localhost:8080
```

Cuando un túnel o un proxy inverso expone la aplicación, use el origen público al que puede acceder el cliente MCP:

```Ini
webforj.origin=https://example.trycloudflare.com
```

No incluya `/mcp` en esta propiedad. La ruta pertenece al punto final de MCP, no al origen de la aplicación.

## Permitir el cliente de inserción {#allowed-origins}

`webforj.mcp.allowed-origins` controla qué orígenes de navegador pueden hacer solicitudes de origen cruzado e incrustar la vista. Para un navegador [MCPJam](./testing#mcpjam) local que funcione en el origen representativo `http://127.0.0.1:6274`, use:

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Utilice el origen mostrado en la barra de direcciones del navegador del cliente porque las herramientas locales pueden elegir un puerto diferente. La dirección del túnel no es un origen de cliente permitido; pertenece a `webforj.origin`.

webforJ ya permite los patrones de origen de las aplicaciones Codex conocidas y de la sandbox de Claude Desktop. Agregue esta propiedad solo para otro origen de cliente. Un comodín como `https://*.example.com` coincide con las etiquetas de host, no con texto de URL arbitrario.

## Permitir recursos y conexiones externas {#browser-domains}

El marco incrustado comienza con una política de seguridad de contenido restrictiva. Agregue `resource-domains` cuando la UI deba cargar un script, estilo, imagen, fuente u otro recurso del navegador desde otro origen:

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Agregue `connect-domains` cuando el código del navegador en el marco deba conectarse a una API externa, WebSocket, o un punto final similar:

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

Estas propiedades amplían lo que el marco incrustado puede cargar o contactar. No permiten que otro cliente incruste la aplicación; use `allowed-origins` para eso.

## Configurar un despliegue estándar {#standard-deployment}

Spring Boot lee estos valores de `application.properties`. Un despliegue estándar de servlets utiliza `webforj.conf` con los valores equivalentes:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Agregue solo los dominios que necesita la aplicación. [Las pruebas del cliente](./testing) muestran dónde encontrar el origen del cliente local y cuándo se requiere un origen de aplicación pública.
