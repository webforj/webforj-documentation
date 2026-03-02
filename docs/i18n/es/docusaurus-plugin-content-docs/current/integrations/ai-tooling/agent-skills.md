---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
Las habilidades de agente enseñan a los asistentes de IA cómo construir aplicaciones webforJ utilizando las API correctas, tokens de diseño y patrones de componentes. En lugar de adivinar las convenciones del marco, un asistente de IA carga una habilidad y sigue su flujo de trabajo estructurado para producir código que compila y sigue las mejores prácticas en el primer intento.

Las habilidades siguen la especificación abierta de [Agentes de Habilidades](https://agentskills.io/specification) y funcionan en múltiples asistentes de IA, incluidos Claude Code, GitHub Copilot en VS Code y Cursor. Cada habilidad es un único directorio con un archivo `SKILL.md` que describe el propósito y el flujo de trabajo de la habilidad, junto con los directorios `references/` y `scripts/` para documentación de soporte y scripts auxiliares.

Las habilidades de agente para webforJ están disponibles en el repositorio de GitHub [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills). Con estas habilidades instaladas, una IA cargará estos archivos automáticamente cuando detecte una tarea relevante. Por ejemplo, pedirle a una IA que "tematice esta aplicación con una paleta azul" activa la habilidad `styling-apps`, que guía a la IA a buscar tokens válidos de DWC, escribir CSS con alcance y validar cada nombre de variable antes de producir la salida.

## ¿Por qué usar habilidades? {#why-use-skills}

Sin habilidades, los asistentes de IA a menudo producen código webforJ que parece plausible pero falla en la práctica. Los problemas comunes incluyen:

- Inventar nombres de tokens `--dwc-*` que no existen (CSS compila pero no tiene efecto)
- Usar la clase base incorrecta para los envolturas de componentes (`Composite` en lugar de `ElementComposite`, o viceversa)
- Patrones de `PropertyDescriptor` olvidados, anotaciones de eventos o interfaces de preocupación
- Colores codificados que rompen el modo oscuro
- Omitir pasos de validación que capturan fallas silenciosas

Las habilidades eliminan estos problemas al dar a la IA tablas de decisiones exactas, scripts de búsqueda y listas de verificación de validación para cada tipo de tarea.

## Cómo difieren las habilidades del MCP {#how-skills-differ-from-mcp}

Las habilidades y el [servidor MCP de webforJ](./mcp) desempeñan roles complementarios. MCP proporciona herramientas en vivo que la IA puede llamar en tiempo de ejecución para buscar documentación o generar proyectos. Las habilidades proporcionan conocimientos estáticos y flujos de trabajo paso a paso que guían cómo la IA aborda una tarea.

| | Servidor MCP | Habilidades de Agente |
|---|---|---|
| **Lo que proporciona** | Herramientas en vivo: búsqueda de documentación, generación de proyectos, generación de temas | Conocimientos estáticos: flujos de trabajo, tablas de decisiones, documentos de referencia, scripts auxiliares |
| **Cuándo actúa** | Bajo demanda, cuando la IA llama a una herramienta | Automáticamente, cuando la IA detecta una tarea coincidente |
| **Mejor para** | Consultar API específicas, generar proyectos iniciales, crear paletas de temas | Tareas de extremo a extremo que requieren seguir convenciones de marco y flujos de trabajo de múltiples pasos |

En la práctica, ambas funcionan bien juntas. La herramienta `webforj-create-theme` del servidor MCP genera una paleta válida a partir de un solo color, y la habilidad `styling-apps` luego guía a la IA a través del estilo a nivel de componente y la validación del modo oscuro utilizando esa paleta.

Las habilidades son archivos estáticos leídos desde el disco: no añaden sobrecarga de tiempo de ejecución ni realizan llamadas a API externas. La IA carga el material de referencia de una habilidad en su ventana de contexto cuando es relevante, lo que utiliza algunos tokens de contexto, pero la calidad de salida resultante para el trabajo específico del marco es significativamente más alta.

## Instalación {#installation}

Clona el [repositorio de habilidades de agente de webforJ](https://github.com/webforj/webforJ-agent-skills), luego copia las carpetas de habilidades en la ubicación que tu herramienta de IA espera. Cada herramienta soporta dos ámbitos:

- **Ámbito de proyecto**: la habilidad está disponible solo en ese proyecto
- **Ámbito de usuario**: la habilidad está disponible en todos tus proyectos

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Ámbito de proyecto
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# Ámbito de usuario
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Ámbito de proyecto
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# Ámbito de usuario
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Ámbito de proyecto
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# Ámbito de usuario
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Qué ámbito usar]
Usa **ámbito de proyecto** cuando colabores con un equipo para que todos en el proyecto se beneficien de las mismas habilidades. Usa **ámbito de usuario** cuando trabajes en múltiples proyectos de webforJ y desees que las habilidades estén disponibles en todas partes sin copiarlas en cada repositorio.
:::

## Habilidades disponibles {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: construir componentes reutilizables de webforJ a partir de bibliotecas de componentes web, bibliotecas de JavaScript o componentes webforJ existentes.
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Esta habilidad](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) guía a un asistente de IA a través de la construcción de componentes reutilizables de Java a partir de cualquier fuente, ya sea una biblioteca de componentes web existente, una biblioteca de JavaScript simple o una composición de componentes webforJ existentes.

