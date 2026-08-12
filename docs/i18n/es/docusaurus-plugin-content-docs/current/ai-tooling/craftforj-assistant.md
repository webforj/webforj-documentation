---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
El asistente de craftforJ es un agente de codificación que trabaja dentro de tu **aplicación en ejecución**. Escribe Java libremente, compila lo que escribió antes de que lo veas, aplica el cambio y sigue trabajando después de que tu aplicación se reinicie. Se envía con webforJ como parte de [craftforJ](/docs/craftforj), el entorno de desarrollo que te proporciona el árbol de componentes, rutas, propiedades en vivo y tematización de una aplicación mientras se ejecuta.

## Cómo se comparan los dos {#how-the-two-compare}

| | [plugin de IA de webforJ](/docs/ai-tooling) | asistente de craftforJ |
|---|---|---|
| **Vive en** | Tu editor | La aplicación en ejecución |
| **Lee** | Tus archivos fuente | Tu aplicación, en vivo, con sus valores reales |
| **Hace** | Escribe código | Escribe código, inspecciona, cambia, navega y tematiza la aplicación en ejecución |
| **Verifica por** | Tu siguiente construcción | Compilando cada edición antes de que la veas, luego mostrándote el resultado en ejecución |
| **Adecuado para** | Construir algo nuevo desde cero | Entender, corregir, construir y prototipar contra la aplicación que tienes delante |

Los dos son complementarios y pueden transferir trabajo entre sí. Una vez que el trabajo exceda a craftforJ, puedes [transferir una conversación de craftforJ](/docs/craftforj/ai#conversations) a tu editor.

## Lo que puede hacer {#what-it-can-do}

Le das al agente un objetivo en lugar de un comando. Planifica, inspecciona lo que necesita, actúa, verifica el resultado y se corrige a sí mismo a lo largo de muchos pasos en un solo turno.

Escribe Java libremente, por lo que no está limitado a los cambios de propiedades que puedes hacer a mano. Cada edición se prepara en lugar de escribirse en disco, se envía a un compilador de Java real y se corrige por el agente en función de los diagnósticos que regresan, por lo que lo que llega a tu revisión ya compila con tu aplicación en ejecución. Aplicarlo reinicia la aplicación, y el agente retoma su plan una vez que está de vuelta.

Además, accede a todo lo que craftforJ conoce: el árbol de componentes en vivo y los valores de propiedades reales, tu fuente de Java, la tabla de enrutamiento y las reglas de acceso a rutas, el tema y la hoja de estilos, la página en sí para CSS y scripts, capturas de pantalla de un componente, y la base de conocimientos de webforJ y las herramientas de token `--dwc-*` integradas. Consulta [Asistente de IA](/docs/craftforj/ai) para más detalles.

## Configurando un modelo {#configuring-a-model}

craftforJ no envía ningún modelo propio, por lo que eliges el que lo ejecuta. Añade una clave API de uno de los proveedores soportados, o señala a craftforJ a un modelo que se ejecuta localmente con Ollama. Tu clave se almacena en la máquina que ejecuta tu aplicación y se mantiene en el navegador solo mientras la página está abierta, y el asistente se comunica con tu proveedor desde el navegador en lugar de a través de tu servidor. Consulta [Configurando un modelo](/docs/craftforj/ai#configuring-a-model).

:::warning La IA aún puede cometer errores
Trabajar contra la aplicación en ejecución y compilar su propia salida hace que el agente sea considerablemente más preciso que uno que escribe a ciegas. Aún puede estar equivocado. Revisa lo que hizo antes de conservarlo.
:::

## Comenzando {#getting-started}

craftforJ está desactivado hasta que lo enciendas, y se ejecuta solo en desarrollo:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Abre craftforJ con <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> y cambia a la pestaña del Asistente de IA. Para la configuración completa, consulta [Comenzando](/docs/craftforj/getting-started).
