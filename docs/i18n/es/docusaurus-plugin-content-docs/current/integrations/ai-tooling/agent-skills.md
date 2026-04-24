---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 98d3f61447c289339f92fc4872e734f1
---
Las habilidades del agente enseñan a los asistentes de codificación AI cómo construir aplicaciones webforJ utilizando las APIs correctas, tokens de diseño y patrones de componentes. En lugar de adivinar las convenciones del marco, el asistente carga una habilidad y sigue un flujo de trabajo estructurado para producir código que compila y sigue las mejores prácticas en el primer intento.

:::tip Usa el plugin
Las habilidades a continuación se entregan dentro del **[plugin de AI webforJ](/docs/integrations/ai-tooling)** junto con el [servidor MCP](/docs/integrations/ai-tooling/mcp). Una instalación le da a tu asistente ambas partes.
:::

Las habilidades siguen el estándar abierto de [Agente Skills](https://agentskills.io/specification) y funcionan en muchos asistentes de AI, incluyendo Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex y más. Una habilidad le dice al asistente qué tipo de tarea maneja; el asistente la carga automáticamente cuando tu mensaje coincide. Por ejemplo, pedir "tematiza esta aplicación con una paleta azul" activa la habilidad `webforj-styling-apps`, que guía al asistente a buscar tokens DWC válidos, escribir CSS escopado y validar cada nombre de variable antes de escribir algo en el disco.

## ¿Por qué usar habilidades? {#why-use-skills}

El servidor MCP hace que la información precisa de webforJ esté disponible bajo demanda, pero por sí solo no le indica al asistente _cuándo_ buscar algo, _qué_ enfoque se ajusta a la tarea, o _en qué orden_ hacer las cosas. Ahí es donde entran las habilidades.

Las habilidades le dan al asistente un manual de tareas específico: cómo clasificar el trabajo que tiene delante, qué patrones de webforJ son adecuados, qué herramientas MCP consultar en cada paso, y cómo validar la salida antes de devolverla. El resultado es un código webforJ consistente y conforme a las convenciones en lugar de una colección de fragmentos técnicamente válidos pero estilísticamente desincronizados.

## Cómo difieren las habilidades del MCP {#how-skills-differ-from-mcp}

Las habilidades y el [servidor MCP de webforJ](/docs/integrations/ai-tooling/mcp) desempeñan roles complementarios. El servidor MCP proporciona herramientas en vivo a las que el asistente puede llamar para obtener información o generar salida. Las habilidades proporcionan el flujo de trabajo que le indica al asistente _cuándo_ recurrir a esas herramientas, en qué orden hacer las cosas y cómo validar el resultado.

| | Servidor MCP | Habilidades del Agente |
|---|---|---|
| **Lo que proporciona** | Herramientas a las que el asistente llama bajo demanda (búsqueda de documentos, andamiaje, generación de temas, validación de tokens) | Flujos de trabajo y tablas de decisiones que guían cómo el asistente aborda una tarea |
| **Cuándo actúa** | Cuando el asistente decide llamar a una herramienta | Automáticamente, cuando el asistente detecta una tarea que coincide |
| **Mejor para** | Responder preguntas específicas, generar artefactos | Tareas de principio a fin que necesitan un enfoque webforJ consistente |

En la práctica, los dos funcionan mejor juntos, y el [plugin de AI webforJ](https://github.com/webforj/webforj-ai) los entrega como una sola instalación.

## Instalación {#installation}

Instala el **[plugin de AI webforJ](/docs/integrations/ai-tooling)** - se entregan ambas habilidades a continuación junto con el servidor MCP. Para clientes que no soportan plugins, el [repositorio de AI webforJ](https://github.com/webforj/webforj-ai#clients) enumera el directorio de habilidades que cada herramienta lee, para que puedas copiar las carpetas de habilidades manualmente.

## Habilidades disponibles {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: construye componentes webforJ reutilizables a partir de bibliotecas de componentes web, bibliotecas de JavaScript o componentes webforJ existentes
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utiliza esto cuando necesites un componente Java reutilizable envuelto alrededor de cualquier fuente: una biblioteca existente de elementos personalizados, una biblioteca de JavaScript simple, o una composición de componentes webforJ existentes. El asistente elige la clase base webforJ adecuada para el trabajo, conecta propiedades, eventos y slots con los patrones correctos, y produce pruebas que siguen las convenciones de webforJ.

**Cuándo se activa**

- *"Envuelve esta biblioteca de elementos personalizados como componentes webforJ."*
- *"Compón estos componentes webforJ en una tarjeta reutilizable."*
- *"Integra esta biblioteca de JavaScript simple como un componente webforJ."*
- *"Expón esta API del navegador como una utilidad webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: tematiza y da estilo a aplicaciones webforJ utilizando el sistema de tokens de diseño DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Utiliza esto para cualquier trabajo visual en una aplicación webforJ: reasignaciones de paletas, estilo a nivel de componente, diseño y espaciado, tipografía, temas completos, apariencia de tablas o colores coordinados de Google Charts. El asistente escribe CSS que se adhiere a los tokens de diseño DWC, escopa selectores correctamente y valida cada referencia `--dwc-*` contra el catálogo real para tu versión de webforJ, para que el modo oscuro y el cambio de tema sigan funcionando.

**Cuándo se activa**

- *"Tematiza esta aplicación con una paleta azul."*
- *"Estila el dwc-button para que coincida con las pautas de la marca."*
- *"Haz este diseño más ajustado - ajusta el espaciado y la tipografía."*

</div>
  </AccordionDetails>
</Accordion>
