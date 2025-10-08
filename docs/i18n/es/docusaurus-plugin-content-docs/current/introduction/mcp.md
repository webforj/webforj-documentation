---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: cfe1c4447876aff3ab7ba15b26966cba
---
El servidor del Protocolo de Contexto del Modelo webforJ (MCP) proporciona a los asistentes de IA acceso directo a la documentación oficial de webforJ, ejemplos de código verificados y patrones específicos del marco, permitiendo respuestas con respuestas más precisas y generación automatizada de proyectos específicamente para el desarrollo de webforJ.

## ¿Qué es un MCP?

El Protocolo de Contexto del Modelo es un estándar abierto que permite a los asistentes de IA conectarse con herramientas y documentación externas. El servidor MCP de webforJ implementa este protocolo para proporcionar:

- **Búsqueda de Conocimiento** - Búsqueda en lenguaje natural a través de la documentación de webforJ, ejemplos de código y patrones
- **Generación de Proyectos** - Crear aplicaciones de webforJ a partir de plantillas oficiales con la estructura adecuada
- **Creación de Temas** - Generar temas de CSS accesibles siguiendo patrones de diseño de webforJ

## ¿Por qué usar MCP?

Mientras que los asistentes de codificación de IA sobresalen en responder preguntas básicas, tienen dificultades con consultas complejas específicas de webforJ que abarcan múltiples secciones de documentación. Sin acceso directo a fuentes oficiales, pueden:

- Generar métodos que no existen en webforJ
- Referenciar patrones de API desactualizados o incorrectos  
- Proporcionar código que no compila
- Confundir la sintaxis de webforJ con otros marcos de Java
- Malinterpretar patrones específicos de webforJ

Con la integración de MCP, las respuestas de IA están ancladas a la documentación real de webforJ, ejemplos de código y patrones de marco, proporcionando respuestas verificables con enlaces directos a fuentes oficiales para una exploración más profunda.

:::warning La IA aún puede cometer errores
Aunque MCP mejora significativamente la precisión al proporcionar acceso a recursos oficiales de webforJ, no garantiza una generación de código perfecta. Los asistentes de IA todavía pueden cometer errores en escenarios complejos. Siempre verifica el código generado y prueba a fondo antes de usar en producción.
:::

## Instalación

El servidor MCP de webforJ está hospedado en `https://mcp.webforj.com` con dos puntos finales:

- **Punto final MCP** (`/mcp`) - Para Claude, VS Code, Cursor
- **Punto final SSE** (`/sse`) - Para clientes heredados

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Agrega esta configuración a tu archivo settings.json de VS Code:

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
<TabItem value="cursor" label="Cursor">

Agrega esta configuración a tus ajustes de Cursor:

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Usa el comando CLI de Claude para registrar el servidor:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Esto configurará automáticamente el servidor MCP en tu entorno de Claude Code.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Agrega este servidor utilizando el panel de Integraciones en la configuración de Claude Desktop:

1. Abre Claude Desktop y ve a Configuración
2. Haz clic en "Integraciones" en la barra lateral
3. Haz clic en "Agregar Integración" y pega la URL: `https://mcp.webforj.com/mcp`
4. Sigue el asistente de configuración para completar la configuración