**Lo que cubre**

La habilidad define cinco caminos para crear componentes y enseña a la IA a seleccionar el correcto según la tarea:

| Camino | Cuándo usar | Clase base |
|---|---|---|
| Envolver una biblioteca de Elementos Personalizados existente | La biblioteca envía Elementos Personalizados (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Construir un Elemento Personalizado y luego envolverlo | Nuevo componente visual o envolviendo una biblioteca JS simple | `ElementComposite` / `ElementCompositeContainer` |
| Componer componentes webforJ | Combinar componentes webforJ existentes en una unidad reutilizable | `Composite<T>` |
| Extender un elemento HTML | Integración ligera de una sola vez sin Shadow DOM | `Div`, `Span`, etc. |
| Utilidad a nivel de página | API de navegador o característica global sin widget DOM | Clase de Java simple + `EventDispatcher` |

**Flujo de trabajo**

Para el envolvimiento de Elementos Personalizados (el camino más común), la habilidad guía a la IA a través de un flujo de trabajo estructurado:

1. **Configuración**: descargar JS/CSS de terceros en el directorio `src/main/resources/static/libs/` del proyecto. La habilidad instruye a la IA para que prefiera recursos locales sobre enlaces CDN para una fiabilidad fuera de línea.
2. **Extraer datos del componente**: usar el script `extract_components.mjs` incluido para analizar un Manifiesto de Elementos Personalizados y producir una especificación estructurada de las propiedades, eventos, slots y propiedades CSS personalizadas de cada componente.
3. **Escribir envolturas de Java**: crear clases `ElementComposite` o `ElementCompositeContainer` con campos de `PropertyDescriptor`, clases de eventos, métodos de slot e interfaces de preocupación, todas siguiendo las convenciones de webforJ.
4. **Escribir pruebas**: generar pruebas de JUnit 5 utilizando `PropertyDescriptorTester` y patrones de prueba estructurados para propiedades, slots y eventos.

**Material de referencia**

La habilidad incluye ocho documentos de referencia que cubren patrones de `ElementComposite`, composición de componentes, descriptores de propiedades, manejo de eventos, interop de JavaScript, patrones de prueba y anti-patrones comunes.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: tematizar y estilizar aplicaciones webforJ utilizando el sistema de token de diseño DWC.
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Esta habilidad](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) enseña a un asistente de IA cómo estilizar aplicaciones webforJ utilizando el sistema de token de diseño DWC. El principio básico es que todos los valores visuales utilizan propiedades CSS personalizadas `--dwc-*`. La habilidad lo refuerza proporcionando pasos de validación y scripts de búsqueda que evitan que la IA invente nombres de tokens o codifique colores.

**Lo que cubre**

| Tarea | Enfoque que la habilidad enseña |
|------|---------------------------|
| Recoloración | Sobrescribir los tokens de tono, saturación y contraste de la paleta en `:root` |
| Estilización de componentes | Buscar primero las variables CSS del componente, recurrir a `::part()` solo cuando sea necesario |
| Diseño y espaciado | Utilizar tokens `--dwc-space-*` y `--dwc-size-*` |
| Tipografía | Utilizar tokens `--dwc-font-*` |
| Tema completo | Configuración de paleta con remapeo de tokens semánticos |
| Estilización de tablas | Selectores de `::part()` solamente (las tablas no exponen variables CSS) |
| Google Charts | Archivo de tema JSON cargado a través de `Assets.contentOf()` y Gson |

**Flujo de trabajo**

La habilidad impone una estricta disciplina de buscar antes de escribir:

1. **Clasificar la tarea**: determinar si se trata de una recoloración de paleta, estilización de componente, trabajo de diseño o un tema completo.
2. **Escanear la aplicación**: leer el código fuente de Java para encontrar cada componente, variante de tema y extensión en uso.
3. **Buscar cada componente**: ejecutar el script `component_styles.py` incluido para recuperar las variables CSS exactas, los nombres de `::part()` y los atributos reflejados que soporta cada componente. La IA no escribe CSS hasta que este paso esté completo.
4. **Escribir CSS**: producir CSS anidado y compacto que siga las convenciones de DWC: primero los tokens globales, luego las variables CSS del componente, luego las sobreescrituras de `::part()` como último recurso.
5. **Validar**: volver a ejecutar el script de búsqueda y verificar que cada token, nombre de parte y selector en la salida realmente existe. Corregir cualquier cosa que falle.

**Reglas clave que la habilidad impone**

- **Solo siete paletas**: `primary`, `success`, `warning`, `danger`, `info`, `default` y `gray`. Nombres como `secondary` o `accent` no existen en DWC y fallan silenciosamente.
- **Sin colores codificados**: cada valor de color debe ser una referencia `var()`, incluyendo dentro de `box-shadow` y `border`. Los valores codificados rompen el modo oscuro.
- **Variables CSS sobre `::part()`**: las variables CSS del componente son la API de estilo pretendida. `::part()` es el recurso de escape para los casos donde no existe ninguna variable.
- **Selectores con alcance**: selectores de etiquetas desnudas en componentes con atributos `theme` o `expanse` sobreescriben todas las variantes. La habilidad requiere `:not([theme])` o `[theme~="value"]` para el alcance.

</div>
  </AccordionDetails>
</Accordion>
