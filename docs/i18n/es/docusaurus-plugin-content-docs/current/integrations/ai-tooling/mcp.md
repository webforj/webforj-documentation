---
title: MCP Server
sidebar_position: 5
_i18n_hash: eea9d8f962b10512151bf7c6935f25e0
---
El servidor del Protocolo de Contexto del Modelo (MCP) de webforJ conecta asistentes de codificación AI a la documentación, APIs, tokens de diseño y herramientas de scaffolding de webforJ. En lugar de adivinar las convenciones del marco, el asistente consulta al servidor y obtiene respuestas basadas en el verdadero webforJ.

:::tip Usa el plugin
A menos que sepas que solo quieres el servidor MCP, instala el **[plugin AI de webforJ](/docs/integrations/ai-tooling)** en su lugar: agrupa este servidor con las [Habilidades del Agente](/docs/integrations/ai-tooling/agent-skills) correspondientes en una sola instalación.
:::

## ¿Qué es un MCP? {#whats-an-mcp}

El Protocolo de Contexto del Modelo es un estándar abierto que permite a los asistentes de AI llamar herramientas externas bajo demanda. El servidor MCP de webforJ implementa este protocolo para que tu asistente pueda:

- Consultar en la documentación de webforJ en lugar de inventar nombres de métodos
- Crear nuevos proyectos de webforJ a partir de arquetipos oficiales de Maven
- Generar temas DWC accesibles a partir de un color de marca
- Leer la verdadera superficie de estilos de un componente DWC y validar cualquier token `--dwc-*` antes de que llegue a tu CSS

:::warning La AI aún puede cometer errores
El servidor MCP mejora significativamente la precisión, pero los asistentes de AI aún pueden producir código incorrecto en escenarios complejos. Siempre revisa y prueba el código generado antes de enviarlo.
:::

## Instalación {#installation}

Para la experiencia completa, instala el **[plugin AI de webforJ](/docs/integrations/ai-tooling)**: configura este servidor junto con las Habilidades del Agente que tu asistente necesita utilizarlo bien.

