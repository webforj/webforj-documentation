---
title: MCP Server
sidebar_position: 5
description: >-
  Connect AI assistants to the webforJ MCP server for live documentation lookup,
  project scaffolding, theme generation, and token validation.
_i18n_hash: e51aa2e6a5a0f6c37a18c404c1104684
---
El servidor del Protocolo de Contexto de Modelo (MCP) de webforJ integra asistentes de codificación de IA en la documentación, las API, los tokens de diseño y las herramientas de scaffolding de webforJ. En lugar de adivinar las convenciones del marco, el asistente consulta al servidor y obtiene respuestas fundamentadas en el verdadero webforJ.

:::tip Usa el plugin
A menos que sepas que solo quieres el servidor MCP, instala el **[plugin de IA de webforJ](/docs/ai-tooling)** en su lugar, ya que agrupa este servidor con las [Habilidades de Agente](/docs/ai-tooling/agent-skills) correspondientes en una sola instalación.
:::

## ¿Qué es un MCP? {#whats-an-mcp}

El Protocolo de Contexto de Modelo es un estándar abierto que permite a los asistentes de IA llamar a herramientas externas bajo demanda. El servidor MCP de webforJ implementa este protocolo para que tu asistente pueda:

- Consultar la documentación de webforJ en lugar de inventar nombres de métodos.
- Crear nuevos proyectos de webforJ a partir de arquetipos de Maven oficiales.
- Generar temas de DWC accesibles a partir de un color de marca.
- Leer la verdadera superficie de estilo de un componente de DWC y validar cualquier token `--dwc-*` antes de que llegue a tu CSS.

:::warning La IA aún puede cometer errores
El servidor MCP mejora significativamente la precisión, pero los asistentes de IA aún pueden producir código incorrecto en escenarios complejos. Siempre revisa y prueba el código generado antes de implementarlo.
:::

## Instalación {#installation}

Para la experiencia completa, instala el **[plugin de IA de webforJ](/docs/ai-tooling)**, ya que configura este servidor junto con las Habilidades de Agente que tu asistente necesita para usarlo bien.

