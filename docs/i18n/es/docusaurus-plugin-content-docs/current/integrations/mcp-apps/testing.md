---
title: Probar una app MCP
sidebar_position: 10
description: >-
  Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app,
  Claude Desktop, and MCPJam.
_i18n_hash: fb9683202651a3aca86843cf27c0626e
---
Las aplicaciones de webforJ MCP pueden ejecutarse en cualquier host compatible con MCP Apps. Las instrucciones aquí cubren la aplicación Codex y Claude Desktop a través de un endpoint HTTPS accesible, y MCPJam a través de localhost. La herramienta mínima `inventory` sin entrada del [Spring Boot setup](./spring) es suficiente para confirmar que un host puede descubrir la herramienta y renderizar la vista de Inventario.

## Clientes remotos {#remote-clients}

La aplicación Codex y Claude Desktop se conectan desde fuera de la máquina de desarrollo. No pueden acceder a `http://localhost:8080/mcp`, por lo que la aplicación en ejecución necesita una URL pública HTTPS de MCP.

### Exponer una aplicación local {#expose-a-local-app}

Utiliza un [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/) para reservar e imprimir un origen HTTPS público que reenvíe a la aplicación en el puerto local predeterminado, `8080`. Puedes iniciar el túnel antes de la aplicación:

```bash
cloudflared tunnel --url http://localhost:8080
```

El comando imprime un origen HTTPS, como `https://example.trycloudflare.com`. Establece ese origen impreso en `src/main/resources/application.properties`:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Inicia la aplicación a través de su flujo de trabajo normal. El origen no tiene `/mcp`; la URL del cliente agrega `/mcp`:

```text
https://example.trycloudflare.com/mcp
```

:::warning[Desarrollo de túnel]

Un túnel de desarrollo hace que la aplicación sea accesible públicamente. Usa datos de prueba, espera un nuevo nombre de host cada vez que se inicie el túnel rápido y usa un túnel gestionado estable cuando el nombre de host debe permanecer igual.
:::

### Aplicación Codex {#codex-app}

<!-- Video: Conectar y probar la aplicación inventario MCP en la aplicación Codex. -->

La [Guía de Plugins](https://developers.openai.com/codex/plugins) de OpenAI cubre los controles de plugin actuales.

1. En **Configuración**, abre **Plugins** y selecciona **Agregar servidor MCP**.
2. Ingresa la URL pública de MCP:

```text
https://example.trycloudflare.com/mcp
```

3. Agrega el servidor, luego inicia una nueva conversación en Codex.
4. Indica a la aplicación Codex:

```text
Abre la aplicación de inventario.
```

5. Confirma que la vista de Inventario renderizada aparece.

<!-- vale Google.Headings = NO -->
### Claude Desktop {#claude-desktop}

<!-- Video: Conectar y probar la aplicación inventario MCP en Claude Desktop. -->
<!-- vale Google.Headings = YES -->

El conector personalizado remoto de Claude Desktop se gestiona a través de la infraestructura de Anthropic, por lo que también necesita la URL pública HTTPS de MCP. La [guía de conectores](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) de Anthropic cubre los controles de conector actuales.

1. Abre **Configuración**, selecciona **Conectores** y haz clic en el botón agregar.
2. Selecciona **Agregar conector personalizado**, ingresa un nombre y utiliza la URL pública de MCP:

```text
https://example.trycloudflare.com/mcp
```

3. Agrega el conector.
4. En una conversación, indica a Claude Desktop:

```text
Abre la aplicación de inventario.
```

6. Confirma que la vista de Inventario renderizada aparece.

Si el servidor requiere OAuth 2.0, completa el flujo de inicio de sesión antes de invocar la herramienta.

:::tip[Nombrar el servidor MCP en el aviso]

Si Codex o Claude no eligen la acción esperada, incluye el nombre del servidor MCP en el aviso. Esto puede suceder cuando varias herramientas podrían aplicarse o el aviso es demasiado vago. Por ejemplo: `Usando el servidor MCP de inventario, abre la aplicación de inventario.`
:::

## MCPJam {#mcpjam}

[MCPJam](https://github.com/MCPJam/inspector) puede conectarse directamente a un servidor MCP que se esté ejecutando en la misma máquina. Utiliza el inspector local para un endpoint HTTP simple; la aplicación MCPJam alojada acepta solo endpoints HTTPS.

1. Inicia el inspector local y abre la URL de localhost que imprime:

```bash
npx @mcpjam/inspector@latest
```

2. Antes de iniciar la aplicación webforJ, configura su origen local y permite el origen del navegador MCPJam. Reemplaza el origen representativo de MCPJam a continuación si el inspector imprimió uno diferente:

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` establece la ubicación desde donde la aplicación MCP renderizada carga sus recursos de webforJ. `webforj.mcp.allowed-origins` permite que la página de MCPJam incruste y se comunique con la aplicación.

3. Inicia la aplicación webforJ a través de su flujo de trabajo normal.

4. En MCPJam, abre **Conectar** y selecciona **Agregar servidor**. Ingresa un nombre, selecciona **HTTP** como el transporte y utiliza el endpoint local de MCP:

```text
http://localhost:8080/mcp
```

5. Selecciona **Sin Autenticación**, luego conecta el servidor. Una conexión exitosa hace que las herramientas del servidor estén disponibles para MCPJam.
6. Abre **Playground**, luego abre **Herramientas** en el panel izquierdo.
7. Selecciona `inventory` y haz clic en **Ejecutar**. La herramienta no requiere entrada, y su vista de Inventario se renderiza en la conversación.

:::warning[Modo de política de seguridad de contenido de MCPJam]

Establece el **Modo de Política de Seguridad de Contenido (CSP)** en la barra de herramientas de Playground a **Permisivo** antes de ejecutar la herramienta. El modo estricto bloquea la evaluación dinámica de JavaScript utilizada durante el inicio actual de webforJ. Usa el modo permisivo solo con servidores MCP y código de aplicación en los que confíes.
:::

## Verificar la aplicación {#verify-the-app}

Utiliza esta base para cada cliente:

- El cliente se conecta al endpoint MCP.
- La herramienta `inventory` es visible.
- Invocar `inventory` renderiza el encabezado **Inventario**.
- La UI renderizada es interactiva.

Después de que la base funcione, agrega [entradas de apertura](./opening-apps), [acciones y actualizaciones](./actions-updates) y [interacción con el host](./host-interaction) cuando la aplicación MCP necesite esas características.

## Resolución de problemas {#troubleshooting}

| Problema | Verifica |
| --- | --- |
| El cliente no puede conectarse | Confirma que la aplicación esté en ejecución, que el túnel esté funcionando para clientes remotos y que la URL completa del cliente termine con `/mcp`. |
| Herramienta visible pero los recursos o la apertura fallan | Confirma que `webforj.origin` coincida con el origen actual de la aplicación y que la aplicación esté en ejecución. |
| MCPJam está en blanco o cargando con un error de política de seguridad de contenido `eval` | Desactiva **Estricto**. |
| Los metadatos están obsoletos | Reconecta el cliente o inicia una nueva conversación. |
