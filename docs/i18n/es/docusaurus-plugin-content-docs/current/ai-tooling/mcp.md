---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: fb0e068ee7d7a489237e021b24a883b0
---
El servidor del Protocolo de Contexto de Modelo (MCP) de webforJ conecta asistentes de codificación AI con la documentación, API, tokens de diseño y herramientas de andamiaje de webforJ. En lugar de adivinar convenciones de framework, el asistente consulta al servidor y recibe respuestas basadas en el verdadero webforJ.

:::tip Usa el plugin
A menos que sepas que solo quieres el servidor MCP, instala el **[plugin de AI de webforJ](/docs/ai-tooling)** en su lugar: agrupa este servidor con las [Habilidades de Agentes](/docs/ai-tooling/agent-skills) correspondientes en una sola instalación.
:::

## ¿Qué es un MCP? {#whats-an-mcp}

El Protocolo de Contexto de Modelo es un estándar abierto que permite a los asistentes AI llamar a herramientas externas bajo demanda. El servidor MCP de webforJ implementa este protocolo para que tu asistente pueda:

- Buscar información en la documentación de webforJ en lugar de imaginar nombres de métodos.
- Crear nuevos proyectos de webforJ a partir de arquetipos oficiales de Maven.
- Generar temas DWC accesibles a partir de un color de marca.
- Leer la verdadera superficie de estilo de un componente DWC y validar cualquier token `--dwc-*` antes de que llegue a tu CSS.

:::warning La IA aún puede cometer errores
El servidor MCP mejora significativamente la precisión, pero los asistentes AI aún pueden producir código incorrecto en escenarios complejos. Siempre revisa y prueba el código generado antes de enviarlo.
:::

## Instalación {#installation}

Para tener la experiencia completa, instala el **[plugin de AI de webforJ](/docs/ai-tooling)**: configura este servidor junto con las Habilidades de Agentes que tu asistente necesita para usarlo bien.

