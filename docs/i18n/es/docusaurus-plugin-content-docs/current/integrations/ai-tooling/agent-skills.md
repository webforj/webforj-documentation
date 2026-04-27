---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
Las habilidades de agente enseñan a los asistentes de codificación AI cómo construir aplicaciones webforJ utilizando las API correctas, tokens de diseño y patrones de componentes. En lugar de adivinar las convenciones del marco, el asistente carga una habilidad y sigue un flujo de trabajo estructurado para producir código que se compila y sigue las mejores prácticas en el primer intento.

:::tip Usa el plugin
Las habilidades a continuación se incluyen dentro del **[plugin de AI de webforJ](/docs/integrations/ai-tooling)** junto con el [servidor MCP](/docs/integrations/ai-tooling/mcp). Una instalación proporciona a tu asistente ambas piezas.
:::

Las habilidades siguen el estándar abierto [Agent Skills](https://agentskills.io/specification) y funcionan en muchos asistentes AI, incluyendo Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex y más. Una habilidad le dice al asistente qué tipo de tarea maneja; el asistente la carga automáticamente cuando tu solicitud coincide. Por ejemplo, pedir "tematiza esta aplicación con una paleta azul" activa la habilidad `webforj-styling-apps`, que guía al asistente a buscar tokens DWC válidos, escribir CSS con alcance, y validar cada nombre de variable antes de escribir cualquier cosa en el disco.

## ¿Por qué usar habilidades? {#why-use-skills}

El servidor MCP hace que la información precisa de webforJ esté disponible bajo demanda, pero por sí solo no le dice al asistente _cuándo_ buscar algo, _qué_ enfoque se adapta a la tarea, o _en qué orden_ hacer las cosas. Ahí es donde entran en juego las habilidades.

Las habilidades le dan al asistente un manual de tareas específico: cómo clasificar el trabajo que tiene frente a sí, qué patrones de webforJ se adaptan, qué herramientas MCP consultar en cada paso, y cómo validar la salida antes de devolverla. El resultado es un código de webforJ consistente que sigue las convenciones, en lugar de una colección de fragmentos técnicamente válidos pero estilísticamente dispares.

## Cómo difieren las habilidades del MCP {#how-skills-differ-from-mcp}

Las habilidades y el [servidor MCP de webforJ](/docs/integrations/ai-tooling/mcp) cumplen roles complementarios. El servidor MCP proporciona herramientas en vivo que el asistente puede llamar para obtener información o generar resultados. Las habilidades proporcionan el flujo de trabajo que le indica al asistente _cuándo_ alcanzar esas herramientas, en qué orden hacer las cosas, y cómo validar el resultado.

| | Servidor MCP | Habilidades de Agente |
|---|---|---|
| **Lo que proporciona** | Herramientas que el asistente invoca a demanda (búsqueda de documentos, scaffolding, generación de temas, validación de tokens) | Flujos de trabajo y tablas de decisiones que guían cómo el asistente aborda una tarea |
| **Cuándo actúa** | Cuando el asistente decide llamar una herramienta | Automáticamente, cuando el asistente detecta una tarea coincidente |
| **Mejor para** | Responder preguntas específicas, generar artefactos | Tareas de extremo a extremo que necesitan un enfoque webforJ consistente |

En la práctica, los dos funcionan mejor juntos, y el [plugin de AI de webforJ](https://github.com/webforj/webforj-ai) los incluye como una sola instalación.

## Instalación {#installation}

Instala el **[plugin de AI de webforJ](/docs/integrations/ai-tooling)** - incluye ambas habilidades a continuación junto con el servidor MCP. Para clientes que no soportan plugins, el [repositorio de AI de webforJ](https://github.com/webforj/webforj-ai#clients) enumera el directorio de habilidades del que cada herramienta lee, para que puedas copiar las carpetas de habilidades manualmente.

## Habilidades disponibles {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: agregar puntos finales REST, webhooks y servlets personalizados
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto cuando necesites una ruta HTTP no UI - un punto final REST, un controlador de webhook o un servlet de terceros como Swagger UI o Spring Web. El asistente elige el enfoque correcto para tu proyecto (Spring `webforj.exclude-urls`, remapeo de `WebforjServlet` a una subruta, o proxy a través de `webforj.conf`) y configura el punto final sin interrumpir el enrutamiento de UI de webforJ.

**Cuándo se activa**

- *"Agrega un punto final REST en `/api/orders`."*
- *"Conecta un controlador de webhook para Stripe."*
- *"Montar Swagger UI en `/api/docs`."*
- *"Exponer un servlet personalizado que funcione junto a la UI de webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: construir formularios con bindings, validación y máscaras de entrada
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo de formulario en una aplicación webforJ: formularios de entrada de datos, binding bidireccional a un bean de Java, validación de Jakarta, componentes de entrada con máscara (teléfono, moneda, IBAN, fechas), formateo de columnas de tabla como moneda o porcentaje, y diseños de múltiples columnas responsivos. El asistente navega a través de `BindingContext`, los componentes `Masked*Field`, los renderizadores de máscara de tabla, y `ColumnsLayout`.

**Cuándo se activa**

- *"Crea un formulario de registro vinculado a mi bean `User`."*
- *"Agrega un campo de número de teléfono con formato a medida que escribes."*
- *"Formato esta columna de tabla como moneda."*
- *"Valida este campo con `@NotEmpty` y un verificador de correo electrónico personalizado."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: envolver componentes web, bibliotecas JS o composiciones
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto cuando necesites un componente Java reutilizable envuelto alrededor de cualquier fuente - una biblioteca de Elementos Personalizados existente, una biblioteca de JavaScript simple o una composición de componentes existentes de webforJ. El asistente elige la clase base de webforJ correcta para el trabajo, conecta propiedades, eventos y slots con los patrones correctos, y produce pruebas que siguen las convenciones de webforJ.

**Cuándo se activa**

- *"Envuelve esta biblioteca de Elementos Personalizados como componentes de webforJ."*
- *"Compón estos componentes de webforJ en una tarjeta reutilizable."*
- *"Integra esta biblioteca de JavaScript simple como un componente de webforJ."*
- *"Expón esta API del Navegador como una utilidad de webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: programar temporizadores, debouncers y trabajos asíncronos
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para tareas periódicas, polling, búsqueda debounce a medida que escribes, throttling y trabajos en segundo plano de larga duración que actualizan la UI a medida que se ejecutan. El asistente elige el primitivo correcto (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) y evita las trampas de tiempo de ejecución de `java.util.Timer`, `javax.swing.Timer`, o hilos creados fuera del entorno de webforJ, todos los cuales lanzan `IllegalStateException` en el momento en que tocan un componente de la UI.

**Cuándo se activa**

- *"Actualiza este panel cada 30 segundos."*
- *"Agrega un debouncer de búsqueda a medida que escribes."*
- *"Ejecuta este trabajo que consume CPU en segundo plano y actualiza la barra de progreso."*
- *"Realiza polling a este punto final REST hasta que devuelva `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: agregar soporte i18n y de traducción
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo de internacionalización: cargar paquetes de mensajes, cambiar de idioma en tiempo de ejecución, detectar automáticamente la configuración regional del navegador del usuario y traducir etiquetas de componentes. El asistente navega a través del `BundleTranslationResolver` de webforJ 25.12, la preocupación `HasTranslation`, `LocaleObserver`, y resolutores personalizables, y cubre tanto los caminos de Spring como los de webforJ simples.

**Cuándo se activa**

- *"Agrega soporte multilingüe con inglés y español."*
- *"Detecta la configuración regional del navegador del usuario y aplícala al iniciar."*
- *"Agrega un conmutador de idioma a la barra de navegación."*
- *"Mueve todas las cadenas codificadas dentro de un paquete de mensajes."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: proteger rutas con inicio de sesión y acceso basado en roles
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier cosa que proteja rutas en una aplicación webforJ: inicio y cierre de sesión, acceso basado en roles, páginas de aterrizaje públicas, secciones solo para administradores, reglas de propiedad y políticas seguras por defecto. El asistente prefiere Spring Security cuando Spring Boot está en el classpath y recurre al marco de seguridad simple de webforJ en caso contrario. Aplica las anotaciones correctas (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) y explica cuáles son terminales y cuáles son composables, de modo que la política segura por defecto aún cumple lo que dice.

**Cuándo se activa**

- *"Protege `/admin` para que solo los usuarios con el rol `ADMIN` puedan verlo."*
- *"Agrega una página de aterrizaje pública que cualquiera pueda visitar."*
- *"Muestra el nombre del usuario conectado en el encabezado."*
- *"Permite que un usuario edite un registro que posee."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: tematizar aplicaciones con tokens de diseño DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo visual en una aplicación webforJ: reestilización de paletas, estilos a nivel de componente, diseño y espaciado, tipografía, temas completos, apariencia de tablas, o colores coordinados de Google Charts. El asistente escribe CSS que se adhiere a los tokens de diseño DWC, escoge selectores correctamente, y valida cada referencia `--dwc-*` contra el catálogo real para tu versión de webforJ - de modo que el modo oscuro y el cambio de temas sigan funcionando.

**Cuándo se activa**

- *"Tematiza esta aplicación con una paleta azul."*
- *"Estiliza el dwc-button para que coincida con las pautas de marca."*
- *"Haz que este diseño sea más ajustado - ajusta el espaciado y la tipografía."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: actualizar a través de versiones principales de webforJ con OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para actualizaciones de versiones principales. El asistente ejecuta la receta oficial `webforj-rewrite` de OpenRewrite para la versión objetivo, que incrementa `<webforj.version>` y la versión de Java, reescribe las APIs y tipos renombrados, e inserta comentarios `TODO webforJ <major>:` en cada método eliminado que necesita una decisión manual. Para objetivos más antiguos sin receta publicada (por ejemplo, 24 a 25), te guía a través de la solución manual.

**Cuándo se activa**

- *"Actualiza esta aplicación de webforJ 25 a 26."*
- *"Ejecuta la receta de reescritura y resuelve los TODOs."*
- *"Migrar de webforJ 24 a 25 manualmente ya que no hay receta."*
- *"¿Qué APIs eliminadas necesito corregir después de la actualización?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