Si solo deseas el servidor MCP (sin habilidades), apunta tu cliente a `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

El camino recomendado en Copilot CLI es el **[plugin de IA de webforJ](/docs/ai-tooling)**, ya que registra el servidor MCP por ti en un solo paso. Para una configuración básica solo de MCP, consulta las instrucciones para cada cliente en el [repositorio de IA de webforJ](https://github.com/webforj/webforj-ai#clients).

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

Cursor, Kiro, Goose, Junie, Antigravity, y cualquier otro cliente MCP sobre HTTP también funcionan; solo utilizan su propio formato de configuración. Consulta la [guía de instalación por cliente](https://github.com/webforj/webforj-ai#clients) para el fragmento exacto para cada uno.

## Lo que el servidor puede hacer {#capabilities}

Cuando el servidor MCP está conectado, tu asistente de IA gana las siguientes capacidades. Cualquiera de ellas puede ser activada por una solicitud en lenguaje natural: el asistente elige la correcta automáticamente.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Dirigir a la versión correcta de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Antes de responder preguntas sensibles a la versión (cualquier cosa específica de estilo o API), el asistente resuelve qué versión de webforJ estás utilizando. Lee el `pom.xml` cuando está disponible y, de lo contrario, te pregunta. Cada respuesta subsiguiente está limitada a esa versión.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Buscar información en la base de conocimientos de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede consultar toda la base de conocimientos de webforJ para respuestas fundamentadas en el verdadero marco. Los resultados se limitan a lo que preguntas: una pregunta de API, una guía, un ejemplo de código o el DSL de Kotlin.

      **Ejemplos de solicitudes:**
      ```
      "Encuentra ejemplos de manejo de eventos del componente Button de webforJ"

      "¿Cómo configuro el enrutamiento con @Route en webforJ?"

      "Muéstrame un ejemplo de validación de formulario en webforJ"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Crear un nuevo proyecto de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente genera el comando correcto de arquetipo de Maven para una nueva aplicación de webforJ a partir de tus requisitos (arquetipo, integración de Spring, nombre, grupo).

      **Arquetipos:**
      - `hello-world` - aplicación inicial con componentes de muestra
      - `blank` - estructura de proyecto mínima
      - `tabs` - diseño de interfaz de pestañas
      - `sidemenu` - diseño de navegación lateral

      **Sabores:**
      - `webforj` - aplicación estándar de webforJ
      - `webforj-spring` - webforJ integrado con Spring Boot

      **Ejemplos de solicitudes:**
      ```
      "Crea un proyecto de webforJ llamado CustomerPortal utilizando el arquetipo de navegación lateral"

      "Genera un proyecto de webforJ Spring Boot con el diseño de pestañas llamado Dashboard"
      ```

      :::tip Arquetipos Disponibles
      Para la lista completa de arquetipos, consulta el [catálogo de arquetipos](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Generar un tema de DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      A partir de un solo color de marca, el asistente produce un tema completo de DWC: paletas primarias, de éxito, de advertencia, de peligro, de información, por defecto y gris con contraste de texto automático. La salida incluye la hoja de estilo y el cableado de `@AppTheme` / `@StyleSheet`.

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
    <strong>Estilizar correctamente los componentes de DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente lee la verdadera superficie de estilo de cada componente de DWC: propiedades CSS personalizadas, partes de sombra, atributos reflejados y ranuras, antes de escribir cualquier CSS. También puede enumerar cada etiqueta de DWC y resolver los nombres de clases de Java de webforJ (`Button`, `TextField`) a sus equivalentes de DWC.

      **Ejemplos de solicitudes:**
      ```
      "¿Qué variables CSS y partes expone dwc-button?"

      "Muéstrame cada ranura disponible en dwc-dialog"

      "¿Qué etiqueta de DWC mapea la clase TextField de webforJ?"
      ```

      Combina esto con la [habilidad de agente de styling-apps](/docs/ai-tooling/agent-skills) para flujos de trabajo de estilo de extremo a extremo.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Trabajar con tokens de diseño de DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede listar el catálogo autorizado de tokens `--dwc-*` para tu versión de webforJ: semillas de paleta, tonos, superficies, espaciado, tipografía, bordes, filtrados por prefijo o substring. También validará cualquier CSS, Java o fuente de Markdown que le proporciones contra el verdadero catálogo de tokens y señalará nombres desconocidos con correcciones sugeridas.

      **Ejemplos de solicitudes:**
      ```
      "Lista cada token --dwc-space-*"

      "Valida app.css para tokens --dwc-* desconocidos"

      "¿Qué tonos de paleta primaria están disponibles?"
      ```

      La validación detecta errores tipográficos e inventos de tokens antes de que se envíen como CSS que falla silenciosamente.
    </div>
  </AccordionDetails>
</Accordion>

## Escribiendo buenas solicitudes {#writing-good-prompts}

El servidor MCP solo se consulta cuando tu asistente cree que es relevante. Algunos hábitos mantienen su compromiso:

- **Nombra el marco.** Menciona "webforJ" en la solicitud para que el asistente recurra al servidor MCP en lugar de a su conocimiento general de Java.
- **Sé específico.** `"Crea un proyecto de webforJ llamado InventorySystem con el arquetipo de navegación lateral y Spring Boot"` supera a `"haz una aplicación"`.
- **Pide verificación.** Frases como `"verifica contra la documentación de webforJ"` o `"verifica este CSS por malos tokens --dwc-*"` empujan al asistente a usar las herramientas en lugar de adivinar.

Si tu asistente aún responde sin consultar al servidor, instala el [plugin de IA de webforJ](https://github.com/webforj/webforj-ai) - incluye Habilidades de Agente correspondientes que obligan al asistente a usar las herramientas MCP automáticamente para tareas de webforJ.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Por qué el asistente de IA no está utilizando el servidor MCP?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La mayoría de los asistentes solo recurren a MCP cuando creen que la pregunta lo necesita. Dos soluciones:

      1. **Instala el [plugin de IA de webforJ](https://github.com/webforj/webforj-ai)**, que combina el servidor con Habilidades de Agente que indican al asistente usar MCP para tareas de webforJ.
      2. **Sé explícito en tu solicitud**: incluye "webforJ" en la pregunta, y para casos obstinados di "usa el servidor MCP de webforJ para responder".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo verificar que la conexión MCP está funcionando?</p>
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
    <p>¿Cómo reportar problemas?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Abre un ticket usando la [plantilla de problema de MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluye la solicitud, el resultado esperado y lo que obtuviste.
    </div>
  </AccordionDetails>
</Accordion>
<br />
