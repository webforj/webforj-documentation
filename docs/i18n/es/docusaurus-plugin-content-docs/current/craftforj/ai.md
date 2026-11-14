---
title: Asistente de IA
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: e9d943043e52eae5368dfe4ba7795142
---
craftforJ incluye un agente de programación completo que trabaja dentro de tu **aplicación en ejecución**. Escribe Java libremente, compila lo que ha escrito antes de que puedas verlo, aplica el cambio y sigue trabajando después de que se reinicie tu aplicación. Todo lo que hace, lo hace sobre la aplicación que está ejecutándose frente a ti, no sobre una suposición basada en tu repositorio.

<VideoPlayer video="craftforj/ai-conversation" width={1920} height={1000} title="AI conversation" />

:::warning La IA aún puede cometer errores
Trabajar sobre la aplicación en ejecución y compilar su propio código hace que el agente sea considerablemente más preciso que uno que escribe a ciegas. Aun así, puede equivocarse. Revisa lo que hizo antes de conservarlo.
:::

## Escribe Java {#it-writes-java}

El agente no se limita a los cambios de propiedades que puedes hacer manualmente. Describe un problema y escribirá el código para resolverlo, añadiendo métodos, cambiando la lógica y reestructurando una vista según lo requiera la tarea.

Cada edición que escribe se prepara en lugar de guardarse en el disco. Las ediciones preparadas se envían directamente a un compilador Java real; el agente lee los diagnósticos que recibe y corrige sus propios errores antes de que el cambio se te proponga. El código que llega a tu revisión ya compila con tu aplicación en ejecución.

La validación completa requiere un JDK. Con un JRE, craftforJ recurre al análisis sintáctico del código, marca la edición como no verificada e indica al agente que lo comunique en lugar de presentarla como validada.

Al aplicar un cambio, tu aplicación se reinicia. El agente espera a que se reinicie, vuelve a conectarse y retoma el plan donde lo dejó, de modo que una tarea que abarque varias ediciones y reinicios se completa.

## Trabaja por pasos {#it-works-in-steps}

Le das al agente un objetivo, no una instrucción. Planifica, inspecciona lo que necesita, actúa, comprueba el resultado y se corrige; puede ejecutar muchos pasos en un solo turno sin que tengas que guiar cada uno. Cada paso aparece en la transcripción a medida que ocurre, y puedes expandir cualquiera para ver exactamente qué invocó el agente y qué respuesta recibió.

## A qué puede acceder {#what-it-can-reach}

El agente cuenta con un amplio conjunto de herramientas que abarca todo lo que craftforJ sabe sobre tu aplicación, entre otras cosas:

- **Tus componentes**: el árbol en vivo, los valores reales de las propiedades y el código Java que creó cada componente. Puede cambiar propiedades, eliminar componentes y resaltar uno en la página.
- **Tu código fuente**: puede leer cualquier archivo bajo la raíz de tu proyecto, preparar ediciones, mostrar diferencias y aplicarlas.
- **Tus rutas**: puede consultar la tabla de rutas y la ruta activa, navegar a cualquier lugar y cambiar las reglas de acceso declaradas en una ruta.
- **Tu tema y tus estilos**: puede leer y establecer tokens de diseño, guardar un tema y buscar las fuentes e iconos disponibles.
- **La propia página**: puede inyectar CSS y JavaScript en la página en vivo y tomar una captura de pantalla de un componente para examinarlo.
- **La base de conocimientos de webforJ**: incluye la misma documentación, la superficie de estilos de componentes y las herramientas de tokens `--dwc-*` que el [servidor MCP de webforJ](/docs/ai-tooling/mcp) pone a disposición de tu editor. Está integrado y siempre disponible.

Como accede a todo esto a través de craftforJ, trabaja con la misma información que tú. Lee valores reales, no los que sugiere tu código fuente.

## Aprobaciones {#approvals}