Si solo deseas el servidor MCP (sin habilidades), apunta tu cliente a `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

El camino recomendado en Copilot CLI es el **[plugin de AI de webforJ](/docs/ai-tooling)**: registra el servidor MCP por ti en un solo paso. Para una configuración de MCP en bruto, consulta las instrucciones por cliente en el [repositorio de AI de webforJ](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Agrega a la configuración de tu VS Code:

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

Agrega a `~/.gemini/settings.json`:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

Agrega a `~/.codex/config.toml`:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Otros clientes {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity y cualquier otro cliente MCP sobre HTTP también funcionan; solo utilizan su propio formato de configuración. Consulta la [guía de instalación por cliente](https://github.com/webforj/webforj-ai#clients) para obtener el fragmento exacto para cada uno.

## Lo que el servidor puede hacer {#capabilities}

Cuando el servidor MCP está conectado, tu asistente AI obtiene las siguientes capacidades. Cualquiera de ellas puede ser activada por una solicitud en lenguaje natural: el asistente elige automáticamente la correcta.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Objetiva la versión correcta de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Antes de responder preguntas sensibles a la versión (cualquier cosa específica de estilo o API), el asistente determina qué versión de webforJ estás utilizando. Lee `pom.xml` cuando está disponible y, de lo contrario, te pregunta. Cada respuesta posterior está destinada a esa versión.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Busca información en la base de conocimientos de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede consultar la base de conocimientos completa de webforJ para obtener respuestas basadas en el verdadero marco. Los resultados se limitan a lo que preguntas: una pregunta de API, una guía, un ejemplo de código o el DSL de Kotlin.

      **Ejemplos de solicitudes:**
      ```
      "Encuentra ejemplos de manejo de eventos del componente Button de webforJ"

      "¿Cómo configuro el enrutamiento con @Route en webforJ?"

      "Muéstrame un ejemplo de validación de formularios en webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Crea un nuevo proyecto de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente genera el comando de arquetipo de Maven correcto para una nueva aplicación de webforJ a partir de tus requisitos (arquetipo, integración con Spring, nombre, grupo).

      **Arquetipos:**
      - `hello-world` - aplicación de inicio con componentes de muestra
      - `blank` - estructura mínima del proyecto
      - `tabs` - diseño de interfaz con pestañas
      - `sidemenu` - diseño de navegación lateral

      **Variantes:**
      - `webforj` - aplicación estándar de webforJ
      - `webforj-spring` - webforJ integrado con Spring Boot

      **Ejemplos de solicitudes:**
      ```
      "Crea un proyecto de webforJ llamado CustomerPortal utilizando el arquetipo sidemenu"

      "Genera un proyecto de Spring Boot de webforJ con el diseño de pestañas llamado Dashboard"
      ```

      :::tip Arquetipos Disponibles
      Para la lista completa de arquetipos, consulta el [catálogo de arquetipos](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Genera un tema DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      A partir de un solo color de marca, el asistente produce un tema DWC completo: paletas primaria, de éxito, de advertencia, de peligro, de información, predeterminada y gris con contraste de texto automático. La salida incluye la hoja de estilos más el cableado de `@AppTheme` / `@StyleSheet`.

      **Ejemplos de solicitudes:**
      ```
      "Genera un tema de webforJ a partir del color de marca #6366f1"

      "Crea un tema accesible con HSL 220, 70, 50 como primario"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Estiliza componentes DWC correctamente</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente lee la verdadera superficie de estilo de cada componente DWC - propiedades personalizadas de CSS, partes de sombra, atributos reflejados y slots - antes de escribir cualquier CSS. También puede enumerar cada etiqueta DWC y resolver nombres de clases Java de webforJ (`Button`, `TextField`) a sus equivalentes DWC.

      **Ejemplos de solicitudes:**
      ```
      "¿Qué variables de CSS y partes expone dwc-button?"

      "Muéstrame cada slot disponible en dwc-dialog"

      "¿Qué etiqueta DWC mapea a la clase TextField de webforJ?"
      ```

      Combina esto con la [habilidad del agente de estilización](/docs/ai-tooling/agent-skills) para flujos de trabajo de estilización de principio a fin.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Trabaja con tokens de diseño DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede listar el catálogo autoritativo de tokens `--dwc-*` para tu versión de webforJ: semillas de paleta, tonos, superficies, espaciado, tipografía, bordes - filtrados por prefijo o subcadena. También validará cualquier fuente CSS, Java o Markdown que le proporciones contra el catálogo de tokens real y marcará nombres desconocidos con correcciones sugeridas.

      **Ejemplos de solicitudes:**
      ```
      "Lista todos los tokens --dwc-space-*"

      "Valida app.css en busca de tokens --dwc-* desconocidos"

      "¿Qué tonos de paleta primaria están disponibles?"
      ```

      La validación detecta errores tipográficos y tokens inventados antes de que se envíen como CSS que falla silenciosamente.
    </div>
  </AccordionDetails>
</Accordion>

## Escribiendo buenas solicitudes {#writing-good-prompts}

El servidor MCP solo se consulta cuando tu asistente cree que es relevante. Algunos hábitos mantienen su interés:

- **Nombra el framework.** Menciona "webforJ" en la solicitud para que el asistente busque el servidor MCP en lugar de su conocimiento general de Java.
- **Sé específico.** `"Crea un proyecto webforJ llamado InventorySystem con el arquetipo sidemenu y Spring Boot"` es mejor que `"hace una app"`.
- **Pide verificación.** Frases como `"verifica contra la documentación de webforJ"` o `"revisa este CSS por tokens --dwc-* malos"` animan al asistente a usar las herramientas en lugar de adivinar.

Si tu asistente aún responde sin consultar al servidor, instala el [plugin de AI de webforJ](https://github.com/webforj/webforj-ai): se envían habilidades de agente coincidentes que animan al asistente a usar las herramientas MCP automáticamente para tareas de webforJ.

## Preguntas frecuentes {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Por qué el asistente AI no está usando el servidor MCP?</p>
    <p>¿Por qué el asistente AI no está usando el servidor MCP?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La mayoría de los asistentes solo recurren a MCP cuando piensan que la pregunta lo necesita. Dos soluciones:

      1. **Instala el [plugin de AI de webforJ](https://github.com/webforj/webforj-ai)**, que empareja el servidor con habilidades de agente que indican al asistente que use MCP para tareas de webforJ.
      2. **Sé explícito en tu solicitud**: incluye "webforJ" en la pregunta y, para casos obstinados, di "usa el servidor MCP de webforJ para responder".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo verificar que la conexión MCP esté funcionando?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Usa el inspector MCP:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Luego, en el inspector, conéctate a `https://mcp.webforj.com/mcp` y explora las herramientas disponibles.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo informar problemas?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Abre un ticket utilizando la [plantilla de problemas MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluye la solicitud, el resultado esperado y lo que obtuviste.
    </div>
  </AccordionDetails>
</Accordion>
<br />
