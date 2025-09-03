---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
_i18n_hash: 7b656643222d616e7c44d14ed1de7bd3
---
El servidor del Protocolo de Contexto del Modelo (MCP) de webforJ proporciona a los asistentes de IA acceso directo a la documentación oficial de webforJ, ejemplos de código verificados y patrones específicos del marco, lo que permite respuestas con respuestas más precisas y generación automática de proyectos específicamente para el desarrollo de webforJ.

## ¿Qué es un MCP?

El Protocolo de Contexto del Modelo es un estándar abierto que permite a los asistentes de IA conectarse con herramientas y documentación externas. El servidor MCP de webforJ implementa este protocolo para proporcionar:

- **Búsqueda de Conocimientos** - Búsqueda en lenguaje natural a través de la documentación de webforJ, ejemplos de código y patrones
- **Generación de Proyectos** - Crear aplicaciones de webforJ a partir de plantillas oficiales con la estructura adecuada
- **Creación de Temas** - Generar temas CSS accesibles siguiendo los patrones de diseño de webforJ

## ¿Por qué usar MCP?

Mientras que los asistentes de codificación de IA sobresalen en responder preguntas básicas, tienen dificultades con consultas complejas específicas de webforJ que abarcan múltiples secciones de documentación. Sin acceso directo a fuentes oficiales, pueden:

- Generar métodos que no existen en webforJ
- Referenciar patrones de API obsoletos o incorrectos  
- Proporcionar código que no compilará
- Confundir la sintaxis de webforJ con otros marcos de Java
- Malinterpretar patrones específicos de webforJ

Con la integración de MCP, las respuestas de IA están ancladas a la documentación real de webforJ, ejemplos de código y patrones del marco, proporcionando respuestas verificables con enlaces directos a fuentes oficiales para una exploración más profunda.

:::warning La IA aún puede cometer errores
Si bien MCP mejora significativamente la precisión al proporcionar acceso a recursos oficiales de webforJ, no garantiza una generación de código perfecta. Los asistentes de IA todavía pueden cometer errores en escenarios complejos. Siempre verifique el código generado y pruébelo a fondo antes de usarlo en producción.
:::

## Instalación

El servidor MCP de webforJ está alojado en `https://mcp.webforj.com` con dos puntos finales:

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

Agrega esta configuración a tus configuraciones de Cursor:

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