Si solo deseas el servidor MCP (sin habilidades), apunta tu cliente a `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

El camino recomendado en Copilot CLI es el **[plugin AI de webforJ](/docs/integrations/ai-tooling)**: registra el servidor MCP para ti en un solo paso. Para una configuración solo de MCP, consulta las instrucciones por cliente en el [repositorio AI de webforJ](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Agrega a tu configuración de VS Code:

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

Cursor, Kiro, Goose, Junie, Antigravity y cualquier otro cliente MCP sobre HTTP también funciona: solo utilizan su propio formato de configuración. Consulta la [guía de instalación por cliente](https://github.com/webforj/webforj-ai#clients) para el fragmento exacto para cada uno.

## Lo que el servidor puede hacer {#capabilities}

Cuando el servidor MCP está conectado, tu asistente AI gana las siguientes capacidades. Cualquiera de ellas puede ser activada por una solicitud en lenguaje natural: el asistente selecciona la correcta automáticamente.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Apuntar a la versión correcta de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Antes de responder preguntas sensibles a la versión (cualquier cosa específica de estilo o API), el asistente determina qué versión de webforJ estás utilizando. Lee `pom.xml` cuando está disponible y, de lo contrario, te pregunta. Cada respuesta subsiguiente se limita a esa versión.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Buscar en la base de conocimientos de webforJ</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede consultar toda la base de conocimientos de webforJ para respuestas basadas en el marco real. Los resultados están limitados a lo que preguntas: una pregunta de API, una guía, un ejemplo de código o el DSL de Kotlin.

      **Ejemplos de solicitudes:**
      ```
      "Encuentra los ejemplos de manejo de eventos del componente Button de webforJ"

      "¿Cómo configuro el enrutamiento con @Route en webforJ?"

      "Muéstrame un ejemplo de validación de formulario de webforJ"
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
      El asistente genera el comando correcto de arquetipo de Maven para una nueva aplicación webforJ a partir de tus requisitos (arquetipo, integración de Spring, nombre, grupo).

      **Arquetipos:**
      - `hello-world` - aplicación inicial con componentes de ejemplo
      - `blank` - estructura de proyecto mínima
      - `tabs` - diseño de interfaz de pestañas
      - `sidemenu` - diseño de navegación lateral

      **Sabores:**
      - `webforj` - aplicación webforJ estándar
      - `webforj-spring` - webforJ integrado con Spring Boot

      **Ejemplos de solicitudes:**
      ```
      "Crea un proyecto webforJ llamado CustomerPortal utilizando el arquetipo sidemenu"

      "Genera un proyecto webforJ Spring Boot con el diseño de pestañas llamado Dashboard"
      ```

      :::tip Arquetipos Disponibles
      Para ver la lista completa de arquetipos, consulta el [catálogo de arquetipos](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Generar un tema DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      A partir de un solo color de marca, el asistente produce un tema DWC completo: paletas de colores primarios, de éxito, de advertencia, de peligro, de información, predeterminada y gris con contraste de texto automático. La salida incluye la hoja de estilo más el cableado de `@AppTheme` / `@StyleSheet`.

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
    <strong>Estilizar correctamente los componentes DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente lee la verdadera superficie de estilos de cada componente DWC: propiedades CSS personalizadas, partes del shadow, atributos reflejados y slots, antes de escribir cualquier CSS. También puede enumerar cada etiqueta DWC y resolver los nombres de clase de Java de webforJ (`Button`, `TextField`) a sus equivalentes en DWC.

      **Ejemplos de solicitudes:**
      ```
      "¿Qué variables CSS y partes expone dwc-button?"

      "Muéstrame cada slot disponible en dwc-dialog"

      "¿Qué etiqueta DWC asigna la clase TextField de webforJ?"
      ```

      Combina esto con la [habilidad del agente de aplicaciones de estilo](/docs/integrations/ai-tooling/agent-skills) para flujos de trabajo de estilo de extremo a extremo.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Trabajar con tokens de diseño DWC</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El asistente puede listar el catálogo autorizado de tokens `--dwc-*` para tu versión de webforJ: semillas de paleta, tonos, superficies, espaciado, tipografía, bordes, filtrados por prefijo o subcadena. También validará cualquier CSS, Java o Markdown que le des contra el catálogo real de tokens y marcará nombres desconocidos con sugerencias de corrección.

      **Ejemplos de solicitudes:**
      ```
      "Lista todos los tokens --dwc-space-*"

      "Valida app.css en busca de tokens --dwc-* desconocidos"

      "¿Qué tonos de paleta primaria están disponibles?"
      ```

      La validación captura errores tipográficos y tokens inventados antes de que se envíen como CSS que falla silenciosamente.
    </div>
  </AccordionDetails>
</Accordion>

## Escribiendo buenas solicitudes {#writing-good-prompts}

El servidor MCP solo se consulta cuando tu asistente piensa que es relevante. Algunos hábitos mantienen su uso:

- **Nombra el marco.** Menciona "webforJ" en la solicitud para que el asistente consulte el servidor MCP en lugar de su conocimiento general de Java.
- **Sé específico.** `"Crea un proyecto webforJ llamado InventorySystem utilizando el arquetipo sidemenu y Spring Boot"` es mejor que `"haz una aplicación"`.
- **Pide verificación.** Frases como `"verifica en la documentación de webforJ"` o `"revisa este CSS por malos tokens --dwc-*"` animan al asistente a utilizar las herramientas en lugar de adivinar.

Si tu asistente aún responde sin consultar al servidor, instala el [plugin AI de webforJ](https://github.com/webforj/webforj-ai): incluye Habilidades de Agente coincidentes que anima al asistente a usar automáticamente las herramientas MCP para tareas de webforJ.

## Preguntas Frecuentes {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Por qué el asistente AI no utiliza el servidor MCP?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La mayoría de los asistentes solo buscan el MCP cuando creen que la pregunta lo necesita. Dos soluciones:

      1. **Instala el [plugin AI de webforJ](https://github.com/webforj/webforj-ai)**, que empareja el servidor con las Habilidades del Agente que indican al asistente usar el MCP para tareas de webforJ.
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
      Abre un ticket usando la [plantilla de issues de MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Incluye la solicitud, el resultado esperado y lo que obtuviste.
    </div>
  </AccordionDetails>
</Accordion>
<br />