Para instrucciones detalladas, consulta la [guía de integración oficial](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Agrega esta configuración del servidor a tus ajustes de MCP de Windsurf:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Herramientas disponibles

Las herramientas son funciones especializadas que el servidor MCP proporciona a los asistentes de IA. Cuando haces una pregunta o haces una solicitud, la IA puede llamar a estas herramientas para buscar documentación, generar proyectos o crear temas. Cada herramienta acepta parámetros específicos y devuelve datos estructurados que ayudan a la IA a proporcionar asistencia precisa y contextualizada.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Buscar documentación y ejemplos
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Esta herramienta proporciona capacidades de búsqueda semántica en todo el ecosistema de documentación de webforJ. Comprende el contexto y las relaciones entre diferentes conceptos del marco, devolviendo secciones de documentación relevantes, referencias de API y ejemplos de código funcional.

      **Consultas de ejemplo:**
      ```
      "Buscar documentación de webforJ para ejemplos de componentes de Botón con íconos"

      "Encontrar patrones de validación de formularios de webforJ en la documentación más reciente"

      "Muéstrame la configuración actual de enrutamiento de webforJ con la anotación @Route"

      "Buscar en la documentación de webforJ los patrones de diseño responsivo de FlexLayout"

      "Encontrar la integración de componentes web de webforJ en la documentación oficial"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Generar nuevos proyectos webforJ  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Estructura aplicaciones completas de webforJ utilizando arquetipos de Maven oficiales. La herramienta crea un diseño de directorio de proyecto estandarizado e incluye código de inicio basado en la plantilla seleccionada. Los proyectos generados incluyen un sistema de construcción listo para usar, carpetas de recursos y archivos de configuración para un desarrollo y despliegue inmediato.

      **Prompts de ejemplo:**
      ```
      "Crear un proyecto webforJ llamado CustomerPortal utilizando el arquetipo hello-world"

      "Generar un proyecto webforJ Spring Boot con diseño de pestañas llamado Dashboard"

      "Crear una nueva aplicación webforJ con el arquetipo sidemenu para el proyecto AdminPanel"

      "Generar un proyecto webforJ en blanco llamado TestApp con com.example como groupId"

      "Crear un proyecto webforJ InventorySystem utilizando el arquetipo sidemenu con Spring Boot"
      ```

      Al utilizar esta herramienta, puedes elegir entre varias plantillas de proyecto:

      **Arquetipos** (plantillas de proyecto):
      - `hello-world` - Aplicación básica con componentes de muestra para demostrar las características de webforJ
      - `blank` - Estructura mínima de proyecto para comenzar desde cero
      - `tabs` - Diseño de interfaz con pestañas preconstruido para aplicaciones de múltiples vistas
      - `sidemenu` - Diseño de menú de navegación lateral para paneles de administrador o tableros

      **Sabores** (integración de marco):
      - `webforj` - Aplicación estándar de webforJ
      - `webforj-spring` - webforJ integrado con Spring Boot para la inyección de dependencias y características empresariales

      :::tip Arquetipos disponibles
      webforJ viene con varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para obtener una lista completa de arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Crear temas de CSS accesibles
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Genera configuraciones de tema de webforJ utilizando [DWC HueCraft](https://huecraft.dwc.style/). La herramienta crea conjuntos completos de propiedades CSS personalizadas con variantes de color primario, secundario, de éxito, de advertencia, de peligro y neutrales.

      **Solicitudes de ejemplo:**
      ```
      "Generar un tema webforJ con HSL 220, 70, 50 como color primario para nuestra marca corporativa"

      "Crear un tema accesible de webforJ llamado 'océano' con color primario #0066CC"

      "Generar un tema webforJ utilizando nuestro color de marca #FF5733"

      "Crear un tema webforJ con HSL 30, 100, 50 llamado 'atardecer' para nuestra aplicación"

      "Generar un tema accesible de webforJ con RGB primario 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Prompts disponibles {#available-prompts}

Los prompts son instrucciones de IA preconfiguradas que combinan múltiples herramientas y flujos de trabajo para tareas comunes. Guiarán a la IA a través de pasos y parámetros específicos para ofrecer resultados confiables y repetibles para cada flujo de trabajo admitido.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Crear y ejecutar una aplicación webforJ
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentos:**
      - `appName` (requerido) - Nombre de la aplicación (por ejemplo, MyApp, TodoList, Dashboard)
      - `archetype` (requerido) - Elegir entre: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (opcional) - Ejecutar automáticamente el servidor de desarrollo (sí/no)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generar un tema webforJ a partir de un color primario
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Argumentos:**
      - `primaryColor` (requerido) - Color en formato hex (#FF5733), rgb (255,87,51) o hsl (9,100,60)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Búsqueda avanzada con solución de problemas autónoma
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El prompt configura a la IA para:

      1. Buscar en la base de conocimiento de manera extensa
      2. Escribir código completo listo para producción
      3. Compilar el proyecto utilizando `mvn compile` para verificar que no hay errores de construcción
      4. Corregir errores de manera iterativa hasta que todo funcione
    </div>
  </AccordionDetails>
</Accordion>

### Cómo usar los prompts

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code y Claude Code">

1. Escribe <kbd>/</kbd> en el chat para ver los prompts disponibles
2. Selecciona un prompt del menú desplegable
3. Rellena los parámetros requeridos cuando se te solicite

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Haz clic en el ícono **+** (más) en el área de entrada del prompt
2. Selecciona **"Agregar desde webforJ"** del menú
3. Elige el prompt deseado (por ejemplo, `create-app`, `create-theme`, `search-webforj`)
4. Claude te solicitará que ingreses los argumentos requeridos
5. Rellena los parámetros según se te pida

:::tip Verifica que MCP está conectado
Busca el ícono de herramientas en la esquina inferior del área de entrada para confirmar que el servidor MCP de webforJ está conectado.
:::

</TabItem>
</Tabs>

## Mejores prácticas

Para obtener la asistencia de webforJ más precisa y actualizada, sigue estas directrices para aprovechar al máximo las características del servidor MCP.

### Asegurando el uso del servidor MCP

Los modelos de IA pueden omitir el servidor MCP si creen que ya conocen la respuesta. Para asegurarte de que el servidor MCP se use realmente:

- **Sé explícito sobre webforJ**: Siempre menciona "webforJ" en tu consulta para activar búsquedas específicas del marco
- **Solicita información actual**: Incluye frases como "documentación más reciente de webforJ" o "patrones actuales de webforJ"
- **Pide ejemplos verificados**: Solicita "ejemplos de código webforJ funcionando" para forzar la búsqueda en la documentación
- **Referencia versiones específicas**: Menciona tu versión de webforJ (por ejemplo, "webforJ `25.02`") para obtener resultados precisos

### Escribiendo prompts específicos

**Buenos ejemplos:**
```
"Buscar en la documentación de webforJ ejemplos de manejo de eventos de componentes Botón"

"Crear un proyecto webforJ llamado InventorySystem utilizando el arquetipo sidemenu con Spring Boot"

"Generar un tema de webforJ con HSL 220, 70, 50 como color primario para la marca corporativa"
```

**Malos ejemplos:**
```
"¿Cómo funcionan los botones?"

"Haz una aplicación"

"Hazlo azul"
```

### Forzar el uso de herramientas MCP

Si la IA proporciona respuestas genéricas sin usar el servidor MCP:

1. **Solicita explícitamente**: "Usa el servidor MCP de webforJ para buscar `[consulta]`"
2. **Pide referencias de documentación**: "Encuentra en la documentación de webforJ cómo `[consulta]`"
3. **Solicita verificación**: "Verifica esta solución contra la documentación de webforJ"
4. **Sé específico del marco**: Siempre incluye "webforJ" en tus consultas

## Personalización de IA {#ai-customization}

Configura a tus asistentes de IA para que utilicen automáticamente el servidor MCP y sigan las mejores prácticas de webforJ. Agrega instrucciones específicas del proyecto para que tus asistentes de IA siempre utilicen el servidor MCP, sigan los estándares de documentación de webforJ y proporcionen respuestas precisas y actualizadas que se ajusten a los requisitos de tu equipo.

### Archivos de configuración del proyecto

- Para **VS Code y Copilot**, crea `.github/copilot-instructions.md`
- Para **Claude Code**, crea `CLAUDE.md` en la raíz de tu proyecto

Agrega lo siguiente al archivo markdown creado:
```markdown
## Usa el servidor MCP de webforJ para responder a cualquier pregunta de webforJ

- Siempre llama a la herramienta "webforj-knowledge-base" para obtener documentación relevante a la pregunta
- Verifica todas las firmas de API contra la documentación oficial
- Nunca supongas que los nombres de métodos o parámetros existen sin verificar

Siempre verifica que el código se compile con `mvn compile` antes de sugerirlo.
```

## Preguntas frecuentes

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Por qué la IA no está utilizando el servidor MCP de webforJ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La mayoría de los asistentes de IA requieren instrucciones explícitas para usar los servidores MCP. Configura tu cliente de IA con las instrucciones de la sección [Personalización de IA](#ai-customization). Sin estas instrucciones, los asistentes de IA pueden recurrir a sus datos de entrenamiento en lugar de consultar el servidor MCP.

      **Solución rápida:**
      Incluye "usa MCP de webforJ" en tu prompt o crea el archivo de configuración apropiado (`.github/copilot-instructions.md` o `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo verificar que la conexión MCP está funcionando?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Utiliza el inspector MCP para depurar conexiones:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Espera el mensaje: `🔍 El Inspector MCP está en funcionamiento en http://127.0.0.1:6274` (el puerto puede variar)

      Luego, en el inspector:
      1. Ingresa la URL del servidor MCP: `https://mcp.webforj.com/mcp`
      2. Haz clic en "Conectar" para establecer la conexión
      3. Visualiza las herramientas disponibles y prueba las consultas
      4. Monitorea los registros de solicitud/respuesta para solucionar problemas
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cuál es la diferencia entre los puntos finales MCP y SSE?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      El servidor MCP de webforJ proporciona dos puntos finales:

      - **Punto final MCP** (`/mcp`) - Protocolo moderno para Claude, VS Code, Cursor
      - **Punto final SSE** (`/sse`) - Eventos enviados por el servidor para clientes heredados como Windsurf

      La mayoría de los usuarios deben usar el punto final MCP. Usa SSE solo si tu cliente no es compatible con el protocolo estándar MCP.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Es posible usar el servidor MCP sin archivos de configuración?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Sí, pero no se recomienda. Sin archivos de configuración, debes solicitar manualmente a la IA que use el servidor MCP en cada conversación. Los archivos de configuración instruyen automáticamente a la IA para que use el servidor MCP para cada interacción, por lo que no tienes que repetir las instrucciones cada vez.

      **Enfoque manual:**
      Comienza los prompts con: "Usa el servidor MCP de webforJ para..."

      **Alternativa: Usar prompts preconfigurados**
      El servidor MCP proporciona prompts que funcionan sin archivos de configuración:
      - `/create-app` - Generar nuevas aplicaciones webforJ
      - `/create-theme` - Crear temas de CSS accesibles
      - `/search-webforj` - Búsqueda avanzada en la documentación

      Consulta [Prompts disponibles](#available-prompts) para más detalles.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo contribuir o reportar problemas?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Reportar problemas:** [Plantilla de Problemas de MCP de webforJ](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml)
      
      **Problemas comunes para reportar:**
      - Documentación desactualizada en los resultados de búsqueda
      - Métodos de API o componentes faltantes
      - Ejemplos de código incorrectos
      - Errores de ejecución de herramientas

      Incluye tu consulta, resultado esperado y resultado actual al reportar problemas.
    </div>
  </AccordionDetails>
</Accordion>
<br />