Decides de antemano cuánto puede hacer el agente por su cuenta:

- **Preguntar antes de actuar**: cada acción con efectos se detiene para pedir tu aprobación.
- **Aplicar ediciones automáticamente**: el agente trabaja libremente, pero sigue preguntando antes de eliminar algo o ejecutar un script.
- **Ejecutar de forma autónoma**: el agente trabaja sin detenerse.

Cuando el agente te pregunta, la solicitud aparece en línea en la transcripción junto con la acción que quiere realizar, y puedes permitirla una vez o durante el resto de la conversación.

![El asistente pregunta antes de actuar, en línea en la transcripción](/img/craftforj/ai/approval-prompt.png#rounded-border)

Si estás empezando a usar el agente, haz que pregunte por todo. Cuando hayas visto cómo trabaja, permitirle aplicar sus propias ediciones reduce la mayoría de las interrupciones y te permite conservar el control de las decisiones importantes.

## Trabajar con la aplicación en una conversación {#working-with-the-app-in-a-conversation}

El agente lee lo que necesita cuando lo necesita, en lugar de recibir toda tu aplicación de antemano, y craftforJ te muestra lo que se adjunta a la conversación. Puedes pasarle un componente directamente desde el árbol o seleccionar uno de la página en medio de una conversación. Si tienes preguntas sobre el aspecto de algo, el agente puede tomar una captura de pantalla de un componente. Para ello, se necesita un modelo que acepte imágenes.

:::warning Las capturas de pantalla incluyen todo lo que aparece en pantalla
Una captura de pantalla contiene cualquier dato que tu aplicación esté mostrando en ese momento. Tenlo en cuenta antes de usar un modelo alojado con una aplicación conectada a datos reales.
:::

## Configurar un modelo {#configuring-a-model}

craftforJ no incluye ningún modelo propio, así que tú eliges cuál lo ejecuta. Añade una clave de API de uno de los proveedores compatibles o conecta craftforJ a un modelo que se ejecute localmente. Tu clave se almacena en la máquina que ejecuta tu aplicación, y el asistente solo la mantiene en memoria mientras la página está abierta; nunca la guarda en el almacenamiento del navegador. Se comunica desde el navegador con el proveedor que hayas elegido, no a través de tu servidor, y no con ningún otro.

El selector de modelos muestra las diferencias entre los modelos: cuánto de tu aplicación y de la conversación cabe a la vez, cuánto cuesta una conversación y si el modelo acepta imágenes o razona antes de responder. Un modelo que no puede invocar herramientas puede mantener una conversación, pero no puede inspeccionar ni cambiar nada.

![El selector de modelos muestra las diferencias entre los modelos disponibles](/img/craftforj/ai/model-picker.png#rounded-border)

Ejecutar un modelo localmente mantiene todo en tu máquina. Los modelos locales suelen tener una ventana de contexto pequeña de forma predeterminada, que se llena rápidamente en una conversación sobre una aplicación real, así que dale al modelo tanto contexto como pueda soportar tu máquina.

## Conversaciones {#conversations}

Las conversaciones se guardan por aplicación, y el agente puede consultar las anteriores cuando una pregunta haga referencia a un trabajo que hayas realizado antes. Cuando una conversación supera el contexto del modelo, craftforJ resume los mensajes anteriores para que el trabajo continúe en lugar de fallar, e indica en el chat que lo ha hecho.

Cuando el trabajo supera las capacidades de craftforJ, puedes resumir la conversación y pasársela al asistente de tu editor. Ese asistente retomará el trabajo con mayor precisión si está instalado el [complemento de IA de webforJ](/docs/ai-tooling).

## Desactivarlo {#turning-it-off}

La propiedad [`ai.enabled`](/docs/craftforj/configuration#feature-flags) elimina por completo el asistente de craftforJ. La propiedad [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) mantiene el asistente, pero impide que escriba Java por su cuenta.
