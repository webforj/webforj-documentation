---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 716e267ebccf57080f5238335670d1b7
---
craftforJ incluye un agente de codificación completo que funciona dentro de tu **aplicación en ejecución**. Escribe Java libremente, compila lo que escribió antes de que lo veas, aplica el cambio y continúa trabajando después de que tu aplicación se reinicie. Todo lo que hace, lo hace contra la aplicación que realmente está ejecutándose frente a ti, en lugar de contra una suposición hecha a partir de tu repositorio.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning La IA aún puede cometer errores
Trabajar contra la aplicación en ejecución y compilar su propia salida hace que el agente sea considerablemente más preciso que uno que trabaja a ciegas. Aún puede estar equivocado. Revisa lo que hizo antes de conservarlo.
:::

## Escribe Java {#it-writes-java}

El agente no está limitado a los cambios de propiedad que puedes hacer a mano. Describe un problema y escribe el código para ello, añadiendo métodos, cambiando la lógica y reestructurando una vista según lo requiera la tarea.

Cada edición que escribe es escalonada en lugar de escrita en el disco. Las ediciones escalonadas van directamente a un compilador de Java real, y el agente lee los diagnósticos que regresan y corrige sus propios errores antes de que el cambio se te ofrezca. Lo que llega a tu revisión es código que ya se compila contra tu aplicación en ejecución.

La validación completa necesita un JDK. En un JRE, craftforJ vuelve a analizar el código, marca la edición como no verificada e instruye al agente para que lo indique en lugar de presentarlo como verificado.

Aplicar un cambio reinicia tu aplicación. El agente espera el reinicio, se reconecta y retoma su plan desde donde lo dejó, por lo que una tarea que abarca varias ediciones y reinicios se completa.

## Funciona en pasos {#it-works-in-steps}

Le das al agente un objetivo, no un comando. Planea, inspecciona lo que necesita, actúa, verifica el resultado y se corrige a sí mismo, ejecutando muchos pasos en un solo turno sin que tú dirijas cada uno. Cada paso aparece en la transcripción a medida que ocurre, y puedes expandir cualquiera de ellos para ver exactamente qué llamó el agente y qué regresó.

## A qué puede acceder {#what-it-can-reach}

El agente tiene un amplio conjunto de herramientas que cubren todo lo que craftforJ sabe sobre tu aplicación, incluyendo:

- **Tus componentes** - el árbol en vivo, los valores de propiedad reales y el Java que construyó cada uno. Puede cambiar propiedades, eliminar componentes y resaltar uno en la página.
- **Tu fuente** - leer cualquier archivo bajo la raíz de tu proyecto, escalar ediciones, mostrar diferencias y aplicarlas.
- **Tus rutas** - la tabla de enrutamiento, la ruta activa, navegar a cualquier lugar y cambiar las reglas de acceso declaradas en una ruta.
- **Tu tema y estilos** - leer y establecer tokens de diseño, guardar un tema y buscar las fuentes y los íconos disponibles.
- **La página misma** - inyectar CSS y JavaScript contra la página en vivo y tomar una captura de pantalla de un componente para examinarlo.
- **La base de conocimientos de webforJ** - la misma documentación, superficie de estilo de componente y herramientas de token `--dwc-*` que el [servidor MCP de webforJ](/docs/ai-tooling/mcp) proporciona a tu editor. Está integrado y siempre disponible.

Debido a que accede a todo esto a través de craftforJ, trabaja con la misma información que tú. Lee valores reales, no los que tu fuente implica.

## Aprobaciones {#approvals}

Decides de antemano cuánto puede hacer el agente por su cuenta:

- **Preguntar antes de actuar** - cada acción con un efecto se detiene para tu aprobación.
- **Aplicar ediciones automáticamente** - el agente trabaja libremente pero aún pregunta antes de eliminar algo o ejecutar un script.
- **Ejecutar de forma autónoma** - el agente trabaja sin detenerse.

Cuando el agente pregunta, la solicitud aparece en línea en la transcripción con la acción que quiere realizar, y puedes permitirlo una vez o para el resto de la conversación.

![El asistente preguntando antes de actuar, en línea en la transcripción](/img/craftforj/ai/approval-prompt.png#rounded-border)

Si eres nuevo con el agente, comienza haciéndole preguntar por todo. Una vez que lo hayas visto trabajar, permitir que aplique sus propias ediciones elimina la mayoría de las interrupciones mientras mantiene las decisiones que importan contigo.

## Trabajando con la aplicación en una conversación {#working-with-the-app-in-a-conversation}

El agente lee lo que necesita a medida que lo necesita, en lugar de recibir tu aplicación completa de antemano, y craftforJ te muestra lo que está adjunto a la conversación. Puedes entregarle un componente directamente del árbol o seleccionar uno de la página en medio de una conversación. Para preguntas sobre cómo se ve algo, el agente puede tomar una captura de pantalla de un componente. Esto requiere un modelo que acepte imágenes.

:::warning Las capturas de pantalla incluyen lo que esté en pantalla
Una captura de pantalla lleva cualquier dato que tu aplicación esté mostrando en ese momento. Considera eso antes de señalar un modelo alojado en una aplicación que se ejecuta con datos reales.
:::

## Configurando un modelo {#configuring-a-model}

craftforJ no envía un modelo propio, así que eliges el que lo ejecute. Agrega una clave API para uno de los proveedores soportados, o apunta a craftforJ a un modelo que se ejecute localmente. Tu clave se almacena en la máquina que ejecuta tu aplicación, y el asistente la mantiene en memoria solo mientras la página esté abierta, nunca en el almacenamiento del navegador. Se comunica con el proveedor que elegiste desde el navegador en lugar de a través de tu servidor, y con nadie más.

El selector de modelos muestra qué distingue a un modelo de otro, incluyendo cuánto de tu aplicación y conversación cabe a la vez, cuánto cuesta una conversación y si el modelo acepta imágenes o razona antes de responder. Un modelo que no puede llamar a herramientas puede mantener una conversación, pero no puede inspeccionar ni cambiar nada.

![El selector de modelos mostrando qué distingue a los modelos disponibles](/img/craftforj/ai/model-picker.png#rounded-border)

Ejecutar un modelo localmente mantiene todo en tu máquina. Los modelos locales a menudo tienen un contexto pequeño por defecto, que una conversación sobre una aplicación real llena rápidamente, así que dale al modelo tanto contexto como tu máquina pueda soportar.

## Conversaciones {#conversations}

Las conversaciones se mantienen por aplicación, y el agente puede mirar hacia atrás en conversaciones anteriores cuando una pregunta se refiere a un trabajo que hiciste antes. Cuando una conversación supera el contexto del modelo, craftforJ resume los mensajes más antiguos para que el trabajo continúe en lugar de fallar, y lo anota en el chat.

Cuando el trabajo supera a craftforJ, puedes resumir la conversación y entregársela al asistente de tu editor. Ese asistente recoge el trabajo de manera más precisa con el [plugin de IA de webforJ](/docs/ai-tooling) instalado.

## Apagándolo {#turning-it-off}

La propiedad [`ai.enabled`](/docs/craftforj/configuration#feature-flags) elimina completamente al asistente de craftforJ. La propiedad [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) mantiene al asistente pero detiene su capacidad de escribir Java por su cuenta.
