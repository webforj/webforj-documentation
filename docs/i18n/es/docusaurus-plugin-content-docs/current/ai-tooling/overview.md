---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
sidebar_class_name: new-content
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: db80016ad151e338c6e353caaa7070d9
---
El **plugin de webforJ AI** es la forma recomendada para conectar tu asistente de codificación AI a webforJ. Una instalación proporciona a tu asistente todas las herramientas necesarias: acceso en vivo a la documentación de webforJ, creación de proyectos, generación de temas, validación de tokens de diseño y flujos de trabajo estructurados que le enseñan cómo usar todo correctamente.

## Lo que obtienes {#what-you-get}

Instalar el plugin conecta dos piezas complementarias en un solo paso:

- **[servidor MCP de webforJ](/docs/ai-tooling/mcp)** - herramientas en vivo a las que el asistente puede acceder bajo demanda: buscar información en la base de conocimientos de webforJ, crear proyectos Maven, generar temas DWC, leer la superficie de estilo de cualquier componente DWC, y validar los tokens `--dwc-*` antes de que lleguen a tu CSS.
- **[Habilidades del Agente](/docs/ai-tooling/agent-skills)** - flujos de trabajo estructurados que indican al asistente _cuándo_ utilizar esas herramientas, en qué orden hacer las cosas y cómo validar el resultado. Cubre la creación de componentes reutilizables y el estilo de aplicaciones webforJ de extremo a extremo.

Juntos convierten a un asistente AI que adivina las convenciones de webforJ en uno que las sigue.

:::warning La AI aún puede cometer errores
Incluso con el plugin, los asistentes AI pueden producir código incorrecto en escenarios complejos. Siempre revisa y prueba el código generado antes de implementarlo.
:::

## Instalación {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Verifica dentro de Claude Code:

```
/plugin
/mcp
```

El plugin `webforj` aparece debajo de **Instalados**. El servidor MCP aparece como `plugin:webforj:webforj-mcp` en los servidores conectados.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Verifica:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Desde la paleta de comandos, ejecuta `Chat: Install Plugin From Source`, luego pega:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Verifica:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Luego abre una sesión de Codex, ejecuta `/plugins`, selecciona `webforj` y presiona **Espacio** para habilitarlo.

Codex no carga automáticamente habilidades por coincidencia de prompt como otros clientes. Invócalas explícitamente:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

Las herramientas MCP funcionan automáticamente sin el prefijo `$`.

</TabItem>
</Tabs>

### Otros clientes {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, y cualquiera de los clientes compatibles con Habilidades del Agente también soportan el plugin - solo utilizan configuración manual en lugar de un comando de marketplace. Consulta la [guía de instalación por cliente](https://github.com/webforj/webforj-ai#clients) para los pasos exactos.

## Usándolo {#using-it}

Una vez instalado, la mayoría de los asistentes cargan la pieza adecuada automáticamente según tu solicitud:

- *"Envuelve esta biblioteca de Custom Element como un componente de webforJ."* - activa la habilidad de crear componentes
- *"Estiliza esta vista con los tokens de diseño DWC."* - activa la habilidad de estilizar aplicaciones
- *"Crea un nuevo proyecto de menú lateral webforJ llamado CustomerPortal."* - llama al creador de proyectos MCP
- *"Genera un tema a partir del color de marca `#6366f1`."* - llama al generador de temas MCP
- *"Encuentra la documentación de webforJ sobre `@Route` y enrutamiento."* - llama a la búsqueda de conocimiento MCP

Para obtener los mejores resultados, siempre menciona **webforJ** en tus solicitudes - esa es la pista que usa el asistente para acudir al plugin en lugar de a conocimientos generales de Java.

## Actualización y desinstalación {#updating-and-uninstalling}

Cada cliente soportado tiene sus propios comandos de actualización y desinstalación. Consulta el [README de webforj-ai](https://github.com/webforj/webforj-ai#clients) para las instrucciones específicas por cliente.
