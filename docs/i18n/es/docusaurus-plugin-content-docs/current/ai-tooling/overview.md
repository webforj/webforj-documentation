---
title: webforJ AI Plugin
sidebar_position: 1
slug: /ai-tooling
description: >-
  Install the webforJ AI plugin to add the MCP server and Agent Skills to Claude
  Code, Copilot, Cursor, Gemini, and Codex in one step.
_i18n_hash: 44bdaad98af3599ab5fcf57c6a4756c1
---
El **plugin de webforJ AI** es la forma recomendada de conectar tu asistente de codificación AI a webforJ. Una instalación le da a tu asistente todo el conjunto de herramientas: acceso en vivo a la documentación de webforJ, creación de proyectos, generación de temas, validación de tokens de diseño y flujos de trabajo estructurados que le enseñan a utilizar todo esto correctamente.

## Lo que obtienes {#what-you-get}

Instalar el plugin conecta dos piezas complementarias en un solo paso:

- **[servidor webforJ MCP](/docs/ai-tooling/mcp)** - herramientas en vivo que el asistente puede invocar a demanda: consultar la base de conocimientos de webforJ, crear proyectos de Maven, generar temas DWC, leer la superficie de estilo de cualquier componente DWC y validar los tokens `--dwc-*` antes de que lleguen a tu CSS.
- **[Habilidades del Agente](/docs/ai-tooling/agent-skills)** - flujos de trabajo estructurados que indican al asistente _cuándo_ recurrir a esas herramientas, en qué orden hacer las cosas y cómo validar el resultado. Cubre la creación de componentes reutilizables y el estilo de aplicaciones webforJ de principio a fin.

Juntas, convierten un asistente AI que adivina las convenciones de webforJ en uno que las sigue.

Junto a ellas, webforJ incluye un asistente de otro tipo:

- **[Asistente craftforJ](/docs/ai-tooling/craftforj-assistant)** - un agente de codificación que trabaja dentro de tu aplicación *en ejecución* en lugar de tu editor. Escribe Java libremente, compila cada edición antes de que la veas, la aplica y sigue trabajando después de que tu aplicación se reinicie, además de leer el árbol de componentes en vivo, cambiar propiedades, navegar por rutas y ajustar el tema. No hay nada que instalar, porque se incluye con webforJ.

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

El plugin `webforj` aparece bajo **Instalados**. El servidor MCP aparece como `plugin:webforj:webforj-mcp` bajo servidores conectados.

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

Codex no carga automáticamente las habilidades por coincidencia de comandos como otros clientes. Invócalas explícitamente:

```
$webforj:webforj-styling-apps tema esta aplicación con una paleta azul
$webforj:webforj-creating-components envuelve este Elemento Personalizado como un componente webforJ
```

Las herramientas MCP funcionan automáticamente sin el prefijo `$`.

</TabItem>
</Tabs>

### Otros clientes {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity y cualquier otro cliente compatible con Habilidades del Agente también soportan el plugin; solo que utilizan configuración manual en lugar de un comando de mercado. Consulta la [guía de instalación por cliente](https://github.com/webforj/webforj-ai#clients) para los pasos exactos.

## Usándolo {#using-it}

Una vez instalado, la mayoría de los asistentes cargan la pieza correcta automáticamente en función de tu solicitud:

- *"Envuelve esta biblioteca de Elementos Personalizados como un componente webforJ."* - activa la habilidad de crear componentes
- *"Estiliza esta vista con los tokens de diseño DWC."* - activa la habilidad de estilizar aplicaciones
- *"Crea un nuevo proyecto de sidemenu de webforJ llamado CustomerPortal."* - invoca el generador de proyectos MCP
- *"Genera un tema a partir del color de marca `#6366f1`."* - invoca el generador de temas MCP
- *"Encuentra la documentación de webforJ sobre `@Route` y enrutamiento."* - invoca la búsqueda de conocimiento de MCP

Para obtener los mejores resultados, siempre menciona **webforJ** en tus solicitudes; esa es la señal que el asistente utiliza para recurrir al plugin en lugar de al conocimiento general de Java.

## Actualización y desinstalación {#updating-and-uninstalling}

Cada cliente soportado tiene sus propios comandos de actualización y desinstalación. Consulta el [README de webforj-ai](https://github.com/webforj/webforj-ai#clients) para instrucciones específicas por cliente.