Para instrucciones detalladas, consulta la [guía oficial de integración](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Agrega esta configuración del servidor a tus configuraciones de MCP de Windsurf:

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

Las herramientas son funciones especializadas que el servidor MCP proporciona a los asistentes de IA. Cuando haces una pregunta o realizas una solicitud, la IA puede llamar a estas herramientas para buscar documentación, generar proyectos o crear temas. Cada herramienta acepta parámetros específicos y devuelve datos estructurados que ayudan a la IA a proporcionar asistencia precisa y contextual.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Buscar documentación y ejemplos
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Esta herramienta proporciona capacidades de búsqueda semántica a través de todo el ecosistema de documentación de webforJ. Entiende el contexto y las relaciones entre diferentes conceptos del marco, devolviendo secciones de documentación relevantes, referencias de API y ejemplos de código funcionales.

      **Consultas de ejemplo:**
      ```
      "Buscar en la documentación de webforJ ejemplos de componentes de Botón con iconos"

      "Encontrar patrones de validación de formularios de webforJ en la documentación más reciente"

      "Muéstrame la configuración de enrutamiento actual de webforJ con la anotación @Route"

      "Buscar en la documentación de webforJ patrones de diseño responsivo FlexLayout"

      "Encontrar la integración de componentes web de webforJ en la documentación oficial"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Generar nuevos proyectos de webforJ  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Crea aplicaciones completas de webforJ utilizando arquetipos oficiales de Maven. La herramienta crea un diseño estándar de directorio de proyectos e incluye código inicial basado en la plantilla seleccionada. Los proyectos generados incluyen un sistema de construcción listo para usar, carpetas de recursos y archivos de configuración para un desarrollo y despliegue inmediato.

      **Indicaciones de ejemplo:**
      ```
      "Crear un proyecto de webforJ llamado CustomerPortal utilizando el arquetipo hello-world"

      "Generar un proyecto de webforJ Spring Boot con diseño de pestañas llamado Dashboard"

      "Crear una nueva aplicación de webforJ con el arquetipo de menú lateral para el proyecto AdminPanel"

      "Generar un proyecto en blanco de webforJ llamado TestApp con groupId com.example"

      "Crear el proyecto de webforJ InventorySystem utilizando el arquetipo de menú lateral con Spring Boot"
      ```

      Al usar esta herramienta, puedes elegir entre varias plantillas de proyectos:

      **Arquetipos** (plantillas de proyectos):
      - `hello-world` - Aplicación básica con componentes de muestra para demostrar las características de webforJ
      - `blank` - Estructura de proyecto mínima para comenzar desde cero
      - `tabs` - Diseño de interfaz con pestañas ya construido para aplicaciones de múltiples vistas
      - `sidemenu` - Diseño de menú de navegación lateral para paneles de administrador o tableros

      **Sabores** (integración del marco):
      - `webforj` - Aplicación estándar de webforJ
      - `webforj-spring` - webforJ integrado con Spring Boot para inyección de dependencias y funciones empresariales

      :::tip Arquetipos Disponibles
      webforJ viene con varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para obtener una lista completa de los arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Crear temas CSS accesibles
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Genera configuraciones de temas de webforJ utilizando [DWC HueCraft](https://huecraft.dwc.style/). La herramienta crea conjuntos completos de propiedades CSS personalizadas con variantes de color primario, secundario, éxito, advertencia, peligro y neutral.

      **Solicitudes de ejemplo:**
      ```
      "Generar un tema de webforJ con HSL 220, 70, 50 como color primario para nuestra marca corporativa"

      "Crear un tema accesible de webforJ llamado 'oceánico' con el color primario #0066CC"

      "Generar un tema de webforJ utilizando nuestro color de marca #FF5733"

      "Crear un tema de webforJ con HSL 30, 100, 50 llamado 'atardecer' para nuestra aplicación"

      "Generar un tema accesible de webforJ con primario RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Prompts disponibles {#available-prompts}

Los prompts son instrucciones de IA preconfiguradas que combinan múltiples herramientas y flujos de trabajo para tareas comunes. Guían a la IA a través de pasos y parámetros específicos para entregar resultados confiables y repetibles para cada flujo de trabajo admitido.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Crear y ejecutar una aplicación de webforJ
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
    <strong><code>create-theme</code></strong> - Generar un tema de webforJ a partir de un color primario
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

      1. Buscar extensamente en la base de conocimientos
      2. Escribir código completo, listo para producción
      3. Compilar el proyecto utilizando `mvn compile` para verificar que no haya errores de compilación
      4. Arregar errores de manera iterativa hasta que todo funcione
    </div>
  </AccordionDetails>
</Accordion>

### Cómo usar los prompts

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code y Claude Code">

1. Escribe <kbd>/</kbd> en el chat para ver los prompts disponibles
2. Selecciona un prompt del menú desplegable
3. Completa los parámetros requeridos cuando se te solicite

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Haz clic en el ícono **+** (más) en el área de entrada de prompts
2. Selecciona **"Agregar desde webforJ"** del menú
3. Elige el prompt deseado (por ejemplo, `create-app`, `create-theme`, `search-webforj`)
4. Claude te pedirá que ingreses los argumentos requeridos
5. Completa los parámetros según lo solicitado

:::tip Verifica que MCP esté conectado
Busca el ícono de herramientas en la esquina inferior del área de entrada para confirmar que el servidor MCP de webforJ esté conectado.
:::

</TabItem>
</Tabs>

## Mejores prácticas

Para obtener la asistencia más precisa y actualizada de webforJ, sigue estas pautas para aprovechar al máximo las características del servidor MCP.

### Asegurando el uso del servidor MCP

Los modelos de IA pueden omitir el servidor MCP si creen que ya conocen la respuesta. Para asegurarte de que se utilice realmente el servidor MCP:

- **Sé explícito sobre webforJ**: Siempre menciona "webforJ" en tu consulta para activar búsquedas específicas del marco
- **Solicita información actual**: Incluye frases como "documentación más reciente de webforJ" o "patrones actuales de webforJ"
- **Pide ejemplos verificados**: Solicita "ejemplos de código funcionales de webforJ" para forzar una búsqueda en la documentación
- **Referencia versiones específicas**: Menciona tu versión de webforJ (por ejemplo, "webforJ `25.02`") para obtener resultados precisos

### Escribiendo prompts específicos

**Buenos ejemplos:**
```
"Buscar en la documentación de webforJ ejemplos de manejo de eventos del componente Botón"

"Crear un proyecto de webforJ llamado InventorySystem utilizando el arquetipo de menú lateral con Spring Boot"

"Generar un tema de webforJ con HSL 220, 70, 50 como color primario para la marca corporativa"
```

**Malos ejemplos:**
```
"¿Cómo funcionan los botones?"

"Hacer una aplicación"

"Hacerlo azul"
```

### Forzar el uso de herramientas MCP

Si la IA brinda respuestas genéricas sin usar el servidor MCP:

1. **Solicitar explícitamente**: "Usa el servidor MCP de webforJ para buscar `[query]`"
2. **Pedir referencias de documentación**: "Encuentra en la documentación de webforJ cómo `[query]`"
3. **Solicitar verificación**: "Verifica esta solución contra la documentación de webforJ"
4. **Ser específico del marco**: Siempre incluye "webforJ" en tus consultas

## Personalización de IA {#ai-customization}

Configura a tus asistentes de IA para que usen automáticamente el servidor MCP y sigan las mejores prácticas de webforJ. Agrega instrucciones específicas del proyecto para que tus asistentes de IA siempre utilicen el servidor MCP, sigan los estándares de documentación de webforJ y proporcionen respuestas precisas y actualizadas que coincidan con los requisitos de tu equipo.

### Archivos de configuración del proyecto

- Para **VS Code y Copilot**, crea `.github/copilot-instructions.md`
- Para **Claude Code**, crea `CLAUDE.md` en la raíz de tu proyecto

Agrega lo siguiente al archivo markdown creado:
```markdown
## Usa el servidor MCP de webforJ para responder cualquier pregunta sobre webforJ

- Siempre llama a la herramienta "webforj-knowledge-base" para obtener documentación relevante a la pregunta
- Verifica todas las firmas de API contra la documentación oficial
- Nunca asumas que los nombres de método o parámetros existen sin verificar

Siempre verifica que el código compila con `mvn compile` antes de sugerir.
```

## Preguntas Frecuentes

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Por qué la IA no está usando el servidor MCP de webforJ?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      La mayoría de los asistentes de IA requieren instrucciones explícitas para usar los servidores MCP. Configura tu cliente de IA con las instrucciones de la sección [Personalización de IA](#ai-customization). Sin estas instrucciones, los asistentes de IA pueden predeterminar sus datos de entrenamiento en lugar de consultar el servidor MCP.

      **Solución rápida:**
      Incluye "usar MCP de webforJ" en tu prompt o crea el archivo de configuración apropiado (`.github/copilot-instructions.md` o `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Cómo verificar que la conexión MCP esté funcionando?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Usa el inspector MCP para depurar conexiones:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Espera el mensaje: `🔍 El Inspector MCP está en funcionamiento en http://127.0.0.1:6274` (el puerto puede variar)

      Luego en el inspector:
      1. Ingresa la URL del servidor MCP: `https://mcp.webforj.com/mcp`
      2. Haz clic en "Conectar" para establecer conexión
      3. Ver herramientas disponibles y probar consultas
      4. Monitorea los registros de solicitudes/respuestas para depurar
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
      - **Punto final SSE** (`/sse`) - Eventos Enviados por el Servidor para clientes heredados como Windsurf

      La mayoría de los usuarios deben usar el punto final MCP. Solo usa SSE si tu cliente no admite el protocolo estándar MCP.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>¿Es posible usar el servidor MCP sin archivos de configuración?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Sí, pero no se recomienda. Sin archivos de configuración, debes pedir manualmente a la IA que use el servidor MCP en cada conversación. Los archivos de configuración instruyen automáticamente a la IA para que utilice el servidor MCP en cada interacción, por lo que no tienes que repetir las instrucciones cada vez.

      **Enfoque manual:**
      Comienza los prompts con: "Usa el servidor MCP de webforJ para..."

      **Alternativa: Usa prompts preconfigurados**
      El servidor MCP proporciona prompts que funcionan sin archivos de configuración:
      - `/create-app` - Generar nuevas aplicaciones de webforJ
      - `/create-theme` - Crear temas CSS accesibles
      - `/search-webforj` - Búsqueda avanzada de documentación

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
      
      **Problemas comunes a reportar:**
      - Documentación obsoleta en los resultados de búsqueda
      - Métodos o componentes de API faltantes
      - Ejemplos de código incorrectos
      - Errores en la ejecución de herramientas

      Incluye tu consulta, el resultado esperado y el resultado actual al reportar problemas.
    </div>
  </AccordionDetails>
</Accordion>
<br />
