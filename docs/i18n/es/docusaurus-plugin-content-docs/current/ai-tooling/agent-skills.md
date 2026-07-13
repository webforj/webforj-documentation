---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
Las habilidades de Agent enseñan a los asistentes de codificación de IA cómo construir aplicaciones webforJ utilizando las API, los tokens de diseño y los patrones de componentes correctos. En lugar de adivinar las convenciones del marco, el asistente carga una habilidad y sigue un flujo de trabajo estructurado para producir código que compila y sigue las mejores prácticas en el primer intento.

:::tip Usa el complemento
Las habilidades a continuación se envían dentro del **[complemento de IA de webforJ](/docs/ai-tooling)** junto con el [servidor MCP](/docs/ai-tooling/mcp). Una instalación le da a su asistente ambas piezas.
:::

Las habilidades siguen el estándar abierto de [Agent Skills](https://agentskills.io/specification) y funcionan en muchos asistentes de IA, incluidos Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex y más. Una habilidad le dice al asistente qué tipo de tarea maneja; el asistente la carga automáticamente cuando su solicitud coincide. Por ejemplo, pedir "cambia el tema de esta app con una paleta azul" activa la habilidad `webforj-styling-apps`, que guía al asistente a buscar tokens DWC válidos, escribir CSS específico y validar cada nombre de variable antes de escribir cualquier cosa en el disco.

## ¿Por qué usar habilidades? {#why-use-skills}

El servidor MCP hace que la información precisa de webforJ esté disponible bajo demanda, pero por sí solo no le dice al asistente _cuándo_ buscar algo, _qué_ enfoque se adapta a la tarea o _en qué orden_ hacer las cosas. Ahí es donde entran las habilidades.

Las habilidades le dan al asistente un manual específico de tareas: cómo clasificar el trabajo que tiene delante, qué patrones de webforJ encajan, qué herramientas de MCP consultar en cada paso y cómo validar el resultado antes de devolverlo. El resultado es un código webforJ consistente y que sigue convenciones, en lugar de una colección de fragmentos técnicamente válidos pero estilísticamente desajustados.

## Cómo las habilidades difieren del MCP {#how-skills-differ-from-mcp}

Las habilidades y el [servidor MCP de webforJ](/docs/ai-tooling/mcp) cumplen roles complementarios. El servidor MCP proporciona herramientas en vivo que el asistente puede llamar para obtener información o generar resultados. Las habilidades proporcionan el flujo de trabajo que le indica al asistente _cuándo_ utilizar esas herramientas, en qué orden hacer las cosas y cómo validar el resultado.

| | Servidor MCP | Habilidades de Agent |
|---|---|---|
| **Lo que proporciona** | Herramientas que el asistente llama bajo demanda (búsqueda de documentos, creación de estructuras, generación de temas, validación de tokens) | Flujos de trabajo y tablas de decisión que guían cómo el asistente aborda una tarea |
| **Cuándo actúa** | Cuando el asistente decide llamar a una herramienta | Automáticamente, cuando el asistente detecta una tarea coincidente |
| **Mejor para** | Responder preguntas específicas, generar artefactos | Tareas de principio a fin que necesitan un enfoque webforJ consistente |

En la práctica, los dos funcionan mejor juntos, y el [complemento de IA de webforJ](https://github.com/webforj/webforj-ai) los envía como una sola instalación.

## Instalación {#installation}

Instala el **[complemento de IA de webforJ](/docs/ai-tooling)**; incluye ambas habilidades a continuación junto con el servidor MCP. Para los clientes que no soportan complementos, el [repositorio de IA de webforJ](https://github.com/webforj/webforj-ai#clients) enumera el directorio de habilidades que cada herramienta lee, para que puedas copiar las carpetas de habilidades manualmente.

## Habilidades disponibles {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: añadir puntos finales REST, manejadores de webhooks y servlets personalizados
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto cuando necesites una ruta HTTP no relacionada con la interfaz de usuario: un punto final REST, un manejador de webhook o un servlet de terceros como Swagger UI o Spring Web. El asistente elige el enfoque correcto para tu proyecto (Spring `webforj.exclude-urls`, remapeo de `WebforjServlet` a una subruta, o el proxy a través de `webforj.conf`) y conecta el punto final sin interrumpir el enrutamiento de la interfaz de usuario de webforJ.

**Cuándo se activa**

- *"Añadir un punto final REST en `/api/orders`."*
- *"Conectar un manejador de webhook para Stripe."*
- *"Montar Swagger UI en `/api/docs`."*
- *"Exponer un servlet personalizado que funcione junto a la interfaz de usuario de webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: construir formularios con enlace, validación y máscaras de entrada
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo de formularios en una app webforJ: formularios de entrada de datos, enlace bidireccional a un bean de Java, validación de Jakarta, componentes de entrada enmascarados (teléfono, moneda, IBAN, fechas), formatear columnas de tablas como moneda o porcentaje, y diseños de múltiples columnas responsivos. El asistente se orienta a través de `BindingContext`, los componentes `Masked*Field`, los renderizadores de máscara de Tabla y `ColumnsLayout`.

**Cuándo se activa**

- *"Construir un formulario de registro vinculado a mi bean `User`."*
- *"Añadir una entrada de número de teléfono con formato a medida que escribes."*
- *"Formatear esta columna de tabla como moneda."*
- *"Validar este campo con `@NotEmpty` y un verificador de correos electrónicos personalizado."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: envolver componentes web, bibliotecas de JS o composiciones
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto cuando necesites un componente de Java reutilizable envuelto alrededor de cualquier fuente: una biblioteca de Elementos Personalizados existente, una biblioteca de JavaScript simple o una composición de componentes webforJ existentes. El asistente elige la clase base de webforJ correcta para el trabajo, conecta propiedades, eventos y ranuras con los patrones correctos, y produce pruebas que siguen las convenciones de webforJ.

**Cuándo se activa**

- *"Envolver esta biblioteca de Elementos Personalizados como componentes de webforJ."*
- *"Componer estos componentes de webforJ en una tarjeta reutilizable."*
- *"Integrar esta biblioteca de JavaScript simple como un componente de webforJ."*
- *"Exponer esta API del Navegador como una utilidad de webforJ."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: programar temporizadores, debouncers y trabajos asíncronos
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para tareas periódicas, sondeos, búsqueda con debounce a medida que escribes, limitación y trabajos de fondo de larga duración que actualizan la interfaz de usuario a medida que se ejecutan. El asistente elige el primitivo adecuado (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) y evita las trampas de tiempo de ejecución de `java.util.Timer`, `javax.swing.Timer`, o hilos creados fuera del entorno de webforJ, todos los cuales lanzan `IllegalStateException` en el momento en que tocan un componente de la interfaz de usuario.

**Cuándo se activa**

- *"Actualizar este panel cada 30 segundos."*
- *"Añadir un debounce de búsqueda a medida que escribes."*
- *"Ejecutar este trabajo intensivo en CPU en segundo plano y actualizar la barra de progreso."*
- *"Sondear este punto final REST hasta que devuelva `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: añadir soporte de i18n y traducción
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo de internacionalización: cargar paquetes de mensajes, cambiar de idioma en tiempo de ejecución, detectar automáticamente el idioma del navegador del usuario y traducir etiquetas de componentes. El asistente se orienta a través de `BundleTranslationResolver` de webforJ 25.12, la preocupación `HasTranslation`, `LocaleObserver`, y resolutores personalizados enchufables, y cubre los caminos de Spring y webforJ plano.

**Cuándo se activa**

- *"Añadir soporte multilingüe con inglés y español."*
- *"Detectar el idioma del navegador del usuario y aplicarlo al iniciar."*
- *"Añadir un selector de idioma a la barra de navegación."*
- *"Mover todas las cadenas codificadas en duro a un paquete de mensajes."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: proteger rutas con inicio de sesión y acceso basado en roles
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier cosa que proteja rutas en una app webforJ: inicio de sesión y cierre de sesión, acceso basado en roles, páginas de aterrizaje públicas, secciones solo para administradores, reglas de propiedad y políticas de seguridad predeterminadas. El asistente prefiere Spring Security cuando Spring Boot está en el classpath y se queda con el marco de seguridad plano de webforJ de lo contrario. Aplica las anotaciones correctas (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) y explica cuáles son terminales versus composables para que la seguridad predeterminada aún cumpla lo que dice.

**Cuándo se activa**

- *"Proteger `/admin` para que solo los usuarios con el rol `ADMIN` puedan verlo."*
- *"Añadir una página de aterrizaje pública que cualquiera pueda visitar."*
- *"Mostrar el nombre del usuario conectado en el encabezado."*
- *"Solo permitir que un usuario edite un registro que posee."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: tematizar aplicaciones con tokens de diseño DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para cualquier trabajo visual en una app webforJ: reasignaciones de paleta, estilo a nivel de componente, diseño y espaciado, tipografía, temas completos, apariencia de la tabla, o colores coordinados para Google Charts. El asistente escribe CSS que se adhiere a los tokens de diseño DWC, delimita selectores correctamente y valida cada referencia `--dwc-*` con respecto al catálogo real para tu versión de webforJ, por lo que el modo oscuro y el cambio de tema siguen funcionando.

**Cuándo se activa**

- *"Tematiza esta app con una paleta azul."*
- *"Estiliza el dwc-button para que coincida con las pautas de marca."*
- *"Hacer que este diseño sea más compacto - ajustar el espaciado y la tipografía."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: actualizar entre versiones principales de webforJ con OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Usa esto para actualizaciones de versiones principales. El asistente ejecuta la receta oficial `webforj-rewrite` de OpenRewrite para la versión objetivo, que incrementa `<webforj.version>` y la versión de Java, reescribe API y tipos renombrados, e inserta comentarios `TODO webforJ <major>:` en cada método eliminado que necesita una decisión manual. Para objetivos más antiguos sin receta publicada (por ejemplo, de 24 a 25), te guía a través de la alternativa manual.

**Cuándo se activa**

- *"Actualizar esta app de webforJ 25 a 26."*
- *"Ejecutar la receta de reescritura y resolver los TODOs."*
- *"Migrar de webforJ 24 a 25 manualmente, ya que no hay receta."*
- *"¿Qué APIs eliminadas necesito arreglar después de actualizar?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
